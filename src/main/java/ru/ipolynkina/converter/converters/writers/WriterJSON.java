package ru.ipolynkina.converter.converters.writers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WriterJSON extends Writer {

    public WriterJSON(File outputFile) {
        super(outputFile);
    }

    @Override
    public void write(List<LinkedHashMap<String, String>> properties) throws Exception {
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator = factory.createGenerator(new File(getOutputFile().getAbsolutePath()), JsonEncoding.UTF8);
        for(LinkedHashMap<String, String> property : properties) {
            generator.writeStartObject();
            for(Map.Entry<String, String> pair : property.entrySet()) {
                generator.writeStringField(pair.getKey(), pair.getValue());
            }
            generator.writeEndObject();
            generator.writeRaw("\n");
        }
        generator.close();
   }
}
