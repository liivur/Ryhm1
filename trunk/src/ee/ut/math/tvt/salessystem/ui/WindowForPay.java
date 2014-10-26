package ee.ut.math.tvt.salessystem.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WindowForPay {

	private static final Logger log = LogManager.getLogger(WindowForPay.class);
	private JButton okButton;
	private JButton abortButton;
	private double sumItemCost;
	private ActionListener actListen;
	private JFrame frame;
	private JPanel panel;
	private JTextField payedMoneyField;
	private JTextField changeField;
	private PurchaseInfoTableModel model;



	public WindowForPay(PurchaseInfoTableModel model, ActionListener actListen) {
		this.model = model;
		this.actListen = actListen;

		sumItemCost = model.findSum();//Kogusumma

		frame = new JFrame("Pay");//P6hiaken
		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));

		//Paneme p6hiaknasse sisu.
		panel.add(new JLabel("Customer payed:"));
		payedMoneyField = new JTextField();
		panel.add(payedMoneyField);	
		panel.add(new JLabel("The Sum is:"));
		JTextField theSum = new JTextField(String.valueOf(sumItemCost));
		theSum.setEditable(false);
		panel.add(theSum);
		panel.add(new JLabel("Change amount:"));
		changeField = new JTextField();
		changeField.setEditable(false);
		panel.add(changeField);

		//Nupud 2 tk
		okButton = new JButton("Accept");
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateInfo();
				actListen.actionPerformed(new ActionEvent(this, 0, "ok purchase"));
				log.debug("ok for the pay!");
			}
		});
		

		abortButton = new JButton("Cancel");
		abortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateInfo();
				actListen.actionPerformed(new ActionEvent(this, 1, "abort purchase"));
				log.debug("aborted paying!");
			}
		});
		
		//Lisamine
		panel.add(okButton);
		panel.add(abortButton);

		//Toimingute j2lgimine
		payedMoneyField.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						updateInfo();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						updateInfo();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						updateInfo();
					}

				});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocation(300, 300);
		frame.setVisible(true);
		frame.pack();//Optimaalsed m66tmed aknale.
	}

	// Infouuendusmeetod
		private void updateInfo() {
			String comparison = payedMoneyField.getText();
			if (!isNumeric(comparison)) {
				changeField.setText("Error!");
				okButton.setEnabled(false);
				return;
			}

			if ((Double.parseDouble(payedMoneyField.getText()) - sumItemCost) >= 0) {
				changeField.setText(String.valueOf(Double.parseDouble(payedMoneyField.getText()) - sumItemCost));
				abortButton.setEnabled(true);
				okButton.setEnabled(true);
			} else {
				changeField.setText("Nothing");
				abortButton.setEnabled(true);
				okButton.setEnabled(false);
			}
		}
		
		public void exit() {
			frame.dispose();
		}

		public static boolean isNumeric(String str) {
			try {
				double number = Double.parseDouble(str);
			} catch (NumberFormatException ne) {
				return false;
			}
			return true;
		}
}


