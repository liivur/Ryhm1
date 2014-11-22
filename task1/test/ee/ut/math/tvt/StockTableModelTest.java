package ee.ut.math.tvt;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockTableModel stockModel;
	private StockItem stockItem;
	private StockItem testStockItem;
	private StockItem stockOne;
	private StockItem stockTwo;
	private SalesDomainController domainController = new SalesDomainControllerImpl();

	// Testandmed testGetSum() jaoks. NB! ALATI
	// KONTROLLIDA, ET ANDMEBAAS TOOTAKS
	// ENNE TESTI K2IVITAMIST
	private Long idA = new Long(18);
	private String nameA = "Salvest";
	private String descA = "Hernesupp";
	private double priceA = 15.0E0;
	private int quantityA = 3;

	private Long idB = new Long(999);
	private int quantityB = 3;

	private Long idC = new Long(19);
	private String nameC = "Salvest";
	private String descC = "Seljanka";
	private double priceC = 30.0E0;
	private int quantityC = 3;

	private Long idD = new Long(20);
	private String nameD = "30g";
	private String descD = "Milka chocowafer";
	private double priceD = 6.0E0;
	private int quantityD = 7;

	@Before
	public void setUp() {
		stockModel = new StockTableModel();
	}

	// NB! Meie programm j2lgib, et unikaalsed
	// oleksid andmebaasi olelmite ID-d.
	// V6ivad olemas olla kaks sama nimega, kuid
	// erinevat kogust t2histavad tooted, millel on siis ka erinev ID.
	// Antud juhul v6ib hernesuppi osta näiteks nii 0,5-liitrist
	// purki kui ka 1-liitrist purki.
	@Test
	public void testValidateNameUniqueness() {
		stockItem = new StockItem(idA, nameA, descA, priceA, quantityA);
		testStockItem = new StockItem(idB, nameA, descA, priceA, quantityB);

		stockModel.addItem(stockItem);
		int rows = stockModel.getRowCount();
		stockModel.addItem(testStockItem);

		assertEquals(rows + 1, stockModel.getRowCount());
		// Saime testitud, et lubatakse
		// kahte sama nimega, kuid erineva ID-ga objekti lisada.
		// EHK LISANDUS YKS RIDA.
	}

	@Test
	public void testHasEnoughInStock() {
		boolean hasEnoughInStock = true;

		stockOne = new StockItem(idC, nameC, descC, priceC, quantityC);
		stockTwo = new StockItem(idD, nameD, descD, priceD, quantityD);
		stockModel.addItem(stockOne);
		stockModel.addItem(stockTwo);
		List<StockItem> items = stockModel.getTableRows();
		for (StockItem st : items) {
			System.out.println(st.getQuantity());
			if (st.getQuantity() <= 0) {
				hasEnoughInStock = false;
				break;
			}
		}
		assertTrue(hasEnoughInStock);
	}

	@Test
	public void testGetItemByIdWhenItemExists() {

	}

	@Test
	public void testGetItemByIdWhenThrowsException() {

	}

}
