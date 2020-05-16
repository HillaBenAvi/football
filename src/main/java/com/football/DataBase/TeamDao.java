package com.football.DataBase;//package DataBase;
//
//import Domain.Game.Team;
//
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.List;
//
//public class TeamDao implements DAOTEMP<Team>
//{
//    private static final TeamDao instance = new TeamDao();
//
//    //private constructor to avoid client applications to use constructor
//    public static TeamDao getInstance(){
//        return instance;
//    }
//
//
//    private TeamDao() {
//
//    }
//
//    DBConnector dbc = DBConnector.getInstance();
//
//
//
//    @Override
//    public String getTableName() {
//        return null;
//    }
//
//    @Override
//    public Team get(String id) {
//        return null;//Optional.empty();
//    }
//
//    @Override
//    public List<Team> getAll() {
//        return null;
//    }
//
//    @Override
//    public void save(Team team){
//        try {
//            Connection connection = DBConnector.getConnection();
//            Statement stmt = connection.createStatement();
//
//            String sql = "INSERT INTO"+getTableName()+
//                    "VALUES ();";
//            System.out.println(sql);
//            stmt.executeUpdate(sql);
//        } catch (java.sql.SQLException e) {
//            System.out.println(e.toString());
//        }
//    }
//
//    @Override
//    public void update(Team team, String[] params) {
//
//    }
//
//    @Override
//    public void delete(Team team) {
//
//    }
//
//    @Override
//    public boolean exist(Team team) {
//
//        return false;
//    }
//}
