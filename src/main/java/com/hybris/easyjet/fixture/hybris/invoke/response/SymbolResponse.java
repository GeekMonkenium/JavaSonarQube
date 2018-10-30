package com.hybris.easyjet.fixture.hybris.invoke.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SymbolResponse extends HybrisResponse {

    private Quote quote = new Quote();
    private List<News> news = new ArrayList<>();
    private List<Chart> chart = new ArrayList<>();

    @Getter
    @Setter
    public static class Quote{
        private String symbol;
        private String companyName;
        private String primaryExchange;
        private String sector;
        private String calculationPrice;
        private String open;
        private String openTime;
        private String close;
        private String closeTime;
        private String high;
        private String low;
        private String latestPrice;
        private String latestSource;
        private String latestTime;
        private String latestUpdate;
        private String latestVolume;
        private String iexRealtimePrice;
        private String iexRealtimeSize;
        private String iexLastUpdated;
        private String delayedPrice;
        private String delayedPriceTime;
        private String extendedPrice;
        private String extendedChange;
        private String extendedChangePercent;
        private String extendedPriceTime;
        private String previousClose;
        private String change;
        private String changePercent;
        private String iexMarketPercent;
        private String iexVolume;
        private String avgTotalVolume;
        private String iexBidPrice;
        private String iexBidSize;
        private String iexAskPrice;
        private String iexAskSize;
        private String marketCap;
        private String peRatio;
        private String week52High;
        private String week52Low;
        private String ytdChange;

    }

    @Getter
    @Setter
    public static class News {
        private String datetime;
        private String headline;
        private String source;
        private String url;
        private String summary;
        private String related;
        private String image;
    }


    @Getter
    @Setter
    public static class Chart {
        private String date;
        private String open;
        private String high;
        private String low;
        private String close;
        private String volume;
        private String unadjustedVolume;
        private String change;
        private String changePercent;
        private String vwap;
        private String label;
        private String changeOverTime;
    }

    }