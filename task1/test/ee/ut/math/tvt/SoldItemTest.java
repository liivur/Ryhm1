package ee.ut.math.tvt;

import static org.junit.Assert.*;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {

	// Testandmed testGetSum() jaoks. NB! ALATI
	// KONTROLLIDA, ET ANDMEBAAS TOOTAKS
	// ENNE TESTI K2IVITAMIST
	private SoldItem dealMade;
	private Long id1 = new Long(500);
	private String name1 = "testtest";
	private String desc1 = "sander6";
	private double price1 = 20.0E0;
	private int quantity1 = 9;
	private int quantityControl = 5;

	// Testandmed testGetSumWithZeroQuantity() jaoks. NB! ALATI
	// KONTROLLIDA, ET ANDMEBAAS TOOTAKS
	// ENNE TESTI K2IVITAMIST
	private SoldItem dealZero;
	private Long id2 = new Long(4);
	private String name2 = "soft drink";
	private String desc2 = "Sprite";
	private double price2 = 15.0E0;
	private int quantity3 = 6;
	
	private SoldItem dealOne;
	private Long id3 = new Long(5);
	private String name3 = "soft drink";
	private String desc3 = "Sprite";
	private double price3 = 16.0E0;
	private int quantity4 = 3;

	@Test
	public void testGetSum() {
		dealMade = new SoldItem(new StockItem(id1, name1, desc1, price1,
				quantity1), quantityControl);
		assertEquals(dealMade.getSum(), price1 * quantityControl, 0.0001);

	}

	@Test
	public void testGetSumWithZeroQuantity() {
		dealZero = new SoldItem(new StockItem(id2, name2, desc2, price2,
				quantity3), 0);
		assertEquals(dealZero.getSum(), price2 * 0, 0.0001);

	}
	
	@Test
	public void testPrice() {
		StockItem stockItem = new StockItem(id3, name3, desc3, price3,
				quantity3, false);
		SoldItem item = new SoldItem(stockItem, 3, false);
		
		assertEquals(item.getPrice(), stockItem.getPrice(), 0.0001);
		item.setPrice(98765);
		assertEquals(98765, item.getPrice(), 0.0001);
	}
	
	@Test
	public void testQuantity() {
		StockItem stockItem = new StockItem(id3, name3, desc3, price3,
				quantity3, false);
		SoldItem item = new SoldItem(stockItem, 3, false);
		assertTrue(3 == item.getQuantity());
		item.setQuantity(Integer.MAX_VALUE);
		assertTrue(Integer.MAX_VALUE == item.getQuantity());
		item.setQuantity(3);
	}
}
