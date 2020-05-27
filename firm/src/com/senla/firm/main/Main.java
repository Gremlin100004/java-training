package com.senla.firm.main;

import com.senla.firm.employee.Employee;
import com.senla.firm.model.Firm;

public class Main {

    public static void main(String[] args) {
        Employee employee1 = new Employee("Petya", 600);
        Employee employee2 = new Employee("Vasya", 700);
        Employee employee3 = new Employee("Petya", 625);
        Employee employee4 = new Employee("Petya", 900);
        Employee employee5 = new Employee("Petya", 700);
        Employee employee6 = new Employee("Petya", 500);
        Employee employee7 = new Employee("Petya", 700);
        Employee employee8 = new Employee("Petya", 950);
        Employee employee9 = new Employee("Petya", 456);
        Employee employee10 = new Employee("Petya", 999);
        Employee employee11 = new Employee("Petya", 10000);
        Firm firm = new Firm("Senla", 2);
        firm.addDepartment("department1", 3);
        firm.addDepartment("department2", 4);
        firm.addDepartment("department3", 4);
        firm.addEmploee(employee1, "department1");
        firm.addEmploee(employee2, "department1");
        firm.addEmploee(employee3, "department1");
        firm.addEmploee(employee4, "department2");
        firm.addEmploee(employee5, "department2");
        firm.addEmploee(employee6, "department2");
        firm.addEmploee(employee7, "department2");
        firm.addEmploee(employee8, "department3");
        firm.addEmploee(employee9, "department3");
        firm.addEmploee(employee10, "department3");
        firm.addEmploee(employee11, "department3");
        firm.countGeneralSelery();
        System.out.println("The firm " + firm.getName());
        System.out.println("General salary: " + firm.getGeneralSalary());
    }
}