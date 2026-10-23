package hexlet.code.formatters;

import hexlet.code.DiffBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StylishFormatter {

  public static String format(List<Map<String, Object>> diff) {
    List<String> lines =
        diff.stream().map(StylishFormatter::formatEntry).collect(Collectors.toList());

    return "{\n" + String.join("\n", lines) + "\n}";
  }

  private static String formatEntry(Map<String, Object> entry) {
    String key = (String) entry.get("key");
    String status = (String) entry.get("status");

    return switch (status) {
      case "unchanged" -> "    " + key + ": " + valueToString(entry.get("value"));
      case "added" -> "  + " + key + ": " + valueToString(entry.get("value"));
      case "removed" -> "  - " + key + ": " + valueToString(entry.get("value"));
      case "changed" ->
          "  - "
              + key
              + ": "
              + valueToString(entry.get("oldValue"))
              + "\n  + "
              + key
              + ": "
              + valueToString(entry.get("newValue"));
      default -> throw new IllegalArgumentException("Unknown status: " + status);
    };
  }

  private static String valueToString(Object value) {
    if (value == DiffBuilder.NullValue.INSTANCE) {
      return "null";
    }
    return String.valueOf(value);
  }
}
