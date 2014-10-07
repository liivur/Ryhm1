package ee.ut.math.tvt.ryhm1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;


public class Intro {
	//loogika paika
	private static final Logger log = LogManager.getLogger(Intro.class);

	public static void main(String[] args) throws IOException {
		
		//Kasutame faili log4j.properties
		
		//TODO: Fix configuration for the log4j
//		InputStream logConfigurationFile =  new FileInputStream(new File("etc/log4j2.xml"));
//		ConfigurationSource source = new ConfigurationSource(logConfigurationFile);
//		Configurator.initialize(null, source);
		//PropertyConfigurator.configure("log4j.properties");
		log.info("Intro initialized");
		
		//Loome uue IntroUI objekti ning muudame selle n�htavaks
		IntroUI theTeamInfo = new IntroUI("Team Introduction");
		theTeamInfo.setVisible(true);
		theTeamInfo.setAlwaysOnTop(true);
	}

}
