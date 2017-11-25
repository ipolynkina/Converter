package parsers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ipolynkina.converter.converters.parsers.ParserXLS;

import java.io.File;

public class TestParserXLS {

    private static ParserXLS parser;

    @BeforeClass
    public static void init() {
        parser = new ParserXLS(new File(MockObject.fileInXLS));
    }

    @Test
    public void testParse() throws Exception {
        parser.parse();
        Assert.assertTrue(MockObject.propertiesIsCorrect(parser.getProperties()));
    }
}
