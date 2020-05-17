package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondaryRefereeService {

    @Autowired
    private DBController dbController;

    /**
     * the function allows the referee to update his own details.
     * @param newName
     * @param newMail
     * @param newTraining
     * @throws IncorrectInputException - in case of illegal input
     * @throws MemberNotExist
     * @throws DontHavePermissionException
     * @throws AlreadyExistException
     */

    public void updateDetails(String id, String newName, String newMail,String newTraining) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException, MemberAlreadyExistException {

        if (newName == null || newMail ==null ||newTraining == null){
            throw new IncorrectInputException("");
        }
        if (dbController.existReferee(id)){
            Referee referee = dbController.getReferee(id);
            dbController.deleteReferee(referee, id);
            if (newName != ""){
                referee.setName(newName);
            }
            if (newMail != ""){
                if (!dbController.existMember(newMail)){
                    referee.setUserMail(newMail);
                }
                else{
                    throw new MemberAlreadyExistException();
                }
            }
            if (newTraining != ""){
                referee.training = newTraining;
            }
            dbController.addReferee(referee, referee);
        }
    }


}
