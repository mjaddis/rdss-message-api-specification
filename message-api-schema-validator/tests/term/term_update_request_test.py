from tests.abstract_schema_validator_test import AbstractSchemaValidatorTest
from unittest import TestCase


class TermUpdateRequestTest(AbstractSchemaValidatorTest, TestCase):
    def get_json_schema_file_name(self):
        return "messages/term/update/request_schema.json"

    def test_validate_request(self):
        self.validate_json("messages/term/update/request.json")
