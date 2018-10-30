package com.hybris.easyjet.fixture.hybris.invoke.requests.refdata;

import com.hybris.easyjet.fixture.IRequest;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import com.hybris.easyjet.fixture.hybris.invoke.pathparams.GetSymbolPathParams;
import com.hybris.easyjet.fixture.hybris.invoke.queryparams.SymbolQueryParams;
import com.hybris.easyjet.fixture.hybris.invoke.requests.HybrisRequest;

import static com.hybris.easyjet.config.constants.HttpMethods.GET;

/**
 * Created by daniel on 26/11/2016.
 */
public class SymbolRequest extends HybrisRequest implements IRequest {

    public SymbolRequest(HybrisHeaders headers, GetSymbolPathParams build, SymbolQueryParams symbolQueryParams) {
        super(headers, GET, build, symbolQueryParams, null);
    }
}
