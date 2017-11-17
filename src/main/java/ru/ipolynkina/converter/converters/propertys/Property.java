package ru.ipolynkina.converter.converters.propertys;

import java.util.LinkedHashMap;
import java.util.Map;

public class Property {

    private Map<String, String> properties;

    public Property() {
        properties = new LinkedHashMap<>();
    }

    public void addProperties(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry property : properties.entrySet()) {
            sb.append(property.getKey());
            sb.append(" : ");
            sb.append(property.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }
}
