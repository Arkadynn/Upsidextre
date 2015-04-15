package upsidextre.comput.xml.sax;


import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import upsidextre.comput.entryPoint.UpsiDextre;
import upsidextre.comput.hardware.Gant;

/**

 */
public class SAXHandler extends DefaultHandler {


	private Stack<String> elementStack = new Stack<String>();
	private Stack<Object> objectStack  = new Stack<Object>();

	private UpsiDextre hardware;
	
	private Gant gant;
	
	private int x;
	private int y;

	public SAXHandler(UpsiDextre hardware) {
		this.hardware = hardware;
	}

	public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException {

		this.elementStack.push(qName);

		if (qName.equals("doigt")) {
			if (attributes.getValue("id").equals("index")) {
				this.objectStack.push(qName);
			}
		}
	}

	public void endElement(String uri, String localName,
			String qName) throws SAXException {

		this.elementStack.pop();

		if (qName.equals("doigt")) {
			this.objectStack.clear();
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {

		String value = new String(ch, start, length).trim();
		
		int valueI = Integer.parseInt(value);

		if (value.isEmpty()) return;
		
		switch (currentElement()) {
		case "flexion": 
			switch (xEmeParent(1)) {
			case "index":
				gant.getIndexe().setFlexion(valueI);
				break;
			case "majeur":
				gant.getMajeur().setFlexion(valueI);
				break;
			case "pouce":
				gant.getPouce().setFlexion(valueI);
				break;
			}
			if (!objectStack.isEmpty()) hardware.feedFinger(valueI);
			break;
		case "opposition":
			gant.getPouce().setOpposition(valueI);
			break;
		case "x":
			this.x = valueI;
			break;
		case "y":
			this.y = valueI;
			break;
		case "z":
			switch (xEmeParent(1)) {
			case "accelerometre":
				gant.getPosition().getAccelerometre().setX(x);
				gant.getPosition().getAccelerometre().setY(y);
				gant.getPosition().getAccelerometre().setZ(valueI);
				break;
			case "magnetometre":
				gant.getPosition().getMagnetometre().setX(x);
				gant.getPosition().getMagnetometre().setY(y);
				gant.getPosition().getMagnetometre().setZ(valueI);
				break;
			case "gyrometre":
				gant.getPosition().getGyroscope().setX(x);
				gant.getPosition().getGyroscope().setY(y);
				gant.getPosition().getGyroscope().setZ(valueI);
				break;
			}
			break;
		}
	}

	private String currentElement() {
		return this.elementStack.peek();
	}

	private String xEmeParent(int deg) {
		if(this.elementStack.size() < deg+1) return null;
		return this.elementStack.get(this.elementStack.size()-(deg+1));
	}

} 