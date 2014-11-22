package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {

	private StockItem stock1;
	private Long id1 = new Long(111);
	private String name1 = "sander3";
	private String desc1 = "testtest";
	private double price1 = 20.0E0;
	private int quantity1 = 9;
	private int quantity2 = 5;

	@Before
	public void setUp() {
		stock1 = new StockItem(id1, name1, desc1, price1, quantity1);
	}

	@Test
	public void testGetSum() {

		SoldItem dealMade = new SoldItem (stock1, quantity2);
		assertEquals(dealMade.getSum(), price1 * quantity2, 0.001);

	}

//	@Test
//	public void testGetSumWithZeroQuantity() {
//
//	}
}
