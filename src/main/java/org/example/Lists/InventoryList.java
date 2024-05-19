package org.example.Lists;

public class InventoryList {
    private int id;
    private String inventory;
    private Integer amount;

    public InventoryList(int id, String inventory, Integer amount) {
        this.id = id;
        this.inventory = inventory;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ServicesList{" +
                "id=" + id +
                ", inventory='" + inventory + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}

