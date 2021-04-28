package Main;

import Graph.Graph;
import PathInputReader.FindPathInputReaderFile;
import PathInputReader.FindPathInputReaderStdIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class ApplicationRunner {
    private static final int EDGE_WEIGHT = 1; //pretoze vzdy len o jedno sa viem posuvat

    private static Scanner scanner = new Scanner(System.in);

    public static String source;
    public static String destination;

    public ApplicationRunner() throws IOException {
        Graph graph = createGraph(run());

        System.out.println();

        System.out.println("Vysledok:");
        graph.dijkstraAlgorithm(source);
        System.out.println(graph.shortestPathTo(destination));

        System.out.println();
    }

    private String[][] run() throws IOException {
        //na testovanie
//        String[][] maze = {
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".","S",".",".",".","#",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".","."},
//            {".",".",".",".",".",".","#",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".",".",".",".",".",".","#",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".","X",".",".",".",".",".","#",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","#",".",".",".",".",".",".",".",".",".",".","#",".",".","."},
//            {".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."}
//        };

        System.out.println("1. Nacitanie zo suboru");
        System.out.println("2. Nacitanie zo vstupu");

        String choose = scanner.nextLine();

        String[][] maze = null;
        switch (choose) {
            case "1":
            {
                FindPathInputReaderFile findPathInputReaderFile = new FindPathInputReaderFile();
                maze = findPathInputReaderFile.read("matrix.txt");
            }
            break;
            case "2":
            {
                System.out.println("Zadajte pocet riadkov");
                int rows = Integer.parseInt(scanner.nextLine());
                System.out.println("Zadajte pocet stlpcov");
                int cols = Integer.parseInt(scanner.nextLine());
                System.out.println("Zadajte bludisko ako cely retazec");
                String _maze = scanner.nextLine();
                FindPathInputReaderStdIn findPathInputReaderStdIn = new FindPathInputReaderStdIn();
                maze = findPathInputReaderStdIn.read(_maze, rows, cols);
            }
            break;
            default:
            {
                FindPathInputReaderFile findPathInputReaderFile = new FindPathInputReaderFile();
                maze = findPathInputReaderFile.read("matrix.txt");
            }
            break;
        }

        return maze;
    }

    public static Graph createGraph(String[][] maze) {
        Graph retVal = new Graph(new ArrayList<>(), new HashMap<>());
        //todo: implement count of rows and columnts, check if maze is rectangle

        for (int row = 0; row < maze.length; ++row) {
            for (int col = 0; col < maze[row].length; ++col) {
                String item = maze[row][col];

                if(!Objects.equals(item, "#")) {
                    if (Objects.equals(item, "S")) {
                        source = createVertexName(row, col);
                    }

                    if(Objects.equals(item, "X")) {
                        destination = createVertexName(row, col);
                    }

                    String actualPoint = createVertexName(row, col);

                    //su 4 smery ktorymi sa moze pohybovat
                    if((row - 1) >= 0 && (row - 1) < maze.length && !Objects.equals(maze[row - 1][col], "#")) {
                        retVal.addEdges(actualPoint, createVertexName(row - 1, col), EDGE_WEIGHT, "u");
                    }

                    if((row + 1) < maze.length && (row + 1) >= 0 && !Objects.equals(maze[row + 1][col], "#")) {
                        retVal.addEdges(actualPoint, createVertexName(row + 1, col), EDGE_WEIGHT, "d");
                    }

                    if((col - 1) >= 0 && (col - 1) < maze[row].length && !Objects.equals(maze[row][col - 1], "#")) {
                        retVal.addEdges(actualPoint, createVertexName(row, col - 1), EDGE_WEIGHT, "l");
                    }

                    if((col + 1) < maze[row].length && (col + 1) >= 0 && !Objects.equals(maze[row][col + 1], "#")) {
                        retVal.addEdges(actualPoint, createVertexName(row, col + 1), EDGE_WEIGHT, "r");
                    }
                }
            }
        }

        return retVal;
    }

    private static String createVertexName(int row, int col) {
        return Integer.toString(row) + "-" + Integer.toString(col);
    }
}
