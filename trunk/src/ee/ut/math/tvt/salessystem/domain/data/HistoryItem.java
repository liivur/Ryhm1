package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Orders history has date, time and total price of order fields.
 */
@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="DATE")
    private String date;
	@Column(name="TIME")
    private String time;
	@Column(name="TOTALPRICE")
    private double totalPrice;
	@OneToMany(cascade=CascadeType.ALL)
    private List<SoldItem> orderDetails;

    public HistoryItem(String date, String time, double totalPrice, List<SoldItem> orderDetails) {
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
    }
    public HistoryItem(){}

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
