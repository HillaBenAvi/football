package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondaryRefereeService {

    @Autowired
    private DBController dbController;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;
    /**
     * the function allows the referee to update his own details.
     *
     * @param newName
     * @param newMail
     * @param newTraining
     * @throws IncorrectInputException     - in case of illegal input
     * @throws MemberNotExist
     * @throws DontHavePermissionException
     * @throws AlreadyExistException
     */

    public void updateDetails(String id, String newName, String newMail, String newTraining) throws DontHavePermissionException, AlreadyExistException{
        try {
            if (newName == null || newMail == null || newTraining == null) {
                throw new IncorrectInputException("");
            }
            if (dbController.existReferee(id)) {
                Referee referee = dbController.getReferee(id);
                dbController.deleteReferee(referee, id);
                eventLogService.addEventLog(id,"updateDetails");
                if (newName != "") {
                    referee.setName(newName);
                }
                if (newMail != "") {
                    if (!dbController.existMember(newMail)) {
                        referee.setUserMail(newMail);
                    } else {
                        throw new MemberAlreadyExistException();
                    }
                }
                if (newTraining != "") {
                    referee.training = newTraining;
                }
                dbController.addReferee(referee, referee);
            }
        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }catch (MemberAlreadyExistException memberAlreadyExistException) {
            errorLogService.addErrorLog("Member Already Exist Exception");
        }

    }


}
