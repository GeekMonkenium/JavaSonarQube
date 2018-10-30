package com.hybris.easyjet.fixture.hybris.invoke.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.fixture.IRequest;
import com.hybris.easyjet.fixture.IService;
import com.hybris.easyjet.fixture.hybris.asserters.ErrorsAssertion;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import com.hybris.easyjet.fixture.hybris.invoke.requests.HybrisRequest;
import com.hybris.easyjet.fixture.hybris.invoke.response.Errors;
import cucumber.api.Scenario;
import io.restassured.RestAssured;
import io.restassured.config.HeaderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.ParamConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.util.*;

import static com.hybris.easyjet.config.SerenityFacade.DataKeys.*;
import static com.hybris.easyjet.config.SerenityFacade.getTestDataFromSpring;
import static com.hybris.easyjet.config.constants.MockTransactionIdentifiers.customXClientTransactionIds;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static net.serenitybdd.rest.RestRequests.given;

/**
 * the hybris service superclass provides all of the common functionality for any service
 */
public abstract class HybrisService implements IService {

    public static final String[] ALLOWABLE_XML_CHANNELS = new String[]{"PublicApiMobile", "PublicApiB2B"};

    public static final ThreadLocal<String> theJSessionCookie = new ThreadLocal<>();
    public static final ThreadLocal<String> theRememberMeCookie = new ThreadLocal<>();
    private static final String JSESSIONID = "JSESSIONID";
    private static final String REMEMBER_ME = "REMEMBER_ME";

    private static final String SVC_100000_2070 = "SVC_100000_2070";

    private static final Logger LOG = LogManager.getLogger(HybrisService.class);

    protected final HybrisRequest request;
    @Getter
    @Setter
    protected Response restResponse;
    protected boolean successful;

    private RequestSpecification restRequest;
    private SerenityFacade testData = SerenityFacade.getTestDataFromSpring();

    private Errors errors;

    private Set<String> unrecognizedProperties;

