from abc import ABCMeta
import boto3
import logging
import sys
import json
from os.path import expanduser


class ClientStream:
    __metaclass__ = ABCMeta

    def __init__(self):
        self.init_logging()
        self.client = self.init_client()

    def init_logging(self):
        # Set up the logging
        stream_handler = logging.StreamHandler(sys.stdout)
        stream_handler.setLevel(logging.INFO)
        stream_handler.setFormatter(logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s'))
        root_logger = logging.getLogger()
        root_logger.setLevel(logging.INFO)
        root_logger.addHandler(stream_handler)

    def init_client(self):
        # Read the ~/.aws/mfa_credentials file to extract the AWS credentials
        with open(expanduser("~") + "/.aws/mfa_credentials") as mfa_credentials_file:
            mfa_credentials = json.load(mfa_credentials_file)
            access_key_id = mfa_credentials["Credentials"]["AccessKeyId"]
            secret_access_key = mfa_credentials["Credentials"]["SecretAccessKey"]
            session_token = mfa_credentials["Credentials"]["SessionToken"]

        # Create the Boto3 client for Kinesis
        return boto3.client(
            'kinesis',
            aws_access_key_id=access_key_id,
            aws_secret_access_key=secret_access_key,
            aws_session_token=session_token
        )
