package org.example.Lists;

public class OrdersList {
    private int id;
    private String orderName;
    private String customer_name;
    private String date;
    private String price;
    private String status;


    public OrdersList(int id, String orderName, String customer_name, String date, String price, String status) {
        this.id = id;
        this.orderName = orderName;
        this.customer_name = customer_name;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServicesList{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

