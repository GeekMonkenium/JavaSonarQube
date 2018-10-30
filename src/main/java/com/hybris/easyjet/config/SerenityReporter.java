package com.hybris.easyjet.config;

import net.thucydides.core.annotations.Step;

import java.util.List;

/**
 * SerenityReporter is meant to print additional information on the serenity report.
 * It contains a single method that will print the string sent as argument as a substep of the calling step
 */
public class SerenityReporter {

    @Step("{0}")
    public void info(String message) {
    }

    @Step("{0}")
    public void info(String message, List<String> subMessages) {
        for (String subMessage : subMessages) {
            info(subMessage);
        }
    }

}