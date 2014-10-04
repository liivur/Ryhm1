package ee.ut.math.tvt.ryhm1;

//kasutada on vaja jpanel ja pilti 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.awt.Font;

import java.util.Properties;

//vaja IO-d
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;


public class IntroUI extends JFrame {
	
	//logija paika
	private static final Logger log = Logger.getLogger(Intro.class);
	
	public IntroUI(String title) {	
		//Pealkirja saame Inro klassis IntroUI väljakutsega
		super(title);
		
		//loeme sisse info
		log.info("UI initialized");
		Properties readVersion = readPropertiesFromFile("version.properties");
		Properties readProperties = readPropertiesFromFile("application.properties");
		makeUserInterface(readProperties, readVersion);
		
		//Paneeli mõõdud		
		this.setSize(600, 400);
		this.setLocation(300, 300);
		
		//Sulgemisel sulgeme rakenduse
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Meetod avaakna jaoks
	void makeUserInterface(Properties readProperties, Properties readVersion) {
		
		//Paneme paika liidese
		//TODO :)
		
		//Pealkirjad 
		final String[] headings = {"Team_name","Team_leader","Leader_email","Team"};
		
	}
		//Kirjutame/loeme properties info välja
	Properties readPropertiesFromFile(String filename) {
		return null;
		//TODO :)
	}

}
