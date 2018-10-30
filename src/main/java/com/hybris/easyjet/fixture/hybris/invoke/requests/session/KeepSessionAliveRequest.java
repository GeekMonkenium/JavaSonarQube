package com.hybris.easyjet.fixture.hybris.invoke.requests.session;

import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import com.hybris.easyjet.fixture.hybris.invoke.pathparams.session.SessionContextParams;
import com.hybris.easyjet.fixture.hybris.invoke.requestbody.session.KeepSessionRequestBody;
import com.hybris.easyjet.fixture.hybris.invoke.requests.HybrisRequest;

import static com.hybris.easyjet.config.constants.HttpMethods.POST;

/**
 * Created by giuseppecioce on 23/11/2017.
 */
public class KeepSessionAliveRequest extends HybrisRequest {
    public KeepSessionAliveRequest(HybrisHeaders headers, SessionContextParams pathParams, KeepSessionRequestBody requestBody) {
        super(headers, POST, pathParams, null, requestBody);
    }
}
