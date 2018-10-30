package com.hybris.easyjet.fixture.hybris.asserters.session;

import com.hybris.easyjet.fixture.hybris.asserters.Assertion;
import com.hybris.easyjet.fixture.hybris.invoke.response.session.KeepSessionAliveResponse;
import lombok.NoArgsConstructor;

/**
 * Created by giuseppecioce on 23/11/2017.
 */
@NoArgsConstructor
public class KeepSessionAliveAssertion extends Assertion<KeepSessionAliveAssertion, KeepSessionAliveResponse> {

    public KeepSessionAliveAssertion(KeepSessionAliveResponse keepSessionAliveResponse) {
        this.response = keepSessionAliveResponse;
    }
}