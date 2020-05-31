package com.senla.firm.department;

import com.senla.firm.employee.Employee;

public class Department {
    private int index;
    private String name;
    private Employee [] employees;

    public Department(String name, int numberEmployee) {
        this.name = name;
        this.index = 0;
        employees = new Employee[numberEmployee];
    }

    public String getName() {
        return name;
    }

    public Employee[] getEmployees() {
        Employee[] arrayEmployees = new Employee[employees.length];
        System.arraycopy(employees, 0, arrayEmployees, 0, employees.length);
        return arrayEmployees;
    }

    public void addEmployee(Employee employee){
        if (this.index < this.employees.length) {
            this.employees[this.index] = employee;
            this.index++;
        }
    }
}
