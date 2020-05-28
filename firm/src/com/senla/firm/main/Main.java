package com.senla.firm.main;

import com.senla.firm.employee.Employee;
import com.senla.firm.model.Firm;

public class Main {

    public static void main(String[] args) {
        Employee employeeOne = new Employee("Petya", 600);
        Employee employeeTwo = new Employee("Vasya", 700);
        Employee employeeThree = new Employee("Petya", 625);
        Employee employeeFour = new Employee("Petya", 900);
        Employee employeeFive = new Employee("Petya", 700);
        Employee employeeSix = new Employee("Petya", 500);
        Employee employeeSeven = new Employee("Petya", 700);
        Employee employeeEight = new Employee("Petya", 950);
        Employee employeeNine = new Employee("Petya", 456);
        Employee employeeTen = new Employee("Petya", 999);
        Employee employeeEleven = new Employee("Petya", 10000);
        Firm firm = new Firm("Senla", 3);
        firm.addDepartment("department1", 3);
        firm.addDepartment("department2", 4);
        firm.addDepartment("department3", 4);
        firm.addEmployee(employeeOne, "department1");
        firm.addEmployee(employeeTwo, "department1");
        firm.addEmployee(employeeThree, "department1");
        firm.addEmployee(employeeFour, "department2");
        firm.addEmployee(employeeFive, "department2");
        firm.addEmployee(employeeSix, "department2");
        firm.addEmployee(employeeSeven, "department2");
        firm.addEmployee(employeeEight, "department3");
        firm.addEmployee(employeeNine, "department3");
        firm.addEmployee(employeeTen, "department3");
        firm.addEmployee(employeeEleven, "department3");
        System.out.println("The firm " + firm.getName());
        System.out.println("General salary: " + firm.countGeneralSalary());
    }
}
