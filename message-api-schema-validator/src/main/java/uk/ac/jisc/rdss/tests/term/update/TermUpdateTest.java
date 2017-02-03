package uk.ac.jisc.rdss.tests.term.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermUpdateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/update/schema.json";
    }

    @Test
    public void validateExample() throws Exception {
        validateJson("messages/term/update/example.json");
    }
}
