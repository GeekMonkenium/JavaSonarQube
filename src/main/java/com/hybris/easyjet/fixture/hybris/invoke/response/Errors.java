package com.hybris.easyjet.fixture.hybris.invoke.response;

import com.hybris.easyjet.fixture.IErrors;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Errors extends HybrisResponse implements IErrors {
    private List<Error> errors = new ArrayList<>();

    @Getter
    @Setter
    public static class Error {
        private String code;
        private String message;
        private String type;
        private String href;
        private List<AffectedData> affectedData;
        private BasketTotal basketTotal;
    }

    @Getter
    @Setter
    public static class AffectedData {
        public String dataName;
        public String dataValue;
        public String oldDataValue;
        public String information;
    }

    @Getter
    @Setter
    public static class BasketTotal {
        public Double basketTotalWithDebitCard;
        public Double basketTotalWithCreditCard;
        public Double oldBasketTotalWithDebitCard;
        public Double oldBasketTotalWithCreditCard;
    }

}