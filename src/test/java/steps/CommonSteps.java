package steps;

import com.hybris.easyjet.TestApplication;
import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.config.SerenityReporter;
import com.hybris.easyjet.exceptions.MissingDataException;
import com.hybris.easyjet.fixture.IService;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import com.hybris.easyjet.fixture.hybris.invoke.services.HybrisService;
import constants.ErrorCodes;
import constants.StepsRegex;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.hybris.easyjet.config.SerenityFacade.DataKeys.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * CommonSteps contains the step used across multiple tests.
 * It contains methods to set channels and headers, and method to check common part of the responses (i.e. errors and additional informations).
 */
@ContextConfiguration(classes = TestApplication.class)
public class CommonSteps {

    private static final Logger LOG = LogManager.getLogger(CommonSteps.class);

    @Autowired
    private SerenityFacade testData;
    @Steps
    private SerenityReporter reporter;

    private IService service;

    private String selectRandomChannel(String[] channels) {
        int rnd = new Random().nextInt(channels.length);
        reporter.info(channels[rnd] + " channel selected");
        return channels[rnd];
    }

    private String channelRandomizer(String[] channels) {
        String channel = selectRandomChannel(channels);
        channelSelection(channel);
        return channel;
    }


    //  ----- channel/header initialization -----
    //Do not perform agent login in this step
    @Step("Channel {0} used")
    @Given("^the channel " + StepsRegex.CHANNELS + " is used$")
    public void channelSelection(String channel) {
        HybrisService.theJSessionCookie.remove();
        testData.setData(CHANNEL, channel);
        testData.setData(HEADERS, HybrisHeaders.getValid(channel));
    }

    @Step("Channel selection")
    @Given("^AD channel is used$")
    public void adChannelSelection() {
        String[] channels = StepsRegex.AD_CHANNELS.substring(1, StepsRegex.AD_CHANNELS.length() - 1).split("\\|");
        channelRandomizer(channels);
    }


    @Step("Channel selection")
    @Given("^Digital channel is used$")
    public void digitalChannelSelection() {
        String[] channels = StepsRegex.DIGITAL_CHANNELS.substring(1, StepsRegex.DIGITAL_CHANNELS.length() - 1).split("\\|");
        channelRandomizer(channels);
    }


    //  ----- other param initialization -----
    @But("^the session is cleared$")
    public void clearSession() {
        HybrisService.theJSessionCookie.set("");
    }

    @And("^the header contains ([^\\s]+)\\s?=\\s?([^\\s]+)$")
    public void setHeader(String param, String value) throws MissingDataException {
        HybrisHeaders.HybrisHeadersBuilder headers = testData.getData(HEADERS);
        try {
            headers.getClass().getMethod(param, String.class).invoke(headers, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Invalid header", e);
        }
    }

    @But("^I set transaction id to ([\\d|\\w]+) in order to (?:.*)$")
    public void setTransactionId(String storyNumber) {
        testData.setData(TRANSACTION_ID, storyNumber);
    }

    //  ----- check success response -----
    @Then("^the channel will receive success response for the operation$")
    public void checkSuccessResponse() {
        service = testData.getData(SERVICE);
        service.getResponse();
    }

    //  ----- additional informations validation -----
    @Then("^the channel will receive a warning with code (SVC_\\d+_\\d+(?:, SVC_\\d+_\\d+)*)$")
    public void warningCheck(List<String> warning) {
        warning.forEach(warningCode -> reporter.info(warningCode + " was associated with message \"" + ErrorCodes.valueOf(warningCode).getMessage() + "\""));
        service = testData.getData(SERVICE);
        service.assertThat().containedTheCorrectWarningMessage(warning);
    }

    @Then("^the channel will not receive a warning with code (SVC_\\d+_\\d+(?:, SVC_\\d+_\\d+)*)$")
    public void warningCheckNegative(List<String> warning) {
        service = testData.getData(SERVICE);
        service.assertThat().doesNotContainTheCorrectWarningMessage(warning);
    }


    //  ----- error response validation -----
    @Then("^the channel will receive an error with code (SVC_\\d+_\\d+(?:, SVC_\\d+_\\d+)*)$")
    public void errorCheck(List<String> errorCodes) {
        errorCodes.forEach(errorCode -> reporter.info(errorCode + " was associated with message \"" + ErrorCodes.valueOf(errorCode).getMessage() + "\""));
        service = testData.getData(SERVICE);
        service.assertThatErrors().containedTheCorrectErrorMessage(errorCodes);
    }

    //TODO add the list of possible types into the regex
    @Then("^the channel will receive an error of type (.*)$")
    public void errorTypeCheck(String type) {
        service = testData.getData(SERVICE);
        service.assertThatErrors().containedTheCorrectErrorType(type);
    }

    @Then("^the affected data of the error (SVC_\\d+_\\d+) contains ([^\\s]+)\\s?=\\s?([^\\s]+)$")
    public void errorAffectedDataCheck(String error, String param, String value) throws MissingDataException {
        List<String> params = Collections.singletonList(param);
        List<String> values = Collections.singletonList(value);

        service.assertThatErrors().containedTheCorrectErrorAffectedData(error, params, values);
    }

    @Then("^the affected data of the error (SVC_\\d+_\\d+) contains:$")
    public void errorAffectedDataCheck(String error, DataTable affectedDatas) throws MissingDataException {
        List<String> params = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> entry : affectedDatas.asMap(String.class, String.class).entrySet()) {
            params.add(entry.getKey());
            values.add(entry.getValue());
        }
        service.assertThatErrors().containedTheCorrectErrorAffectedData(error, params, values);
    }

