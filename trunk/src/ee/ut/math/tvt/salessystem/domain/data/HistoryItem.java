package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

/**
 * Orders history has date, time and total price of order fields.
 */

public class HistoryItem implements Cloneable, DisplayableItem {
    private String date;
    private String time;
    private double TotalPrice;
    private List<SoldItem> orderDetails;

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
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
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
