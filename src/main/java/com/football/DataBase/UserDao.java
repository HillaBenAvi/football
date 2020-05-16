package com.football.DataBase;//package DataBase;
//
//import Domain.Users.Member;
//import java.sql.SQLException;
//import java.util.List;
//
//public class UserDao  implements DAOTEMP<Member> {
//
//    private static final UserDao instance = new UserDao();
//
//    //private constructor to avoid client applications to use constructor
//    public static UserDao getInstance(){
//        return instance;
//    }
//
//    @Override
//    public String getTableName() {
//        return null;
//    }
//
//    private UserDao() {
//
//    }
//    DBConnector dbc = DBConnector.getInstance();
//
//
//
//
//    @Override
//    public Member get(String id) {
//        return null;
//    }
//
//    @Override
//    public List<Member> getAll() {
//        return null;
//    }
//
//    @Override
//    public void save(Member member) throws SQLException {
//
//    }
//
//    @Override
//    public void update(Member member, String[] params) {
//
//    }
//
//    @Override
//    public void delete(Member member) {
//
//    }
//
//    @Override
//    public boolean exist(Member member) {
//
//        return false;
//    }
//}
