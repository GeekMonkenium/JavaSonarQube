package com.hybris.easyjet.fixture;

import io.restassured.response.Response;

/**
 * Created by daniel on 04/12/2016.
 */
public interface IService {

    void invoke() throws Throwable;

    IResponse getResponse();

    IErrorAssertion assertThatErrors();

    IErrors getErrors();

    IAssertion assertThat();

    Response getRestResponse();

}