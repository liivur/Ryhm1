package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StockTab {

	private JButton addItem;
	private static final Logger log = LogManager.getLogger(StockTab.class);
	private SalesSystemModel model;

	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = new JButton("Add");
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFrame addProductWindow = new JFrame("Add new product");
				JPanel infoPanel = new JPanel(new GridBagLayout());
				infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				GridBagConstraints panelConstraints = new GridBagConstraints();
				panelConstraints.weighty = 0;

				// ID v2li
				panelConstraints.weightx = 0;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = 0;
				panelConstraints.fill = GridBagConstraints.HORIZONTAL;
				JLabel idLabel = new JLabel("ID: ");
				infoPanel.add(idLabel, panelConstraints);

				panelConstraints.weightx = 1;
				panelConstraints.gridx = 1;
				panelConstraints.gridy = 0;
				final JTextField idField = new JTextField(15);
				infoPanel.add(idField, panelConstraints);

				// Nime v2li
				panelConstraints.weightx = 0;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = 1;
				JLabel nameLabel = new JLabel("Name: ");
				infoPanel.add(nameLabel, panelConstraints);

				panelConstraints.weightx = 1;
				panelConstraints.gridx = 1;
				panelConstraints.gridy = 1;
				final JTextField nameField = new JTextField(15);
				infoPanel.add(nameField, panelConstraints);

				// Hinna v2li
				panelConstraints.weightx = 0;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = 2;
				JLabel priceLabel = new JLabel("Price: ");
				infoPanel.add(priceLabel, panelConstraints);

				panelConstraints.weightx = 1;
				panelConstraints.gridx = 1;
				panelConstraints.gridy = 2;
				final JTextField priceField = new JTextField(15);
				infoPanel.add(priceField, panelConstraints);

				// Kirjelduse v2li
				panelConstraints.weightx = 0;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = 3;
				JLabel descLabel = new JLabel("Description: ");
				infoPanel.add(descLabel, panelConstraints);

				panelConstraints.weightx = 1;
				panelConstraints.gridx = 1;
				panelConstraints.gridy = 3;
				final JTextField descField = new JTextField(15);
				infoPanel.add(descField, panelConstraints);

				// Koguse v2li
				panelConstraints.weightx = 0;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = 4;
				JLabel qtyLabel = new JLabel("Quantity: ");
				infoPanel.add(qtyLabel, panelConstraints);

				panelConstraints.weightx = 1;
				panelConstraints.gridx = 1;
				panelConstraints.gridy = 4;
				final JTextField qtyField = new JTextField(15);
				infoPanel.add(qtyField, panelConstraints);

				// Cancel nupp
				panelConstraints.weightx = 0;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = 5;
				JButton cancelButton = new JButton("Cancel");
				infoPanel.add(cancelButton, panelConstraints);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addProductWindow.setVisible(false);
						addProductWindow.dispose();

					}
				});

				// Lisamise nupp
				panelConstraints.gridx = 1;
				panelConstraints.gridy = 5;
				JButton addingButton = new JButton("Add Product");
				infoPanel.add(addingButton, panelConstraints);

				// Toote lisamine
				addingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StockItem product;
						boolean actionPerformed = false;
						boolean quantityExists = false;
						int productQuantity = 0;
						try {
							Long productID = Long.parseLong(idField.getText());
							String productName = nameField.getText();
							double productPrice = Double.parseDouble(priceField
									.getText());
							if (productPrice < 0) {
								throw new Exception();
							}
							String productDescription = descField.getText();
							if (qtyField.getText() != null) {
								productQuantity = Integer.parseInt(qtyField
										.getText());
								quantityExists = true;
							}
							try {
								model.getWarehouseTableModel()
								.getItemById(productID);
								int selectedOption = JOptionPane
										.showConfirmDialog(
												null,
												"An item with the given ID is already in the system. Would you like to overwrite it?",
												"Duplicate ID found.",
												JOptionPane.YES_NO_OPTION);
								if (selectedOption == JOptionPane.YES_OPTION) {
									actionPerformed = true;
									model.getWarehouseTableModel()
											.getItemById(productID)
											.setName(productName);
									model.getWarehouseTableModel()
											.getItemById(productID)
											.setDescription(productDescription);
									model.getWarehouseTableModel()
											.getItemById(productID)
											.setPrice(productPrice);
									if (qtyField.getText() != null) {
										model.getWarehouseTableModel()
												.getItemById(productID)
												.setQuantity(
														Integer.parseInt(qtyField
																.getText()));
									}
									model.getWarehouseTableModel()
											.fireTableDataChanged();
								}
							} catch (NoSuchElementException elem) {
								actionPerformed = true;
								if(quantityExists){
									product = new StockItem(productID,productName,productDescription,productPrice,productQuantity);
								}
								else{
									product = new StockItem(productID,productName,productDescription,productPrice);
								}
								model.getWarehouseTableModel().addItem(product);
							}
							if(actionPerformed){
								addProductWindow.setVisible(false);
								addProductWindow.dispose();
							}
						} catch (Exception except) {
							log.debug("Error parsing product info upon adding.");
							JOptionPane.showMessageDialog(null,
									"Please double-check the information!",
									"Error", JOptionPane.ERROR_MESSAGE);
						}

					}
				});
				addProductWindow.getContentPane().add(infoPanel);
				addProductWindow.pack();
				addProductWindow.setVisible(true);
				addProductWindow.setLocation(MouseInfo.getPointerInfo()
						.getLocation());
				addProductWindow
						.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			}
		});
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	// table of the warehouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

}
