package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class DifferTest {

  @Test
  void testGenerateJson() throws Exception {
    Path file1 = Path.of(getClass().getClassLoader().getResource("file1.json").toURI());
    Path file2 = Path.of(getClass().getClassLoader().getResource("file2.json").toURI());

    String expected =
        """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

    assertEquals(expected, Differ.generate(file1.toString(), file2.toString()));
  }

  @Test
  void testGenerateYaml() throws Exception {
    Path file1 = Path.of(getClass().getClassLoader().getResource("file1.yml").toURI());
    Path file2 = Path.of(getClass().getClassLoader().getResource("file2.yml").toURI());

    String expected =
        """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

    assertEquals(expected, Differ.generate(file1.toString(), file2.toString()));
  }

  @Test
  void testGenerateNestedJson() throws Exception {
    Path file1 = Path.of(getClass().getClassLoader().getResource("nested1.json").toURI());
    Path file2 = Path.of(getClass().getClassLoader().getResource("nested2.json").toURI());

    String result = Differ.generate(file1.toString(), file2.toString());

    assertEquals(
        """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""",
        result);
  }
}
