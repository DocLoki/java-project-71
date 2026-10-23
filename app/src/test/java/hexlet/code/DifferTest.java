package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
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

  @Test
  void testGeneratePlain() throws Exception {
    Path file1 = Path.of(getClass().getClassLoader().getResource("nested1.json").toURI());
    Path file2 = Path.of(getClass().getClassLoader().getResource("nested2.json").toURI());

    String expected =
        """
            Property 'chars2' was updated. From [complex value] to false
            Property 'checked' was updated. From false to true
            Property 'default' was updated. From null to [complex value]
            Property 'id' was updated. From 45 to null
            Property 'key1' was removed
            Property 'key2' was added with value: 'value2'
            Property 'numbers2' was updated. From [complex value] to [complex value]
            Property 'numbers3' was removed
            Property 'numbers4' was added with value: [complex value]
            Property 'obj1' was added with value: [complex value]
            Property 'setting1' was updated. From 'Some value' to 'Another value'
            Property 'setting2' was updated. From 200 to 300
            Property 'setting3' was updated. From true to 'none'""";

    assertEquals(expected, Differ.generate(file1.toString(), file2.toString(), "plain"));
  }

  @Test
  void testGenerateJsonFormat() throws Exception {
    Path file1 = Path.of(getClass().getClassLoader().getResource("nested1.json").toURI());
    Path file2 = Path.of(getClass().getClassLoader().getResource("nested2.json").toURI());

    String result = Differ.generate(file1.toString(), file2.toString(), "json");

    com.fasterxml.jackson.databind.ObjectMapper mapper =
        new com.fasterxml.jackson.databind.ObjectMapper();

    List<?> parsed = mapper.readValue(result, List.class);

    assertFalse(parsed.isEmpty());
    assertTrue(result.startsWith("["));
    assertTrue(result.contains("\"status\""));
    assertTrue(result.contains("\"changed\""));
  }
}
