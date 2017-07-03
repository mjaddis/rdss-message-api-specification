import logging
import argparse
from time import sleep
from client_stream import ClientStream


class ReadStream(ClientStream):
    def __init__(self):
        ClientStream.__init__(self)
        # Extract the program arguments
        args = self.parse_args()
        stream_name = args.stream_name
        sequence_number = args.sequence_number
        # Get a handle on the Shard ID
        shard_id = self.get_shard_id(stream_name)
        # Get a handle on the Shard Iterator
        shard_iterator = self.get_shard_iterator(stream_name, shard_id, sequence_number)
        # Begin polling the stream
        self.poll_stream(shard_iterator)

    def parse_args(self):
        # Set up the argument parser
        parser = argparse.ArgumentParser()
        parser.add_argument("stream_name", help="the name of the stream to poll")
        parser.add_argument("--sequence_number", help="the sequence number of the record where polling should begin")
        return parser.parse_args()

    def get_shard_id(self, stream_name):
        # Start by describing the Kinesis stream to extract the Shard ID
        response = self.client.describe_stream(
            StreamName=stream_name
        )
        return response["StreamDescription"]["Shards"][0]["ShardId"]

    def get_shard_iterator(self, stream_name, shard_id, sequence_number):
        # Get the initial shard_iterator, which will get all untrimmed records from the Kinesis stream
        if not sequence_number:
            response = self.client.get_shard_iterator(
                StreamName=stream_name,
                ShardId=shard_id,
                ShardIteratorType='TRIM_HORIZON'
            )
        else:
            response = self.client.get_shard_iterator(
                StreamName=stream_name,
                ShardId=shard_id,
                ShardIteratorType='AT_SEQUENCE_NUMBER',
                StartingSequenceNumber=sequence_number
            )
        return response["ShardIterator"]

    def poll_stream(self, shard_iterator):
        try:
            bool
            caught_up = False
            while True:
                response = self.client.get_records(
                    ShardIterator=shard_iterator,
                    Limit=100
                )
                records = response["Records"]
                if len(records) > 0:
                    caught_up = False
                    logging.info("Got %s records:", len(records))
                    for record in records:
                        logging.info("\t" + str(record))
                shard_iterator = response["NextShardIterator"]
                millis_behind_latest = response["MillisBehindLatest"]
                if millis_behind_latest > 0:
                    logging.info("Currently %s milliseconds from the tip of the stream, catching up...",
                                 millis_behind_latest)
                    caught_up = False
                elif millis_behind_latest == 0 and not caught_up:
                    logging.info("Caught up with the tip of the stream, will continue to poll...")
                    caught_up = False
                sleep(0.2)
        except KeyboardInterrupt:
            pass


ReadStream()
