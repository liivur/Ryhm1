package ee.ut.math.tvt.ryhm1;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

public class Intro {

	// loogika paika
	private static final Logger log = LogManager.getLogger(Intro.class);
	private static final String MODE = "console";

	public static void main(String[] args) {

		final SalesDomainController domainController = new SalesDomainControllerImpl();

		log.info("Intro initialized");

		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			// Loome uue IntroUI objekti ning muudame selle n�htavaks
			IntroUI introUI = new IntroUI("Team Introduction");
			introUI.setVisible(true);
			introUI.setAlwaysOnTop(true);
			final SalesSystemUI ui = new SalesSystemUI(domainController);
			ui.setVisible(true);

			introUI.setAlwaysOnTop(false);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			introUI.setVisible(false);
		}
	}
}
