package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class Parser {

  public static Map<String, Object> parse(String content, String dataFormat) throws Exception {

    ObjectMapper mapper =
        switch (dataFormat) {
          case "yml", "yaml" -> new ObjectMapper(new YAMLFactory());
          case "json" -> new ObjectMapper();
          default -> throw new Exception("Unknown data format: " + dataFormat);
        };

    return mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
  }
}
