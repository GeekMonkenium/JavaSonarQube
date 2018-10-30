package steps;

import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.database.hybris.dao.MarketGroupDao;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.referencedataservices.GetIEXMarketHelper;
import org.springframework.beans.factory.annotation.Autowired;
import transformers.Optional2Boolean;

import static com.hybris.easyjet.config.SerenityFacade.DataKeys.SYMBOL;

public class GetIEXMarketSteps extends GetIEXMarketHelper {

    @Autowired
    private MarketGroupDao marketGroupsDao;

    @Given("^I specify a valid (.*)$")
    public void setRequestParameter(String symbol) {
        testData.setData(SYMBOL, symbol);
    }

    @When("I (successfully )?send the request to getSymbol service$")
    public void iCallTheGetMarketDataService(@Transform(Optional2Boolean.class) Boolean isSuccessful) {
        if (isSuccessful) {
            sentSuccessfulRequest();
        } else {
            sendRequest();
        }
    }

    @Then("^the stock data for (.*) is successfully returned$")
    public void correctlocalizedNamesIsReturned(String symbol) {
        assertThat().marketGroupHasTheseLocalizedNames(
                testData.getData(SerenityFacade.DataKeys.EXPECTED_MARKETGROUP_NAMES));
    }
}




