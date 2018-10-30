package com.hybris.easyjet.fixture.hybris.invoke.services.refdata;

import com.hybris.easyjet.fixture.IRequest;
import com.hybris.easyjet.fixture.IService;
import com.hybris.easyjet.fixture.hybris.asserters.SymbolAssertion;
import com.hybris.easyjet.fixture.hybris.invoke.response.MarketGroupsResponse;
import com.hybris.easyjet.fixture.hybris.invoke.response.SymbolResponse;
import com.hybris.easyjet.fixture.hybris.invoke.services.HybrisService;

/**
 * Created by daniel on 26/11/2016.
 */
public class SymbolService extends HybrisService implements IService {

    private SymbolResponse symbolResponse;

    /**
     * @param request  the request object required
     * @param endPoint the endpoint of the service
     *                 <p>
     */
    public SymbolService(IRequest request, String endPoint) {
        super(request, endPoint);
    }

    @Override
    public SymbolAssertion assertThat() {
        assertThatServiceCallWasSuccessful();
        return new SymbolAssertion(symbolResponse);
    }

    @Override
    public SymbolResponse getResponse() {
        assertThatServiceCallWasSuccessful();
        return symbolResponse;
    }

    @Override
    protected void checkThatResponseBodyIsPopulated() {
        //checkThatResponseBodyIsPopulated(symbolResponse.getMarketGroups());
    }

    @Override
    protected void mapResponse() {
        symbolResponse = restResponse.as(SymbolResponse.class);
    }
}
