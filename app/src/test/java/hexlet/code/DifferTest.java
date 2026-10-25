package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class DifferTest {

  private Path getResourcePath(String name) throws Exception {
    return Path.of(getClass().getClassLoader().getResource(name).toURI());
  }

  @Test
  void testJsonInputDefaultFormat() throws Exception {
    Path file1 = getResourcePath("file1.json");
    Path file2 = getResourcePath("file2.json");

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
  void testYamlInputDefaultFormat() throws Exception {
    Path file1 = getResourcePath("file1.yml");
    Path file2 = getResourcePath("file2.yml");

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
  void testJsonInputStylishFormat() throws Exception {
    Path file1 = getResourcePath("file1.json");
    Path file2 = getResourcePath("file2.json");

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

    assertEquals(expected, Differ.generate(file1.toString(), file2.toString(), "stylish"));
  }

  @Test
  void testYamlInputStylishFormat() throws Exception {
    Path file1 = getResourcePath("file1.yml");
    Path file2 = getResourcePath("file2.yml");

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

    assertEquals(expected, Differ.generate(file1.toString(), file2.toString(), "stylish"));
  }

  @Test
  void testJsonInputPlainFormat() throws Exception {
    Path file1 = getResourcePath("nested1.json");
    Path file2 = getResourcePath("nested2.json");

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
  void testYamlInputPlainFormat() throws Exception {
    Path file1 = getResourcePath("file1.yml");
    Path file2 = getResourcePath("file2.yml");

    String result = Differ.generate(file1.toString(), file2.toString(), "plain");

    assertTrue(result.contains("Property '"));
  }

  @Test
  void testJsonInputJsonFormat() throws Exception {
    Path file1 = getResourcePath("nested1.json");
    Path file2 = getResourcePath("nested2.json");

    String result = Differ.generate(file1.toString(), file2.toString(), "json");

    ObjectMapper mapper = new ObjectMapper();
    List<?> parsed = mapper.readValue(result, List.class);

    assertFalse(parsed.isEmpty());
    assertTrue(result.startsWith("["));
    assertTrue(result.contains("\"status\""));
    assertTrue(result.contains("\"changed\""));
  }

  @Test
  void testYamlInputJsonFormat() throws Exception {
    Path file1 = getResourcePath("file1.yml");
    Path file2 = getResourcePath("file2.yml");

    String result = Differ.generate(file1.toString(), file2.toString(), "json");

    ObjectMapper mapper = new ObjectMapper();
    List<?> parsed = mapper.readValue(result, List.class);

    assertFalse(parsed.isEmpty());
    assertTrue(result.startsWith("["));
    assertTrue(result.contains("\"status\""));
  }
}
