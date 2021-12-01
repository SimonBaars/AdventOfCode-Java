package com.sbaars.adventofcode.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Submit {

  private final HttpClient client;

  public Submit() {
    this.client = WebClient.getClient();
  }

  public void submit(Object answer, int year, int day, int part){
    String res = doRequest(answer.toString(), year, day, part);
    if(res.contains("<article>")){
      res = res.substring(res.indexOf("<article>"), res.indexOf("</article>"));
    }
    System.out.println(res);
  }

  private String doRequest(String answer, int year, int day, int part) {
    System.out.println("level="+part+"&answer="+answer);
    try {
      HttpRequest req = HttpRequest.newBuilder()
          .uri(URI.create("https://adventofcode.com/"+year+"/day/"+day+"/answer"))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .POST(HttpRequest.BodyPublishers.ofString("level="+part+"&answer="+answer)).build();
      System.out.println(req);
      return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