    /**
     * a service comprises a request, a client and an endpoint
     *
     * @param request  the request object required
     * @param endPoint the endpoint of the service
     */
    public HybrisService(IRequest request, String endPoint) {
        this.request = (HybrisRequest) request;

        unrecognizedProperties = new HashSet<>();
        DeserializationProblemHandler deserializationProblemHandler = new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                String position = StringUtils.join(beanOrClass.toString().substring(beanOrClass.toString().lastIndexOf('.') + 1, beanOrClass.toString().lastIndexOf('@')).split("\\$"), " -> ");
                unrecognizedProperties.add(propertyName + " in " + position);
                jp.skipChildren();
                return true;
            }
        };
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true)
                .addHandler(deserializationProblemHandler);
        ObjectMapperConfig objectMapperConfig = new ObjectMapperConfig().jackson2ObjectMapperFactory((aClass, s) -> objectMapper);
        RestAssuredConfig config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
                .objectMapperConfig(objectMapperConfig)
                .headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("", HybrisHeaders.headers))
                .paramConfig(ParamConfig.paramConfig().replaceAllParameters().queryParamsUpdateStrategy(ParamConfig.UpdateStrategy.REPLACE));

        restRequest = given().config(config).log().all().baseUri(endPoint);
    }

    private void setHeaders() {
        // Set transactionId checking mock configuration
//        if (!customXClientTransactionIds.contains(request.getHeaders().getXClientTransactionId())) {
//            String guid = UUID.randomUUID().toString();
//            if (testData.keyExist(TRANSACTION_ID)) {
//                request.getHeaders().setXClientTransactionId(guid.substring(0, 24) + testData.getData(TRANSACTION_ID));
//            } else {
//                request.getHeaders().setXClientTransactionId(guid);
//            }
//        }
//
//        // Add all the headers.
//        restRequest.headers(request.getHeaders().get());
//
//        // If we're sending XML and we're on an allowable channel, then set the content type appropriately otherwise use JSON.
//        if ("true".equals(System.getProperty("AsXml", "false")) && Arrays.asList(ALLOWABLE_XML_CHANNELS).contains(request.getHeaders().getXPosId())) {
//            restRequest.contentType(ContentType.XML);
//        } else {
//            // Set the content type to be JSON.
//            restRequest.contentType(ContentType.JSON);
//        }
//
//        if (StringUtils.isNotBlank(theJSessionCookie.get())) {
//            restRequest.sessionId(JSESSIONID, theJSessionCookie.get());
//        }
    }

    private void buildRequest() {
        setHeaders();

        if (request.getPathParameters() != null) {
            restRequest.basePath(request.getPathParameters().get());
        }
        if (request.getQueryParameters() != null) {
            restRequest.queryParams(request.getQueryParameters().getParameters());
        }
        if (request.getRequestBody() != null) {
            restRequest.body(request.getRequestBody());
        }
    }

    private Response restAssuredWhen() {
        switch (request.getHttpMethod()) {
            case GET:
                return restRequest.when().get();
            case PUT:
                return restRequest.when().put();
            case POST:
                return restRequest.when().post();
            case DELETE:
                return restRequest.when().delete();
            default:
                throw new IllegalArgumentException("Unexpected HTTP method");
        }
    }

    private void sendRequest() {
        long startTime = System.currentTimeMillis();
        restResponse = restAssuredWhen().then().log().all().extract().response();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOG.info("The service " + this.getClass().toString() + " responded in " + elapsedTime + "ms." + "Call was to " + restResponse.toString());

        //TODO check error pojo for XML, then re-enable the check
//        mapErrors();
//        List<String> errorCodes = getErrors().getErrors().stream()
//                .map(Errors.Error::getType)
//                .collect(Collectors.toList());
//
//        unrecognizedProperties = new HashSet<>();
//        errors = null;
//        //TODO change type to code
//        if (errorCodes.contains("InvalidTokenError")) {
//            Assertions.fail("Access token has been deleted");
//        }

        setSuccessState();
    }

    private void checkResponseCookie() {
        // Check session cookie
        if (Objects.nonNull(restResponse.getCookie(JSESSIONID))) {
            theJSessionCookie.set(restResponse.getCookie(JSESSIONID));
        }
        //TODO delete cookie on logout, then reitroduce this check
//        if (StringUtils.isBlank(theJSessionCookie.get()) && Objects.nonNull(restResponse.getCookie(JSESSIONID))) {
//            theJSessionCookie.set(restResponse.getCookie(JSESSIONID));
//        } else if (StringUtils.isBlank(theJSessionCookie.get()) && Objects.isNull(restResponse.getCookie(JSESSIONID))) {
//            Assertions.fail("Cookie has not been returned by the service");
//        } else if (StringUtils.isNotBlank(theJSessionCookie.get()) && Objects.nonNull(restResponse.getCookie(JSESSIONID))) {
//            Assertions.fail("New cookie received by the service during same session");
//        }

        // Check rememberMe cookie
        if (Objects.nonNull(restResponse.getCookie(REMEMBER_ME))) {
            theRememberMeCookie.set(restResponse.getCookie(REMEMBER_ME));
        } else {
            theRememberMeCookie.set(null);
        }
    }

    /**
     * Map the errors to Error objects.
     *
     * @see Errors
     */
    private void mapErrors() {
        errors = restResponse.as(Errors.class);
    }

    /**
     * Get all the errors as one string.
     *
     * @return The error string.
     */
    private String getMessageError() {
        final List<String> messageError = new ArrayList<>();

        errors.getErrors().forEach(error ->
                messageError.add("(type: " + error.getCode() + ", message: " + error.getMessage() + ")\n"));

        return Arrays.toString(messageError.toArray());
    }

    /**
     * If a 200 was returned set successful to be true.
     */
    private void setSuccessState() {
        this.successful = restResponse.getStatusCode() == 200;
    }


    protected abstract void checkThatResponseBodyIsPopulated();

    protected abstract void mapResponse();

    /**
     * Asserts the successful property to see if the request was a success.
     */
    protected void assertThatServiceCallWasSuccessful() {
        Assertions.assertThat(unrecognizedProperties)
                .withFailMessage("Unrecognized properties: " + unrecognizedProperties)
                .isEmpty();

        Scenario scenario = getTestDataFromSpring().getData(SCENARIO);
        if (!scenario.getSourceTagNames().contains("@negative")) {
            Assertions.assertThat(restResponse)
                    .withFailMessage("Response was null")
                    .isNotNull();
            Assertions.assertThat(successful)
                    .withFailMessage("The service was not called successfully.  Response type was: " + restResponse.getStatusCode() + ". The error reported was: " + (Objects.nonNull(errors) ? getMessageError() : "") + ". Endpoint: " + restResponse.getStatusLine())
                    .isTrue();
        }
    }

    /**
     * Asserts the successful property to see if the request was NOT a success.
     */
    protected void assertThatServiceCallWasNotSuccessful() {
        Assertions.assertThat(successful)
                .withFailMessage("The service returned a: " + restResponse.getStatusCode() + ":" + restResponse.getStatusLine())
                .isFalse();
    }

    protected void checkThatResponseBodyIsPopulated(Object expectedResponseContent) {
        Assertions.assertThat(expectedResponseContent)
                .withFailMessage("The message body was not populated but the service reported a " + restResponse.getStatusCode() + " " + restResponse.getStatusLine())
                .isNotNull();
    }


    /**
     * adds the query parameters
     * adds the path parameters
     * adds the headers
     * invokes the service call
     * sets the success state based upon the return type
     * maps the response to either errors or the valid response domain model and verifies mapping has been successful
     */
    @Override
    public void invoke() {
        buildRequest();
        sendRequest();
        checkResponseCookie();

        if (restResponse.getStatusLine() != null) {
            testData.setData(LAST_RESPONSE_STATUS_LINE, restResponse.getStatusLine());
        }

        if (successful) {
            mapResponse();
            checkThatResponseBodyIsPopulated();
        } else {
            mapErrors();
        }
    }

    /**
     * provides a wrapper for asserting the contents of any errors
     *
     * @return the error assertion object which provides fluent reusable assertions
     */
    @Override
    public ErrorsAssertion assertThatErrors() {
        assertThatServiceCallWasNotSuccessful();
        return new ErrorsAssertion(errors);
    }

    /**
     * @return the errors object model
     */
    @Override
    public Errors getErrors() {
        return errors;
    }


    public int getStatusCode() {
        return restResponse.getStatusCode();
    }

    public String getStatusLine() {
        return restResponse.getStatusLine();
    }

}