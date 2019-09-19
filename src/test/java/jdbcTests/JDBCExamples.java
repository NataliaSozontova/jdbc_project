package jdbcTests;

import org.junit.Test;

import java.sql.*;

public class JDBCExamples {

    //public final String ORACLE_DB_URL = "jdbc:oracle:thin:@ec2-18-212-67-90.compute-1.amazonaws.com:1521:xe";
    public final String ORACLE_DB_URL = "jdbc:oracle:thin:@ec2-54-174-135-89.compute-1.amazonaws.com:1521:xe";
  //
    public final String ORACLE_DB_USERNAME="hr";
    public final String ORACLE_DB_PASSWORD="hr";

    @Test
    public void readRegionNames() throws SQLException {
        Connection connection = DriverManager.getConnection(ORACLE_DB_URL,ORACLE_DB_USERNAME,ORACLE_DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select REGION_NAME from regions");
        //loop through region_name column in resultSet

        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
//          System.out.println(resultSet.getString("region_name"));

            resultSet=statement.executeQuery("select region_id, region_name from regions");
            while (resultSet.next()){
                int rID = resultSet.getInt(1);
                String rName = resultSet.getString(2);
                System.out.println(rID+"|"+rName);
            }
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void countAndNavigations() throws SQLException {
        Connection connection = DriverManager.getConnection(ORACLE_DB_URL,ORACLE_DB_USERNAME,ORACLE_DB_PASSWORD);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //find out how many records in the resultSet
        //go to last record last()
        resultSet.last();
        int rowCount = resultSet.getRow();
        System.out.println("rowCount = " + rowCount);
        System.out.println("last department name is "+resultSet.getString("department_name"));

        //go to first row
        resultSet.first();
        System.out.println("first department name is "+resultSet.getString("department_name"));

        resultSet.beforeFirst();// in order to go on top of first row
        while(resultSet.next()){
            int depID = resultSet.getInt("department_id");
            String depName = resultSet.getString("department_name");
            System.out.println(depID+"|"+depName);
        }

        System.out.println("Print department in reverse order");

        while (resultSet.previous()){
            int depID = resultSet.getInt("department_id");
            String depName = resultSet.getString("department_name");

            System.out.println(depID+"|"+depName);
        }

        //go to specific row number in resultSet
        resultSet.absolute(5);
        System.out.println("Fifth department name: "+ resultSet.getString("department_name"));

    }


}
