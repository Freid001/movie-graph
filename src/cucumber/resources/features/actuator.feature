Feature: Actuator

Scenario: can retrieve health
  Given I request "/actuator/health" using HTTP GET
  Then the response code is 200
