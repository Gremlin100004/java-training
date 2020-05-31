package com.senla.firm.main;

import com.senla.firm.employee.Employee;
import com.senla.firm.model.Firm;

public class Main {

    public static void main(String[] args) {
        Employee[] arrayEmployee = new Employee[] {
                new Employee("Petya", 600),
                new Employee("Vasya", 700),
                new Employee("Georgiy", 625),
                new Employee("Antonina", 900),
                new Employee("Igor", 700),
                new Employee("Dima", 500),
                new Employee("Aleksandr", 700),
                new Employee("Jon", 950),
                new Employee("Kostya", 456),
                new Employee("Andrey", 999),
                new Employee("Pasha", 10000)
        };
        String [] arrayNameDepartments = new String[] {"department1", "department2", "department3"};
        int [] arrayNumberEmployeeDepartment = new int[] {3, 4, 4};
        Firm firm = new Firm("Senla", arrayNameDepartments.length);
        int index = 0;
        for (int i = 0; i < arrayNameDepartments.length; i++){
            firm.addDepartment(arrayNameDepartments[i], arrayNumberEmployeeDepartment[i]);
            for (int j = 0; j < arrayNumberEmployeeDepartment[i]; j++){
                firm.addEmployee(arrayEmployee[index], arrayNameDepartments[i]);
                index++;
            }
        }
        System.out.println("The firm " + firm.getName());
        System.out.println("General salary: " + firm.countGeneralSalary());
    }
}
