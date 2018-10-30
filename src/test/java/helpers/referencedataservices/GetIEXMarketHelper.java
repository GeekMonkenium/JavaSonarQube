package helpers.referencedataservices;

import static com.hybris.easyjet.config.SerenityFacade.DataKeys.*;
import static com.hybris.easyjet.fixture.hybris.invoke.pathparams.GetSymbolPathParams.SymbolPath.ADD_SYMBOL;


import com.hybris.easyjet.TestApplication;
import com.hybris.easyjet.fixture.hybris.asserters.SymbolAssertion;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import com.hybris.easyjet.fixture.hybris.invoke.pathparams.GetSymbolPathParams;
import com.hybris.easyjet.fixture.hybris.invoke.queryparams.SymbolQueryParams;
import com.hybris.easyjet.fixture.hybris.invoke.requests.refdata.SymbolRequest;
import com.hybris.easyjet.fixture.hybris.invoke.response.SymbolResponse;
import com.hybris.easyjet.fixture.hybris.invoke.services.refdata.SymbolService;
import helpers.AbstractHelper;
import net.thucydides.core.annotations.Steps;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestApplication.class)
public class GetIEXMarketHelper extends AbstractHelper<SymbolService,SymbolResponse> {

    @Steps
    private SymbolAssertion assertion;


    private GetSymbolPathParams.GetSymbolPathParamsBuilder symbolPathParams = GetSymbolPathParams.builder();
    private SymbolQueryParams.SymbolQueryParamsBuilder symbolQueryParams = SymbolQueryParams.builder();

    @Override
    protected void setPathParam() {
        symbolPathParams = GetSymbolPathParams.builder()
                .symbol(testData.getData(SYMBOL))
                .path(ADD_SYMBOL);
    }

    @Override
    protected void setQueryParam() {
        symbolQueryParams = SymbolQueryParams.builder()
                                .types("quote,news,chart")
                                .range("1m")
                                .last("10");
    }

    @Override
    protected void setRequestBody() {
    }

    @Override
    protected void invokeService() {
        HybrisHeaders.HybrisHeadersBuilder headers = testData.getData(HEADERS);
        service = serviceFactory
                    .getMarketSymbol(new SymbolRequest(null, symbolPathParams.build(),symbolQueryParams.build()));
        testData.setData(SERVICE, service);
        service.invoke();
    }

    @Override
    public SymbolAssertion assertThat() {
        assertion.setResponse(getResponse());
        return assertion;
    }
}