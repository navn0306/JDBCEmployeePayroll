package com.bridge.payrolljdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollRepository {

    private Connection getConnection() {
        Connection connection = null;
        //Loading Driver and Making Connection
        try {
            String JDBCURL = "jdbc:mysql://localhost:3306/payroll_service";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBCURL, "root", "mysql@123");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Driver not loaded");
        }
        return connection;
    }

    public List<EmployeeInfo> retrieveData() {
        List<EmployeeInfo> employeeInfos = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sqlQuery = "select * from employee";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                EmployeeInfo info = new EmployeeInfo();
                info.setId(resultSet.getInt("id"));
                info.setName(resultSet.getString("name"));
                info.setGender(resultSet.getString("gender"));
                info.setPhone(resultSet.getString("phone"));
                info.setAddress(resultSet.getString("address"));
                info.setStartDate(resultSet.getDate("startDate").toLocalDate());
                employeeInfos.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeInfos;
    }

    public void updateSalary(String name, String gender) {

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sqlQuery = String.format("update employee set gender = '%s' where name = '%s'", gender, name);
            int result = statement.executeUpdate(sqlQuery);
            if (result > 1) {
                System.out.println("Gender Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateSalaryUsingPreparedStatement(String name, String gender) {

        try (Connection connection = getConnection()) {

            String sqlQuery = "update employee set gender = ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, gender);
            preparedStatement.setString(2, name);
            int result = preparedStatement.executeUpdate();
            if (result > 1) {
                System.out.println("Gender Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}