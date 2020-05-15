package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.IncorrectInputException;
import com.football.Exception.MemberNotExist;

import java.util.Date;

public class SecondaryReferee extends Referee {

    public SecondaryReferee(String name, String userMail, String password, String training , Date birthdate, DBController dbController) {
        super(name, userMail, password, training , birthdate, dbController);
    }
    public SecondaryReferee(Fan fan, DBController dbcontroller)
    {
        super(fan.getName(),fan.getUserMail(), fan.getPassword() , "" , fan.getBirthDate(),dbcontroller);
    }

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

    public void updateDetails(String newName, String newMail,String newTraining) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (newName == null || newMail ==null ||newTraining == null){
            throw new IncorrectInputException("");
        }
        super.dbController.deleteReferee(this, super.getUserMail());
        if (newName != ""){
            super.setName(newName);
        }
        if (newMail != ""){
            super.setUserMail(newMail);
        }
        if (newTraining != ""){
            super.training = newTraining;
        }
        super.dbController.addReferee(this, this);
    }
    @Override
    public String getType() {
        return "SecondaryReferee";
    }

}
