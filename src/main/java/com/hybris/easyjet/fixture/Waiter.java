package com.hybris.easyjet.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.fail;

public class Waiter {
    private int retrial;
    private long timeout;
    private long pollDelay;
    private long pollInterval;

    public Waiter() {
        this.retrial = 3;
        this.timeout = 60000;
        this.pollDelay = 0;
        this.pollInterval = 500;
    }

    private void activeWait(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }

    public Waiter atMost(int retrial) {
        this.retrial = retrial;
        return this;
    }

    public Waiter asLong(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public Waiter pollDelay(long pollDelay) {
        this.pollDelay = pollDelay;
        return this;
    }

    public Waiter pollInterval(long pollInterval) {
        this.pollInterval = pollInterval;
        return this;
    }

    public void until(Callable<Boolean> conditionEvaluator) {
        activeWait(this.pollDelay);
        int retry = this.retrial;
        Boolean test = false;
        List<String> error = new ArrayList<>();
        long endTimeMillis = System.currentTimeMillis() + this.timeout;
        do {
            try {
                test = conditionEvaluator.call();
            } catch (Exception e) {
                error.add(e.getMessage());
            }
            if (test) {
                break;
            } else {
                activeWait(this.pollInterval);
                retry--;
            }
            if (System.currentTimeMillis() > endTimeMillis) {
                fail(error + "\nUnable to proceed after " + (this.retrial - retry) + " attempts within " + this.timeout / 1000 + " seconds.");
            }
        } while (retry > 0);
        if (!test) {
            fail(error + "\nUnable to proceed after " + this.retrial + " attempts.");
        }
    }

    public void untilAsserted(Runnable conditionEvaluator) {
        activeWait(this.pollDelay);
        int retry = this.retrial;
        Boolean test = false;
        List<String> error = new ArrayList<>();
        long endTimeMillis = System.currentTimeMillis() + this.timeout;
        do {
            try {
                conditionEvaluator.run();
                test = true;
                break;
            } catch (AssertionError e) {
                error.add(e.getMessage());
                activeWait(this.pollInterval);
                retry--;
            }
            if (System.currentTimeMillis() > endTimeMillis) {
                fail(error + "\nUnable to proceed after " + (this.retrial - retry) + " attempts within " + this.timeout / 1000 + " seconds.");
            }
        } while (retry > 0);
        if (!test) {
            fail(error + "\nUnable to proceed after " + this.retrial + " attempts.");
        }
    }

}
