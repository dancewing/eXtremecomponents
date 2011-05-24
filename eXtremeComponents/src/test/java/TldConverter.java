import org.extremecomponents.util.XmlUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: jeff
 * Date: 11-5-14
 * Time: 上午3:27
 */
public class TldConverter {
    @Test
    public void test() throws Exception {
        Document document = XmlUtils.readXml(this.getClass().getResourceAsStream("extremecomponents.tld"));
        List<Element> elementList = XmlUtils.findElements("tag", document.getDocumentElement());
        for (Element ele : elementList) {
            exchange(ele);

            List<Element> attributeList = XmlUtils.findElements("attribute", ele);
            for (Element attribute : attributeList) {
                exchange(attribute);
            }

            Node display  = XmlUtils.findNode("display-name",ele);
            if (display!=null) {
                 ele.removeChild(display);
            }

        }
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");

        XmlUtils.writeXml(transformer, new FileOutputStream(new File("extreme_new.tld")), document);

    }

    private void exchange(Element ele) {
            Node name = XmlUtils.findNode("name",ele);
            Node description = XmlUtils.findNode("description", ele);
            Node newDesc = description.cloneNode(true);
            ele.insertBefore(newDesc, name);
            ele.removeChild(description);

    }
}
