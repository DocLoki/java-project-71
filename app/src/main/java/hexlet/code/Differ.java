package hexlet.code;

import java.util.Map;

public class Differ {

  public static String generate(String filePath1, String filePath2) throws Exception {
    return generate(filePath1, filePath2, "stylish");
  }

  public static String generate(String filePath1, String filePath2, String formatName)
      throws Exception {

    Map<String, Object> data1 = Parser.parse(filePath1);
    Map<String, Object> data2 = Parser.parse(filePath2);

    return Formatter.format(DiffBuilder.build(data1, data2), formatName);
  }
}
