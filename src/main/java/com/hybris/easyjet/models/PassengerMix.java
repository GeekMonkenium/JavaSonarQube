package com.hybris.easyjet.models;

import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by giuseppedimartino on 05/06/17.
 * <p>
 * PassengerMix define the possible passengers to be included in a flight booking process.
 * </p>
 */
@Getter
public class PassengerMix {
    private static final String INFANT = "infant";
    private static final Logger LOG = LogManager.getLogger(PassengerMix.class);

    private int adult = 0;
    private int additionalAdult = 0;
    private int child = 0;
    private int additionalChild = 0;
    private int infantOnLap = 0;
    private int infantOnSeat = 0;
    private int additionalInfant = 0;
    private int totalSeats;
    private int adminSeats;
    private int totalPassengers = 0;

    public PassengerMix(String passengerMix) {
        String[] passengers = passengerMix.split("\\s*;\\s*");

        for (String passenger : passengers) {
            String seats = passenger.split("\\s+")[0];
            String type = passenger.split("\\s+")[1];

            if (type.equals(INFANT)) {
                String[] infants = seats.split(",");
                if (infants.length > 1) {
                    this.totalSeats += Integer.valueOf(seats.split(",")[1]);
                } else {
                    this.totalSeats += Integer.valueOf(seats.split(",")[0]);
                }
            } else {
                this.adminSeats += Integer.valueOf(seats.split(",")[0]);
                this.totalSeats += Pattern.compile(",")
                        .splitAsStream(seats)
                        .mapToInt(Integer::parseInt)
                        .sum();
            }
            totalPassengers += Integer.valueOf(seats.split(",")[0]);

            try {
                this.getClass().getMethod("set" + type, String.class).invoke(this, seats);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LOG.error("Invalid passenger mix defined", e);
            }
        }
    }

    public void setadult(String adults) {
        String[] adultList = adults.split(",");
        this.adult = Integer.valueOf(adultList[0]);
        if (adultList.length > 1) {
            this.additionalAdult = Integer.valueOf(adultList[1]);
        }
    }

    public void setAdult(int adults) {
        this.adult = adults;
    }

    public void setchild(String childs) {
        String[] childList = childs.split(",");
        this.child = Integer.valueOf(childList[0]);
        if (childList.length > 1) {
            this.additionalChild = Integer.valueOf(childList[1]);
        }
    }
    public void setChild(int childs) {
        this.child = childs;
    }


    public void setinfant(String infants) {
        String[] infantsList = infants.split("-");
        if (infantsList.length > 1) {
            this.additionalInfant = Integer.valueOf(infantsList[1]);
        }
        infantsList = infantsList[0].split(",");
        if (infantsList.length > 1) {
            this.infantOnSeat = Integer.valueOf(infantsList[1]);
            this.infantOnLap = Integer.valueOf(infantsList[0]) - Integer.valueOf(infantsList[1]);
        } else {
            this.infantOnSeat = Integer.valueOf(infantsList[0]);
        }
    }

    public void setInfantOnLap(int infantsOnLap) {
        this.infantOnLap = infantsOnLap;
    }

    public void setInfantOnSeat(int infantsOnSeat) {
        this.infantOnSeat = infantsOnSeat;
    }

    public List<String> getPassengerOrderedList() {
        List<String> passengers = new ArrayList<>();
        int adults = this.adult;
        int infantsOnLap = this.infantOnLap;
        int infantsOnSeat = this.infantOnSeat;

        while (adults > 0) {
            passengers.add("adult");
            if (infantsOnLap > 0) {
                passengers.add("InfantOnLap");
                infantsOnLap--;
            }
            if (infantsOnSeat > 0) {
                passengers.add("InfantOnSeat");
                infantsOnSeat--;
            }
            if (infantsOnSeat > 0) {
                passengers.add("InfantOnSeat");
                infantsOnSeat--;
            }
            adults--;
        }

        if (infantsOnLap > 0) {
            throw new IllegalStateException("Too many infants on lap for this basket");
        }
        if (infantsOnSeat > 0) {
            throw new IllegalStateException("Too many infants on seat for this basket");
        }

        for (int i = 0; i < this.child; i++) {
            passengers.add("child");
        }

        return passengers;
    }

}