package heuristics.fromConvexHull.edgeChoice;

import basic.Edge;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SelectEdges {

  List<Edge> selectEdges(int n);

  /**
   * sort the list in order of apperance in the convexHull
   * @param edges selected edges
   */
  default void sortBByA(List<Edge> convexHull, List<Edge> edges) {
    // Create a map from elements in A to their indices
    Map<Edge, Integer> indexMap = new HashMap<>();
    for (int i = 0; i < convexHull.size(); i++)
      indexMap.put(convexHull.get(i), i);

    // Sort B using the indices from the map
    edges.sort(Comparator.comparingInt(indexMap::get));
  }
}
