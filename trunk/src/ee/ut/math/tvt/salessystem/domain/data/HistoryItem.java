package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

/**
 * Orders history has date, time and total price of order fields.
 */

public class HistoryItem implements Cloneable, DisplayableItem {
    private String date;
    private String time;
    private double totalPrice;
    private List<SoldItem> orderDetails;

    public HistoryItem(String date, String time, double totalPrice, List<SoldItem> orderDetails) {
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<SoldItem> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<SoldItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public Long getId() {
        return null;
    }
}
