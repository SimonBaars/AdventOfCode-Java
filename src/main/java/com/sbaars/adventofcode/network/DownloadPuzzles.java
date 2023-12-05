package com.sbaars.adventofcode.network;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Files;

import static com.sbaars.adventofcode.network.FetchInput.doRequest;

public class DownloadPuzzles {
  private final HttpClient client;

  public DownloadPuzzles() {
    this.client = WebClient.getClient();
  }

  public static void main(String[] args) {
    DownloadPuzzles dlPuzzles = new DownloadPuzzles();
    dlPuzzles.runForYear("2015");
    dlPuzzles.runForYear("2016");
    dlPuzzles.runForYear("2017");
    dlPuzzles.runForYear("2018");
    dlPuzzles.runForYear("2019");
    dlPuzzles.runForYear("2020");
    dlPuzzles.runForYear("2021");
    dlPuzzles.runForYear("2022");
    dlPuzzles.runForYear("2023");
  }

  public void retrieveTests(String day, String year) {
    String s = doRequest(client,year + "/day/" + day);
    s = s.replace("/static/style.css?31", "../style.css");
    File file = getFile(day, year);
    file.getParentFile().mkdirs();
    writeFile(file, s);
  }

  private void runForYear(String year) {
    for (int day = 1; day <= 25; day++) {
      retrieveTests(Integer.toString(day), year);
    }
  }

  private File getFile(String day, String year) {
    return new File("puzzles/" + year + "/" + day + ".html");
  }

  public static void writeFile(File file, String content) {
    try {
      Files.writeString(file.toPath(), content);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
