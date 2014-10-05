package ee.ut.math.tvt.ryhm1;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Intro {
	//loogika paika
	private static final Logger log = LogManager.getLogger(Intro.class);

	public static void main(String[] args) {
		
		//Kasutame faili log4j.properties
		
		//TODO: Fix configuration for the log4j
		//PropertyConfigurator.configure("log4j.properties");
		log.info("Intro initialized");
		
		//Loome uue IntroUI objekti ning muudame selle n�htavaks
		IntroUI theTeamInfo = new IntroUI("Team Introduction");
		theTeamInfo.setVisible(true);
		theTeamInfo.setAlwaysOnTop(true);
	}

}
