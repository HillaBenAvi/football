package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import com.football.Service.SecurityMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class GuestService {

//    @Autowired
//    private DBController dbController;
    @Autowired
    private SecurityMachine securityMachine;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;

    @Autowired
    private DBController dbController;


    public Member signIn(String userMail, String userName, String password , Date birthDate) throws AlreadyExistException, IncorrectPasswordInputException, IncorrectInputException, DontHavePermissionException {
           if (! checkMailInput(userMail)) {
               errorLogService.addErrorLog("Incorrect Input Exception");
               throw new IncorrectInputException("incorrect mail input");
           }
           if (! checkPasswordValue(password)) {
               errorLogService.addErrorLog(" Incorrect Password Input Exception");
               throw new IncorrectPasswordInputException();
           }
           String encryptPassword = securityMachine.encrypt(password);
           Fan newMember = new Fan(userName, userMail, encryptPassword, birthDate);
        try {
            dbController.addFan(newMember,newMember);
        } catch (AlreadyExistException e) {
            errorLogService.addErrorLog("Already Exist Exception");
            throw new AlreadyExistException();
        }
        eventLogService.addEventLog("Guest", "sign in");
           return newMember;
    }

    /**
     * this function fill the logIn-form : user name , user password
     * @return String array  - details[name,password]
     */
    public Member logIn(String userMail, String userPassword) throws MemberNotExist, PasswordDontMatchException, AlreadyExistException, DontHavePermissionException {
        try{
            Member existingMember;
            existingMember = (Member) dbController.getMember(userMail);
            checkValidationPassword(existingMember, userPassword);
            eventLogService.addEventLog(userMail, "log in");
            return existingMember;
        }
       catch(MemberNotExist e){
            errorLogService.addErrorLog("Member Not Exist");
            throw new MemberNotExist();
       }catch(PasswordDontMatchException | AlreadyExistException e){
            errorLogService.addErrorLog("Password Dont Match Exception");
            throw new PasswordDontMatchException();
        }
    }

    /******************************* function for guest exceptions **********************************/

    /**
     * this function check if an email address is valid using Regex.
     *
     * @param mailInput
     * @return
     */
    public static boolean checkMailInput(String mailInput) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (mailInput == null)
            return false;
        return pat.matcher(mailInput).matches();
    }

    /**
     * this function Use a regular expression (regex) to check for only letters and numbers
     * The regex will check for upper and lower case letters and digits
     *
     * @param password
     * @return
     */
    public static boolean checkPasswordValue(String password) {
        // todo - check input of password - only numbers and letters
        if (!password.matches("[a-zA-Z0-9]+")) {
            /* A non-alphanumeric character was found, return false */
            return false;
        }
        return true;
    }
    private void checkValidationPassword(Member member, String userPassword) throws PasswordDontMatchException {

        if (!member.getPassword().equals(securityMachine.encrypt(userPassword)))
            throw new PasswordDontMatchException();
        return;
    }

}
