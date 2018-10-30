package com.hybris.easyjet.fixture.hybris.invoke.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsResponse extends HybrisResponse {
    private List<MarketGroup> marketGroups = new ArrayList<>();

    @Getter
    @Setter
    public static class MarketGroup {
        private String code;
        private String type;
        private String status;
        private List<String> airports = new ArrayList<>();
    }
}