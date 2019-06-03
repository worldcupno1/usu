package xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

/**
 * Title:  xml文件解析<br>
 * Description: <br>
 * Date: 2019/6/3 11:53<br>
 * Copyright (c)   <br>
 *
 * @author worldcupno1
 */

public class XmlParse {
    Logger log = LogManager.getLogger(XmlParse.class);

    /**
     * 解析xml
     */
    @Test
    public void strParse() throws DocumentException {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><commands><command condition=\"text\" targetIndex=\"0\" action=\"input\" value=\"minchen\"></command><command condition=\"button\" targetIndex=\"0\" action=\"click\" value=\"\"></command></commands>";
        Document document = DocumentHelper.parseText(xmlStr);
        Element root = document.getRootElement();
        log.debug(root.elementText("command"));
        Iterator<Element> elementIterator = root.elementIterator();

        while (elementIterator.hasNext()) {
//            log.debug("in");
            Element commandElem = elementIterator.next();
            System.out.println(commandElem.attributeValue("condition"));
            System.out.println(commandElem.attributeValue("targetIndex"));
            System.out.println(commandElem.attributeValue("action"));
            System.out.println(commandElem.attributeValue("value"));
        }
    }
}
