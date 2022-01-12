package com.bridge.payrolljdbc;

import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeePayrollService {


    EmployeePayrollRepository repository = new EmployeePayrollRepository();

    public static void main(String[] args) throws SQLException {
        EmployeePayrollService service = new EmployeePayrollService();
     //   service.retriveData();
      //  service.updateSalary("Terisa","male");
        service.retrieveFromDate(LocalDate.parse("2015-02-02"));
        service.arithmeticMethods();
    }

    private void updateSalary(String name, String gender) {
        repository.updateSalaryUsingPreparedStatement(name,gender);
    }

    private void retriveData() {
        System.out.println(repository.retrieveData());
    }

    private void retrieveFromDate(LocalDate date) throws SQLException {
        System.out.println(repository.retrieveFromDate(date));
    }

    private void arithmeticMethods() throws SQLException {
        repository.arithmeticMethods();
    }
}
