package com.hybris.easyjet.config.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giuseppedimartino on 28/04/17.
 */
public class DBConstants {

    public static final List<String> FARE_TYPE = new ArrayList<String>() {{
        add("STANDARD");
        add("FLEXI");
        add("STAFF");
        add("STANDBY");
        add("INFANTONLAP");
    }};

    public static final int CONVERSION_DECIMAL_PLACES = 6;
}


