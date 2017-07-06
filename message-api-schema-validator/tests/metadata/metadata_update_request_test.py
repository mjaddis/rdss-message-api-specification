from tests.abstract_schema_validator_test import AbstractSchemaValidatorTest
from unittest import TestCase


class MetadataUpdateRequestTest(AbstractSchemaValidatorTest, TestCase):
    def get_json_schema_file_name(self):
        return 'messages/metadata/update/request_schema.json'

    def runTest(self):
        self.validate_json('messages/metadata/update/request.json')
