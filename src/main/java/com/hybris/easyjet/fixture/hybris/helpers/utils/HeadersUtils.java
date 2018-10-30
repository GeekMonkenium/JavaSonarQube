package com.hybris.easyjet.fixture.hybris.helpers.utils;

import com.hybris.easyjet.config.SerenityFacade;
import com.hybris.easyjet.fixture.hybris.invoke.HybrisHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import static com.hybris.easyjet.config.SerenityFacade.DataKeys.HEADERS;

@Component
public class HeadersUtils {

    @Autowired
    SerenityFacade testData;
    private String channelUsed;

    public HybrisHeaders.HybrisHeadersBuilder getValidHeader() {
        String channel;

            HybrisHeaders.HybrisHeadersBuilder hybrisHeaders = testData.getData(HEADERS);
            testData.setData(HEADERS, hybrisHeaders);
            return testData.getData(SerenityFacade.DataKeys.HEADERS);

    }

    public HybrisHeaders.HybrisHeadersBuilder getValidHeader(String channel) {
        if (Objects.isNull(testData.getData(SerenityFacade.DataKeys.HEADERS))) {
            return HybrisHeaders.getValid(channel);
        } else
            return testData.getData(SerenityFacade.DataKeys.HEADERS);

    }


}
