package ru.ipolynkina.converter.converters.parsers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ParserJSON extends Parser {

    public ParserJSON(File inputFile) {
        super(inputFile);
    }

    @Override
    public void parse() throws Exception {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new File(getInputFile().getAbsolutePath()));
        while(parser.nextToken() != null) {
            LinkedHashMap<String, String> property = new LinkedHashMap<>();
            while(parser.nextToken() != JsonToken.END_OBJECT) {
                String key = parser.getText();
                String value = readValue(parser);
                property.put(key, value);
            }
            addProperty(property);
        }
        parser.close();
    }

    private String readValue(JsonParser parser) throws IOException {
        String value;
        JsonToken currentToken = parser.nextToken();
        if(currentToken.isBoolean()) {
            value = String.valueOf(parser.getBooleanValue());
        } else if(currentToken.isNumeric()) {
            value = String.valueOf(parser.getDoubleValue());
        } else {
            value = parser.getText();
        }
        return value;
    }
}
