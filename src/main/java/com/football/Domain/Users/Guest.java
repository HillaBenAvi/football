package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Exception.*;
//import com.football.Service.SecurityMachine;

import java.util.Date;
import java.util.regex.Pattern;

public class Guest extends Role{
    private DBController dbController;
//    private SecurityMachine securityMachine;
    public Guest(DBController dbC, Date birthDate) {
        super("guest", birthDate);
        this.dbController = dbC;
//        this.securityMachine = new SecurityMachine();
    }

    /**
     * This function fill the signIn-form with user mail, user name and user password
     * @return String array - details[mail,name,password]
     */
    public Member signIn(String userMail, String userName, String password ,  Date birthDate) throws AlreadyExistException, IncorrectPasswordInputException, IncorrectInputException, DontHavePermissionException {
        if (! checkMailInput(userMail)) {
            throw new IncorrectInputException("incorrect mail input");
        }
        if (! checkPasswordValue(password)) {
            throw new IncorrectPasswordInputException();
        }
        String encryptPassword = "";//securityMachine.encrypt(password);
        Fan newMember = new Fan(userName, userMail, encryptPassword, birthDate, dbController );
        dbController.addFan(this,newMember);
        return newMember;
    }


    /**
     * this function fill the logIn-form : user name , user password
     * @return String array  - details[name,password]
     */
    public Member logIn(String userMail, String userPassword) throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException {
        Member existingMember;
        existingMember = (Member) dbController.getMember( userMail);
        checkValidationPassword(existingMember, userPassword);
        return existingMember;
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

//        if (!member.getPassword().equals(securityMachine.encrypt(userPassword)))
//            throw new PasswordDontMatchException();
        return;
    }
}
