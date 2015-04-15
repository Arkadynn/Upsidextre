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

	Gant gant;

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

		if (value.isEmpty()) return;
		
		switch (currentElement()) {
		case "flexion": if (!objectStack.isEmpty()) hardware.feedFinger(Integer.parseInt(value));
		}
	}

	private String currentElement() {
		return this.elementStack.peek();
	}

	private String currentElementParent() {
		if(this.elementStack.size() < 2) return null;
		return this.elementStack.get(this.elementStack.size()-2);
	}

} 