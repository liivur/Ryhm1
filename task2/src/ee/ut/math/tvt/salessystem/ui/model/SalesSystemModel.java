package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

    // Warehouse model
    private StockTableModel warehouseTableModel;

    // Current shopping cart model
    private PurchaseInfoTableModel currentPurchaseTableModel;

    // Puchase history model
    private PurchaseHistoryTableModel purchaseHistoryTableModel;

    private ClientTableModel clientTableModel;

    private Sale currentSale;
    
    private SalesDomainController domainController;

    public SalesDomainController getDomainController() {
		return domainController;
	}
	/**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel(this);
        purchaseHistoryTableModel = new PurchaseHistoryTableModel();
        clientTableModel = new ClientTableModel();

        // Load data from the database

        List<StockItem> stockItems = domainController.getAllStockItems();
        warehouseTableModel.populateWithData(stockItems);

        List<Client> clients = domainController.getAllClients();
        clientTableModel.populateWithData(clients);

        List<Sale> sales = domainController.getAllSales();
        purchaseHistoryTableModel.populateWithData(sales);

    }
    public SalesSystemModel(){
    	this.currentSale = new Sale(new ArrayList<SoldItem>());
    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }

    public PurchaseHistoryTableModel getPurchaseHistoryTableModel() {
        return purchaseHistoryTableModel;
    }

    public ClientTableModel getClientTableModel() {
        return clientTableModel;
    }

    public void setPurchaseHistoryTableModel(
            PurchaseHistoryTableModel purchaseHistoryTableModel) {
        this.purchaseHistoryTableModel = purchaseHistoryTableModel;
    }

    public Sale getCurrentSale() {
        return currentSale;
    }

    public void setCurrentSale(Sale sale) {
        this.currentSale = sale;
    }

}
