package hexlet.code.formatters;

import hexlet.code.DiffBuilder;
import java.util.List;
import java.util.Map;

public class PlainFormatter {

  public static String format(List<Map<String, Object>> diff) {
    return diff.stream()
        .filter(entry -> !"unchanged".equals(entry.get("status")))
        .map(PlainFormatter::formatEntry)
        .reduce((first, second) -> first + "\n" + second)
        .orElse("");
  }

  private static String formatEntry(Map<String, Object> entry) {
    String key = (String) entry.get("key");
    String status = (String) entry.get("status");

    return switch (status) {
      case "added" ->
          "Property '" + key + "' was added with value: " + formatValue(entry.get("value"));
      case "removed" -> "Property '" + key + "' was removed";
      case "changed" ->
          "Property '"
              + key
              + "' was updated. From "
              + formatValue(entry.get("oldValue"))
              + " to "
              + formatValue(entry.get("newValue"));
      default -> throw new IllegalArgumentException("Unknown status: " + status);
    };
  }

  private static String formatValue(Object value) {
    if (value == DiffBuilder.NullValue.INSTANCE) {
      return "null";
    }

    if (value instanceof Map || value instanceof List) {
      return "[complex value]";
    }

    if (value instanceof String) {
      return "'" + value + "'";
    }

    return String.valueOf(value);
  }
}
