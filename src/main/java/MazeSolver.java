import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by hradev01 on 26-Oct-17.
 */
public class MazeSolver extends Maze {


  /**
   * Uses Dijkstra's algorithm shortest path algorithm to solve the maze.
   * For each traversable (FLOOR) node it determines it's current weight
   * based on node weight and edge length and chooses the one with the smallest value.
   * When a current smallest value is found it adds the current node as a parent to the
   * neighbour node, until the END node is found.
   * @param vertices the input graph.
   * @throws IOException
   */
  public void solveMaze(List<Node> vertices) throws IOException {
    Node current;
    List<Node> map = vertices.stream()
        .filter(e -> e.getType() != Type.WALL)
        .collect(Collectors.toList());

    while (!map.isEmpty()) {
      current = map.stream().min(Node::compareTo).get();
      if (current.getType().equals(Type.END)) {
        retrievePath(current);
        return;
      } else {
        map.remove(current);
        List<Node> neightbours = current.getNeighbours().stream()
            .filter(Objects::nonNull)
            .filter(e -> !e.getType().equals(Type.WALL))
            .collect(Collectors.toList());
        if (!neightbours.isEmpty()) {
          for (Node n : neightbours) {
            int tempWeight = current.getWeight() + current.getEdge(n).getLength();
            if (tempWeight < n.getWeight()) {
              n.setWeight(tempWeight);
              n.setParent(current);
            }
          }
        }
      }
    }
  }

  /**
   * Replaces the input tiles with the desired string output.
   * @param vertices
   */
  private void resolveMap(List<Node> vertices) {
    maze[start.getX()][start.getY()] = "S";
    maze[end.getX()][end.getY()] = "E";
    vertices.remove(start);
    vertices.remove(end);
    for (Node v : vertices) {
      int x = v.getX();
      int y = v.getY();

      String value = maze[x][y];

      if (value.equals("1")) {
        maze[x][y] = "#";
      } else if (value.equals("0")) {
        maze[x][y] = " ";
      }
    }
  }

  /**
   * Prints the final result.
   */
  private void printOutput() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        System.out.print(maze[x][y]);
      }
      System.out.println();
    }
  }

  /**
   * Goes through each parent of the last node and adds them to list
   * The output list is the path with all nodes with smallest weight which is
   * the shortest path from START to END.
   * @param current
   * @throws IOException
   */
  private void retrievePath(Node current) throws IOException {
    List<Node> path = new ArrayList<>();
    Node target = current;
    while (target.getParent() != null) {
      path.add(target);
      target = target.getParent();
    }
    path.remove(end);
    resolveMap(vertices);
    for (Node p : path) {
      maze[p.getX()][p.getY()] = "X";
    }
    printOutput();
  }
}
