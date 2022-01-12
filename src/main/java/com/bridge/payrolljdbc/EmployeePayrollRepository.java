package com.bridge.payrolljdbc;

import java.sql.*;
import java.time.LocalDate;
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

    public List<EmployeeInfo> retrieveFromDate(LocalDate date) throws SQLException {
        List<EmployeeInfo> employeeInfos = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sqlQuery = "select * from employee where startDate between '" + date + "' and Date(now())";
            Statement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
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
        }
        return employeeInfos;
    }

    public void arithmeticMethods() throws SQLException {

        try (Connection connection = getConnection()) {
            String sqlQuery = "select sum(basic_pay),avg(basic_pay),min(basic_pay),max(basic_pay) from employee,payroll where" +
                    " employee.gender='M' and employee.id=payroll.emp_id group by gender";
            Statement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                System.out.print("Total Salary of Male Employee : ");
                System.out.println(resultSet.getInt(1));
                System.out.print("Average Salary of Male Employee : ");
                System.out.println(resultSet.getInt(2));
                System.out.print("Minimum Salary of Male Employee : ");
                System.out.println(resultSet.getInt(3));
                System.out.print("Maximum Salary of Male Employee : ");
                System.out.println(resultSet.getInt(4));

            }
        }
    }
}