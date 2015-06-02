package upsidextre.comput.serveur;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import socketServer.Server;
import upsidextre.comput.entryPoint.UpsiDextre;
import upsidextre.comput.xml.sax.SAXHandler;

public class ServeurGants implements SerialPortEventListener {
	
	private static final int ETB = 23;

	private byte[] buffer = new byte[65535];

	private StringBuffer sb = new StringBuffer ();

	private boolean firstReading = true;

	private Enumeration<Object> ports;

	// map the port names to CommPortIdentifiers
	private HashMap<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();

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
		
		new Thread () {
			@Override
			public void run() {
				super.run();
				new Server (hardware).connect();
			}
		}.start();
		
		start();
	}

	/**
	 * Initialize the serial port to be read
	 */
	public void start() {

		searchForPorts();

		Object reponse = null;
		// Affiche les ports disponibles pour connexion
		do {
			if (portMap.keySet().isEmpty()) {
				System.out.println("please connect a device");
			} else {
				reponse = JOptionPane.showInputDialog(null, // composant parent
						"Choix du port série :", // message
						"Port Select", // titre
						JOptionPane.DEFAULT_OPTION, // type de dialogue
						null, // icone
						portMap.keySet().toArray(), // Liste de choix
						portMap.keySet().toArray()[0]); // Choix par défaut
			}
		} while (reponse == null); // To make sure user select a port

		connect((String)reponse);
		initIOStream();
		initListener();

		write();
	}

	// search for all the serial ports
	// post: adds all the found ports to a combo box on the GUI
	public void searchForPorts() {
		ports = CommPortIdentifier.getPortIdentifiers();

		while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();

			// get only serial ports
			if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portMap.put(curPort.getName(), curPort);
			}
		}
	}

	// connect to the selected port in the combo box
	// post: the connected comm port is stored in commPort, otherwise, an exception is generated
	public void connect(String selectedPort) {
		selectedPortIdentifier = (CommPortIdentifier) portMap.get(selectedPort);

		CommPort commPort = null;

		try {
			// the method below returns an object of type CommPort
			commPort = selectedPortIdentifier.open(this.getClass().getName(), TIMEOUT);

			// the CommPort object can be casted to a SerialPort object
			serialPort = (SerialPort) commPort;
			serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
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

	/**
	 * Initialize the listener that knows when data is available to be read
	 */
	public void initListener() {
		try {
			serialPort.addEventListener((SerialPortEventListener) this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			logText = "Too many listeners. (" + e.toString() + ")";
			System.err.println(logText);
		}
	}

	/**
	 * Disconnect the serial port
	 */
	public void disconnect() {
		// close the serial port
		try {

			serialPort.removeEventListener();
			serialPort.close();
			input.close();
			output.close();

			logText = "Disconnected.";
			System.err.println(logText);
		} catch (Exception e) {
			logText = "Failed to close " + serialPort.getName() + "("
					+ e.toString() + ")";
			System.err.println(logText);
		}
	}

	public void write () {
		try {
			output.write(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Event rose when data is received.</br>
	 * Define data processing
	 */
	@Override
	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int i = input.read(buffer, 0, buffer.length); 

				for (int j = 0; j < i; j++) { // For each char of the buffer
					if (buffer[j] == ETB) { // If it is an ETB (End of Transmission Block) character
						if (!firstReading) { // First reading got an very high chance of being incomplete because 
											 // the gloves are permanently sending the XML over Serial Port
							// if not the first reading, construct an InputStream from the reconstructed XML
							// necessary because XML parser can't read a String and Must be used with an Stream
							parse (new InputSource (new StringReader (sb.toString())));
						} else {
							firstReading = false;
						}
						sb = new StringBuffer(); // clearing of the buffer to prepare the construction of the next XML
					} else {
						sb.append((char)buffer[j]); // add the current char to the StringBuffer for reconstruction
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parse the input as an XML document and stock information into the UpsiDextre class tree
	 * @param input : input source where XML doc can be read
	 */
	public void parse (InputSource input) {
		try {
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			SAXHandler handler = new SAXHandler(hardware);
			parser.parse(input, handler);

		} catch (Exception e) {
			logText = "Failed to read data. (" + e.toString() + ")";
			e.printStackTrace();
		}
	}
}
