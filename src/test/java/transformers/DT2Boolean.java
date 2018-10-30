package transformers;

import cucumber.api.Transformer;

public class DT2Boolean extends Transformer<Boolean> {
    @Override
    public Boolean transform(String value) {
        switch (value) {
            case "d":
                return Boolean.FALSE;
            case "t":
                return Boolean.TRUE;
            default:
                throw new IllegalArgumentException("The successful check must be either d or t");
        }
    }

}