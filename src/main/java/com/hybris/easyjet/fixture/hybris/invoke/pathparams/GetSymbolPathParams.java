package com.hybris.easyjet.fixture.hybris.invoke.pathparams;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetSymbolPathParams extends PathParameters {

    @Builder.Default
    private SymbolPath path;
    private String symbol;

    /**
     * @return the Path Parameters to update symbol
     */
    @Override
    public String get() {

        switch (path) {
            case ADD_SYMBOL:
                if (isPopulated(symbol)) {
                    return "/" + "AAPL"+ "/batch";
                } else {
                    return "";
                }
        }
        return null;
    }

    public enum SymbolPath{
        DEFAULT,
        ADD_SYMBOL;
    }

}