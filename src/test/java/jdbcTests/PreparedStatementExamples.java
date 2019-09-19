package jdbcTests;

import org.junit.Assert;
import org.junit.Test;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PreparedStatementExamples {


    @Test
    public void preparedStatementExample() {
        List<String> countries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement("select * from countries");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countries.add(resultSet.getString("country_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(resultSet!=null && !resultSet.isClosed()){
                    resultSet.close();
                }
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    resultSet.close();
                }
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();

            }

            Assert.assertEquals(25,countries.size());
            Assert.assertTrue(countries.contains("Zimbabwe"));
            System.out.println(countries.toString());

        }

    }
    @Test
    public void preparedStatementWithIndex() {
        List<String> countries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement("select * from countries where region_id=?");
            preparedStatement.setInt(1, 2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countries.add(resultSet.getString("country_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            List<String> expectedCountries = Arrays.asList("Argentina", "Brazil", "Canada", "Mexico", "United States of America");
            Assert.assertEquals(5, countries.size());
            Assert.assertTrue(countries.contains("Canada"));
            System.out.println(countries.toString());
            Assert.assertEquals(expectedCountries, countries);
            System.out.println(countries.toString());

        }
    }
        @Test
        public void resultSetToMap(){
            Map<String,String> countries = new LinkedHashMap<>(); //keeps order
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String sql = "select * from countries where region_id in(?,?)";

            try {
                connection = DBUtils.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setInt(1,2);
                preparedStatement.setInt(2,4);

                resultSet=preparedStatement.executeQuery();

                //print how many rows records returns
                resultSet.last();
                int rowCount = resultSet.getRow();
                System.out.println("rowCount = " + rowCount);

                resultSet.beforeFirst();

                while (resultSet.next()) {
                    countries.put(resultSet.getString("country_id"),
                                  resultSet.getString("country_name"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
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

                Assert.assertEquals("Canada", countries.get("CA"));

                //print all values of map
                System.out.println(countries.toString());

                for (String cID:countries.keySet()) {
                    System.out.println(cID + "|" + countries.get(cID));
                }
        }


    }
}
