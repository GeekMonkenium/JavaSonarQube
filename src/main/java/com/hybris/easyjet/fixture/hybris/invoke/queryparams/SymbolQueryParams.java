package com.hybris.easyjet.fixture.hybris.invoke.queryparams;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Builder
public class SymbolQueryParams extends QueryParameters {

    private String types;
    private String range;
    private String last;

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> queryParams = new HashMap<>();

        if (isPopulated(types)) {
            queryParams.put("types", types);
        }
        if (isPopulated(range)) {
            queryParams.put("range", range);
        }
        if (isPopulated(last)) {
            queryParams.put("last", last);
        }

        return queryParams;
    }

}