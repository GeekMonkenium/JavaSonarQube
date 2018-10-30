package com.hybris.easyjet.fixture.hybris.invoke.queryparams;

import com.hybris.easyjet.fixture.IQueryParams;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by daniel on 28/11/2016.
 */
abstract class QueryParameters implements IQueryParams {

    boolean isPopulated(Boolean value) {
        return Objects.nonNull(value);
    }

    boolean isPopulated(String value) {
        return StringUtils.isNotBlank(value);
    }

    boolean isPopulated(List value) {
        return CollectionUtils.isNotEmpty(value);
    }
}