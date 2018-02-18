/**
 * Created by hradev01 on 25-Oct-17.
 */
public class Edge {

  private Node src;
  private Node dest;
  private int length;

  public Edge(Node src, Node dest, int len) {
    this.src = src;
    this.dest = dest;
    this.length = len;
  }

  public Node getSrc() {
    return src;
  }

  public void setSrc(Node src) {
    this.src = src;
  }

  public Node getDest() {
    return dest;
  }

  public void setDest(Node dest) {
    this.dest = dest;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public Node getOther(Node n) {
    if (src == n) {
      return dest;
    }
    if (dest == n) {
      return src;
    }
    throw new IllegalArgumentException("getOther: Edge must contain provided node");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Edge edge = (Edge) o;

    if (length != edge.length) {
      return false;
    }
    if (src != null ? !src.equals(edge.src) : edge.src != null) {
      return false;
    }
    return dest != null ? dest.equals(edge.dest) : edge.dest == null;
  }

  @Override
  public int hashCode() {
    int result = src != null ? src.hashCode() : 0;
    result = 31 * result + (dest != null ? dest.hashCode() : 0);
    result = 31 * result + length;
    return result;
  }

  @Override
  public String toString() {
    return "Edge{" +
        "src=" + src +
        ", dest=" + dest +
        ", length=" + length +
        '}';
  }
}
