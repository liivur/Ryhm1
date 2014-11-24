package ee.ut.math.tvt;

import static org.junit.Assert.*;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest extends PurchaseInfoTableModel {	
	private static final long serialVersionUID = 1L;

	StockItem stockItem = new StockItem((long) 10, "TestName1", "TestDesctiption1", 10.10, 11, false);
	SoldItem soldItem = new SoldItem(stockItem, 3, false);	
	
	@Test
	public void testGetColumnValue() {
		assertEquals(soldItem.getId(), getColumnValue(soldItem, 0));
		assertEquals(soldItem.getName(), getColumnValue(soldItem, 1));
		assertEquals(soldItem.getPrice(), getColumnValue(soldItem, 2));
		assertEquals(soldItem.getQuantity(), getColumnValue(soldItem, 3));
		assertEquals(soldItem.getSum(), getColumnValue(soldItem, 4));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetColumnValueWithInvalidColumn() {
		getColumnValue(soldItem, 5);
	}
	
	@Test
	public void testfindSum() {
		PurchaseInfoTableModel table2 = new PurchaseInfoTableModel();
		table2.addItem(soldItem, false);
		table2.addItem(soldItem, false);
		assertEquals(table2.findSum(), stockItem.getPrice()*soldItem.getQuantity()*2,0.0001);
		
	}
}
