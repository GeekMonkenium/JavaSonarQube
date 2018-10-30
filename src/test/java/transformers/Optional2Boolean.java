package transformers;

import cucumber.api.Transformer;
import org.apache.commons.lang.StringUtils;

public class Optional2Boolean extends Transformer<Boolean> {
    @Override
    public Boolean transform(String value) {
        if (StringUtils.isBlank(value)) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

}