package uk.ac.jisc.rdss;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.ac.jisc.rdss.tests.metadata.create.MetadataCreateRequestTest;
import uk.ac.jisc.rdss.tests.metadata.delete.MetadataDeleteRequestTest;
import uk.ac.jisc.rdss.tests.metadata.read.MetadataReadRequestTest;
import uk.ac.jisc.rdss.tests.metadata.read.MetadataReadResponseTest;
import uk.ac.jisc.rdss.tests.metadata.update.MetadataUpdateRequestTest;
import uk.ac.jisc.rdss.tests.term.create.TermCreateRequestTest;
import uk.ac.jisc.rdss.tests.term.update.TermUpdateRequestTest;
import uk.ac.jisc.rdss.tests.vocabulary.create.VocabularyCreateRequestTest;
import uk.ac.jisc.rdss.tests.vocabulary.update.VocabularyUpdateRequestTest;

@RunWith(Suite.class)
@SuiteClasses({
    MetadataCreateRequestTest.class,
    MetadataUpdateRequestTest.class,
    MetadataDeleteRequestTest.class,
    MetadataReadRequestTest.class,
    MetadataReadResponseTest.class,
    TermCreateRequestTest.class,
    TermUpdateRequestTest.class,
    VocabularyCreateRequestTest.class,
    VocabularyUpdateRequestTest.class
})
public class SchemaValidatorTestSuite {

    public static void main(String[] args) {
        JUnitCore.main(SchemaValidatorTestSuite.class.getName());
    }
}
