package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

  public static Map<String, Object> parse(String filePath) throws Exception {
    String content = Files.readString(Path.of(filePath));

    ObjectMapper mapper;
    if (filePath.endsWith(".yml") || filePath.endsWith(".yaml")) {
      mapper = new ObjectMapper(new YAMLFactory());
    } else {
      mapper = new ObjectMapper();
    }

    return mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
  }
}
