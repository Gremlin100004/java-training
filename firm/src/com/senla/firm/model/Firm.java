package com.senla.firm.model;

import com.senla.firm.department.Department;
import com.senla.firm.employee.Employee;

public class Firm {
    private String name;
    private int index;
    private int numberDepartment;
    private Department[] departments;

    public Firm(String name, int numberDepartment) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNumberDepartment(int numberDepartment) {
        this.numberDepartment = numberDepartment;
    }

    public void addDepartment(String name, int numberDepartment) {
        Department department = new Department(name, numberDepartment);
        if (this.index < this.numberDepartment) {
            this.departments[this.index] = department;
            this.index++;
        }
    }

    public void addEmployee(Employee employee, String nameDepartment) {
        boolean isDepartmentsEqual = false;
        for (Department department : departments) {
            if (department.getName().equals(nameDepartment)) {
                isDepartmentsEqual = true;
                department.addEmployee(employee);
            }
        }
        if (!isDepartmentsEqual){
            System.out.println(nameDepartment + " there is no such department");
        }

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
