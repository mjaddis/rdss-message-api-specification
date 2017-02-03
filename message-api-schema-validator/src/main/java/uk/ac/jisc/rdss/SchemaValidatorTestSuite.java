package uk.ac.jisc.rdss;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.ac.jisc.rdss.tests.metadata.create.MetadataCreateTest;
import uk.ac.jisc.rdss.tests.metadata.update.MetadataUpdateTest;
import uk.ac.jisc.rdss.tests.term.create.TermCreateTest;
import uk.ac.jisc.rdss.tests.term.update.TermUpdateTest;
import uk.ac.jisc.rdss.tests.vocabulary.create.VocabularyCreateTest;
import uk.ac.jisc.rdss.tests.vocabulary.update.VocabularyUpdateTest;

@RunWith(Suite.class)
@SuiteClasses({
    MetadataCreateTest.class,
    MetadataUpdateTest.class,
    TermCreateTest.class,
    TermUpdateTest.class,
    VocabularyCreateTest.class,
    VocabularyUpdateTest.class
})
public class SchemaValidatorTestSuite {

    public static void main(String[] args) {
        JUnitCore.main(SchemaValidatorTestSuite.class.getName());
    }
}
