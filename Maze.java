/**
 * This program is an implementation of the algorithm defined in Chapter 8 of the textbook
 * The resultant maze is displayed on a separate window
 * @author Praveen Aravendan
 */
import java.io.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;

class Point {

    public boolean visitedIndex;
    public LinkedList<Point> adjacentList;
    public Double distance;
    public int mazeIndex;

    public Point()
    {
        visitedIndex = false;
        adjacentList = new LinkedList();
        distance = Double.POSITIVE_INFINITY;
    }

    public void makeAdjacent(Point a)
    {
        adjacentList.add(a);
    }
}

/**
 * This is the class that contains all the attributes and methods required for the Maze
 */
public class Maze 
{
    static int rows, cols;
    static int[][] maze;
    static int[][] mazeIndex;
    static DisjSets setResultMaze;
    static Point[][] resultMaze;

    /**
     * This method is used to generate the Maze
     */
    public static void generateMaze()
    {
        boolean flag = false;
        while (!flag) {

            int val1 = (int) (Math.random() * 10000) % (cols * rows);
            int rt1Height = getMazeHeight(val1);
            int rt1Width = getMazeWidth(val1);

            while (getAdjacentVal(val1) < 0)
            {
                val1 = (int) (Math.random() * 10000) % (cols * rows);
            }
            int val2 = getAdjacentVal(val1);

            int rt2Height = getMazeHeight(val2);
            int rt2Width = getMazeWidth(val2);

            int minWidth = getMazeWidth(Math.min(val1, val2));
            int minHeight = getMazeHeight(Math.min(val1, val2));

            if (!(setResultMaze.find(val1) == setResultMaze.find(val2)))
            {
                setResultMaze.union(setResultMaze.find(val1), setResultMaze.find(val2));
                if (rt1Height == rt2Height)
                {
                    if (maze[rt1Height][minWidth] == 3)
                        maze[rt1Height][minWidth] = 2;
                    else
                        maze[rt1Height][minWidth] = 0;
                } else if (rt1Width == rt2Width)
                {
                    if (maze[minHeight][rt1Width] == 3)
                        maze[minHeight][rt1Width] = 1;
                    else
                        maze[minHeight][rt1Width] = 0;
                }
            }
            for (int i = 0; i < cols * rows; i++)
            {
                if (setResultMaze.set[i] == -1 * (cols * rows))
                {
                    flag = true;
                }
            }
        }
    }

