/**
 * 
 */
package com.ss.sftraining.assignment.week.one;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @author kareemelsayed
 * To fix the SampleSingleton class provided simply take out static
 */

public class SampleSingleton {
    //make constructor private
    private SampleSingleton(){};

    private static Connection conn = null;

    private static SampleSingleton instance = null;

    public static SampleSingleton getInstance() {
        return instance;
    }

    public static void databaseQuery(BigDecimal input) {
        conn = DriverManager.getConnection("url of database");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select id from table");
        int x = 0;
        while(rs.next()) {
            x = rs.getInt(1) * input;
        }
    }
}
