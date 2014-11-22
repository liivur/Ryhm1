package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryItemTest {

	PurchaseInfoTableModel model;
	// Testandmed. NB! ALATI
	// KONTROLLIDA, ET ANDMEBAAS TOOTAKS
	// ENNE TESTI K2IVITAMIST
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

	private SoldItem madeMoney3;
	private Long id3 = new Long(14);
	private String name3 = "Yellow";
	private String desc3 = "Banana";
	private double price3 = 3.0E0;
	private int quantity3 = 8;

	private SoldItem madeMoney4;
	private Long id4 = new Long(15);
	private String name4 = "Roasted";
	private String desc4 = "Hazelnuts";
	private double price4 = 26.0E0;
	private int quantity4 = 4;

	private SoldItem madeMoney5;
	private Long id5 = new Long(16);
	private String name5 = "250g";
	private String desc5 = "Bacon";
	private double price5 = 27.0E0;
	private int quantity5 = 5;

	private SoldItem madeMoney6;
	private Long id6 = new Long(17);
	private String name6 = "Talvine maius";
	private String desc6 = "Verivorst";
	private double price6 = 16.0E0;
	private int quantity6 = 6;

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
		madeMoney3 = new SoldItem(new StockItem(id3, name3, desc3, price3,
				quantity3), 5);
		madeMoney4 = new SoldItem(new StockItem(id4, name4, desc4, price4,
				quantity4), 2);
		madeMoney5 = new SoldItem(new StockItem(id5, name5, desc5, price5,
				quantity5), 3);
		madeMoney6 = new SoldItem(new StockItem(id6, name6, desc6, price6,
				quantity6), 4);
		model.addItem(madeMoney3);
		model.addItem(madeMoney4);
		model.addItem(madeMoney5);
		model.addItem(madeMoney6);
		assertEquals(
				madeMoney3.getSum() + madeMoney4.getSum() + madeMoney5.getSum()
						+ madeMoney6.getSum(), model.findSum(), 0.001);
	}

}
