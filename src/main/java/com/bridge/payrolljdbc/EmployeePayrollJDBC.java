package com.bridge.payrolljdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class EmployeePayrollJDBC {
    public static void main(String[] args) {

        // Step 1- Loading Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not loaded");
        }

        listDrivers();
        //Step 2- Making Connection
        String JDBCURL = "jdbc:mysql://localhost:3306/payroll_service";
        try {
            DriverManager.getConnection(JDBCURL, "root", "mysql@123");
            System.out.println("Connection Established");
        } catch (SQLException e) {
            System.out.println("Connection not established");
        }
    }

    // To print Drivers
    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driver = (Driver) driverList.nextElement();
            System.out.println("Driver Names : " + driver.getClass().getName());
        }
    }
}

