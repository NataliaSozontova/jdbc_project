package jdbcTests;

import org.junit.Test;
import utils.DBUtils;

import java.sql.*;

public class MetaData {

    @Test
    public void metaDataExamples() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement("select * from countries");
            resultSet = preparedStatement.executeQuery();

            DatabaseMetaData dbmd = connection.getMetaData();
            System.out.println("database type: " + dbmd.getDatabaseProductName());
            System.out.println("database version: " + dbmd.getDatabaseProductVersion());
            System.out.println("database user: " + dbmd.getUserName());
            System.out.println("database driver version: " + dbmd.getDriverVersion());

            ResultSetMetaData rsmd = resultSet.getMetaData();
            System.out.println(rsmd.getColumnCount());
            System.out.println(rsmd.getColumnName(1));
            System.out.println(rsmd.getColumnName(2));
            System.out.println(rsmd.getColumnName(3));


        } catch (SQLException e) {
            throw new RuntimeException(); //it will show error and stop the test, but if you print only trace it will not stop you test
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    resultSet.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}