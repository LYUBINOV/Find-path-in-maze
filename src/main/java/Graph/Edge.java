package Graph;

import java.util.Objects;

public class Edge {
    private Vertex dst;
    private int weight;
    private String way;

    public Edge(Vertex dst, int weight, String way) {
        this.dst = dst;
        this.weight = weight;
        this.way = way;
    }

    public Vertex getDst() {
        return dst;
    }

    public void setDst(Vertex v1) {
        this.dst = dst;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        Edge edge = (Edge) o;

        return weight == edge.weight && Objects.equals(dst.getVertexName(), edge.dst.getVertexName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dst.getVertexName());
    }
}
