package helpers;

import com.hybris.easyjet.TestApplication;
import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.config.SerenityReporter;
import com.hybris.easyjet.fixture.hybris.asserters.Assertion;
import com.hybris.easyjet.fixture.hybris.asserters.ErrorsAssertion;
import com.hybris.easyjet.fixture.hybris.invoke.response.HybrisResponse;
import com.hybris.easyjet.fixture.hybris.invoke.services.HybrisService;
import com.hybris.easyjet.fixture.hybris.invoke.services.HybrisServiceFactory;
import com.hybris.easyjet.fixture.hybris.invoke.services.refdata.SymbolService;
import lombok.NoArgsConstructor;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@NoArgsConstructor
@ContextConfiguration(classes = TestApplication.class)
public abstract class AbstractHelper<ServiceClass extends HybrisService, ResponseClass extends HybrisResponse> {

    @Autowired
    protected SerenityFacade testData;
    @Autowired
    protected HybrisServiceFactory serviceFactory;

    @Steps
    protected SerenityReporter reporter;

    @Steps
    protected ErrorsAssertion errorsAssertion;

    protected ServiceClass service;

    /**
     * Set the path param with defined parameter from testData
     */
    protected abstract void setPathParam();

    /**
     * Set the query param with defined parameter from testData
     */
    protected abstract void setQueryParam();

    /**
     * Set the request body with defined parameter from testData
     */
    protected abstract void setRequestBody();

    /**
     * Build the request and send it
     */
    protected abstract void invokeService();

    /**
     * Retrieve the response of the service
     *
     * @return the response received after calling invokeService
     */
    @SuppressWarnings("unchecked")
    public ResponseClass getResponse() {
        return (ResponseClass) service.getResponse();
    }

    /**
     * Retrieve the status code of the response
     *
     * @return the status code received after calling invokeService
     */
    public int getStatusCode() {
        return service.getStatusCode();
    }

    /**
     * Retrieve the assertion class of the service
     *
     * @return the assertion object on the latest response of the service
     */
    public abstract Assertion assertThat();

    /**
     * Retrieve the error assertion class
     *
     * @return the assertion object on the latest error of the service
     */
    public ErrorsAssertion assertThatErrors() {
        errorsAssertion.setResponse(service.getErrors());
        return errorsAssertion;
    }

    /**
     * Set the request body with the defined values and send the request to the service; this method doesn't check if the response is successful or not
     */
    public void sendRequest() {
        setPathParam();
        setQueryParam();
        setRequestBody();
        invokeService();
    }

    /**
     * Set the request body with the defined values and send the request to the service; this method assume the response was successful
     * It stores the values in testData
     */
    public void sentSuccessfulRequest() {
        sendRequest();
        getResponse();
    }

}