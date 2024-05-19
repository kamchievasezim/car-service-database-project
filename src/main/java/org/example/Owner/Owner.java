package org.example.Owner;

import org.example.DBUtils;
import org.example.Lists.Employees;
import org.example.Lists.ServicesList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Owner {
    Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Show a list of employees; \n" +
                "2. Show a list of services; \n" +
                "3. Hire employee; \n" +
                "4. Fire employee; \n" +
                "5. Exit;");
    }

    public String getOption() {
        String option = "null";
        System.out.println("Enter the number from the menu: ");
        String getOption = scanner.next();

        switch (getOption) {
            case "1":
                List<Employees> employees = OwnerCRUDUtils.getEmployees("SELECT * FROM employees");
                option = employees.toString();
                break;
            case "2":
                List<ServicesList> servicesList = OwnerCRUDUtils.getServicesList("SELECT * FROM servicesList");
                option = servicesList.toString();
                break;
            case "3":
                System.out.println("Adding a new employee");
                System.out.print("Name: ");
                String name = scanner.next();
                System.out.print("Salary: ");
                String salary = scanner.next();
                System.out.print("Position: ");
                String position = scanner.next();
                OwnerCRUDUtils.addEmployee(name, salary, position);
                option = "Employee added successfully.";
                break;
            case "4":
                System.out.print("Enter employee ID to fire: ");
                int employeeId = Integer.parseInt(scanner.next());

                try {
                    OwnerCRUDUtils.deleteEmployee(employeeId);
                    option = "Employee deleted successfully.";
                } catch (SQLException e) {
                    option = "Failed to delete employee.";
                    e.printStackTrace();
                }
                break;
            case "5":
                option = "The program is over.";
                break;
            default:
                option = "Invalid input";
                break;
        }
        return option;
    }

    public static class OwnerCRUDUtils {
        private static String INSERT_EMPLOYEES = "INSERT INTO employees(name, salary, position) VALUES(?, ?, ?);";
        private static String DELETE_EMPLOYEES = "DELETE FROM employees WHERE id = ?";

        public static List<Employees> getEmployees(String query) {
            List<Employees> employees = new ArrayList<>();
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String salary = rs.getString("salary");
                    String position = rs.getString("position");
                    employees.add(new Employees(id, name, salary, position));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return employees;
        }

        public static List<ServicesList> getServicesList(String query) {
            List<ServicesList> servicesList = new ArrayList<>();

            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String service = rs.getString("service");
                    String price = rs.getString("price");
                    servicesList.add(new ServicesList(id, service, price));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return servicesList;
        }

        public static boolean addEmployee(String name, String salary, String position) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEES)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, salary);
                preparedStatement.setString(3, position);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            System.out.println("New employee added with ID: " + generatedId);
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }

        public static void deleteEmployee(int employeeId) throws SQLException {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEES)) {

                preparedStatement.setInt(1, employeeId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee with ID " + employeeId + " deleted.");
                } else {
                    System.out.println("Employee with ID " + employeeId + " not found.");
                }
            }
        }
    }
}
