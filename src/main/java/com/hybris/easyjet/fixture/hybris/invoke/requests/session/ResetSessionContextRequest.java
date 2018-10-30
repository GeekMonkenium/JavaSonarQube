package com.hybris.easyjet.fixture.hybris.invoke.requests.session;


import com.hybris.easyjet.fixture.hybris.invoke.pathparams.session.SessionContextParams;
import com.hybris.easyjet.fixture.IRequest;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import com.hybris.easyjet.fixture.hybris.invoke.requests.HybrisRequest;

import static com.hybris.easyjet.config.constants.HttpMethods.POST;

/**
 * Created by albertowork on 11/20/17.
 */
public class ResetSessionContextRequest extends HybrisRequest {
    public ResetSessionContextRequest(HybrisHeaders headers, SessionContextParams pathParams) {
        super(headers, POST, pathParams, null, null);
    }
}
