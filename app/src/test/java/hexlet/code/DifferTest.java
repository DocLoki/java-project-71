package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class DifferTest {

  @Test
  void testGenerate() throws Exception {
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
}
