package parsers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MockObject {

    public final static String fileInJson = "src/main/testResources/example.json";
    public final static String fileInXLS = "src/main/testResources/example.xls";
    public final static String fileInXLSX = "src/main/testResources/example.xlsx";
    public final static String fileInXML = "src/main/testResources/example.xml";

    private static List<LinkedHashMap<String, String>> properties = init();

    private static List<LinkedHashMap<String, String>> init() {
        List<LinkedHashMap<String, String>> properties = new ArrayList<>();

        LinkedHashMap<String, String> property = new LinkedHashMap<>();
        property.put("Id", "15.0");
        property.put("Name", "Andrei");
        property.put("Address", "Krasnodar");
        property.put("Age", "28.0");
        property.put("Weight", "85.5");
        property.put("HasAFamily", "true");
        property.put("Points", "155.0");
        properties.add(property);

        property = new LinkedHashMap<>();
        property.put("Id", "86.0");
        property.put("Name", "Max");
        property.put("Address", "Moscow");
        property.put("Age", "18.0");
        property.put("Weight", "70.6");
        property.put("HasAFamily", "false");
        property.put("Points", "148.0");
        properties.add(property);

        property = new LinkedHashMap<>();
        property.put("Id", "59.0");
        property.put("Name", "Anna");
        property.put("Address", "Volgograd");
        property.put("Age", "24.0");
        property.put("Weight", "60.4");
        property.put("HasAFamily", "false");
        property.put("Points", "136.0");
        properties.add(property);

        property = new LinkedHashMap<>();
        property.put("Id", "6.0");
        property.put("Name", "David");
        property.put("Address", "Tambow");
        property.put("Age", "35.0");
        property.put("Weight", "90.1");
        property.put("HasAFamily", "");
        property.put("Points", "120.0");
        properties.add(property);

        property = new LinkedHashMap<>();
        property.put("Id", "130.0");
        property.put("Name", "Oksana");
        property.put("Address", "Vladimir");
        property.put("Age", "20.0");
        property.put("Weight", "59.9");
        property.put("HasAFamily", "true");
        property.put("Points", "119.0");
        properties.add(property);

        return properties;
    }

    private static void printErrorMessage(Map<String, String> property, Map<String, String> incorrectData) {
        System.out.println("correct property:   " + property);
        System.out.println("incorrect property: " + incorrectData);
    }

    public static boolean propertiesIsCorrect(List<LinkedHashMap<String, String>> testData) {
        int indexRow = 0;
        for(Map<String, String> testObject : testData) {
            Map<String, String> property = properties.get(indexRow);
            for(Map.Entry<String, String> entry : testObject.entrySet()) {
                if(!property.containsKey(entry.getKey()) || (!property.containsValue(entry.getValue()))) {
                    printErrorMessage(property, testObject);
                }
            }
            ++indexRow;
        }
        return true;
    }
}
