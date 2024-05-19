package org.example.Lists;

public class InventoriesToBuyList {
    private int id;
    private String inventoryToBuy;
    private Integer number;

    public InventoriesToBuyList(int id, String inventoryToBuy, Integer number) {
        this.id = id;
        this.inventoryToBuy = inventoryToBuy;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventoryToBuy() {
        return inventoryToBuy;
    }

    public void setInventoryToBuy(String inventoryToBuy) {
        this.inventoryToBuy = inventoryToBuy;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ServicesList{" +
                "id=" + id +
                ", inventoryToBuy='" + inventoryToBuy + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

