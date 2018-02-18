import java.io.IOException;
import java.util.List;

/**
 * Created by hradev01 on 26-Oct-17.
 */
public class Launcher {

  public static void main(String[] args) {
    Launcher launcher = new Launcher();
    launcher.launch("large_input.txt");
  }

  private void launch(String fileName) {
    try {
      MazeCreator creator = new MazeCreator();
      MazeSolver solver = new MazeSolver();
      List<Node> graph = creator.createMaze(fileName);
      solver.solveMaze(graph);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
