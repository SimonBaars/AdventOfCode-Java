package com.sbaars.adventofcode.year19.pathfinding;
import java.util.Set;

/**
 * A node in a graph, useful for pathfinding.
 * 
 * @author Ben Ruijl
 * 
 * @param <T>
 *            Actual type of the node
 */
public interface Node<T> {
	/**
	 * The heuristic cost to move from the current node to the goal. In most
	 * cases this is the Manhattan distance or Chebyshev distance.
	 * 
	 * @param goal
	 * @return
	 */
	double getHeuristic(T goal);

	/**
	 * The cost of moving from the current node to the neighbour. In most cases
	 * this is just the distance between the nodes, but additional terrain cost
	 * can be added as well (for example climbing a mountain is more expensive
	 * than walking on a road).
	 * 
	 * @param neighbour
	 *            Neighbour of current node
	 * @return Traversal cost
	 */
	double getTraversalCost(T neighbour);

	/**
	 * Gets the set of neighbouring nodes.
	 * 
	 * @return Neighbouring nodes
	 */
	Set<T> getNeighbours();
}
