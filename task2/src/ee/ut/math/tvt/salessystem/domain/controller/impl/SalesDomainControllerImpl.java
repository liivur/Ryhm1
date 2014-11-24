package ee.ut.math.tvt.salessystem.domain.controller.impl;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

    private static final Logger log = Logger.getLogger(SalesDomainControllerImpl.class);

    private SalesSystemModel model;

    private Session session = HibernateUtil.currentSession();

    @SuppressWarnings("unchecked")
    public List<StockItem> getAllStockItems() {
        List<StockItem> result =
            session
                .createQuery("from StockItem")
                .list();

        log.info(result.size() + " items loaded from disk");

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Sale> getAllSales() {
        List<Sale> result = session.createQuery("from Sale").list();
        log.info(result.size() + " Sales loaded from disk");

        return result;
    }


    @SuppressWarnings("unchecked")
    public List<Client> getAllClients() {
        List<Client> clients =
            session.createQuery("from Client").list();
        
        log.info(clients.size() + " clients loaded from disk");
        return clients;
    }

    public Client getClient(long id) {
        return (Client) session.get(Client.class, id);
    }


    private StockItem getStockItem(long id) {
        return (StockItem) session.get(StockItem.class, id);
    }
    
    public void registerSale(Sale sale){
    	Transaction tx = session.beginTransaction();
    	sale.setSellingTime(new Date());
    	for(SoldItem solditem:sale.getSoldItems()){
    		session.save(solditem.getStockItem());
    	}
    	///TODO:Siin voib olla kala - solditemi salvestamised! Samas eelmisel ei salvestata solditemeid, nii et ehk ei.
    	session.save(sale);
    	tx.commit();
    	model.getPurchaseHistoryTableModel().addRow(sale);
    	
    }

    public void createStockItem(StockItem stockItem) {
        // Begin transaction
        Transaction tx = session.beginTransaction();
        session.save(stockItem);
        tx.commit();
        model.getWarehouseTableModel().addRow(stockItem);
        log.info("Added new stockItem : " + stockItem);
    }


    public void cancelCurrentPurchase() {
        Sale sale = model.getCurrentSale();
        for(SoldItem sold:sale.getSoldItems()){
        	StockItem stock = sold.getStockItem();
        	stock.setQuantity(stock.getQuantity()+sold.getQuantity());
        }
        sale.setSoldItems(new HashSet<SoldItem>());
        model.getCurrentPurchaseTableModel().fireTableDataChanged();
    }

    public void startNewPurchase() {
        model.getCurrentPurchaseTableModel().fireTableDataChanged();
        log.info("New purchase started");
    }



    public void setModel(SalesSystemModel model) {
        this.model = model;
    }


    public Sale getSale(Long id) {
        return (Sale) session.get(Sale.class, id);
    }

    @Override
    public void endSession() {
        HibernateUtil.closeSession();
    }

}
