package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.WindowForPay;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab implements ActionListener {

	private static final Logger log = LogManager.getLogger(PurchaseTab.class);
	// private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private WindowForPay payWin;
	private final SalesDomainController domainController;

	private JButton newPurchase;

	private JButton submitPurchase;

	private JButton cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel model;

	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();
		
		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model, this);
		panel.add(purchasePane, getConstraintsForPurchasePanel());

		return panel;
	}

	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined
		// above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}

	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/*
	 * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();

		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			for (SoldItem soldItem : model.getCurrentPurchaseTableModel().getTableRows()) {
				soldItem.getStockItem().setQuantity(soldItem.getStockItem().getQuantity() + soldItem.getQuantity());
			}	
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>submit purchase</code> event. */
	protected void submitPurchaseButtonClicked() {
		if (payWin == null) {
			payWin = new WindowForPay(model.getCurrentPurchaseTableModel(),
					this);
		} else if (!payWin.getFrame().isDisplayable()) {
			payWin.updateInfo();
			payWin.getFrame().setVisible(true);
		}

	}

	/*
	 * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}

	/*
	 * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	// The constraints that control the layout of the buttons in the purchase
	// menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getID() == 0) { // Successful sale
			// NB!If the payment is accepted then order should be accepted and
			// saved.
			// If the payment is canceled, then the screen should be
			// closed/hided and the
			// shopping cart should restore the state when it was left.
            Date today = new Date();
            List<SoldItem> order = new ArrayList<>();
            Double sum = 0.0;

            log.info("Sale complete");
			try {
				log.debug("Contents of the current basket:\n"
						+ model.getCurrentPurchaseTableModel());
				domainController.submitCurrentPurchase(model
						.getCurrentPurchaseTableModel().getTableRows());

                order = model.getCurrentPurchaseTableModel().getSoldItems();
                sum = model.getCurrentPurchaseTableModel().findSum();

				endSale();
				model.getCurrentPurchaseTableModel().clear();
			} catch (VerificationFailedException e1) {
				log.error(e1.getMessage());
			}
			payWin.exit();

            // Add saved order to the history tab
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");

            HistoryItem saved_order = new HistoryItem(dateFormat.format(today),
                                          timeFormat.format(today), sum, order);

            model.getCurrentHistoryTableModel().addItem(saved_order);

		} else if (arg0.getID() == 1) {// Unsuccessful sale
			payWin.exit();
		}

	}

	public WindowForPay getPayWindow() {
		return this.payWin;
	}
	
	public PurchaseItemPanel getPurchasePanel() {
		return this.purchasePane;
	}

}
