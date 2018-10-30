@ReferenceData
@Release1 @me
Feature: Get Airport Market group areas

  @FCPH-493 @FCPH-499
  Scenario Outline: Market Data - Happy Path
    Given there is active market data
    When I successfully send the request to getMarketGroup service
    Then the correct market data is returned
  @AllChannels @random
  Examples: All channels
  | channels                                                             |
  | ADAirport, ADCustomerService, Digital, PublicApiMobile, PublicApiB2B |
  @ADChannels
  Examples: AD channels
  | channels                     |
  | ADAirport, ADCustomerService |
  @DigitalChannels
  @regression
  Examples: Digital channels
  | channels                 |
  | Digital, PublicApiMobile |
  @PublicApiB2BChannels
  Examples: PublicApiB2B channels
  | channels     |
  | PublicApiB2B |