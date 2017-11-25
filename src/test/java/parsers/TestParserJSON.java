package parsers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ipolynkina.converter.converters.parsers.ParserJSON;

import java.io.File;

public class TestParserJSON {

    private static ParserJSON parser;

    @BeforeClass
    public static void init() {
        parser = new ParserJSON(new File(MockObject.fileInJson));
    }

    @Test
    public void testParse() throws Exception {
        parser.parse();
        Assert.assertTrue(MockObject.propertiesIsCorrect(parser.getProperties()));
    }
}
