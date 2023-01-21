package com.sbaars.adventofcode.network;

import java.net.*;
import java.net.http.HttpClient;
import java.time.Duration;

import static com.sbaars.adventofcode.common.Day.getResourceAsString;

public class WebClient {
  public static HttpClient getClient() {
    String session = getResourceAsString("APIKEY").trim();
    CookieHandler.setDefault(new CookieManager());

    HttpCookie sessionCookie = new HttpCookie("session", session);
    sessionCookie.setPath("/");
    sessionCookie.setVersion(0);

    try {
      ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://adventofcode.com"), sessionCookie);
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }

    return HttpClient.newBuilder()
        .cookieHandler(CookieHandler.getDefault())
        .connectTimeout(Duration.ofSeconds(10))
        .build();
  }
}
