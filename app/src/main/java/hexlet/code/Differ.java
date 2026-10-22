package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class Differ {

  public static String generate(String filePath1, String filePath2) throws Exception {
    Map<String, Object> data1 = Parser.parse(filePath1);
    Map<String, Object> data2 = Parser.parse(filePath2);

    TreeSet<String> keys = new TreeSet<>();
    keys.addAll(data1.keySet());
    keys.addAll(data2.keySet());

    List<String> lines = new ArrayList<>();
    lines.add("{");

    for (String key : keys) {
      boolean existsInFirst = data1.containsKey(key);
      boolean existsInSecond = data2.containsKey(key);

      if (existsInFirst && existsInSecond) {
        Object value1 = data1.get(key);
        Object value2 = data2.get(key);

        if (Objects.equals(value1, value2)) {
          lines.add("    " + key + ": " + value1);
        } else {
          lines.add("  - " + key + ": " + value1);
          lines.add("  + " + key + ": " + value2);
        }
      } else if (existsInFirst) {
        lines.add("  - " + key + ": " + data1.get(key));
      } else {
        lines.add("  + " + key + ": " + data2.get(key));
      }
    }

    lines.add("}");
    return String.join("\n", lines);
  }
}