    @Then("^the affected data of the error (SVC_\\d+_\\d+) does not contains (.+(?:, .+)*)$")
    public void errorAffectedDataValidation(String error, List<String> params) throws MissingDataException {
        service.assertThatErrors().notContainedTheErrorAffectedData(error, params);
    }

    @Then("^the channel will receive an error with code \"([^\"]*)\" for the \"([^\"]*)\" flight$")
    public void theChannelWillReceiveAnErrorWithCodeForTheFlight(String errorCode, String oldOrNewFlight) {
        service = testData.getData(SERVICE);

        String flightKey;
        if (oldOrNewFlight.equals("old")) {
            flightKey = testData.getData(FLIGHT_KEY);
        } else {
            flightKey = testData.getData(CHANGE_FLIGHT_KEY);
        }
        service.assertThatErrors().containedTheCorrectErrorMessageAndFlightkeys(errorCode, flightKey);
    }

    //  ----- manual steps -----
    @Given("^I am logged in backoffice$")
    public void backofficeLogin() {
        // Manual step
    }

    @Then("^ACP will send the email$")
    public void iWillSendTheEmail() {
        // Manual step
    }

    //  ----- other steps -----




    @Then("^Last response status line is ([^\"]*)$")
    public void lastResponseStatusLine(String statusLine) {
        if (statusLine.equals("HTTP/1.1 403 Forbidden or HTTP/1.1 401 Unauthorized or HTTP/1.1 400 Bad Request"))
            assert (testData.getData(LAST_RESPONSE_STATUS_LINE).equals("HTTP/1.1 403 Forbidden") || testData.getData(LAST_RESPONSE_STATUS_LINE).equals("HTTP/1.1 401 Unauthorized") || testData.getData(LAST_RESPONSE_STATUS_LINE).equals("HTTP/1.1 400 Bad Request"));
        else {
            assertThat((String) testData.getData(LAST_RESPONSE_STATUS_LINE))
                    .as("Unexpected status code ")
                    .isEqualTo(statusLine);
        }
    }

    @And("I use different session with same auth token")
    public void resetCookie() {
        HybrisService.theJSessionCookie.remove();
    }


    @And("^I set booking type is ([^\"]*)")
    public void iSetBookingTypeIsWORLDWIDE(String bookingType) {
        testData.setData(BOOKING_TYPE, bookingType);
    }

    @And("^I used the old access token$")
    public void iUsedTheOldAccessToken() {
        HybrisHeaders.HybrisHeadersBuilder headers = testData.getData(HEADERS);
        headers.authorization("bearer " + testData.getData(AGENT_ACCESS_TOKEN));
        testData.setData(HEADERS, headers);
    }
}