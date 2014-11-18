package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	public List<SoldItem> soldGoods = new ArrayList<SoldItem>();

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		this.soldGoods.addAll(goods);
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {

	}

	public void startNewPurchase() throws VerificationFailedException {

	}

	@Override
	public List<StockItem> loadWarehouseState() {
		Query query = HibernateUtil.currentSession().createQuery(
				"from StockItem");
		@SuppressWarnings("unchecked")
		List<StockItem> dataset = query.list();
		return dataset;
	}

	public void endSession() {
		HibernateUtil.closeSession();
	}

	//Loeb andmebaasist sisse ajaloo, mis sinna salvestatud. Kontrollida!
	@Override
	public List<HistoryItem> loadHistoryState() {
		Query query = HibernateUtil.currentSession().createQuery(
				"from HistoryItem");
		@SuppressWarnings("unchecked")
		List<HistoryItem> dataset = query.list();
		return dataset;
		
	}


}
