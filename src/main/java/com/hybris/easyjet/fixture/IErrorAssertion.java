package com.hybris.easyjet.fixture;

import com.hybris.easyjet.exceptions.MissingDataException;

import java.util.List;

/**
 * Created by daniel on 02/12/2016.
 */
public interface IErrorAssertion {
    void containedTheCorrectErrorMessage(String... codes);
    void containedTheCorrectErrorMessage(List<String> codes);
    void containedTheCorrectErrorType(String type);
    void containedTheCorrectErrorMessageAndFlightkeys(String codes, String flightKey);
    void containedTheCorrectErrorAffectedData(String errorCode, List<String> params, List<String> values) throws MissingDataException;

    void notContainedTheErrorAffectedData(String errorCode, List<String> params) throws MissingDataException;
}