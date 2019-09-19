package jdbcTests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;
import utils.DBType;
import utils.DBUtils;
import static org.junit.Assert.*;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDBCwithUtils {

    @BeforeClass
    public static void setUp(){
        //connect to database using utility
        DBUtils.establishConnection(DBType.ORACLE);
    }

    @Test
    public void getEmployeesCount(){
        int employeesCount = DBUtils.getRowsCount("select * from employees");
        System.out.println("employeesCount = " + employeesCount);
        int expectedCount = 107;
        assertEquals(expectedCount,employeesCount);
    }

    @Test
    public void getEmployees(){
        String sql = "select first_name, last_name,job_id,salary " +
                "from employees " +
                "where salary between 5000 and 6000";

        int empCount = DBUtils.getRowsCount(sql);
        System.out.println("empCount = " + empCount);
        List<Map<String,Object>> empDBData = DBUtils.runSQLQuery(sql);
        System.out.println(empDBData.toString());

        Map<String, Object> firstEmployee = empDBData.get(0);
        System.out.println("firstEmployee = " + firstEmployee);
        System.out.println("first name: "+ firstEmployee.get("FIRST_NAME"));
        System.out.println("LAST name: "+ firstEmployee.get("LAST_NAME"));
        System.out.println("JOB ID: "+ firstEmployee.get("JOB_ID"));
    }

    @Test
    public void regionsPOJOTest() throws Exception{
        List<Regions> regions = new ArrayList<>();
        Connection connection = DriverManager.getConnection
                (ConfigurationReader.getProperty("oracledb.url"),
                ConfigurationReader.getProperty("oracledb.user"),
                ConfigurationReader.getProperty("oracledb.password"));

        PreparedStatement preparedStatement = connection.prepareStatement("select * from regions",
                ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int regionID = resultSet.getInt("REGION_ID");
            String regionName = resultSet.getString("REGION_NAME");
            regions.add(new Regions(regionName,regionID));
        }

        System.out.println(regions.toString());

        resultSet.close();
        preparedStatement.clearParameters();
        connection.close();

    }

    @AfterClass
    public static void tearDown(){
        DBUtils.closeConnections();
    }
}
