package com.bridge.payrolljdbc;

public class EmployeePayrollService {


    EmployeePayrollRepository repository = new EmployeePayrollRepository();

    public static void main(String[] args) {
        EmployeePayrollService service = new EmployeePayrollService();
        service.retriveData();
    }

    private void retriveData() {
        System.out.println(repository.retrieveData());
    }
}
