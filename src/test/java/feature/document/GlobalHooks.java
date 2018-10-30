package feature.document;

import com.hybris.easyjet.TestApplication;
import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.database.eres.EresFlightsDao;
import com.hybris.easyjet.database.seating.SeatingDao;
import com.hybris.easyjet.exceptions.MissingDataException;
import com.hybris.easyjet.fixture.hybris.invoke.services.HybrisService;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestTag;
import net.thucydides.core.steps.StepEventBus;
import org.assertj.core.util.Lists;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hybris.easyjet.config.SerenityFacade.DataKeys.SCENARIO;

@ContextConfiguration(classes = TestApplication.class)
public class GlobalHooks {
    private static boolean dunit = false;

    @Rule
    public SpringIntegrationMethodRule springIntegration = new SpringIntegrationMethodRule();

    @Autowired
    private SerenityFacade testData;


    private String mock;
    private String tags;
    private Pattern pattern;
    private Matcher matcher;
    private String processName;

    private void setDefaultProperties(Scenario scenario) {
        Serenity.initializeTestSession();
        testData.setData(SCENARIO, scenario);
        System.setProperty("channel", "Digital");
        if (System.getProperty("eres") == null) {
            System.setProperty("eres", "false");
        }
        if (System.getProperty("mocked") == null) {
            System.setProperty("mocked", "false");
        }
        mock = System.getProperty("mocked");
        processName = ManagementFactory.getRuntimeMXBean().getName();
    }


