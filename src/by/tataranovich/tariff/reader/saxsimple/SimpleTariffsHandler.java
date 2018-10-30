package by.tataranovich.tariff.reader.saxsimple;



import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class SimpleTariffsHandler extends DefaultHandler {

    @Override
    public void startDocument() {
	System.out.println("Parsing started");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
	String s = localName;
	// получение и вывод информации об атрибутах элемента
	for (int i = 0; i < attrs.getLength(); i++) {
	    s += " " + attrs.getLocalName(i) + "=" + attrs.getValue(i);
	}
	System.out.print(s.trim());
    }

    @Override
    public void characters(char[] ch, int start, int length) {
	System.out.print(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
	System.out.print(localName);
    }

    @Override
    public void endDocument() {
	System.out.println("\nParsing ended");
    }
}
