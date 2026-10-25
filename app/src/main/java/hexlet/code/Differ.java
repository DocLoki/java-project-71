package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Differ {

  public static String generate(String filePath1, String filePath2) throws Exception {
    return generate(filePath1, filePath2, "stylish");
  }

  public static String generate(String filePath1, String filePath2, String formatName)
      throws Exception {

    String content1 = Files.readString(Path.of(filePath1));
    String content2 = Files.readString(Path.of(filePath2));

    Map<String, Object> data1 = Parser.parse(content1, getDataFormat(filePath1));
    Map<String, Object> data2 = Parser.parse(content2, getDataFormat(filePath2));

    return Formatter.format(DiffBuilder.build(data1, data2), formatName);
  }

  private static String getDataFormat(String filePath) throws Exception {
    if (filePath.endsWith(".yml")) {
      return "yml";
    }
    if (filePath.endsWith(".yaml")) {
      return "yaml";
    }
    if (filePath.endsWith(".json")) {
      return "json";
    }

    throw new Exception("Unknown data format: " + filePath);
  }
}
