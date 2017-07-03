@wip
Feature: Write Read Kinessis stream

 @test
 Scenario: verify sequence number in read
  Given User write to the kinessis stream
  When User read from the kinesis stream
  Then I validate the sequence number matches