    private void beforeAll() {
        if (!dunit) {
//            releaseData();
            try {
                URL checkip = new URL("http://checkip.amazonaws.com");
                BufferedReader ipReader = new BufferedReader(new InputStreamReader(checkip.openStream()));
                String ipAddress = ipReader.readLine();
                ipReader.close();
                Serenity.setSessionVariable("X-Forwarded-For").to(InetAddress.getLocalHost().getHostAddress() + "@" + ipAddress);
            } catch (IOException e) {
                Serenity.setSessionVariable("X-Forwarded-For").to("Unknown address");
            }
            Path path = Paths.get("target/cleaningErrors");
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
            }
            dunit = true;
        }
    }

    /**
     * tag method will analyze the tag of the scenario and add the proper issue link to jira, the story/backoffice tag for listing in serenity report and mark backoffice scenarios as manual
     * <p>
     * if scenario is annotated with one of the specified tag; an AssumptionViolatedException will be thrown and the test steps will not be executed and the outcome of each step of the test will be 'ignored'.
     * <p>
     * if scenario is annotated with @AsXml the system property will be set for an XML response from the API that we get one.
     *
     * @param scenario is the scenario being tested, it's automatically given by cucumber API
     * @see com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders This property is checked for and if is "true" it sets the accept header.
     */
    private void parseTagBefore(Scenario scenario) {
        if (StepEventBus.getEventBus().isBaseStepListenerRegistered()) {
            pattern = Pattern.compile("@FCPH-\\d+");
            matcher = pattern.matcher(System.getProperty("cucumber.options"));
            boolean defectRetest = false;
            if (matcher.find()) {
                defectRetest = true;
            }

            tags = String.join(";", scenario.getSourceTagNames());

            pattern = Pattern.compile("@FCPH-\\d+");
            matcher = pattern.matcher(tags);
            while (matcher.find()) {
                String story = matcher.group().substring(1);
                StepEventBus.getEventBus().addIssuesToCurrentTest(Collections.singletonList(story));
                StepEventBus.getEventBus().addTagsToCurrentTest(Lists.newArrayList(TestTag.withName(story).andType("story")));
            }

            pattern = Pattern.compile("@Sprint\\d+");
            matcher = pattern.matcher(tags);
            while (matcher.find()) {
                StepEventBus.getEventBus().addTagsToCurrentTest(Lists.newArrayList(TestTag.withName(matcher.group().substring(1)).andType("sprint")));
            }

            pattern = Pattern.compile("@backoffice:FCPH-\\d+");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                Arrays.asList(matcher.group().substring(12).split(",")).forEach(
                        story -> {
                            StepEventBus.getEventBus().addIssuesToCurrentTest(Collections.singletonList(story));
                            StepEventBus.getEventBus().addTagsToCurrentTest(Lists.newArrayList(TestTag.withName(story).andType("story")));
                        }
                );
                StepEventBus.getEventBus().testIsManual();
                throw new AssumptionViolatedException("This test is manual");
            }

            pattern = Pattern.compile("@Deprecated|@deprecated");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                throw new MissingDataException("This test is deprecated");
            }

            pattern = Pattern.compile("@Ignore|@Ignored|@ignore|@ignored|@Wip|@wip|@Skip|@skip|@Pending|@pending|@Manual|@manual");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                throw new AssumptionViolatedException("This test is " + matcher.group().substring(1));
            }

            pattern = Pattern.compile("@defect:FCPH-\\d+|@defect:FQT-\\d+|@defect:FCP-\\d+");
            matcher = pattern.matcher(tags);
            if (matcher.find() && !defectRetest) {
                Arrays.asList(matcher.group().substring(8).split(",")).forEach(
                        issue -> StepEventBus.getEventBus().addIssuesToCurrentTest(Collections.singletonList(issue))
                );
                StepEventBus.getEventBus().testIgnored();
                throw new AssumptionViolatedException("This test has known defect");
            }

            pattern = Pattern.compile("@local");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                System.setProperty("mocked", "true");
            }

            pattern = Pattern.compile("@AsXml");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                System.setProperty("AsXml", "true");
            }

        }
    }

    /**
     * defectedTest method will be executed after each test of the feature files annotated with @defect:FCPH-\\d+.
     * The outcome of each step of the test will be overwritten to be 'skipped'.
     * if scenario is annotated with one of the specified tag, the outcome of each step of the test will be overwritten to be 'skipped'.
     * if scenario is annotated with one of the specified tag, the outcome of each step of the test will be overwritten to be 'pending'.
     * After a scenario that is tagged with @AsXml we want to reset the system property back.
     */
    private void parseTagAfter() {
        if (StepEventBus.getEventBus().isBaseStepListenerRegistered()) {
            pattern = Pattern.compile("@AsXml");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                System.setProperty("AsXml", "false");
            }

            pattern = Pattern.compile("@Skip|@skip|@Wip|@wip");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                StepEventBus.getEventBus().setAllStepsTo(TestResult.SKIPPED);
            }

            pattern = Pattern.compile("@Pending|@pending");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                StepEventBus.getEventBus().setAllStepsTo(TestResult.PENDING);
            }

            pattern = Pattern.compile("@deprecated");
            matcher = pattern.matcher(tags);
            if (matcher.find()) {
                StepEventBus.getEventBus().setAllStepsTo(TestResult.COMPROMISED);
            }
        }
    }

    /**
     * clear system properties, please set here values that need to be reset in system properties
     */
    private void initSystemProperties() {
        HybrisService.theJSessionCookie.remove();
        testData.cleanStoredData();
        testData.clearData();
        System.setProperty("mocked", mock);
    }

    @Before
    public void startUp(Scenario scenario) {
        setDefaultProperties(scenario);
        beforeAll();
        parseTagBefore(scenario);
        HybrisService.theJSessionCookie.remove();
    }

    @After
    public void tearDown(Scenario scenario) {
        parseTagAfter();
        String[] scenarioId = scenario.getId().split(";");
        String scenarioName = scenarioId[1].replace("-", " ");
        if (scenarioId.length > 2) {
            scenarioName = scenarioName.concat("_" + scenarioId[3]);
        }
     //   afterStep.dataCleaning(scenarioName, processName);
        initSystemProperties();
    }

}