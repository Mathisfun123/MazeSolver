import java.util.List;

/**
 * Created by hradev01 on 25-Oct-17.
 */
public class Node implements Comparable<Node> {

    private int x;
    private int y;
    private int weight = Integer.MAX_VALUE;
    private Node parent;
    private List<Node> neighbours;
    private Type type;
    private List<Edge> edges;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Node> neighbours) {
        this.neighbours = neighbours;
    }

    void addEdge(Edge e) {
        edges.add(e);
        neighbours.add(e.getOther(this));
    }

    public Edge getEdge(Node q) {
        for (Edge e : edges) {
            if (e.getDest().equals(q)) {
                return e;
            }
        }
        throw new IllegalArgumentException("getEdge: Node must be a neighbour of this Node");
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.weight, o.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;

        if (x != node.x) {
            return false;
        }
        return y == node.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
            "x=" + x +
            ", y=" + y +
            ", weight=" + weight +
            '}';
    }
}
