package org.example.Specialist;

import org.example.DBUtils;
import org.example.Lists.InventoryList;
import org.example.Lists.InventoriesToBuyList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Specialist {
    Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Show a list of inventories; \n" +
                "2. Add a new inventory; \n" +
                "3. Delete an inventory; \n" +
                "4. Update the number of exact inventory; \n" +
                "5. Show a list of inventories need to buy; \n" +
                "6. Add an inventory to list of inventories need to buy; \n" +
                "7. Delete an inventory to list of inventories need to buy; \n" +
                "8. Exit;");
    }

    public String getOption() {
        boolean logout = true;
        String option = "null";
        System.out.println("Enter the number from the menu: ");
        String getOption = scanner.next();

        switch (getOption) {
            case "1":
                List<InventoryList> inventoryList = SpecialistCRUDUtils.inventoryList("SELECT * FROM inventoryList");
                option = inventoryList.toString();
                break;
            case "2":
                System.out.println("Add a new inventory");
                System.out.print("Inventory name: ");
                String inventory = scanner.next();
                System.out.print("Amount: ");
                int amount = scanner.nextInt();
                SpecialistCRUDUtils.addInventory(inventory, amount);
                option = "Inventory added successfully.";
                break;
            case "3":
                System.out.println("Delete an inventory");
                System.out.print("Enter inventory ID to delete: ");
                int inventoryId = scanner.nextInt();
                try {
                    SpecialistCRUDUtils.deleteInventory(inventoryId);
                    option = "Inventory deleted successfully.";
                } catch (SQLException e) {
                    option = "Error deleting inventory: " + e.getMessage();
                }
                break;
            case "4":
                System.out.println("Change number of exact inventory");
                System.out.print("Enter inventory ID to change: ");
                int changeInventoryId = scanner.nextInt();
                System.out.print("Enter new amount: ");
                int newAmount = scanner.nextInt();
                boolean updated = SpecialistCRUDUtils.updateInventoryAmount(changeInventoryId, newAmount);
                if (updated) {
                    option = "Inventory amount updated successfully.";
                } else {
                    option = "Failed to update inventory amount.";
                }
                break;
            case "5":
                List<InventoriesToBuyList> inventoriesToBuyList = SpecialistCRUDUtils.inventoriesToBuyList("SELECT * FROM inventoriesToBuyList");
                option = inventoriesToBuyList.toString();
                break;
            case "6":
                System.out.println("Add an inventory to list of inventories need to buy");
                System.out.print("Inventory name: ");
                String inventoryToBuy = scanner.next();
                System.out.print("Number: ");
                int number = scanner.nextInt();
                boolean addedSuccessfully = SpecialistCRUDUtils.addInventoriesToBuyList(inventoryToBuy, number);
                if (addedSuccessfully) {
                    option = "Inventory added successfully.";
                } else {
                    option = "Failed to add inventory.";
                }
                break;
            case "7":
                System.out.println("Delete an inventory from the list of inventories needed to buy");
                System.out.print("Enter the inventory ID to delete: ");
                int inventoryToBuyId = scanner.nextInt();
                try {
                    SpecialistCRUDUtils.deleteInventoriesToBuyList(inventoryToBuyId);
                    option = "Inventory deleted successfully.";
                } catch (SQLException e) {
                    option = "Failed to delete inventory.";
                }
                break;
            case "8":
                option = "You logout from the account.";
                logout = false;
                break;
            default:
                option = "Invalid input";
                break;
        }
        return option;
    }

    public static class SpecialistCRUDUtils {
        private static String INSERT_INVENTORY = "INSERT INTO inventory(name, amount) VALUES(?, ?);";
        private static String DELETE_INVENTORY = "DELETE FROM inventory WHERE id = ?";
        private static String INSERT_INVENTORIESTOBUYLiST = "INSERT INTO inventoriesToBuyList(inventoryToBuy, number) VALUES(?, ?);";
        private static String DELETE_INVENTORIESTOBUYLIST = "DELETE FROM inventoriesToBuyList WHERE id = ?";

        private static String UPDATE_INVENTORY = "UPDATE inventory SET amount = ? WHERE id = ?";

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

        public static List<InventoriesToBuyList> inventoriesToBuyList(String query) {
            List<InventoriesToBuyList> inventoriesToBuyList = new ArrayList<>();
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String inventoryToBuy = rs.getString("inventoryToBuy");
                    int number = rs.getInt("number");
                    inventoriesToBuyList.add(new InventoriesToBuyList(id, inventoryToBuy, number));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return inventoriesToBuyList;
        }

        public static boolean addInventory(String inventory, int amount) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INVENTORY)) {
                preparedStatement.setString(1, inventory);
                preparedStatement.setInt(2, amount);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            System.out.println("New inventory added");
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

        public static void deleteInventory(int inventoryId) throws SQLException {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INVENTORY)) {

                preparedStatement.setInt(1, inventoryId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Inventory with ID " + inventoryId + " deleted.");
                } else {
                    System.out.println("Inventory with ID " + inventoryId + " not found.");
                }
            }
        }

        public static boolean addInventoriesToBuyList(String inventoryToBuy, int number) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INVENTORIESTOBUYLiST)) {
                preparedStatement.setString(1, inventoryToBuy);
                preparedStatement.setInt(2, number);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            System.out.println("New inventory added");
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

        public static void deleteInventoriesToBuyList(int inventoryToBuyId) throws SQLException {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INVENTORIESTOBUYLIST)) {

                preparedStatement.setInt(1, inventoryToBuyId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Inventory with ID " + inventoryToBuyId + " deleted.");
                } else {
                    System.out.println("Inventory with ID " + inventoryToBuyId + " not found.");
                }
            }
        }

        public static boolean updateInventoryAmount(int inventoryId, int newAmount) {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INVENTORY)) {
                preparedStatement.setInt(1, newAmount);
                preparedStatement.setInt(2, inventoryId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException throwable) {
                throwable.printStackTrace();
                return false;
            }
        }
    }
}
