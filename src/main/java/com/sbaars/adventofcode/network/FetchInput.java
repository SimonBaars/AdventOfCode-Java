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

public class FetchInput {
  private final HttpClient client;

  public FetchInput() {
    this.client = WebClient.getClient();
  }

  public static void main(String[] args) {
    new FetchInput().retrieveDay("11", "2024");
  }

  private void retrieveDay(String day, String year) {
    retrieveInput(day, year);
    retrieveExamples(day, year);
  }

  public void retrieveExamples(String day, String year) {
    var matches = getMatchesByXpath(doRequest(client, year + "/day/" + day), "//pre/code");
    for (int i = 0; i < matches.size(); i++) {
      File file = getFile(day + "-" + (i + 1), year + "-examples");
      file.getParentFile().mkdirs();
      if (!file.exists()) {
        writeFile(file, clean(matches.get(i)));
      }
    }
  }

  // Turn Windows line separators into *nix ones
  private String clean(String file) {
    return file
        .replace("\r\n", "\n")
        .replace("&gt;", ">")
        .replace("&lt;", "<");
  }

  private void runForYear(String year) {
    for (int day = 1; day <= 25; day++) {
      retrieveDay(Integer.toString(day), year);
    }
  }

  public void retrieveInput(String day, String year) {
    File dayFile = getFile(day, year);
    dayFile.getParentFile().mkdirs();
    if (!dayFile.exists()) {
      writeFile(dayFile, clean(doRequest(client, year + "/day/" + day + "/input")));
    }
  }

  private File getFile(String day, String year) {
    return getResource(year + "/day" + day + ".txt");
  }

  public static void writeFile(File file, String content) {
    try {
      Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private File getResource(String path) {
    return new File("src/main/resources/" + path);
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

  public static String doRequest(HttpClient client, String path) {
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
