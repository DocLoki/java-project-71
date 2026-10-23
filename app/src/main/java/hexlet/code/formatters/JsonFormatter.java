package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffBuilder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {

  public static String format(List<Map<String, Object>> diff) {
    List<Map<String, Object>> normalized =
        diff.stream().map(JsonFormatter::normalizeEntry).toList();

    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(normalized);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private static Map<String, Object> normalizeEntry(Map<String, Object> entry) {
    Map<String, Object> result = new LinkedHashMap<>();

    entry.forEach(
        (key, value) -> {
          if (value == DiffBuilder.NullValue.INSTANCE) {
            result.put(key, null);
          } else {
            result.put(key, value);
          }
        });

    return result;
  }
}
