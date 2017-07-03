import subprocess

import self
from behave import *

use_step_matcher("re")


@given("User write to the kinessis stream")
def step_impl(context):
    p = subprocess.Popen(
        'python write_stream.py rdss_qa_input_staging /Users/zakir.ahmed/workspace/RDSS-964/rdss-message-api-docs/messages/metadata/create/',
        shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    (out, err) = p.communicate()
    print("program output:", out)
    string = out.decode(encoding="utf-8", errors="strict")
    z = (string.split("\'"))
    global sequence_number
    sequence_number = (z[43])
    print(sequence_number)
    http_status = (z[10])
    assert http_status == ': 200, '

    pass


@when("User read from the kinesis stream")
def step_impl(context):
    print(sequence_number)
    z1 = subprocess.Popen('python read_stream.py rdss_qa_input_staging --sequence_number' + " " + sequence_number,
                          shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    (readout, err) = z1.communicate()
    print("program output2:", readout)
    global string
    string = readout.decode(encoding="utf-8", errors="strict")

    pass


@then("I validate the sequence number matches")
def step_impl(context):

    z2 = (string.split("u'"))
    print(z2[3])
    print(z2[5])
    pass
