package parsers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ipolynkina.converter.converters.parsers.ParserXLSX;

import java.io.File;

public class TestParserXLSX {

    private static ParserXLSX parser;

    @BeforeClass
    public static void init() {
        parser = new ParserXLSX(new File(MockObject.fileInXLSX));
    }

    @Test
    public void testParse() throws Exception {
        parser.parse();
        Assert.assertTrue(MockObject.propertiesIsCorrect(parser.getProperties()));
    }
}
