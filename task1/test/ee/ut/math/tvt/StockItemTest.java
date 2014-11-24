package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

//Before polnud hea kasutada, sest muidu tekiks konflikt Unique-ga.
public class StockItemTest {

	// Testandmed testGetColumn() jaoks. NB! ALATI
	// KONTROLLIDA, ET ANDMEBAAS TOOTAKS
	// ENNE TESTI K2IVITAMIST
	private StockItem stock1;
	private Long id1 = new Long(100);
	private String name1 = "testtest";
	private String desc1 = "sander2";
	private double price1 = 20.0E0;
	private int quantity1 = 9;

	// Testandmed testClone() jaoks. NB! ALATI
	// KONTROLLIDA, ET ANDMEBAAS TOOTAKS
	// ENNE TESTI K2IVITAMIST
	private StockItem stock2;
	private Long id2 = new Long(3);
	private String name2 = "soft drink";
	private String desc2 = "Coca-Cola";
	private double price2 = 15.0E0;
	private int quantity2 = 8;

	@Test
	public void testClone() {
		stock2 = new StockItem(id2, name2, desc2, price2, quantity2, false);
		StockItem cloneBobaFett = (StockItem) stock2.clone();
		assertEquals(cloneBobaFett.getId().longValue(),
				id2.longValue() + 50000, 0.0001);
		assertEquals(cloneBobaFett.getName(), name2);
		assertEquals(cloneBobaFett.getDescription(), desc2);
		assertEquals(cloneBobaFett.getPrice(), price2, 0.001);
		assertEquals(cloneBobaFett.getQuantity(), quantity2);

	}

	@Test
	public void testGetColumn() {
		stock1 = new StockItem(id1, name1, desc1, price1, quantity1, false);
		assertEquals(((Long) stock1.getColumn(0)).longValue(), id1.longValue(),
				0.0001);
		assertEquals(((String) stock1.getColumn(1)), name1);
		assertEquals((((Number) (Object) stock1.getColumn(2)).doubleValue()),
				price1, 0.0001);
		assertEquals((((Integer) stock1.getColumn(3)).intValue()), quantity1,
				0.0001);
	}
	
	@Test
	public void testToString() {
		stock1 = new StockItem(id1, name1, desc1, price1, quantity1, false);
		assertEquals(id1 + " " + name1 + " " + desc1 + " " + price1, stock1.toString());
	}
}
