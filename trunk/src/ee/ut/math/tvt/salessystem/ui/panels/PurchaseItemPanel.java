package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.ryhm1.Intro;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {
    private static final Logger log = LogManager.getLogger(PurchaseItemPanel.class);

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(Intro.class);
	private JComboBox<String> dropDownMenu;// Loome "dropdown menu" muutuja
	private List<StockItem> itemTrunk; // Kuskil vaja hoida tooteid
	private JTextField barCodeField;// Text field on the dialogPane
	private JTextField quantityField;// Text field
	private JTextField nameField;// Text field
	private JTextField priceField;// Text field
	private JButton addItemButton;
	private SalesSystemModel model;// Warehouse model

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 3));// Nii tuli m6istlik paigutus
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Loome "dropdown" valikud
		dropDownMenu = new JComboBox<String>();
		dropDownMenu.addItem("");// Alguses tyhjus

		// Initialize the textfields
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();
		// T2idame nyyd laohoonest saadavate toodetega oma "kohvri"
		itemTrunk = model.getDomainController().loadWarehouseState();
		// K2ime "kohvri" l2bi, lisame rippmenuusse tooteid
		for (StockItem item : itemTrunk) {
			dropDownMenu.addItem(item.getName());
		}

		// Fill the dialog fields
		// Muudatuste korral tuleb teha muudatusi
		dropDownMenu.addItemListener(new ItemListener() {
			// J2lgime hoopis toodet
			public void itemStateChanged(ItemEvent arg0) {
				if (dropDownMenu.isEnabled() == true) {
					fillDialogFields();
				}
			}
		});

		nameField.setEditable(false);
		barCodeField.setEditable(false);// Ei lase muuta niisama
		priceField.setEditable(false);

		// == Add components to the panel

		// N6utud rippmenuu
		panel.add(new JLabel("Product selection:"));
		panel.add(dropDownMenu);

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByBarcode();

		if (stockItem != null) {
			nameField.setText(stockItem.getName());
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
			// Siia oli vaja lisada juurde "barCodeField", et kood ikka
			// n2ha oleks.
			barCodeField.setText(String.valueOf(stockItem.getId()));
		} else {
			reset();
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	// Otsime.
	private StockItem getStockItemByBarcode() {
		try {
			// K2ia l2bi valikud
			for (StockItem item : model.getWarehouseTableModel().getTableRows()) {
				// V6rrelda valikuga
				if (item.getName() == (String) dropDownMenu.getSelectedItem()) {
					int code = item.getId().intValue();
					// Tagastada 6ige vastus
					return model.getWarehouseTableModel().getItemById(code);
				}
			}
			return null;
		} catch (NoSuchElementException | NumberFormatException exception) {
			return null;

		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			model.getCurrentPurchaseTableModel().addItem(
					new SoldItem(stockItem, quantity));
		}
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.dropDownMenu.setEnabled(enabled);// Siia oli vaja lisada menuu
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		barCodeField.setText("");
		quantityField.setText("1");
		nameField.setText("");
		priceField.setText("");
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}
