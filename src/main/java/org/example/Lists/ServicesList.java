package org.example.Lists;

public class ServicesList {
    private int id;
    private String service;
    private String price;

    public ServicesList(int id, String service, String price) {
        this.id = id;
        this.service = service;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ServicesList{" +
                "id=" + id +
                ", service='" + service + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

