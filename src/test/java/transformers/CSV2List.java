package transformers;

import cucumber.api.Transformer;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class CSV2List extends Transformer<List<String>> {
    @Override
    public List<String> transform(String csv) {
        if (StringUtils.isBlank(csv)) {
            return null;
        }

        return Arrays.asList(csv.split("\\s*,\\s*"));
    }
}
