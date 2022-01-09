package com.bridge.payrolljdbc;

public class EmployeePayrollService {


    EmployeePayrollRepository repository = new EmployeePayrollRepository();

    public static void main(String[] args) {
        EmployeePayrollService service = new EmployeePayrollService();
     //   service.retriveData();
        service.updateSalary("Terisa","Female");
    }

    private void updateSalary(String name, String gender) {
        repository.updateSalary(name,gender);
    }

    private void retriveData() {
        System.out.println(repository.retrieveData());
    }
}
