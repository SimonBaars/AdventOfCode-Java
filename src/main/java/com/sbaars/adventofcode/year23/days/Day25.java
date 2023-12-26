package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.map.ListMap.toListMap;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day25 extends Day2023 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  public record Wiring(String comp, List<String> connectedTo) {}

  @Override
  public Object part1() {
    ListMap<String, String> connections = dayStream().map(s -> readString(s, "%s: %ls", Wiring.class)).flatMap(w -> w.connectedTo.stream().map(c -> new Pair<>(w.comp, c))).flatMap(p -> Stream.of(p, p.flip())).collect(toListMap(Pair::a, Pair::b));
    System.out.println(minCut(connections, new ArrayList<>(connections.keySet())));
    return "";
  }

  @Override
  public Object part2() {
    return "";
  }

  public static int minCut(Map<String, List<String>> adjList, List<String> vertices)
  {
    // Initialize the new label that would be applied to contracted edges to begin
    // with n+1 (just make sure that labels don't repeat and so they conflict)
    String label = "aaaaaa";
    // Need to loop over the graph until there are only 2 vertices remained.
    while (vertices.size() > 2)
    {
      // Build an edge matrix (m x 2) corresponding to the edge graph
      // This is updated at each iteration in order to synchronize it with the new graph changes.
      int noOfEdges = getNoOfEdges(adjList, vertices);
      String[][] edges = buildEdges(noOfEdges, adjList, vertices);

      //Randomly choose an edge
      Random rand = new Random();

      // Randomly choose an edge numbered from 0 to no of edges - 1.
      int no = rand.nextInt(edges.length);
      // Let vertex1 be the first end of the randomly chosen edge
      String vertex1 = edges[no][0];
      // Let vertex2 be the second end of the randomly chosen edge
      String vertex2 = edges[no][1];

      // Let valuesV1 be the array of values corresponding to vertex1
      List<String> valuesV1 = adjList.get(vertex1);
      // Let valuesV2 be the array of values corresponding to vertex2
      List<String> valuesV2 = adjList.get(vertex2);

      // Add all values from vertex2 to vertex1
      valuesV1.addAll(valuesV2);

      // Remove vertex2 from the array of vertices
      vertices.remove(vertices.indexOf(vertex2));

      // Remove vertex2 from the adjacency list
      adjList.remove(vertex2);

      // Give a new label to vertex1 and change all the labels of
      // of vertex1 or vertex2 to the new label
      for(int i = 0; i < vertices.size(); i++)
      {
        List<String> values = adjList.get(vertices.get(i));
        for(int j = 0; j < values.size(); j++)
        {
          if(values.get(j).equals(vertex1))
            values.set(j, label);
          if(values.get(j).equals(vertex2))
            values.set(j, label);
        } // for
      } // for

      // Change the label of the key node vertex1 in the adjList
      List<String> values = adjList.get(vertex1);
      adjList.remove(vertex1);
      adjList.put(label, values);

      // Change the label of vertex1 in the vertices ArrayList
      int index = vertices.indexOf(vertex1);
      vertices.set(index, label);

      // Remove self loops by looping over the element of the new label
      // and by deleting all edges outgoing to its own label.
      int position = 0;
      int length = adjList.get(label).size();
      while (position < length)
      {
        if(adjList.get(label).get(position).equals(label))
        {
          adjList.get(label).remove(position);
          // Need to decrease length, since we removed an edge
          length--;
        } // if
        else
          position++;
      } // while

      // Increase the label by 1 so its uniqueness is preserved
      label+="a";

    } // while

    // The minimum cut no is the number of edges between the two vertices left.
    return adjList.get(vertices.get(0)).size();
  }

  public static String[][] buildEdges(int noOfEdges, Map<String, List<String>> adjList, List<String> vertices)
  {
    int k = 0;
    String[][] edges = new String[noOfEdges][2];
    for (String vertex : vertices) {
      List<String> vector = adjList.get(vertex);
      for (String integer : vector) {
        edges[k][0] = vertex;
        edges[k][1] = integer;
        k++;
      } // for
    } // for
    return edges;
  } // buildEdges

  // Given the adjList and the vertices of a graph, this method returns the number of edges
  public static int getNoOfEdges(Map<String, List<String>> adjList, List<String> vertices)
  {
    int noOfEdges = 0;
    for (String vertex : vertices) {
      List<String> vector = adjList.get(vertex);
      noOfEdges += vector.size();
    } // for
    return noOfEdges;
  }
}