    public static void makePoint()
    {
        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                resultMaze[i][j] = new Point();
                resultMaze[i][j].mazeIndex = mazeIndex[i][j];
            }
        }
        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (i != 0)
                {
                    if (maze[i - 1][j] == 0 || maze[i - 1][j] == 1)
                    {
                        resultMaze[i][j].makeAdjacent(resultMaze[i - 1][j]);
                    }
                }
                if (j != 0)
                {
                    if (maze[i][j - 1] == 0 || maze[i][j - 1] == 2)
                    {
                        resultMaze[i][j].makeAdjacent(resultMaze[i][j - 1]);
                    }
                }
                if (i != (cols - 1))
                {
                    if (maze[i][j] == 0 || maze[i][j] == 1)
                    {
                        resultMaze[i][j].makeAdjacent(resultMaze[i + 1][j]);
                    }
                }
                if (j != (rows - 1))
                {
                    if (maze[i][j] == 0 || maze[i][j] == 2)
                    {
                        resultMaze[i][j].makeAdjacent(resultMaze[i][j + 1]);
                    }
                }
            }
        }
    }

    /**
     * This method is used to obtain height of the maze
     */
    public static int getMazeHeight(int cellIndexVal) 
    {
        for (int i = 0; i < mazeIndex.length; i++)
        {
            for (int j = 0; j < mazeIndex[i].length; j++)
            {
                if (mazeIndex[i][j] == cellIndexVal)
                    return i;
            }
        }
        return -1;
    }

    /**
     * This method is used to obtain the width of the maze
     */
    public static int getMazeWidth(int cellIndexVal)
    {
        for (int i = 0; i < mazeIndex.length; i++)
        {
            for (int j = 0; j < mazeIndex[i].length; j++)
            {
                if (mazeIndex[i][j] == cellIndexVal)
                {
                    return j;
                }
            }
        }
        return -1;
    }

    public static int getAdjacentVal(int cell) 
    {
        int cellWidth = getMazeWidth(cell);
        int cellHeight = getMazeHeight(cell);
        
        int pos = 0;
        int[] tempVal = new int[4];
        
        for (int i = 0; i < tempVal.length; i++)
            tempVal[i] = -1;
        if (cellHeight != 0) 
        {
            if (maze[cellHeight - 1][cellWidth] != 0 || maze[cellHeight - 1][cellWidth] != 1) 
            {
                tempVal[pos] = mazeIndex[cellHeight - 1][cellWidth];
                pos++;

            }
        }
        if (cellHeight != mazeIndex.length - 1) {
            if (maze[cellHeight][cellWidth] != 0 || maze[cellHeight][cellWidth] != 1) 
            {
                tempVal[pos] = mazeIndex[cellHeight + 1][cellWidth];
                pos++;
            }
        }
        if (cellWidth != 0) {
            if (maze[cellHeight][cellWidth - 1] != 0 || maze[cellHeight][cellWidth] != 2) 
            {
                tempVal[pos] = mazeIndex[cellHeight][cellWidth - 1];
                pos++;
            }
        }
        if (cellWidth != mazeIndex[0].length - 1) {
            if (maze[cellHeight][cellWidth] != 0 || maze[cellHeight][cellWidth] != 2) 
            {
                tempVal[pos] = mazeIndex[cellHeight][cellWidth + 1];
                pos++;
            }
        }
        if (tempVal[0] < 0 && tempVal[1] < 0 && tempVal[2] < 0 && tempVal[3] < 0)
        {
            return -1;
        }
        int tempValDex = (int) (Math.random() * 10) % pos;
        while (tempVal[tempValDex] < 0) {
            tempValDex = (int) (Math.random() * 10) % pos;
        }
        return tempVal[tempValDex];
    }

    static class CreateFinalMaze extends JPanel
    {
        private int[][] a;
        private int mazeSize;

        public CreateFinalMaze(int[][] array, int boxsize) 
        {
            a = array;
            this.mazeSize = boxsize;
            setPreferredSize(new Dimension(300, 300));
        }

        /**
         * This method is used to generate the required graphics component for the maze
         */
        public void paintComponent(Graphics gr)
        {
            int startx = 5; int currY = startx;
            gr.setColor(Color.BLACK);

            for (int i = 0; i < a.length; i++) {
                int currX = startx;
                for (int j = 0; j < a[i].length; j++) {
                    currX += mazeSize;
                    if ((a[i][j] == 1 || a[i][j] == 3) && j != a[i].length - 1)
                        gr.drawLine(currX, currY, currX, currY + mazeSize);
                }
                currY += mazeSize;
            }
            
            gr.drawLine(startx + mazeSize, startx, mazeSize * a[0].length + 5, startx);
            gr.drawLine(startx, mazeSize * a.length + 5, mazeSize * a[0].length + 5, mazeSize * a.length + 5);
            
            gr.drawLine(startx, startx, startx, mazeSize * a.length + 5);
            gr.drawLine(mazeSize * a[0].length + 5, startx, mazeSize * a[0].length + 5, mazeSize * a.length + 5 - mazeSize);

            currY = startx;
            for (int i = 0; i < a.length; i++) 
            {
                int currX = startx;
                currY += mazeSize;
                for (int j = 0; j < a[0].length; j++) 
                {
                    if ((a[i][j] == 2) || (a[i][j] == 3))
                    {
                        gr.drawLine(currX, currY, currX + mazeSize, currY);
                    }
                    currX += mazeSize;
                }

            }
        }
    }

    /**
     * main() method to execute the program, it also creates the maze frame
     */
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter dimensions (rows*columns) for the Maze: - ");
        System.out.print("Enter number of rows: -");

        rows = input.nextInt();
        System.out.print("Enter number of columns: -");
        cols = input.nextInt();

        input.close();

        maze = new int[cols][rows];
        mazeIndex = new int[cols][rows];

        int tempVal = 0;
        for (int i = 0; i < cols; i++)
        { for (int j = 0; j < rows; j++)
            {
                mazeIndex[i][j] = tempVal;
                maze[i][j] = 3;
                tempVal++;
            }
        }
        setResultMaze = new DisjSets(cols * rows);
        generateMaze();

        resultMaze = new Point[cols][rows]; makePoint();

        JFrame mazeFrame = new JFrame();

        mazeFrame.setTitle("Maze has been successfully generated!!");

        mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeFrame.getContentPane().add(new CreateFinalMaze(maze, 10));

        mazeFrame.pack();
        mazeFrame.setBackground(Color.WHITE);
        mazeFrame.setLocationRelativeTo(null);

        mazeFrame.setVisible(true); } }