package com.senla.firm.model;

import com.senla.firm.department.Department;
import com.senla.firm.employee.Employee;

public class Company {
    private String name;
    private int index;
    private int numberDepartment;
    private Department[] departments;
    private int generalSalary;

    public Company(String name, int numberDepartment) {
        this.name = name;
        this.numberDepartment = numberDepartment;
        this.index = 0;
        departments = new Department[this.numberDepartment];
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getNumberDepartment() {
        return numberDepartment;
    }

    public Department[] getDepartments() {
        return departments;
    }

    public int getGeneralSalary() {
        return generalSalary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNumberDepartment(int numberDepartment) {
        this.numberDepartment = numberDepartment;
    }

    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

    public void setGeneralSalary(int generalSalary) {
        this.generalSalary = generalSalary;
    }

    public void addDepartment(String name, int numberDepartment) {
        Department department = new Department(name, numberDepartment);
        if (this.index < this.numberDepartment) {
            this.departments[this.index] = department;
            this.index += 1;
        }
    }

    public void addEmploee(Employee employee, String nameDepartment) {
        for (Department department : departments) {
            if (department.getName().equals(nameDepartment)) {
                department.addEmployee(employee);
            }
        }
    }

    public void countGeneralSelery() {
        for (Department value : this.departments) {
            Employee[] employees = value.getEmployees();
            for (Employee item : employees) {
                this.generalSalary += item.getSalary();
            }
        }
    }
}