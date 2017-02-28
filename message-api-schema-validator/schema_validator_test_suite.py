from unittest import TestSuite, TextTestRunner
from tests.metadata import MetadataCreateRequestTest, MetadataReadRequestTest, MetadataReadResponseTest, \
    MetadataUpdateRequestTest, MetadataDeleteRequestTest
from tests.term import TermCreateRequestTest, TermReadRequestTest, TermReadResponseTest, TermUpdateRequestTest, \
    TermDeleteRequestTest
from tests.vocabulary import VocabularyCreateRequestTest, VocabularyReadRequestTest, VocabularyReadResponseTest, \
    VocabularyUpdateRequestTest, VocabularyDeleteRequestTest


class SchemaValidatorTestSuite:
    def suite(self):
        suite = TestSuite()

        suite.addTest(MetadataCreateRequestTest)
        suite.addTest(MetadataReadRequestTest)
        suite.addTest(MetadataReadResponseTest)
        suite.addTest(MetadataUpdateRequestTest)
        suite.addTest(MetadataDeleteRequestTest)

        suite.addTest(TermCreateRequestTest)
        suite.addTest(TermReadRequestTest)
        suite.addTest(TermReadResponseTest)
        suite.addTest(TermUpdateRequestTest)
        suite.addTest(TermDeleteRequestTest)

        suite.addTest(VocabularyCreateRequestTest)
        suite.addTest(VocabularyReadRequestTest)
        suite.addTest(VocabularyReadResponseTest)
        suite.addTest(VocabularyUpdateRequestTest)
        suite.addTest(VocabularyDeleteRequestTest)

        return suite


if __name__ == '__main__':
    runner = TextTestRunner()
    test_suite = SchemaValidatorTestSuite.suite()
    runner.run(test_suite)
