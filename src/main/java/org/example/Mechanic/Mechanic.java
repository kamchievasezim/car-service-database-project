package org.example.Mechanic;

import org.example.DBUtils;
import org.example.Lists.OrdersList;
import org.example.Lists.InventoryList;
import org.example.Lists.ServicesList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Mechanic {
    Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Show a list of orders; \n" +
                "2. Show a list of inventories; \n" +
                "3. Show a list of services; \n" +
                "4. Update status of order; \n" +
                "5. Exit;");
    }

    public String getOption() {
        String option = "null";
        System.out.println("Enter the number from the menu: ");
        String getOption = scanner.next();

        switch (getOption) {
            case "1":
                List<OrdersList> ordersList = MechanicCRUDUtils.ordersList("SELECT * FROM ordersList");
                option = ordersList.toString();
                break;
            case "2":
                List<InventoryList> inventoryList = MechanicCRUDUtils.inventoryList("SELECT * FROM inventoryList");
                option = inventoryList.toString();
                break;
            case "3":
                List<ServicesList> servicesList = MechanicCRUDUtils.servicesList("SELECT * FROM servicesList");
                option = servicesList.toString();
                break;
            case "4":
                try {
                    System.out.print("Enter the ID of the order whose status you want to update: ");
                    int orderId = scanner.nextInt();
                    System.out.print("Enter the new status: ");
                    String newStatus = scanner.next();
                    boolean updateSuccessful = MechanicCRUDUtils.updateOrdersList(orderId, newStatus);
                    if (updateSuccessful) {
                        option = "Status updated successfully.";
                    } else {
                        option = "Failed to update status.";
                    }
                } catch (InputMismatchException e) {
                    option = "Invalid input.";
                    System.out.println(option);
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

    public static class MechanicCRUDUtils {
        private static String UPDATE_ORDERSLIST = "UPDATE ordersList SET status = ? WHERE id = ?";

        public static boolean updateOrdersList(int orderNameId, String newStatus) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDERSLIST)) {
                preparedStatement.setString(1, newStatus);
                preparedStatement.setInt(2, orderNameId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                return false;
            }
        }

        public static List<InventoryList> inventoryList(String query) {
            List<InventoryList> inventoryList = new ArrayList<>();
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int amount = rs.getInt("amount");
                    inventoryList.add(new InventoryList(id, name, amount));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return inventoryList;
        }

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

        public static List<ServicesList> servicesList(String query) {
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
    }
}
