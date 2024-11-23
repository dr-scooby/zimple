package com.jah.Zimple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DBModel {

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USER;

    @Value("${spring.datasource.password}")
    private String DB_PASS;

    private String table_name = "alien"; // name of table

    // Testing only for now
    private String dburl = "jdbc:mysql://localhost:3306/";
    private String user = "nurali";
    private String pass = "java1973";
    private String dbname = "skyhawk";

    private Connection conn; // connection to DB


    public DBModel(){

        //connect();

    }

    public void connect(){
        System.out.println("\nconnect called...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // new mysql driver version
            //Class.forName("com.mysql.jdbc.Driver"); // old mysql driver version
            dburl+=dbname;
            conn = DriverManager.getConnection(dburl, user, pass);

        }catch(ClassNotFoundException e) {
            System.err.println(e);
        } catch (SQLException e) {
            System.err.println(e);;
        }
    }


    public void connecttoKube(){
        System.out.println("\nconnect to kubernetes called...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // new mysql driver version
            //Class.forName("com.mysql.jdbc.Driver"); // old mysql driver version

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        }catch(ClassNotFoundException e) {
            System.err.println(e);
        } catch (SQLException e) {
            System.err.println(e);;
        }
    }

    private void creatTable(){
        connecttoKube(); // connect
        String sql_create = "create table alien(aid INTEGER, aname varchar(40) )";
        if(conn != null){
            try {
                System.out.println("creating table aliens");
                Statement state = conn.createStatement();
                state.executeUpdate(sql_create) ;
                System.out.println("Create Table success :> " + sql_create);
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String selectTable(){
        System.out.println("checking if table alien exists...");
        //connecttoKube(); // connect
        // check if table exists
        try {
            System.out.println("checking if table alien exists...");
            if(tableExists("alien")){
                System.out.println("table alien exist");
                //creatTable();
            }else{
                System.out.println("table alien NOT exist");
                creatTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from alien";
        String data = "";

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("aid");
                String aname = rs.getString("aname");

                System.out.println(id + " " + aname + " " );
                data += id + " " + aname + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public void select(){
        String sql = "select * from product";

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String proname = rs.getString("name");
                String type = rs.getString("type");
                String place = rs.getString("place");
                System.out.println(proname + " " + type + " " + place);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    // check if Table exists
    /**
     *
     * @param tableName
     * @return boolean
     * @throws SQLException
     */
    public boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet resultSet = null;
        String rsTableName = null;

        try {
            resultSet = databaseMetaData.getTables(conn.getCatalog(), "%", "%", null);
            while (resultSet.next()) {
                rsTableName = resultSet.getString("TABLE_NAME");
                if (rsTableName.equalsIgnoreCase(tableName)) {
                    return true;
                }
            }
        } finally {
            resultSet.close();
        }

        return false;
    }
}
