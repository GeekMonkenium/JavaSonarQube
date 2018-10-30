package com.hybris.easyjet.fixture.hybris.asserters.session;


import com.hybris.easyjet.fixture.hybris.asserters.Assertion;
import com.hybris.easyjet.fixture.hybris.invoke.response.session.ResetSessionContextResponse;

public class ResetSessionContextAssertion extends Assertion<ResetSessionContextAssertion, ResetSessionContextResponse> {

    public ResetSessionContextAssertion(ResetSessionContextResponse resetSessionContextResponse) {
        this.response = resetSessionContextResponse;
    }
}
