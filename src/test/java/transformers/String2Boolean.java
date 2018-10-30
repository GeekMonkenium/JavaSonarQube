package transformers;

import cucumber.api.Transformer;
import org.apache.commons.lang.StringUtils;

public class String2Boolean extends Transformer<Boolean> {
    @Override
    public Boolean transform(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        if (value.contains("without") || value.contains("should not") || value.contains("do not want")) {
            return false;
        }

        if (value.contains("with") || value.contains("should") || value.contains("want")) {
            return true;
        }
        throw new IllegalArgumentException("String2Boolean transformer cannot transform " + value + " to a boolean value");
    }

}