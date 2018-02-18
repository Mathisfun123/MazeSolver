import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hradev01 on 25-Oct-17.
 */
public class MazeCreator extends Maze {

  private List<String> map;

  public List<Node> createMaze(String fileName) throws IOException {
    loadFile(fileName);
    return loadMap();
  }

  /**
   * Loads the input file into a 2D array
   * @param fileName the name of the file to get the data from.
   * @throws IOException
   */
  private void loadFile(String fileName) throws IOException {
    map = new ArrayList<>();
    URL url = getClass().getClassLoader().getResource(fileName);
    File file = new File(url.getPath());
    final List<String> firstFew = new ArrayList<>();
    Files.lines(Paths.get(file.getPath())).limit(3).forEach(firstFew::add);
    int[][] temp = new int[3][2];
    for (int i = 0; i < firstFew.size(); i++) {
      String[] splitted = firstFew.get(i).split("\\s+");
      temp[i][0] = Integer.parseInt(splitted[0]);
      temp[i][1] = Integer.parseInt(splitted[1]);
    }
    width = temp[0][0];
    height = temp[0][1];
    maze = new String[width][height];
    start = new Node(temp[1][0], temp[1][1]);
    end = new Node(temp[2][0], temp[2][1]);

    Files.lines(Paths.get(file.getPath())).skip(3).forEach(map::add);
  }

  /**
   * Reads the 2D array and creates a Node object for each tile.
   * The type of the tile is set as one of the Type enum formats.
   * The neighbour and edge of each node is also set.
   * @return a list of Nodes with the whole map
   * @throws IOException
   */
  private List<Node> loadMap() throws IOException {
    vertices = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        String[] splitted = map.get(y).split("\\s+");
        String value = splitted[x];
        maze[x][y] = value;
        Node node = new Node(x, y);
        if (node.equals(start)) {
          node.setType(Type.START);
          node.setWeight(0);
        } else if (node.equals(end)) {
          node.setType(Type.END);
        } else {
          node.setType(getNodeType(value));
        }
        vertices.add(node);
      }
    }
    for (Node v : vertices) {
      setNeighboursAndEdges(v);
    }
    return vertices;
  }

  private Node getNode(int x, int y) {
    return vertices.stream().filter(e -> e.getX() == x && e.getY() == y).findFirst().get();
  }

  /**
   * Sets the north, south, west and east neighbours of each node,
   * with a relevant type. From the neighbours it builds edges,
   * where each edge has a random length.
   * @param current
   */
  private void setNeighboursAndEdges(Node current) {
    List<Node> neightbours = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    Random rand = new Random();
    int x = current.getX();
    int y = current.getY();
    if (y > 0 && y < height - 1) {
      neightbours.add(getNode(x, y - 1));
      neightbours.add(getNode(x, y + 1));
    }
    if (x > 0 && x < width - 1) {
      neightbours.add(getNode(x - 1, y));
      neightbours.add(getNode(x + 1, y));
    }
    current.setNeighbours(neightbours);
    neightbours.stream()
        .filter(e -> !e.getType().equals(Type.WALL))
        .forEach(n -> edges.add(new Edge(current, n, rand.nextInt(15))));
    current.setEdges(edges);
  }

  private Type getNodeType(String value) {
    return value.equals("0") ? Type.FLOOR : Type.WALL;
  }
}

