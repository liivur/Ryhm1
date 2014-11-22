package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

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
	private String nameB = "Salvest";
	private String descB = "Hernesupp";
	private double priceB = 30.0E0;
	private int quantityB = 3;

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
		int rows = stockModel.getRowCount();
		stockModel.addItem(stockItem);
		assertEquals(rows + 1, stockModel.getRowCount());
		// Saime testitud, et lubatakse
		// kahte sama nimega, kuid erineva ID-ga objekti lisada.
		// EHK LISANDUS YKS RIDA. 
	}

	@Test
	public void testHasEnoughInStock() {

	}

	@Test
	public void testGetItemByIdWhenItemExists() {

	}

	@Test
	public void testGetItemByIdWhenThrowsException() {

	}

}
