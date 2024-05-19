package org.example.Manager;

import org.example.DBUtils;
import org.example.Lists.OrdersList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager {
    Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Show a list of orders; \n" +
                "2. Add a new order; \n" +
                "3. Delete an order; \n" +
                "4. Exit;");
    }

    public String getOption() {
        String option = "null";
        System.out.println("Enter the number from the menu: ");
        String getOption = scanner.next();

        switch (getOption) {
            case "1":
                List<OrdersList> ordersList = ManagerCRUDUtils.ordersList("SELECT * FROM ordersList");
                option = ordersList.toString();
                break;
            case "2":
                System.out.println("Adding a new order");
                System.out.print("Order name: ");
                String orderName = scanner.next();
                System.out.print("Customer name: ");
                String customerName = scanner.next();
                System.out.print("Date: ");
                String date = scanner.next();
                System.out.print("Price: ");
                String price = scanner.next();
                System.out.print("Status: ");
                String status = scanner.next();
                boolean orderAdded = ManagerCRUDUtils.addOrder(orderName, customerName, date, price, status);
                if (orderAdded) {
                    System.out.println("Order added successfully.");
                } else {
                    System.out.println("Failed to add order.");
                }
                break;
            case "3":
                System.out.print("Enter order ID to delete: ");
                int orderId = scanner.nextInt();
                try {
                    ManagerCRUDUtils.deleteOrder(orderId);
                    System.out.println("Order deleted successfully.");
                } catch (SQLException e) {
                    System.out.println("Failed to delete order.");
                    e.printStackTrace();
                }
                break;
            case "4":
                option = "The program is over.";
                break;
            default:
                option = "Invalid input";
                break;
        }
        return option;
    }

    public static class ManagerCRUDUtils {

        private static String INSERT_ORDERSLIST = "INSERT INTO ordersList(orderName, customer_name, date, price) VALUES(?, ?, ?, ?);";
        private static String DELETE_ORDERLIST = "DELETE FROM ordersList WHERE id = ?";

        public static List<OrdersList> ordersList(String query) {
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

        public static boolean addOrder(String orderName, String customerName, String date, String price, String status) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDERSLIST)) {
                preparedStatement.setString(1, orderName);
                preparedStatement.setString(2, customerName);
                preparedStatement.setString(3, date);
                preparedStatement.setString(4, price);
                preparedStatement.setString(4, status);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            System.out.println("New order added with ID: " + generatedId);
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

        public static void deleteOrder(int orderNameId) throws SQLException {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDERLIST)) {

                preparedStatement.setInt(1, orderNameId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Order with ID " + orderNameId + " deleted.");
                } else {
                    System.out.println("Order with ID " + orderNameId + " not found.");
                }
            }
        }
    }
}
