package org.example.Accountant;

import org.example.DBUtils;
import org.example.Lists.Employees;
import org.example.Lists.OrdersList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Accountant {
    Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Show a list of employees; \n" +
                "2. Update employee’s salary; \n" +
                "3. Show a list of orders; \n" +
                "4. Update a list of services(price); \n" +
                "5. Exit;");
    }

    public String getOption() {
        String option = "null";
        System.out.println("Enter the number from the menu: ");
        String getOption = scanner.next();

        switch (getOption) {
            case "1":
                List<Employees> employees = AccountantCRUDUtils.getEmployees("SELECT * FROM employees");
                option = employees.toString();
                break;
            case "2":
                try {
                    System.out.print("Enter the ID of the employee whose salary you want to update: ");
                    int employeeId = scanner.nextInt();
                    System.out.print("Enter the new salary: ");
                    String newSalary = scanner.next();
                    boolean updateSuccessful = AccountantCRUDUtils.updateSalary(employeeId, newSalary);
                    if (updateSuccessful) {
                        System.out.println("Salary updated successfully.");
                    } else {
                        System.out.println("Failed to update salary.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. ");
                }
                break;
            case "3":
                List<OrdersList> ordersList = AccountantCRUDUtils.getOrdersList("SELECT * FROM ordersList");
                option = ordersList.toString();
                break;
            case "4":
                try {
                    System.out.print("Enter the ID of the service which price you want to update: ");
                    int serviceId = scanner.nextInt();
                    System.out.print("Enter the new salary: ");
                    String newPrice = scanner.next();
                    boolean updateSuccessful = AccountantCRUDUtils.updateServicesList(serviceId, newPrice);
                    if (updateSuccessful) {
                        System.out.println("Price updated successfully.");
                    } else {
                        System.out.println("Price to update status.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. ");
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

    public static class AccountantCRUDUtils {
        private static String UPDATE_EMPLOYEES = "UPDATE employees SET salary = ? WHERE id = ?";
        private static String UPDATE_SERVICESLIST = "UPDATE servicesList SET price = ? WHERE id = ?";

        public static boolean updateServicesList(int serviceId, String newPrice) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICESLIST)) {
                preparedStatement.setString(1, newPrice);
                preparedStatement.setInt(2, serviceId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                return false;
            }
        }

        public static boolean updateSalary(int employeeId, String newSalary) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEES)) {
                preparedStatement.setString(1, newSalary);
                preparedStatement.setInt(2, employeeId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                return false;
            }
        }

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

        public static List<OrdersList> getOrdersList(String query) {
            List<OrdersList> ordersList = new ArrayList<>();
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String orderName = rs.getString("orderName");
                    String customer_name = rs.getString("customer_name");
                    String date = rs.getString("date");
                    String price = rs.getString("price");
                    String status = rs.getString("status");
                    ordersList.add(new OrdersList(id, orderName, customer_name, date, price, status));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return ordersList;
        }
    }
}
