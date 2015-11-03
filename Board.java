import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    public HashMap<Coordinate,
            board = new HashMap<Coordinate, CellValues>();
    private int width = 0;
    private int height = 0;
    private long counter=0;
    private int successcounter=0;
    private int oneIsolatedCellCounter = 0;
    private int twoIsolatedCellCounter = 0;
    private boolean isSingleIsolatedCells = false;
    private boolean isDoubleIsolatedCells = false;
    private boolean isLeastCoordinateHeuristic = false;

    Board(int curWidth, int curHeight, boolean inSingleIsoltedCells, boolean inDoubleIsolatedCells, boolean inLeastCoordinateHeuristic)
    {
        width = curWidth;
        height = curHeight;

        for (int y = 0; y < curHeight; y++) {
            for (int x = 0; x < curWidth; x++) {
                board.put(new Coordinate(x, y), new CellValues(0, 0));
            }
        }

        isSingleIsolatedCells = inSingleIsoltedCells;
        isDoubleIsolatedCells = inDoubleIsolatedCells;
        isLeastCoordinateHeuristic = inLeastCoordinateHeuristic;


    }


    public int getXMinStartCoordinate(Pentomino curPentomino){
        int min = getWidth();

        /* Looping through all the current pentomino coordinates and determine the lowest X value where Y=0  */
        for (Coordinate curCoord : curPentomino.coords) {

            if (curCoord.y == 0) {
                if (curCoord.x < min)
                    min = curCoord.x;
            }
        }
        return min;
    }

    public int getYMinStartCoordinate(Pentomino curPentomino){
        int min = getHeight();

        /* Looping through all the current pentomino coordinates and determine the lowest X value where Y=0  */
        for (Coordinate curCoord : curPentomino.coords) {

            if (curCoord.x == 0) {
                if (curCoord.y < min)
                    min = curCoord.y;
            }
        }
        return min;
    }


    public boolean checkCollision(Coordinate cell, Pentomino curPentomino) {



        /* Looping through all the current pentomino coordinates and checking for collissions & outside the board exceptions  */
        for (Coordinate curCoord : curPentomino.coords) {


            if (!isLeastCoordinateHeuristic || getWidth() < getHeight()) {

                int min = getXMinStartCoordinate(curPentomino);

                int shiftedCoord = curCoord.x - min; //shift

            /* check if our pentomino is going to be placed outside of the board */
                if (cell.x + shiftedCoord >= getWidth() || cell.x + shiftedCoord < 0 || cell.y + curCoord.y >= getHeight() || cell.y + curCoord.y < 0)
                    return true;

            /* Let's check if there are collissions :) */
                try {

                    // if (board.get(new Coordinate(cell.x, cell.y)).matrixValue + board.get(new Coordinate(curCoord.x + cell.x, curCoord.y + cell.y)).matrixValue > 1)
                    if (board.get(new Coordinate(shiftedCoord + cell.x, curCoord.y + cell.y)).matrixValue == 1)
                        return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                int min = getYMinStartCoordinate(curPentomino);
                int shiftedCoord = curCoord.y - min; //shift

            /* check if our pentomino is going to be placed outside of the board */
                if (cell.y + shiftedCoord >= getHeight() || cell.y + shiftedCoord < 0 || cell.x + curCoord.x >= getWidth() || cell.x + curCoord.x < 0)
                    return true;

            /* Let's check if there are collissions :) */
                try {

                    // if (board.get(new Coordinate(cell.x, cell.y)).matrixValue + board.get(new Coordinate(curCoord.x + cell.x, curCoord.y + cell.y)).matrixValue > 1)
                    if (board.get(new Coordinate(curCoord.x + cell.x, shiftedCoord + cell.y)).matrixValue == 1)
                        return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }

        return false;
    }

    public boolean checkIsolatedCells() {

        if (!isSingleIsolatedCells && !isDoubleIsolatedCells)
            return false;


        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
               if (board.get(new Coordinate(x, y)).matrixValue == 0) {
                   if ((x == getWidth() - 1 || board.get(new Coordinate(x + 1, y)).matrixValue == 1)
                       && (y == getHeight() - 1 || board.get(new Coordinate(x, y + 1)).matrixValue == 1)
                           && (y == 0 || board.get(new Coordinate(x, y - 1)).matrixValue == 1)
                           && ( x == 0 || board.get(new Coordinate(x - 1, y)).matrixValue == 1)
                           ) {
                       oneIsolatedCellCounter++;
                       //if (oneIsolatedCellCounter % 100000 == 0)
                       // System.out.println(oneIsolatedCellCounter + " single isolated cell found");
                       return true;
                   }
               }


                if ((board.get(new Coordinate(x, y)).matrixValue == 0
                        && x != getWidth() - 1
                        && board.get(new Coordinate(x + 1, y)).matrixValue == 0
                        && (x + 1 == getWidth() - 1 || board.get(new Coordinate(x + 2, y)).matrixValue == 1)
                        && (y == 0 || (board.get(new Coordinate(x, y - 1)).matrixValue == 1 && board.get(new Coordinate(x + 1, y - 1)).matrixValue == 1) )
                        && (x == 0 || (board.get(new Coordinate(x - 1, y)).matrixValue == 1))
                        && (y == getHeight() - 1 || (board.get(new Coordinate(x, y + 1)).matrixValue == 1 && board.get(new Coordinate(x + 1, y + 1)).matrixValue == 1)))

                        ||


                        (board.get(new Coordinate(x, y)).matrixValue == 0
                        && y != getHeight() - 1
                        && board.get(new Coordinate(x, y + 1)).matrixValue == 0
                        && (x == getWidth() - 1 || (board.get(new Coordinate(x + 1, y)).matrixValue == 1 && board.get(new Coordinate(x + 1, y + 1)).matrixValue == 1))
                        && (y == 0 || (board.get(new Coordinate(x, y - 1)).matrixValue == 1 ))
                        && (x == 0 || (board.get(new Coordinate(x - 1, y)).matrixValue == 1) && board.get(new Coordinate(x - 1, y + 1)).matrixValue == 1)
                        && (y + 1 == getHeight() - 1 || board.get(new Coordinate(x, y + 2)).matrixValue == 1)
                )
                        ) {
                    twoIsolatedCellCounter++;
                   // if (twoIsolatedCellCounter % 100000 == 0)
                   //     System.out.println(twoIsolatedCellCounter + " double isolated cell found");
                    return true;

                }

            }
        }

        return false;
    }


    public void PrintBoard() {

        //Method to print the pentominoes
        for (int a = 0; a < getHeight(); a++) {
            for (int b = 0; b < getWidth(); b++) {
                System.out.print(board.get(new Coordinate(b, a)).matrixValue + "  ");
            }
            System.out.println();
        }
    }

    public void fillPentomino(Coordinate cell, Pentomino curPentomino, int toAdd ){

        if (!isLeastCoordinateHeuristic || getWidth() < getHeight()) {
            int min = getXMinStartCoordinate(curPentomino);

            for (Coordinate curCoord : curPentomino.coords) {

                int shiftedCoord = curCoord.x - min; //shift
                board.put(new Coordinate(shiftedCoord + cell.x, curCoord.y + cell.y), new CellValues(toAdd, curPentomino.type));

            }
        }
        else
        {
            int min = getYMinStartCoordinate(curPentomino);

            for (Coordinate curCoord : curPentomino.coords) {

                int shiftedCoord = curCoord.y - min; //shift
                board.put(new Coordinate(curCoord.x + cell.x, shiftedCoord + cell.y), new CellValues(toAdd, curPentomino.type));

            }
        }

       // PrintBoard();
    }

    public void removePentomino(Coordinate cell, Pentomino curPentomino){

        if (!isLeastCoordinateHeuristic || getWidth() < getHeight()) {

            int min = getXMinStartCoordinate(curPentomino);

            for (Coordinate curCoord : curPentomino.coords) {

                int shiftedCoord = curCoord.x - min; //shift
                board.put(new Coordinate(shiftedCoord + cell.x, curCoord.y + cell.y), new CellValues(0, 0));

            }
        }
        else
        {
            int min = getYMinStartCoordinate(curPentomino);

            for (Coordinate curCoord : curPentomino.coords) {

                int shiftedCoord = curCoord.y - min; //shift
                board.put(new Coordinate(curCoord.x + cell.x, shiftedCoord + cell.y), new CellValues(0, 0));

            }

        }

       // PrintBoard();
    }


    public boolean checkCell(Coordinate cell, HashMap<Pentomino, ArrayList<Pentomino>> pentominos, PentominoGrid grid) {

        int numberOfCells = getHeight() * getWidth();
        if (numberOfCells % 5 != 0)
            return false;


        if (pentominos.size() == 0) {
            successcounter++;
            System.out.println("success! found " + successcounter +  " solution(s)");

            return true;
        }

        Coordinate originalEmptyCell = new Coordinate(cell.x, cell.y);
        while (board.get(cell)!=null && board.get(cell).matrixValue == 1) {

            if (!isLeastCoordinateHeuristic || getWidth() < getHeight()) {
                if (cell.x < getWidth() - 1) {
                    cell.x++;
                    //System.out.println("column is " + cell.x);

                } else {
                    cell.x = 0;
                    cell.y++;
                    //System.out.println("row is " + cell.y);

                }
                if (cell.y >= getHeight()) {
                    System.out.println("no more cells left");
                    return true;
                }
            }
            else
            {
                if (cell.y < getHeight() - 1) {
                    cell.y++;
                    //System.out.println("column is " + cell.x);

                } else {
                    cell.y = 0;
                    cell.x++;
                    //System.out.println("row is " + cell.y);

                }
                if (cell.x >= getWidth() + 1) {
                    System.out.println("no more cells left");
                    return true;
                }

            }
        }

        for (Map.Entry<Pentomino, ArrayList<Pentomino>> entry : pentominos.entrySet()) {
            Pentomino pentominoToRemove = entry.getKey();
            for (Pentomino curPentomino : entry.getValue()) {
                if (!checkCollision(cell, curPentomino)) {
                    fillPentomino(cell, curPentomino, 1);
                 //   if (counter % 1000 == 0)
                   //     grid.paintSquares();

                    if (!checkIsolatedCells())
                    {
                        counter++;

                         /* try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                               e.printStackTrace();
                           }*/
                        if (counter % 100000 == 0)
                            System.out.println("amount of possibilities tried: " + counter);

                        HashMap<Pentomino, ArrayList<Pentomino>> reduced = new HashMap<Pentomino, ArrayList<Pentomino>>(pentominos);
                        reduced.remove(pentominoToRemove);
                        if (checkCell(cell, reduced, grid)) {
                            if (successcounter > 0) {
                                //System.out.println("amount of solutions found:" + successcounter);
                            }
                            //successcounter++;
                           // grid.paintSquares();

                             /*  try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                               e.printStackTrace();
                           }*/
                           //return true;
                        }
                    }
                    removePentomino(cell, curPentomino);
//                    if (counter % 1000 == 0)
  //                      grid.paintSquares();

                }
            }
        }

        cell.x = originalEmptyCell.x;
        cell.y = originalEmptyCell.y;
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
