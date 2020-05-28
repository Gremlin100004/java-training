package com.senla.firm.department;

import com.senla.firm.employee.Employee;

public class Department {
    private int index;
    private int numberEmployee;
    private String name;
    private Employee [] employees;

    public Department(String name, int numberEmployee) {
        this.name = name;
        this.numberEmployee = numberEmployee;
        this.index = 0;
        employees = new Employee[this.numberEmployee];
    }

    public int getIndex() {
        return index;
    }

    public int getNumberEmployee() {
        return numberEmployee;
    }

    public String getName() {
        return name;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNumberEmployee(int numberEmployee) {
        this.numberEmployee = numberEmployee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee){
        if (this.index < this.numberEmployee) {
            this.employees[this.index] = employee;
            this.index++;
        }
    }
}
