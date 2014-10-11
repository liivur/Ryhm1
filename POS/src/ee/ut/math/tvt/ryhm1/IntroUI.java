package ee.ut.math.tvt.ryhm1;


import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class IntroUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	//logija paika
	private static final Logger log = LogManager.getLogger(Intro.class);

	//Muutujad hilisema aknasisu tarbeks
	String name;
	String leader;
	String email ;
	String teamMembers;
	String logo;
	String version;

	//loome seaded
	Properties properties = new Properties();



	public IntroUI(String title) {

		super(title);
		
		//loeme sisse info
		log.info("UI initialized");
		
		this.setSize(265, 550);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		Properties readProperties = new Properties();
		Properties readVersion  = new Properties();
		try {
//			readProperties.load(new FileInputStream("application.properties"));
//			readVersion.load(new FileInputStream("version.properties"));
//			InputStream resource = getClass().getResourceAsStream("/application.properties");
//			System.out.println(resource);
//			System.out.println(resource.available());
			readProperties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
			readVersion.load(getClass().getClassLoader().getResourceAsStream("version.properties"));
		} catch (IOException e) {
			log.error("There seems to be an error with porperties files");
			log.error(e.getLocalizedMessage(), e);
		}
				
		makeUserInterface(readProperties, readVersion);
		
		log.info("Window creation completed");
	}
	
	private void makeUserInterface(Properties readProperites, Properties readVersion ) {
		
		this.setLayout(new GridLayout(0, 1));
			
		Container screenTable = this.getLayeredPane();
		screenTable.setLayout(new BoxLayout(screenTable, BoxLayout.Y_AXIS));
		
		Font shape = new Font("ROMAN_BASELINE",1,16);
		
		name = readProperites.getProperty("Team_name");
		screenTable.add(new JLabel("*TEAM NAME: " +name)).setFont(shape);
		screenTable.add(new JLabel("--"));
		leader = readProperites.getProperty("Team_leader");
		screenTable.add(new JLabel("*LEADER: " +leader)).setFont(shape);
		screenTable.add(new JLabel("--"));
		email =  readProperites.getProperty("Leader_email");
		screenTable.add(new JLabel("*LEADER'S EMAIL: ")).setFont(shape);
		screenTable.add(new JLabel(email)).setFont(shape);
		screenTable.add(new JLabel("--"));
		teamMembers =  readProperites.getProperty("Team");
		String delimiter = ", ";
		String[] temp;
		temp = teamMembers.split(delimiter);
		String e = temp[0];
		String s = temp[1];
		String r = temp[2];
		String d = temp[3];
		
		screenTable.add(new JLabel("*TEAM MEMBERS: ")).setFont(shape);
		screenTable.add(new JLabel("  1."+e)).setFont(shape);
		screenTable.add(new JLabel("  2."+s)).setFont(shape);
		screenTable.add(new JLabel("  3."+r)).setFont(shape);
		screenTable.add(new JLabel("  4."+d)).setFont(shape);
		logo =  readProperites.getProperty("logo");	
		screenTable.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(logo))));
		version = readVersion.getProperty("build.number");
		screenTable.add(new JLabel("Version: " +version)).setFont(shape);	
	}
		
}


