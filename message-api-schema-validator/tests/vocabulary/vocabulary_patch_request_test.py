from tests.abstract_schema_validator_test import AbstractSchemaValidatorTest
from unittest import TestCase


class VocabularyPatchRequestTest(AbstractSchemaValidatorTest, TestCase):
    def get_json_schema_file_name(self):
        return 'messages/body/vocabulary/patch/request_schema.json'

    def runTest(self):
        self.validate_json('messages/body/vocabulary/patch/request.json')
