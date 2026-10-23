package hexlet.code;

import hexlet.code.formatters.StylishFormatter;
import java.util.Map;

public class Differ {

  public static String generate(String filePath1, String filePath2) throws Exception {
    Map<String, Object> data1 = Parser.parse(filePath1);
    Map<String, Object> data2 = Parser.parse(filePath2);

    return StylishFormatter.format(DiffBuilder.build(data1, data2));
  }
}
