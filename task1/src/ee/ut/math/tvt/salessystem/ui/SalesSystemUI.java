package ee.ut.math.tvt.salessystem.ui;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.ryhm1.Intro;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.Logger;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends JFrame {

  private static final long serialVersionUID = 1L;

//  private static final Logger log = Logger.getLogger(SalesSystemUI.class);
  private static final Logger log = LogManager.getLogger(SalesSystemUI.class);
  
  private final SalesDomainController domainController;

  // Warehouse model
  private SalesSystemModel model;

  // Instances of tab classes
  private PurchaseTab purchaseTab;
  private HistoryTab historyTab;
  private StockTab stockTab;

  /**
   * Constructs sales system GUI.
   * @param domainController Sales domain controller.
   */
  public SalesSystemUI(final SalesDomainController domainController) {
    this.domainController = domainController;
    this.model = new SalesSystemModel(domainController);

    // Create singleton instances of the tab classes
    stockTab = new StockTab(model);
    purchaseTab = new PurchaseTab(domainController, model);
    historyTab = new HistoryTab(model);

    setTitle("Sales system");

    // set L&F to the nice Windows style
    try {
      UIManager.setLookAndFeel(new WindowsLookAndFeel());

    } catch (UnsupportedLookAndFeelException e1) {
      log.warn(e1.getMessage());
    }

    drawWidgets();

    // size & location
    int width = 600;
    int height = 400;
    setSize(width, height);
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screen.width - width) / 2, (screen.height - height) / 2);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
    	//application should end the database session before the exit (using SalesDomainController#endSession) 
    	domainController.endSession();
        System.exit(0);
      }
    });
  }

  private void drawWidgets() {
    final JTabbedPane tabbedPane = new JTabbedPane();

    tabbedPane.add("Point-of-sale", purchaseTab.draw());
    tabbedPane.add("Warehouse", stockTab.draw());
    tabbedPane.add("History", historyTab.draw());

    tabbedPane.addChangeListener(new ChangeListener() {
    	public void stateChanged(ChangeEvent e) {
    		purchaseTab.getPurchasePanel().updateDropdown();
    	}
    });
    getContentPane().add(tabbedPane);
  }

}


