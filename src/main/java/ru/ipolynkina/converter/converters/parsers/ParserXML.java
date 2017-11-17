package ru.ipolynkina.converter.converters.parsers;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import ru.ipolynkina.converter.converters.propertys.Property;

import java.io.File;
import java.util.List;

public class ParserXML extends Parser {

    public ParserXML(File inputFile) {
        super(inputFile);
    }

    @Override
    public void parse() throws Exception {
        Element rootElement = new SAXBuilder().build(getInputFile()).getRootElement();
        List<Element> elements = rootElement.getChildren();

        for(Element element : elements) {
            Property property = new Property();

            List<Attribute> attributes = element.getAttributes();
            if(attributes.size() != 0) {
                for(Attribute attribute : attributes) {
                    property.addProperties(attribute.getName(), attribute.getValue());
                }
            }

            List<Element> children = element.getChildren();
            for(Element child : children) {
                property.addProperties(child.getName(), child.getValue());
            }

            addProperty(property);
        }
    }
}
