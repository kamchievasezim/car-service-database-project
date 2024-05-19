package org.example.Lists;

public class Employees extends Person {
    private String salary;
    private String position;

    public Employees(int id, String name, String salary, String position) {
        super(id, name);
        this.salary = salary;
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", salary='" + salary + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
