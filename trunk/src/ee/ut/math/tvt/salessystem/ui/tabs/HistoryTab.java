package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

    private SalesSystemModel model;

    public HistoryTab(SalesSystemModel model) {
        this.model = model;
    }

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

        panel.add(drawHistoryMenuPane(), gc);

        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(drawHistoryMainPane(), gc);

        return panel;
    }

    // menu of the orders history
    private Component drawHistoryMenuPane() {
        JPanel panel = new JPanel();

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();

        panel.setLayout(gb);

        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.weightx = 0;

        gc.gridwidth = GridBagConstraints.RELATIVE;
        gc.weightx = 1.0;

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;

    }

    // table of the orders history
    private Component drawHistoryMainPane() {
        JPanel panel = new JPanel();

        JTable table = new JTable(model.getCurrentHistoryTableModel());

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                final JFrame orderDetailWindow = new JFrame("Order details");
                JPanel panel = new JPanel();

                PurchaseInfoTableModel detail_model = new PurchaseInfoTableModel();
                JTable details = new JTable(detail_model);

                JTableHeader header = details.getTableHeader();
                header.setReorderingAllowed(false);

                JScrollPane scrollPane = new JScrollPane(details);

                GridBagConstraints gc = new GridBagConstraints();
                GridBagLayout gb = new GridBagLayout();
                gc.fill = GridBagConstraints.BOTH;
                gc.weightx = 1.0;
                gc.weighty = 1.0;

                details.setLayout(gb);
                details.add(scrollPane, gc);

                HistoryItem item = model.getCurrentHistoryTableModel().getItemById(listSelectionEvent.getFirstIndex());

                for (SoldItem detail : item.getOrderDetails()) {
                    detail_model.addItem(detail);
                }

                details.setBorder(BorderFactory.createTitledBorder("History status"));
                orderDetailWindow.getContentPane().add(panel);
                orderDetailWindow.pack();
                orderDetailWindow.setVisible(true);
                orderDetailWindow.setLocation(MouseInfo.getPointerInfo()
                        .getLocation());
                orderDetailWindow
                        .setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

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

        panel.setBorder(BorderFactory.createTitledBorder("History status"));
        return panel;
    }
}