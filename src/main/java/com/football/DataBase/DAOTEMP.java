package com.football.DataBase;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface DAOTEMP<T> {
        /**
         * this interface only for 1 table
         * @return
         */

        //DAOTEMP<T> getInstance();

        String getTableName();

        String get(String id);

        List<String> getAll();

        void save(T t);

        void update(String id , T t); // the id of the one you want to update and a new object

        void delete(String id);

        boolean exist(String id);



}