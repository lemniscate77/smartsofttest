package com.example.SmartSoftTestv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConvertToJavaObj {   // PoJo класс для преобразовния из ХМЛ в Java Объект - Valute
    private List<Valute> valutes; // массив валют
    private String date; // дата актуальной информации
    public void getObj() throws ParseException, IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream()); // считывание хмл
        valutes = parseValuteXML(doc);

    }
    public List<Valute> getValute(){
        return valutes;
    }
    public String getDate(){
        return date;
    }


    private List<Valute> parseValuteXML(Document document) throws ParseException {
        List<Valute> valcurs = new ArrayList<>();
        Valute valute;
        document.getDocumentElement().normalize();
        date = document.getDocumentElement().getAttribute("Date");
        //valute.setTodayDate(date);

        NodeList nList = document.getElementsByTagName("Valute");
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) // вытаскиваем из хмл документа то что нам надо
            {
                Element eElement = (Element) node;
                //Create new Valute object
                valute = new Valute();
                valute.setValuteID(eElement.getAttribute("ID"));
                valute.setCharCode(eElement.getElementsByTagName("CharCode").item(0).getTextContent());
                valute.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                Number number = format.parse(eElement.getElementsByTagName("Value").item(0).getTextContent());
                double d = number.doubleValue();
                valute.setValue(d);
                valcurs.add(valute);
            }
        }
        return valcurs;
    }
}
