from unittest import TestSuite, TextTestRunner
from tests.header import HeaderValidateTest
from tests.metadata import MetadataCreateRequestTest, MetadataReadRequestTest, MetadataReadResponseTest, \
    MetadataUpdateRequestTest, MetadataDeleteRequestTest
from tests.structure import MessageStructureTest
from tests.vocabulary import VocabularyReadRequestTest, VocabularyReadResponseTest, VocabularyPatchRequestTest


def suite():
    suite = TestSuite()

    suite.addTest(MetadataCreateRequestTest())
    suite.addTest(MetadataReadRequestTest())
    suite.addTest(MetadataReadResponseTest())
    suite.addTest(MetadataUpdateRequestTest())
    suite.addTest(MetadataDeleteRequestTest())

    suite.addTest(VocabularyReadRequestTest())
    suite.addTest(VocabularyReadResponseTest())
    suite.addTest(VocabularyPatchRequestTest())

    suite.addTest(HeaderValidateTest())

    suite.addTest(MessageStructureTest())

    return suite


if __name__ == '__main__':
    runner = TextTestRunner()
    test_suite = suite()
    runner.run(test_suite)
