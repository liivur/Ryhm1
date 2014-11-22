package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "STOCKITEM")
public class StockItem implements Cloneable, DisplayableItem {
	@Id
    private Long id;
	@Column(name="name")
    private String name;
	@Column(name="price")
    private double price;
	@Column(name="description")
    private String description;
    @Column(name="quantity")
    private int quantity;

    /**
     * Constucts new <code>StockItem</code> with the specified values.
     * @param id barcode id
     * @param name name of the product
     * @param desc description of the product
     * @param price price of the product
     */
    public StockItem(Long id, String name, String desc, double price) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
        writeToDB();
    }
    
    public StockItem(Long id, String name, String desc, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
        this.quantity = quantity;
        writeToDB();
    }

    /**
     * Constructs new  <code>StockItem</code>.
     */
    public StockItem() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        writeToDB();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        writeToDB();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        writeToDB();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
		StockItem test = (StockItem)HibernateUtil.currentSession().get(StockItem.class,this.id);
		if(test!=null){
			HibernateUtil.currentSession().delete(this);
		}
        this.id = id;
        writeToDB();
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        writeToDB();
    }

    public String toString() {
        return id + " " + name + " " + description + " " + price;
    }

    /**
     * Method for querying the value of a certain column when StockItems are shown
     * as table rows in the SalesSystemTableModel. The order of the columns is:
     * id, name, price, quantity.
     */
    public Object getColumn(int columnIndex) {
        switch(columnIndex) {
            case 0: return id;
            case 1: return name;
            case 2: return new Double(price);
            case 3: return new Integer(quantity);
            default: throw new RuntimeException("invalid column!");
        }
    }
    
    // Tahame, et kardetud tegelasega, keda tuntakse kui UNIQUE,
    // pahandusi ei tekiks, selleks peab kloonitud ID olema teistsugune
    // olgu erinevus +50000
    public Object clone() {
        StockItem item =
            new StockItem(getId()+50000, getName(), getDescription(), getPrice(), getQuantity());
        return item;
    }
    
    public void writeToDB(){
    	HibernateUtil.currentSession().beginTransaction();
    	HibernateUtil.currentSession().saveOrUpdate(this);
    	HibernateUtil.currentSession().getTransaction().commit();
    }
		
}
