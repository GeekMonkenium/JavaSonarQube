package com.hybris.easyjet.fixture.hybris.asserters;

import com.hybris.easyjet.exceptions.MissingDataException;
import com.hybris.easyjet.fixture.IErrorAssertion;
import com.hybris.easyjet.fixture.hybris.invoke.response.Errors;
import lombok.NoArgsConstructor;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Created by daniel on 26/11/2016.
 * assertion wrapper for errors response object, provides reusable assertions to all tests
 */
@NoArgsConstructor
public class ErrorsAssertion implements IErrorAssertion {
    private Errors errors;

    //  Error code for basket price change
    private static final String PRICE_CHANGE_ERROR_FOR_SEAT_PRODUCT = "SVC_100022_3056";
    private static final String PRICE_CHANGE_ERROR_FOR_FARE_PRODUCT = "SVC_100022_3012";

    public ErrorsAssertion(Errors errors) {
        this.errors = errors;
    }

    public void setResponse(Errors errors) {
        this.errors = errors;
    }

    @Override
    public void containedTheCorrectErrorMessage(String... codes) {
        Arrays.asList(codes).forEach(code ->
                assertThat(
                        errors.getErrors().stream().anyMatch(
                                error -> error.getCode().equalsIgnoreCase(code)
                        )
                ).withFailMessage(
                        "EXPECTED : " + code
                ).isTrue()
        );
    }

    @Override
    public void containedTheCorrectErrorMessage(List<String> codes) {
        codes.forEach(code ->
                assertThat(errors.getErrors())
                        .withFailMessage(code + " error code, not included in the response.")
                        .extracting("code")
                        .contains(code)
        );
    }

    @Override
    public void containedTheCorrectErrorType(String types) {
        assertThat(
                errors.getErrors().stream().anyMatch(
                        error -> error.getType().equalsIgnoreCase(types)
                )
        ).withFailMessage(
                "EXPECTED : " + types
        ).isTrue();
    }

    @Override
    public void containedTheCorrectErrorAffectedData(String errorCode, List<String> params, List<String> values) throws MissingDataException {
        List<Errors.AffectedData> affetctedData = errors.getErrors().stream()
                .filter(error -> error.getCode().equals(errorCode))
                .findFirst().orElseThrow(() -> new MissingDataException("The error code " + errorCode + " was not present"))
                .getAffectedData();

        for (int i = 0; i < params.size(); i++) {
            assertThat(affetctedData)
                    .withFailMessage("Additional information " + params.get(i) + " with value " + values.get(i) + " was not present")
                    .extracting("dataName", "dataValue")
                    .contains(tuple(
                            params.get(i),
                            values.get(i)));
        }
    }

    @Override
    public void notContainedTheErrorAffectedData(String errorCode, List<String> params) throws MissingDataException {
        List<Errors.AffectedData> affetctedData = errors.getErrors().stream()
                .filter(error -> error.getCode().equals(errorCode))
                .findFirst().orElseThrow(() -> new MissingDataException("The error code " + errorCode + " was not present"))
                .getAffectedData();

        for (int i = 0; i < params.size(); i++) {
            assertThat(affetctedData)
                    .withFailMessage("Additional information contains " + params.get(i))
                    .extracting("dataName")
                    .doesNotContain(params.get(i));
        }
    }

    public void containedTheAffectedData(String... affectedData) {

        for (String information : affectedData) {
            final List<String> affectedData1 = errors.getErrors().stream().flatMap(error -> error.getAffectedData().stream().map(data -> data.getDataName())).collect(Collectors.toList());
            assertThat(affectedData1.stream().anyMatch(data -> data.equalsIgnoreCase(information))).isTrue();
        }

    }
    @Override
    public void containedTheCorrectErrorMessageAndFlightkeys(String errorCodes, String flightKey) {

        Arrays.asList(errorCodes).forEach(code ->
                assertThat(
                        errors.getErrors().stream().anyMatch(
                                error -> error.getCode().equalsIgnoreCase(errorCodes)
                        )
                ).withFailMessage(
                        "EXPECTED : " + code
                ).isTrue()
        );
        SoftAssertions softly = new SoftAssertions();
        List<String> affectedData = errors.getErrors().stream().map(Errors.Error::getMessage).collect(Collectors.toList());
        softly.assertThat(affectedData.stream().anyMatch(data -> data.contains(flightKey))).isTrue();
    }

    public void getErrorMessage() {
        errors.getErrors();
    }

