package com.hybris.easyjet.fixture.hybris.asserters;


import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.config.SerenityReporter;
import com.hybris.easyjet.fixture.IAssertion;
import com.hybris.easyjet.fixture.hybris.invoke.response.HybrisResponse;
import com.hybris.easyjet.fixture.hybris.invoke.response.SymbolResponse;
import com.hybris.easyjet.fixture.hybris.invoke.response.common.AdditionalInformation;
import lombok.NoArgsConstructor;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by g.dimartino on 07/03/17.
 * This abstract class is used to generalize the checks on the additional information for every service
 *
 * @param <AssertionClass> Assertion class that will extend this class
 * @param <ResponseClass>  Response class used by the assertion class T
 */
@NoArgsConstructor
public class Assertion<AssertionClass extends Assertion, ResponseClass extends HybrisResponse> implements IAssertion<AssertionClass> {

    protected ResponseClass response;

    protected SerenityFacade testData = SerenityFacade.getTestDataFromSpring();

    @Steps
    protected static SerenityReporter reporter;
    protected SoftAssertions softly = new SoftAssertions();

    public void setResponse(ResponseClass response) {
        cleanAssertion();
        this.response = response;
    }

    public void checkAssertions() {
        softly.assertAll();
    }

    private void cleanAssertion() {
        softly = new SoftAssertions();
    }

    @SuppressWarnings("unchecked")
    public AssertionClass additionalInformationReturned(String... codes) {

        for (String code : codes) {
            assertThat(response.getAdditionalInformations().stream().anyMatch(
                    warning -> warning.getCode().contains(code)))
                    .withFailMessage("No additional information returned.");
        }
        return (AssertionClass) this;
    }


    @SuppressWarnings("unchecked")
    public AssertionClass affectedDataContains(String... informations) {

        for (String information : informations) {
            final List<AdditionalInformation.AffectedData> affectedData = response.getAdditionalInformations().stream().flatMap(additionalInformation -> additionalInformation.getAffectedData().stream()).collect(Collectors.toList());
            affectedData.forEach(affectedData1 -> assertThat(affectedData1.getInformation().contains(information)).isTrue());
        }

        return (AssertionClass) this;
    }

    @SuppressWarnings("unchecked")
    public AssertionClass affectedDataNameReturned(String... dataNames) {

        for (String dataName : dataNames) {
            final boolean[] assertPassed = new boolean[1];
            assertPassed[0] = false;

            response.getAdditionalInformations().get(0).getAffectedData().forEach(affectedData -> {
                if (affectedData.dataName.contains(dataName)) {
                    assertPassed[0] = true;
                }
            });
            assertThat(assertPassed[0]).withFailMessage("Expected to contain '" + dataName + "' in the response but not found").isEqualTo(true);
        }

        return (AssertionClass) this;
    }

    @SuppressWarnings("unchecked")
    public AssertionClass additionalInformationOnlyReturned(String... codes) {
        assertThat(response.getAdditionalInformations().size())
                .withFailMessage("Additional information contains unexpected warning: " +
                        response.getAdditionalInformations().get(0).getCode())
                .isEqualTo(codes.length);

        return (AssertionClass) this;

    }

    @SuppressWarnings("unchecked")
    public AssertionClass additionalInformationContains(String... codes) {
        java.util.Arrays.asList(codes).forEach(code ->
                assertThat(
                        response.getAdditionalInformations().stream().anyMatch(
                                error -> error.getCode().equalsIgnoreCase(code)
                        )
                ).withFailMessage(
                        "EXPECTED : " + code
                ).isTrue()
        );
        return (AssertionClass) this;
    }

    public AssertionClass additionalInformationContainsMessage(String... messages) {
        java.util.Arrays.asList(messages).forEach(message ->
                assertThat(
                        response.getAdditionalInformations().stream().anyMatch(
                                error -> error.getMessage().equalsIgnoreCase(message)
                        )
                ).withFailMessage(
                        "EXPECTED : " + message
                ).isTrue()
        );
        return (AssertionClass) this;
    }


    @SuppressWarnings("unchecked")
    @Step("Additional information {1}")
    public AssertionClass additionalInformationContains(HybrisResponse response, String... codes) {
        java.util.Arrays.asList(codes).forEach(code ->
                assertThat(response.getAdditionalInformations().stream()
                        .anyMatch(error -> error.getCode().equalsIgnoreCase(code)))
                        .withFailMessage("EXPECTED : " + code)
                        .isTrue()
        );
        return (AssertionClass) this;
    }

    @SuppressWarnings("unchecked")
    public AssertionClass additionalInformationIsEmpty() {
        if (!"true".equalsIgnoreCase(System.getProperty("mocked"))) {
            assertThat(response.getAdditionalInformations())
                    .withFailMessage("Additional information contains unexpected messages" +
                            response.getAdditionalInformations())
                    .isEmpty();
        }
        return (AssertionClass) this;

    }

    @SuppressWarnings("unchecked")
    @Override
    public AssertionClass containedTheCorrectWarningMessage(String... codes) {
        Arrays.asList(codes).forEach(code ->
                assertThat(response.getAdditionalInformations().stream()
                        .anyMatch(warning -> warning.getCode().equalsIgnoreCase(code)))
                        .withFailMessage("Missing expected warning: " + code)
                        .isTrue()
        );
        return (AssertionClass) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssertionClass containedTheCorrectWarningMessage(List<String> codes) {
        codes.forEach(code ->
                assertThat(response.getAdditionalInformations())
                        .withFailMessage(code + " warning code, not included in the response.")
                        .extracting("code")
                        .contains(code)
        );
        return (AssertionClass) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssertionClass doesNotContainTheCorrectWarningMessage(List<String> codes) {
        codes.forEach(code ->
                assertThat(Objects.isNull(response.getAdditionalInformations()) ||
                        response.getAdditionalInformations().stream()
                                .noneMatch(warning -> warning.getCode().equalsIgnoreCase(code)))
                        .withFailMessage(code + " warning code, not included in the response.")
        );
        return (AssertionClass) this;
    }

}