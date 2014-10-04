package ee.ut.math.tvt.ryhm1;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Intro {
	
	/**
	org.apache.log4j.Logger- i importimiseks on vaja alla laadida
	leheküljelt https://logging.apache.org/log4j/1.2/download.html
	zip-kaust ja see ekstraktida. Seejärel peab kaustast faili log4j-1.2.17.jar
	kopeerima projektis kausta lib ning projekti porperties' kohas viitama 
	library'de all sellele jar failile.
	**/
	
	//logija paika
	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		
		//Kasutame faili log4j.properties
		PropertyConfigurator.configure("log4j.properties");
		log.info("Intro initialized");
		
		//Loome uue IntroUI objekti ning muudame selle nähtavaks
		IntroUI theTeamInfo = new IntroUI("Rühma tutvustav aken");
		theTeamInfo.setVisible(true);
	}

}
