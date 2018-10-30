package com.hybris.easyjet.fixture;

import com.hybris.easyjet.TenantBeanFactoryPostProcessor;
import com.hybris.easyjet.config.EasyjetHybrisConfig;

public class WaitHelper {

    private static EasyjetHybrisConfig easyjetHybrisConfig = TenantBeanFactoryPostProcessor.getFactory().getBean(EasyjetHybrisConfig.class);

    private WaitHelper() {
    }

    /**
     * Retrieves a ConditionFactory configured with default delay and interval. Exits when supplied assertion passes/returns true
     * <p>
     * Usage: pollingLoop().untilAsserted(() -> {assertThat(i).isTrue()}
     */
    public static Waiter pollingLoop() {
        return new Waiter()
                .asLong(easyjetHybrisConfig.getPollingLimit())
                .pollDelay(easyjetHybrisConfig.getPollingDelay())
                .pollInterval(easyjetHybrisConfig.getPollingInterval())
                .atMost(easyjetHybrisConfig.getPollingRetrial());
    }

    /**
     * Returns a ConditionFactory configured with default delay and interval. Exits when supplied assertion passes/returns true
     * <p>
     * Usage: pollingLoop().untilAsserted(() -> {assertThat(i).isTrue()}
     */
    public static Waiter pollingLoopForSearchBooking() {
        return new Waiter()
                .asLong(easyjetHybrisConfig.getSearchBookingLimit())
                .pollDelay(easyjetHybrisConfig.getSearchBookingInterval())
                .pollInterval(easyjetHybrisConfig.getSearchBookingDelay())
                .atMost(easyjetHybrisConfig.getSearchBookingRetrial());
    }

    public static void pollingLoopUntilServiceSuccess(IService service, int noOfRetries) {
        pollingLoopUntilServiceStatus(service, 200, noOfRetries);
    }

    public static void pollingLoopUntilServiceStatus(IService service, int statusCode, int noOfRetries) {
        pollingLoop()
                .atMost(noOfRetries)
                .pollInterval(500)
                .until(() -> {
                    try {
                        service.invoke();
                        return service.getRestResponse().statusCode() == statusCode;
                    } catch (Throwable ignored) {
                        return false;
                    }
                });
    }

    /**
     * Pauses execution using default values
     * <p>
     * Usage: pause(); waits for pauseDuration in milliseconds
     */
    public static void pause() {
        pause(easyjetHybrisConfig.getPauseDuration());
    }

    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ignored) {
        }
    }

}