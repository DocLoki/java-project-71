package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class DiffBuilder {

  public static List<Map<String, Object>> build(
      Map<String, Object> data1, Map<String, Object> data2) {

    TreeSet<String> keys = new TreeSet<>();
    keys.addAll(data1.keySet());
    keys.addAll(data2.keySet());

    List<Map<String, Object>> result = new ArrayList<>();

    for (String key : keys) {
      boolean existsInFirst = data1.containsKey(key);
      boolean existsInSecond = data2.containsKey(key);

      if (existsInFirst && existsInSecond) {
        Object value1 = data1.get(key);
        Object value2 = data2.get(key);

        if (Objects.equals(value1, value2)) {
          result.add(
              Map.of(
                  "key",
                  key,
                  "status",
                  "unchanged",
                  "value",
                  value1 == null ? NullValue.INSTANCE : value1));
        } else {
          result.add(
              Map.of(
                  "key",
                  key,
                  "status",
                  "changed",
                  "oldValue",
                  value1 == null ? NullValue.INSTANCE : value1,
                  "newValue",
                  value2 == null ? NullValue.INSTANCE : value2));
        }
      } else if (existsInFirst) {
        result.add(
            Map.of(
                "key",
                key,
                "status",
                "removed",
                "value",
                data1.get(key) == null ? NullValue.INSTANCE : data1.get(key)));
      } else {
        result.add(
            Map.of(
                "key",
                key,
                "status",
                "added",
                "value",
                data2.get(key) == null ? NullValue.INSTANCE : data2.get(key)));
      }
    }

    return result;
  }

  public enum NullValue {
    INSTANCE
  }
}
