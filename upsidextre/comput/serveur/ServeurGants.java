package upsidextre.comput.serveur;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import upsidextre.comput.entryPoint.UpsiDextre;
import upsidextre.comput.xml.sax.SAXHandler;

public class ServeurGants implements SerialPortEventListener {

	private byte[] buffer = new byte[1024];

	private Enumeration<Object> ports;

	// map the port names to CommPortIdentifiers
	private HashMap<String, CommPortIdentifier> portMap = new HashMap();

	// this is the object that contains the opened port
	private CommPortIdentifier selectedPortIdentifier = null;
	private SerialPort serialPort = null;

	// input and output streams for sending and receiving data
	private InputStream input = null;
	private OutputStream output = null;

	// just a boolean flag that i use for enabling
	// and disabling buttons depending on whether the program
	// is connected to a serial port or not
	private boolean bConnected = false;

	// the timeout value for connecting with the port
	final static int TIMEOUT = 2000;

	// some ascii values for for certain things
	final static int SPACE_ASCII = 32;
	final static int DASH_ASCII = 45;
	final static int NEW_LINE_ASCII = 10;

	// a string for recording what goes on in the program
	// this string is written to the GUI
	String logText = "";

	private UpsiDextre hardware;

	public ServeurGants(UpsiDextre hardware) {
		this.hardware = hardware;
	}

	public void run() {

		System.out.println("enter");

		// *
		searchForPorts();
		
		if (portMap.keySet().isEmpty()) {
			System.out.println("please connect a device");
		}
		
		Object reponse;
		// Affiche les ports disponibles pour connexion
		do {
			reponse = JOptionPane.showInputDialog(null, // composant parent
					"Choix du port s�rie :", // message
					"Port Select", // titre
					JOptionPane.DEFAULT_OPTION, // type de dialogue
					null, // icone
					portMap.keySet().toArray(), // Liste de choix
					portMap.keySet().toArray()[0]); // Choix par
			// d�faut
		} while (reponse == null);
		
		System.out.println(reponse);
		connect((String)reponse);
		initIOStream();
		initListener();
		// */
		// *
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			SAXHandler handler = new SAXHandler(hardware);
			parser.parse(input, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// */
		if (!bConnected)
			disconnect();
	}

	// search for all the serial ports
	// pre style="font-size: 11px;": none
	// post: adds all the found ports to a combo box on the GUI
	public void searchForPorts() {
		ports = CommPortIdentifier.getPortIdentifiers();

		while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier) ports
					.nextElement();

			// get only serial ports
			if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portMap.put(curPort.getName(), curPort);
			}
		}
	}

	// connect to the selected port in the combo box
	// pre style="font-size: 11px;": ports are already found by using the
	// searchForPorts
	// method
	// post: the connected comm port is stored in commPort, otherwise,
	// an exception is generated
	public void connect(String selectedPort) {
		selectedPortIdentifier = (CommPortIdentifier) portMap.get(selectedPort);

		CommPort commPort = null;

		try {
			// the method below returns an object of type CommPort
			commPort = selectedPortIdentifier
					.open("TigerControlPanel", TIMEOUT);
			// the CommPort object can be casted to a SerialPort object
			serialPort = (SerialPort) commPort;
			// for controlling GUI elements
			bConnected = true;

			// logging
			logText = selectedPort + " opened successfully.";
			System.out.println(logText);

		} catch (PortInUseException e) {
			logText = selectedPort + " is in use. (" + e.toString() + ")";
			System.err.println(logText);
		} catch (Exception e) {
			logText = "Failed to open " + selectedPort + "(" + e.toString()
					+ ")";
			System.err.println(logText);
		}
	}

	// open the input and output streams
	// pre style="font-size: 11px;": an open port
	// post: initialized input and output streams for use to communicate data
	public boolean initIOStream() {
		// return value for whether opening the streams is successful or not
		boolean successful = false;

		try {
			//
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			successful = true;
			return successful;
		} catch (IOException e) {
			logText = "I/O Streams failed to open. (" + e.toString() + ")";
			System.err.println(logText);
			return successful;
		}
	}

	// starts the event listener that knows whenever data is available to be
	// read
	// pre style="font-size: 11px;": an open serial port
	// post: an event listener for the serial port that knows when data is
	// received
	public void initListener() {
		try {
			serialPort.addEventListener((SerialPortEventListener) this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			logText = "Too many listeners. (" + e.toString() + ")";
			System.err.println(logText);
		}
	}

	// disconnect the serial port
	// pre style="font-size: 11px;": an open serial port
	// post: closed serial port
	public void disconnect() {
		// close the serial port
		try {

			serialPort.removeEventListener();
			serialPort.close();
			input.close();
			output.close();
			bConnected = false;

			logText = "Disconnected.";
			System.err.println(logText);
		} catch (Exception e) {
			logText = "Failed to close " + serialPort.getName() + "("
					+ e.toString() + ")";
			System.err.println(logText);
		}
	}

	// what happens when data is received
	// pre style="font-size: 11px;": serial event is triggered
	// post: processing on the data it reads
	@Override
	public void serialEvent(SerialPortEvent evt) {
		// *
		if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				//*
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				SAXHandler handler = new SAXHandler(hardware);
				parser.parse(input, handler);
				// */
				/*
				 int i = input.read(buffer, 0, buffer.length); char[]
				 xmlReaded = new char[i]; for (int j = 0; j < i; j++) {
				 xmlReaded[j] = (char)buffer[j]; }
				 
				 System.out.println(String.valueOf(xmlReaded));
				 
				 //*/

			} catch (Exception e) {
				logText = "Failed to read data. (" + e.toString() + ")";
				System.err.println(logText);
			}
		}
		// */
	}
}
