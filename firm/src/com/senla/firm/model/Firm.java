package com.senla.firm.model;

import com.senla.firm.department.Department;
import com.senla.firm.employee.Employee;

public class Firm {
    private String name;
    private int index;
    private Department[] departments;

    public Firm(String name, int numberDepartment) {
        this.name = name;
        this.index = 0;
        departments = new Department[numberDepartment];
    }

    public String getName() {
        return name;
    }

    public void addDepartment(String name, int numberDepartment) {
        Department department = new Department(name, numberDepartment);
        if (this.index < this.departments.length) {
            this.departments[this.index] = department;
            this.index++;
        }
    }

    public void addEmployee(Employee employee, String nameDepartment) {
        for (Department department : departments) {
            if (department.getName().equals(nameDepartment)) {
                department.addEmployee(employee);
                return;
            }
        }
        System.out.println(nameDepartment + " does not exist");
    }

    public int countGeneralSalary() {
        int generalSalary = 0;
        for (Department value : this.departments) {
            Employee[] employees = value.getEmployees();
            for (Employee item : employees) {
                if (item != null) {
                    generalSalary += item.getSalary();
                }
            }
        }
        return generalSalary;
    }
}
