package com.hybris.easyjet.fixture.hybris.invoke.services;

import com.hybris.easyjet.config.EasyjetHybrisConfig;
import com.hybris.easyjet.fixture.hybris.invoke.requests.refdata.*;
import com.hybris.easyjet.fixture.hybris.invoke.services.refdata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by daniel on 02/12/2016.
 * this factory class is under spring control and therefore allows autowiring of configuration and jersey client in the
 * instantiation of new 'service' objects, calling get with a specific type of request as the argument will return the correct
 * service object, ready to be controlled, modified, invoked and queried
 */
@Component
public class HybrisServiceFactory {

    private final EasyjetHybrisConfig config;


    /**
     * @param config autowired configuration
     */
    @Autowired
    public HybrisServiceFactory(EasyjetHybrisConfig config) {
        this.config = config;
    }

    public SymbolService getMarketSymbol(SymbolRequest symbolRequest) {
        return new SymbolService(symbolRequest, config.getIEXSymbolEndpoint());
    }

}
