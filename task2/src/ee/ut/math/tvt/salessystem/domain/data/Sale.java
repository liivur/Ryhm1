package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;

/**
 * Sale object. Contains client and sold items.
 */
@Entity
@Table(name = "SALE")
public class Sale implements DisplayableItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = SoldItem.class, mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<SoldItem> soldItems;
    private Date sellingTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    /** Empty constructors are used by hibernate */
    public Sale() {
    }
    
    public Sale(Client client) {
    	this.client = client;
    	this.sellingTime = new Date();
    	this.soldItems = new HashSet<SoldItem>();
    }
    public Sale(List<SoldItem> goods) {
        this.soldItems = new HashSet<SoldItem>(goods);
        this.sellingTime = new Date();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getSellingTime() {
        return sellingTime;
    }

    public void setSellingTime(Date sellingTime) {
        this.sellingTime = sellingTime;
    }

    public Set<SoldItem> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(Set<SoldItem> soldItems) {
        this.soldItems = soldItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addSoldItem(SoldItem item) {
        item.setSale(this);
        soldItems.add(item);
    }
    
    public void addItem (StockItem item,int quantity) throws SalesSystemException{
    	if (item.getQuantity()<quantity){
    		throw new SalesSystemException();
    	}
    	else{
	    	for (SoldItem solditem: soldItems){
	    		if (solditem.getStockItem() == item){
	    			solditem.setQuantity(solditem.getQuantity()+quantity);
	    			item.setQuantity(item.getQuantity()-quantity);
	    			return;
	    		}
	    	}
	    	soldItems.add(new SoldItem(item,quantity));
	    	item.setQuantity(item.getQuantity()-quantity);
    	}

    }

    public double getSum() {
        double sum = 0.0;
        for (SoldItem item : soldItems) {
            sum = sum + item.getPrice() * ((double) item.getQuantity());
        }
        return sum;
    }

}
