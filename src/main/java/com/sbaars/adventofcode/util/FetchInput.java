package com.sbaars.adventofcode.util;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FetchInput {
  private final HttpClient client;

  private FetchInput(String session) {
    CookieHandler.setDefault(new CookieManager());

    HttpCookie sessionCookie = new HttpCookie("session", session);
    sessionCookie.setPath("/");
    sessionCookie.setVersion(0);

    try {
      ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://adventofcode.com"), sessionCookie);
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }

    this.client = HttpClient.newBuilder()
        .cookieHandler(CookieHandler.getDefault())
        .connectTimeout(Duration.ofSeconds(10))
        .build();
  }

  public static void main(String[] args) throws Exception {
//    String year = args[0];
//    String day = args[1];
    String session = System.getenv("AOCSESSION");
    new FetchInput(session).retrieveDay("1", "2021");
  }

  private void retrieveDay(String day, String year) {
    retrieveInput(day, year);
    retrieveExamples(day, year);
  }

  private void retrieveExamples(String day, String year) {
    var matches = getMatchesByXpath(doRequest(year + "/day/" + day), "//pre/code");
    for (int i = 0; i < matches.size(); i++) {
      File file = getFile(day + "-" + (i + 1), year + "-examples");
      file.getParentFile().mkdirs();
      if (!file.exists()) {
        writeFile(file, matches.get(i));
      }
    }
  }

  private void runForYear(String year) {
    for (int day = 1; day <= 25; day++) {
      retrieveDay(Integer.toString(day), year);
    }
  }

  private void retrieveInput(String day, String year) {
    File dayFile = getFile(day, year);
    dayFile.getParentFile().mkdirs();
    if (!dayFile.exists()) {
      writeFile(dayFile, doRequest(year + "/day/" + day + "/input"));
    }
  }

  private File getFile(String day, String year) {
    return getResource(year + "/day" + day + ".txt");
  }

  private void writeFile(File file, String content) {
    try {
      FileUtils.writeStringToFile(file, content);
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
      org.w3c.dom.Document doc = new DomSerializer(
          new CleanerProperties()).createDOM(tagNode);

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
