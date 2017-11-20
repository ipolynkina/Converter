package ru.ipolynkina.converter.converters.parsers;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.List;
import java.util.LinkedHashMap;

public class ParserXML extends Parser {

    public ParserXML(File inputFile) {
        super(inputFile);
    }

    @Override
    public void parse() throws Exception {
        Element rootElement = new SAXBuilder().build(getInputFile()).getRootElement();
        List<Element> elements = rootElement.getChildren();

        for(Element element : elements) {
            LinkedHashMap<String, String> property = new LinkedHashMap<>();

            List<Attribute> attributes = element.getAttributes();
            if(attributes.size() != 0) {
                for(Attribute attribute : attributes) {
                    property.put(attribute.getName(), attribute.getValue());
                }
            }

            List<Element> childrenElements = element.getChildren();
            for(Element childElement : childrenElements) {
                property.put(childElement.getName(), childElement.getValue());
            }

            addProperty(property);
        }
    }
}
