package dataBasePractice;

import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetUpConnection {

    String url = "jdbc:oracle:thin:@ec2-54-174-135-89.compute-1.amazonaws.com:1521:xe";
    String user = "hr";
    String password = "hr";

    @Test
    public void setUpConnection() throws Exception {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select last_name, salary from employees");


        while (resultSet.next()) {
            System.out.print(resultSet.getString(1) + ",");
//            System.out.println(resultSet.getInt(2));
//            System.out.println("*********************");
//            //or by column name
//            System.out.println(resultSet.getString("last_name"));
//            System.out.println(resultSet.getInt("salary"));
        }

        int rowNum = resultSet.getRow();
        System.out.println("rowNum = " + rowNum);
        resultSet.beforeFirst();
        System.out.println("resultSet.getRow() = " + resultSet.getRow());
        //put result into List and do assertions

        List<String> listOfLastNames = new ArrayList<>();
        while (resultSet.next()) {
            listOfLastNames.add(resultSet.getString("last_name"));
        }

        System.out.println("listOfLastNames = " + listOfLastNames);

        List<String> expectedResults = Arrays.asList("King", "Kochhar", "De Haan", "Hunold");

        Assert.assertEquals(listOfLastNames.get(0), expectedResults.get(0));

    }

    @Test
    public void scrollRows() throws Exception {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from regions");
        resultSet.next();
        Boolean first = resultSet.first();
        System.out.println("first " + first);
        Boolean last = resultSet.last();
        System.out.println("last " + last);
        System.out.println(resultSet.getRow());

    }

    @Test
    public void DBTest() throws SQLException {

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from regions");

        while(resultSet.next()){


        }

    }
}
