package com.szforums.dao;
import com.szforums.bean.Answer;
import com.szforums.bean.Query;
import com.szforums.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static Connection getCon(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/szforums","root","");
            System.out.println("Connection success");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static Connection getConTester(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/szforums","root","");
            System.out.println("Connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static int sendData(User u){
        int status = 0;
        try {
            Connection con = getCon();
            PreparedStatement sendDataQuery = con.prepareStatement("insert into szforumsusers(username,name,lastname,email,occupation,password) values (?,?,?,?,?,?)");
            sendDataQuery.setString(1,u.getUsername());
            sendDataQuery.setString(2,u.getName());
            sendDataQuery.setString(3,u.getLastname());
            sendDataQuery.setString(4,u.getEmail());
            sendDataQuery.setString(5,u.getOccupation());
            sendDataQuery.setString(6,u.getPassword());
            status = sendDataQuery.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String[] getUserRecord(String s1 , String s2){
        String [] list = new String[2];

        try{
            Connection con = getCon();
            PreparedStatement getUserQuery = con.prepareStatement("select * from szforumsusers where username = ? and password = ?");
            getUserQuery.setString(1,s1);
            getUserQuery.setString(2,s2);
            ResultSet rs = getUserQuery.executeQuery();
            while (rs.next()){
                User u = new User();
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(7));
                list[0] = u.getUsername();
                list[1] = u.getPassword();
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Query> getAllQueriesRecords(){
        List<Query> queries = new ArrayList<Query>();
        try{
            Connection con = getCon();
            PreparedStatement getQueries = con.prepareStatement("select * from queries");
            ResultSet rs = getQueries.executeQuery();
            while (rs.next()){
                Query q = new Query();
                q.setQuery_name(rs.getString(1));
                q.setQuery_description(rs.getString(2));
                q.setQuery_user(rs.getString(3));
                queries.add(q);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queries;
    }

    public static int sendQueryData(Query q){
        int status = 0;
        try{
            Connection con = getCon();
            PreparedStatement sendQueryDataQuery = con.prepareStatement("insert into queries(query_name,query_description,query_user) values (?,?,?)");
            sendQueryDataQuery.setString(1,q.getQuery_name());
            sendQueryDataQuery.setString(2,q.getQuery_description());
            sendQueryDataQuery.setString(3,q.getQuery_user());
            status = sendQueryDataQuery.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int sendAnsData(Answer a){
        int status = 0;
        try{
            Connection con = getCon();
            PreparedStatement sendAnsDataQuery = con.prepareStatement("insert into answers(ans_content,ans_query_name) values (?,?)");
            sendAnsDataQuery.setString(1,a.getAns_content());
            sendAnsDataQuery.setString(2,a.getAns_query_name());
            status = sendAnsDataQuery.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static List<Answer> getAnswers(String query_name){
        List<Answer> answers = new ArrayList<Answer>();
        try{
            Connection con = getCon();
            PreparedStatement getAnswers = con.prepareStatement("select ans_content,ans_query_name from answers where ans_query_name = ?");
            getAnswers.setString(1,query_name);
            ResultSet rs = getAnswers.executeQuery();
            while (rs.next()){
                Answer a = new Answer();
                a.setAns_content(rs.getString(1));
                a.setAns_query_name(rs.getString(2));
                System.out.println(a.getAns_content());
                answers.add(a);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
}
