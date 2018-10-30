package com.hybris.easyjet.fixture.hybris.invoke.pathparams;

import com.hybris.easyjet.fixture.IPathParameters;

public abstract class PathParameters implements IPathParameters {

    public boolean isPopulated(String stringToCheck) {
        return stringToCheck != null && !stringToCheck.isEmpty();
    }

}