package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryItemTest {

	PurchaseInfoTableModel model;
	private SoldItem madeMoney1;
	private Long id1 = new Long(12);
	private String name1 = "Green";
	private String desc1 = "Tea";
	private double price1 = 5.0E0;
	private int quantity1 = 15;

	private SoldItem madeMoney2;
	private Long id2 = new Long(13);
	private String name2 = "chocolate candy";
	private String desc2 = "Toblerone";
	private double price2 = 13.0E0;
	private int quantity2 = 4;

	@Before
	public void setUp() {
		model = new PurchaseInfoTableModel();
	}

	@Test
	public void testAddSoldItem() {
		madeMoney1 = new SoldItem(new StockItem(id1, name1, desc1, price1,
				quantity1), 5);
		model.addItem(madeMoney1);
		SoldItem checkMoney1 = model.getTableRows().get(0);

		assertEquals(madeMoney1.getStockItem().getId(), checkMoney1
				.getStockItem().getId());
		assertEquals(madeMoney1.getStockItem().getName(), checkMoney1
				.getStockItem().getName());
		assertEquals(madeMoney1.getStockItem().getDescription(), checkMoney1
				.getStockItem().getDescription());
		assertEquals(madeMoney1.getStockItem().getPrice(), checkMoney1
				.getStockItem().getPrice(), 0.001);
		assertEquals(madeMoney1.getStockItem().getQuantity(), checkMoney1
				.getStockItem().getQuantity());
		assertEquals(madeMoney1.getQuantity(), checkMoney1.getQuantity());
	}

	@Test
	public void testGetSumWithNoItems() {
		assertEquals(0.0, model.findSum(), 0.001);
	}

	@Test
	public void testGetSumWithOneItem() {
		madeMoney2 = new SoldItem(new StockItem(id2, name2, desc2, price2,
				quantity2), 2);
		model.addItem(madeMoney2);
		assertEquals(madeMoney2.getSum(), model.findSum(), 0.001);
	}

	@Test
	public void testGetSumWithMultipleItems() {

	}

}
