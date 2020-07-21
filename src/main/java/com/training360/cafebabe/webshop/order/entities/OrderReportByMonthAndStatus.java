package com.training360.cafebabe.webshop.order.entities;

public class OrderReportByMonthAndStatus {

    private String date;

    private String status;
    private int count;

    private int sum;


    public OrderReportByMonthAndStatus(String date, String status, int count, int sum) {
        this.date = date;
        this.status = status;
        this.count = count;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public String getStatus() {
        return status;
    }

    public int getSum() {
        return sum;
    }
}
