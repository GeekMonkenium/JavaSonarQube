@ReferenceData
@Release1 @me
Feature: Get Airport Market group areas

  @PTR-001
  Scenario Outline: IEX Markets - GET Symbol
    Given I specify a valid "symbol"
    When I successfully send the request to getSymbol service
    Then the stock data for <symbol> is successfully returned
  Examples:
    | symbol|
    | AAPL  |

  Scenario: IEX Markets - GET with Query Params and Path Params

  Scenario : IEX Markets - POST