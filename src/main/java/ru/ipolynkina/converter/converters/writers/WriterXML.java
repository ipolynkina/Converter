package ru.ipolynkina.converter.converters.writers;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WriterXML extends Writer {

    public WriterXML(File outputFile) {
        super(outputFile);
    }

    @Override
    public void write(List<LinkedHashMap<String, String>> properties) throws Exception {
        Document xmlDoc = new Document();
        Element root = new Element("root");
        xmlDoc.setRootElement(root);

        for(LinkedHashMap<String, String> property : properties) {
            Element head = new Element("object");
            for(Map.Entry<String, String> pair : property.entrySet()) {
                head.setAttribute(pair.getKey(), pair.getValue());
            }
            root.addContent(head);
        }

        Format format = Format.getPrettyFormat();
        XMLOutputter serializer = new XMLOutputter(format);
        serializer.output(xmlDoc, new FileOutputStream(new File(getOutputFile().getAbsolutePath())));
    }
}
