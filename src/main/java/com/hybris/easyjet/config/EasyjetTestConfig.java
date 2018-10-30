package com.hybris.easyjet.config;

import lombok.Getter;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Getter
abstract class EasyjetTestConfig {

    private final String hybrisDbDriver;
    private final String hybrisDbConnectionUrl;
    private final String hybrisDbUserName;
    private final String hybrisDbPassword;
    private final String eResDbConnectionUrl;
    private final String eResDbUserName;
    private final String eResDbPassword;
    private final String seatingDbConnectionUrl;
    private final String seatingDbUserName;
    private final String seatingDbPassword;
    private final int hybrisTimeout;
    private final int searchBookingLimit;
    private final int searchBookingInterval;
    private final int searchBookingDelay;
    private final int searchBookingRetrial;
    private final int pollingDelay;
    private final int pollingInterval;
    private final int pauseDuration;
    private final int pollingLimit;
    private final int pollingRetrial;
    private final int mysqlConnectionMaxAgeInMinutes;
    private final int idleConnectionTestPeriodInSeconds;

    EasyjetTestConfig(Environment environment) {
        this.hybrisDbDriver = environment.getProperty("hybris.db.driverName");
        this.hybrisDbConnectionUrl = environment.getProperty("hybris.db.connectionUrl");
        this.hybrisDbUserName = environment.getProperty("hybris.user.name");
        this.hybrisDbPassword = environment.getProperty("hybris.user.password");
        this.eResDbConnectionUrl = environment.getProperty("eres.db.connectionUrl");
        this.eResDbUserName = environment.getProperty("eres.write.user.name");
        this.eResDbPassword = environment.getProperty("eres.write.user.password");
        this.seatingDbConnectionUrl = environment.getProperty("seating.db.connectionUrl");
        this.seatingDbUserName = environment.getProperty("seating.write.user.name");
        this.seatingDbPassword = environment.getProperty("seating.write.user.password");
        this.hybrisTimeout = Integer.valueOf(environment.getProperty("hybris.timeout"));
        this.searchBookingLimit = Integer.valueOf(environment.getProperty("hybris.searchBooking.limit"));
        this.searchBookingDelay = Integer.valueOf(environment.getProperty("hybris.searchBooking.delay"));
        this.searchBookingInterval = Integer.valueOf(environment.getProperty("hybris.searchBooking.interval"));
        this.searchBookingRetrial = Integer.valueOf(environment.getProperty("hybris.searchBooking.retrial"));
        this.pollingDelay = Integer.valueOf(environment.getProperty("polling.delay"));
        this.pollingInterval = Integer.valueOf(environment.getProperty("polling.interval"));
        this.pauseDuration = Integer.valueOf(environment.getProperty("polling.duration"));
        this.pollingLimit = Integer.valueOf(environment.getProperty("polling.limit"));
        this.pollingRetrial = Integer.valueOf(environment.getProperty("polling.retrial"));
        this.mysqlConnectionMaxAgeInMinutes = Integer.valueOf(Optional.ofNullable(environment.getProperty("mysqlConnectionMaxAgeInMinutes")).orElse("1"));
        this.idleConnectionTestPeriodInSeconds = Integer.valueOf(Optional.ofNullable(environment.getProperty("idleConnectionTestPeriodInSeconds")).orElse("30"));
    }

}