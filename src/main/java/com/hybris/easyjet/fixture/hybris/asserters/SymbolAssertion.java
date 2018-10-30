package com.hybris.easyjet.fixture.hybris.asserters;

import com.hybris.easyjet.database.hybris.models.ExpectedLocalizedDescription;
import com.hybris.easyjet.database.hybris.models.ExpectedLocalizedName;
import com.hybris.easyjet.database.hybris.models.ExpectedMarketGroup;
import com.hybris.easyjet.fixture.hybris.invoke.response.MarketGroupsResponse;
import com.hybris.easyjet.fixture.hybris.invoke.response.SymbolResponse;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Created by daniel on 26/11/2016.
 * assertion wrapper for market groups response object, provides reusable assertions to all tests
 */
@NoArgsConstructor
public class SymbolAssertion extends Assertion<SymbolAssertion, SymbolResponse> {

    public SymbolAssertion(SymbolResponse symbolResponse) {

        this.response = symbolResponse;
    }

    public SymbolAssertion theseMarketGroupsWereReturned(List<ExpectedMarketGroup> expectedMarketGroups, List<String> marketGroupsList) {

//        assertThat(marketGroupsList.size())
//                .withFailMessage("Expected Market Group size does not match the Market Group returned.")
//                .isEqualTo(response.getMarketGroups()
//                        .size());
//
//
//        assertThat(response.getMarketGroups().stream().filter(s -> !s.getStatus().equals("ACTIVE"))
//                .collect(Collectors.toList()).size())
//                .withFailMessage("Inactive market group returned")
//                .isEqualTo(0);
//
//        for (ExpectedMarketGroup expectedMarketGroup : expectedMarketGroups) {
//            assertThat(response.getMarketGroups())
//                    .as("The Market Group values not as expected.")
//                    .extracting(
//                            "code",
//                            "type")
//                    .contains(tuple(
//                            expectedMarketGroup.getCode(),
//                            expectedMarketGroup.getType()));
//        }
        return this;
    }

    public SymbolAssertion marketGroupHasTheseLocalizedDescriptions(List<ExpectedLocalizedDescription> expectedLocalizedDescriptions) {

//        for (ExpectedLocalizedDescription expectedLocalizedDescription : expectedLocalizedDescriptions) {
//            assertThat(response.getMarketGroups())
//                    .as("The Market Group values not as expected.")
//                    .flatExtracting("localizedDescriptions")
//                    .extracting("locale", "description")
//                    .contains(tuple(
//                            expectedLocalizedDescription.getLocale(),
//                            expectedLocalizedDescription.getDescription()));
//        }
        return this;
    }

    public SymbolAssertion marketGroupHasTheseLocalizedNames(List<ExpectedLocalizedName> expectedLocalizedNames) {

//        for (ExpectedLocalizedName expectedLocalizedName : expectedLocalizedNames) {
//            Assertions.assertThat(response.getMarketGroups())
//                    .as("The Market Group values not as expected.")
//                    .flatExtracting("localizedNames")
//                    .extracting("locale", "name")
//                    .contains(tuple(
//                            expectedLocalizedName.getLocale(),
//                            expectedLocalizedName.getName()));
//        }
        return this;
    }
}
