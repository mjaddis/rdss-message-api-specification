import logging
import argparse
import json
import glob
from client_stream import ClientStream


class WriteStream(ClientStream):
    def __init__(self):
        ClientStream.__init__(self)
        # Extract the program arguments
        args = self.parse_args()
        stream_name = args.stream_name
        json_dir = args.json_dir
        # Write the messages to the stream
        self.write_messages(stream_name, json_dir)

    def parse_args(self):
        # Set up the argument parser
        parser = argparse.ArgumentParser()
        parser.add_argument("stream_name", help="the name of the stream to poll")
        parser.add_argument("json_dir", help="the directory containing the JSON files to put on the stream")
        return parser.parse_args()

    def write_messages(self, stream_name, json_dir):
        # Read all files from the JSON directory that end with '.json'
        json_file_names = glob.glob(json_dir + "*.json")
        for json_file_name in json_file_names:
            with open(json_file_name) as json_file:
                json_data = json.load(json_file)
                response = self.client.put_record(
                    StreamName=stream_name,
                    Data=str(json_data),
                    PartitionKey="test-data"
                )
                logging.info("Put message on stream [%s] with response [%s]", stream_name, response)


WriteStream()
