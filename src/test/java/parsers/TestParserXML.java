package parsers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ipolynkina.converter.converters.parsers.ParserXML;

import java.io.File;

public class TestParserXML {

    private static ParserXML parser;

    @BeforeClass
    public static void init() {
        parser = new ParserXML(new File(MockObject.fileInXML));
    }

    @Test
    public void testParse() throws Exception {
        parser.parse();
        Assert.assertTrue(MockObject.propertiesIsCorrect(parser.getProperties()));
    }
}