    public void verifyAffectedData(String type, Double oldDebitValue, Double oldCreditValue) {
        List<String> affectedData = errors.getErrors().stream().flatMap(error -> error.getAffectedData().stream().map(data -> data.getDataName())).collect(Collectors.toList());

        SoftAssertions softly = new SoftAssertions();
        List<Errors.AffectedData> affectedDataDebitTotal = null;
        List<Errors.AffectedData> affectedDataCreditTotal = null;

        switch (type) {
            case "flight": {
                softly.assertThat(affectedData.stream().anyMatch(data -> data.contains("flightKey"))).isTrue();
                affectedDataDebitTotal = errors.getErrors().stream()
                        .filter(a -> a.getCode().equalsIgnoreCase(PRICE_CHANGE_ERROR_FOR_FARE_PRODUCT))
                        .findFirst().get()
                        .getAffectedData().stream()
                        .filter(d -> d.getDataName().equalsIgnoreCase("totalAmountWithDebitCard"))
                        .collect(Collectors.toList());
                affectedDataCreditTotal = errors.getErrors().stream()
                        .findFirst().get()
                        .getAffectedData().stream()
                        .filter(d -> d.getDataName().equalsIgnoreCase("totalAmountWithCreditCard"))
                        .collect(Collectors.toList());
                break;
            }
            case "seat": {
                softly.assertThat(affectedData.stream().anyMatch(data -> data.contains("Seat"))).isTrue();
                affectedDataDebitTotal = errors.getErrors().stream()
                        .filter(a -> a.getCode().equalsIgnoreCase(PRICE_CHANGE_ERROR_FOR_SEAT_PRODUCT))
                        .findFirst().get()
                        .getAffectedData().stream()
                        .filter(d -> d.getDataName().equalsIgnoreCase("debitCardPrice"))
                        .collect(Collectors.toList());
                affectedDataCreditTotal = errors.getErrors().stream()
                        .findFirst().get()
                        .getAffectedData().stream()
                        .filter(d -> d.getDataName().equalsIgnoreCase("creditCardPrice"))
                        .collect(Collectors.toList());
                break;
            }
            default:
                throw new IllegalArgumentException("You must specify proper product type.");
        }

        Double expectedOldDebitValue = Double.valueOf(affectedDataDebitTotal.stream().findFirst().get().getOldDataValue());
        softly.assertThat(affectedDataDebitTotal.stream().findFirst().get().getDataValue())
                .withFailMessage("New value is not correct")
                .isNotNull();
        softly.assertThat(expectedOldDebitValue)
                .withFailMessage("Old value is not correct")
                .isEqualTo(oldDebitValue);


        Double expectedOldCreditValue = Double.valueOf(affectedDataCreditTotal.stream().findFirst().get().getOldDataValue());
        softly.assertThat(affectedDataCreditTotal.stream().findFirst().get().getDataValue())
                .withFailMessage("New value is not correct")
                .isNotNull();
        softly.assertThat(expectedOldCreditValue)
                .withFailMessage("Old value is not correct")
                .isEqualTo(oldCreditValue);

        softly.assertAll();
    }

    public void verifyBasketTotal(String type, Double oldDebitValue, Double oldCreditValue) {

        SoftAssertions softly = new SoftAssertions();
        Errors.BasketTotal basketTotal = null;
        if (type.equalsIgnoreCase("flight")) {
            basketTotal = errors.getErrors().stream()
                    .filter(a -> a.getCode().equalsIgnoreCase(PRICE_CHANGE_ERROR_FOR_FARE_PRODUCT))
                    .findFirst().get()
                    .getBasketTotal();
        } else if (type.equalsIgnoreCase("seat")) {
            basketTotal = errors.getErrors().stream()
                    .filter(a -> a.getCode().equalsIgnoreCase(PRICE_CHANGE_ERROR_FOR_SEAT_PRODUCT))
                    .findFirst().get()
                    .getBasketTotal();
        }

        softly.assertThat(basketTotal.getBasketTotalWithCreditCard())
                .withFailMessage("New Basket total with Credit is not correct")
                .isNotNull();
        softly.assertThat(basketTotal.getBasketTotalWithDebitCard())
                .withFailMessage("New Basket total with Debit is not correct")
                .isNotNull();
        softly.assertThat(basketTotal.getOldBasketTotalWithCreditCard().doubleValue())
                .withFailMessage("Old Basket Total with Credit is not correct")
                .isEqualTo(oldCreditValue);
        softly.assertThat(basketTotal.getOldBasketTotalWithDebitCard().doubleValue())
                .withFailMessage("Old Basket Total with Debit is not correct")
                .isEqualTo(oldDebitValue);
        softly.assertAll();
    }
}
