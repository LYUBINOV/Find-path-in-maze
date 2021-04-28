package Graph;

import java.util.*;

public class Graph {
    private List<Vertex> vertexes;
    private Map<String, HashSet<Edge>> edges;
    private Map<String, DijkstraObjectHelper> dijkstra = new HashMap<>();

    public Graph(List<Vertex> vertexes, Map<String, HashSet<Edge>> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public void addEdges(String src, String dst, int EDGE_WEIGHT, String way) {
        Map<String, Vertex> _vertexes = addVertexes(src, dst);

        edges.get(src).add(new Edge(_vertexes.get(dst), EDGE_WEIGHT, way));
        edges.get(dst).add(new Edge(_vertexes.get(src), EDGE_WEIGHT, inverseWay(way)));
    }

    public Map<String, Vertex> addVertexes(String _v1, String _v2) {
        Map<String, Vertex> retVal = new HashMap<>();

        if(!edges.containsKey(_v1)) {
            Vertex v1 = new Vertex(_v1);

            vertexes.add(v1);
            edges.put(v1.getVertexName(), new HashSet<>());

            retVal.put(_v1, v1);
        }
        else {
            retVal.put(_v1, this.findVertex(_v1));
        }

        if(!edges.containsKey(_v2)) {
            Vertex v2 = new Vertex(_v2);

            vertexes.add(v2);
            edges.put(v2.getVertexName(), new HashSet<>());

            retVal.put(_v2, v2);
        }
        else {
            retVal.put(_v2, this.findVertex(_v2));
        }

        return retVal;
    }

    private Vertex findVertex(String vertexName){
        return vertexes.stream().filter(itr -> Objects.equals(itr.getVertexName(), vertexName)).findFirst().orElse(null);
    }

    private String inverseWay(String way) {
        if(Objects.equals(way, "l")) return "r";
        if(Objects.equals(way, "r")) return "l";

        if(Objects.equals(way, "u")) return "d";
        if(Objects.equals(way, "d")) return "u";

        return null;
    }

    /*DIJKSTRA*/
    public class DijkstraObjectHelper implements Comparable<DijkstraObjectHelper> {
        private Vertex vertex;
        private boolean isVisited;
        private int distance;
        private Vertex pathFrom;
        private String way;

        public DijkstraObjectHelper(Vertex vertex, boolean isVisited, int distance, Vertex pathFrom, String way) {
            this.vertex = vertex;
            this.isVisited = isVisited;
            this.distance = distance;
            this.pathFrom = pathFrom;
            this.way = way;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Vertex getPathFrom() {
            return pathFrom;
        }

        public void setPathFrom(Vertex pathFrom) {
            this.pathFrom = pathFrom;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        @Override
        public int compareTo(DijkstraObjectHelper o) {
            return Integer.compare(this.distance, o.getDistance());
        }
    }

    public void dijkstraAlgorithm(String src) {
        vertexes.forEach(v -> dijkstra.put(v.getVertexName(), new DijkstraObjectHelper(v, false, Integer.MAX_VALUE, null, "")));

        DijkstraObjectHelper source = dijkstra.get(src);
        source.setDistance(0);
        source.setVisited(true);

        Queue<DijkstraObjectHelper> queue = new PriorityQueue<>();
        queue.add(source);

        while(!queue.isEmpty()) {
            DijkstraObjectHelper currentPoint = queue.poll();

            for(Edge edge : edges.get(currentPoint.getVertex().getVertexName())) {
                DijkstraObjectHelper neighbour = dijkstra.get(edge.getDst().getVertexName());

                if(!neighbour.isVisited()) {
                    int distance = currentPoint.getDistance() + edge.getWeight();

                    if(distance < neighbour.getDistance()) {
                        queue.remove(neighbour);
                        neighbour.setDistance(distance);
                        neighbour.setPathFrom(currentPoint.getVertex());
                        neighbour.setWay(edge.getWay());
                        queue.add(neighbour);
                    }
                }
            }

            currentPoint.setVisited(true);
        }
    }

    public String shortestPathTo(String dst) {
        if(dijkstra.get(dst).getPathFrom() != null) {
            return shortestPathTo(dijkstra.get(dst).getPathFrom().getVertexName()) + dijkstra.get(dst).getWay() + ",";
//            System.out.print();
        }

        return "";
    }

    /*GETTERS & SETTERS*/
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public Map<String, HashSet<Edge>> getEdges() {
        return edges;
    }

    public void setEdges(Map<String, HashSet<Edge>> edges) {
        this.edges = edges;
    }
}
