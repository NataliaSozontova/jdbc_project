package jdbcTests;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String oracleDbUrl = "jdbc:oracle:thin:@ec2-54-174-135-89.compute-1.amazonaws.com:1521:xe";
        String oracleDbUsername = "hr";
        String oracleDbPassword = "hr";

        Connection connection = DriverManager.getConnection(oracleDbUrl,oracleDbUsername,oracleDbPassword);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from regions");

        resultSet.next();//MOVE POINTER TO FIRST ROW

        System.out.println(resultSet.getString("REGION_NAME"));

        //close connections IN OPPOSITE DIRECTIONS THEN YOU OPEN IT
        resultSet.close();
        statement.close();
        connection.close();

    }
}
