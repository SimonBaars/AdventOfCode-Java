package com.sbaars.adventofcode.network;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.IntStream;

public class DownloadTests {
  private final HttpClient client;

  public DownloadTests() {
    this.client = WebClient.getClient();
  }

  public static void main(String[] args) {
    new DownloadTests().retrieveTests("19", "2022");
  }

  public void retrieveTests(String day, String year) {
    var matches = getMatchesByXpath(doRequest(year + "/day/" + day), "/html/body/main/p/code");
    String y = year.substring(2);
    getFile(day, y).getParentFile().mkdirs();
    writeFile(getFile(day, y), """
            package com.sbaars.adventofcode.year%s.days;
                        
            import static org.junit.jupiter.api.Assertions.assertEquals;
                        
            import org.junit.jupiter.api.Test;
            import com.sbaars.adventofcode.year%s.days.Day%s;
                        
            class Day%sTest {
                Day%s day = new Day%s();
                
                @Test
                void testPart1() {
                    assertEquals("%s", day.part1().toString());
                }
                
                @Test
                void testPart2() {
                    assertEquals("%s", day.part2().toString());
                }
            }
          """.formatted(y, y, day, day, day, day, !matches.isEmpty() ? matches.get(0) : "true", matches.size() > 1 ? matches.get(1) : "true"));
  }

  private void runForYear(String year) {
    for (int day = 1; day <= 25; day++) {
      retrieveTests(Integer.toString(day), year);
    }
  }

  private File getFile(String day, String year) {
    return new File("src/test/java/com/sbaars/adventofcode/year"+year + "/days/Day" + day + "Test.java");
  }

  public static void writeFile(File file, String content) {
    try {
      Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private List<String> getMatchesByXpath(String html, String xpath) {
    try {
      TagNode tagNode = new HtmlCleaner().clean(html);
      org.w3c.dom.Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);

      XPath xpathObj = XPathFactory.newInstance().newXPath();
      NodeList matches = (NodeList) xpathObj.evaluate(xpath, doc, XPathConstants.NODESET);
      return IntStream.range(0, matches.getLength()).mapToObj(matches::item).map(Node::getTextContent).toList();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private String doRequest(String path) {
    try {
      HttpRequest req = HttpRequest.newBuilder()
          .uri(URI.create("https://adventofcode.com/" + path))
          .GET().build();
      return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
