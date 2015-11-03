import javax.swing.*;
import java.util.*;

public class Pentomino{

	public List<Coordinate> coords = new ArrayList<Coordinate>();
	private boolean isRotatable;
	private boolean isFlippable;
	public int type;

	public Pentomino(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, boolean l, boolean m){
		coords.add(new Coordinate(b, c));
		coords.add(new Coordinate(d, e));
		coords.add(new Coordinate(f, g));
		coords.add(new Coordinate(h, i));
		coords.add(new Coordinate(j, k));
		isRotatable = l;
		isFlippable = m;
		type = a;
	}


	public Pentomino(List<Coordinate> newCoords, boolean l, boolean m, int inType)
	{
		coords = newCoords;
		isRotatable = l;
		isFlippable = m;
		type = inType;
	}


	public void PrintPento(){
		for (Coordinate curCoord : coords){

			System.out.println(curCoord.x +  " "+  curCoord.y);
		}
		System.out.println("");
	}

	public static int MinAr(int[] array){
		if (array.length == 1){
			return array[0];
		}

		else {
			int min = Math.min(array[0], array[1]);

			if (min == array[0]){
				int temp = array[0];
				array[0] = array[1];
				array[1] = temp;
			}

			int newArray[] = new int[array.length -1];
			System.arraycopy(array, 1, newArray, 0, newArray.length);

			return MinAr(newArray);
		}

	}


	public Pentomino Rotate(){
		// Method to rotate the pentominoes
		List<Coordinate> Rotated = new ArrayList<Coordinate>();

		//Transposition of the array (rotation +90° and flip all in once
		for (Coordinate curCoord : coords){
			//Coordinate temp = new Coordinate(coords.get(i));
			Coordinate Transp = new Coordinate(curCoord.y, curCoord.x);
			Coordinate Reverse = new Coordinate((5 - (Transp.x) - 1), Transp.y);
			Rotated.add(Reverse);
		}

		int[] position = new int[5];
		for (int a = 0; a < 5; a++){
			//Coordinate cell = new Coordinate(Rotated.get(a));
			position[a] = Rotated.get(a).x;
		}

		List<Coordinate> ShapedUp = new ArrayList<Coordinate>();

		int c = MinAr(position);
		for (Coordinate Up : Rotated){
			//Coordinate Up = new Coordinate (Rotated.get(e));
			Coordinate ShapeUp = new Coordinate((Up.x)-c, Up.y);
			ShapedUp.add(ShapeUp);
		}

		Pentomino finite = new Pentomino(ShapedUp, this.isRotatable, this.isFlippable, this.type);

		return finite;

	}



	public void PrintArrayList() {
		for (int i = 0; i < coords.size(); i++) {
			System.out.print(coords.get(i).x + "," + coords.get(i).y);
			System.out.println(" ");
		}
	}

	public int hashCode() {
		return type;
	}


	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		// object must be Test at this point
		Pentomino pento = (Pentomino)obj;
		return type == pento.type;
	}

	public Pentomino Flip(){

		List<Coordinate> flipped = new ArrayList<Coordinate>();

		for (Coordinate curCoord : coords){
			Coordinate Flip = new Coordinate (5 - 1 - curCoord.x, curCoord.y);
			flipped.add(Flip);
		}

		List<Coordinate> Up = new ArrayList<Coordinate>();

		int[] position = new int[5];

		for (int a = 0; a < 5; a++){
			//Coordinate cell = new Coordinate();
			position[a] = flipped.get(a).x;
		}

		List<Coordinate> ShapedUp = new ArrayList<Coordinate>();

		int c = MinAr(position);

		for (Coordinate Upped : flipped){
			Coordinate ShapeUp = new Coordinate(Upped.x - c, Upped.y);
			Up.add(ShapeUp);
		}

		Pentomino finite = new Pentomino(Up, this.isRotatable, this.isFlippable, this.type);

		return finite;
	}

	public static boolean contains(int[] array, int x) {
		boolean found = false;
		int start = 0;
		int end = array.length;

		while (!found && start <= end) {
			int middle = (start + end) / 2;

			if (array[middle] < x)
				start = middle + 1;
			else if (x < array[middle])
				end = middle - 1;
			else
				found = true;
		}

		return found;
	}

	public static boolean contains(int[] array, int x, int start, int end) {

		if (start > end)
			return false;

		int middle = (start + end) / 2;

		if (array[middle] < x)
			return contains(array, x, middle + 1, end);
		else if (x < array[middle])
			return contains(array, x, start, middle - 1);
		else
			return true;

	}

	public static int sizeofChar() {
		char i = 1, j = 0;
		while (i != 0) {
			i =  (char) (i<<1); j++;
		}
		return j;
	}

	public static boolean checkIsMatrix(double[][] matrix) {
		int secondDimensionLen = matrix[0].length;

		for (int curRow = 0; curRow < matrix.length; curRow++) {
			if (matrix[curRow].length != secondDimensionLen)
				return false;
		}

		return true;
	}

	public static double[][] addMatrices(double[][] first, double[][] second) {

		if (!checkIsMatrix(first) || !checkIsMatrix(second)) {
			System.out.println("One of the parameters is not a matrix");
			return new double[0][0];
		}

		int firstMatrix1stDimension = first.length;
		int firstMatrix2ndDimenstion = first[0].length;
		int secondMatrix1stDimension = second.length;
		int secondMatrix2ndDimension = second[0].length;

		if (firstMatrix1stDimension != secondMatrix1stDimension
				|| firstMatrix2ndDimenstion != secondMatrix2ndDimension) {
			System.out.println("Only matrices with identical dimensions could be added");
			return  new double[0][0];
		}

		double[][] result = new double[firstMatrix1stDimension][firstMatrix2ndDimenstion];

		for (int curRow = 0; curRow < firstMatrix1stDimension; curRow++) {
			for (int curColumn = 0; curColumn < firstMatrix2ndDimenstion; curColumn++) {
				result[curRow][curColumn] = first[curRow][curColumn] + second[curRow][curColumn];
			}
		}

		return result;

	}

	public static double[][] multiplyMatrices(double[][] first, double[][] second) {

		if (!checkIsMatrix(first) || !checkIsMatrix(second)) {
			System.out.println("One of the parameters is not a matrix");
			return new double[0][0];
		}

		int firstWidth = first[0].length;
		int secondHeight = second.length;

		if (firstWidth != secondHeight) {
			System.out.println("The width of the first matrix should be equal to the height of the second");
			return null;
		}

		double[][] result = new double[first.length][second[0].length];

		for (int curRow = 0; curRow < first.length; curRow++) {
			for (int curColumn = 0; curColumn < second[0].length; curColumn++) {
				for (int curCell = 0; curCell < firstWidth; curCell++) {
					result[curRow][curColumn] += first[curRow][curCell] * second[curCell][curColumn];
				}
			}
		}

		return result;

	}


    public static void main(String[] args) throws Exception {
        //Establishing shape of pentominoes, as well as defining whether they are reversable and/or flipable. Also making sure pentonino I will only be flipped once.
       // System.out.println("Shape j and its rotations & flips");
       // System.out.println("");

		double[][] first = {{1.0,1.0,1.0},{2.0,2.0,2.0},{3.0,3.0,3.0}};
		double[][] second = {{2.0,2.0,2.0},{0.0, 0.0, 0.0},{-2.0,-2.0,-2.0}};

		double[][] result = addMatrices(first, second);

		//double val = matrix[0][2];

		String[] words = new String[10];

		Scanner input = new Scanner(System.in);

		System.out.println("Enter the number of levels your pyramid will have: ");

		int height = input.nextInt();

		for (int curRow = 0; curRow < height; curRow++) {
			for (int curColumn = 0; curColumn < height - curRow - 1; curColumn++)
				System.out.print("  ");

			for (int curPyramidCell = 0; curPyramidCell < curRow * 2 + 1; curPyramidCell++)
				System.out.print("[]");

			System.out.println();
		}

		System.out.print("Enter a sequence of number, ");
		System.out.println("end with a letter. ");

		int cntr = 0;

		while (input.hasNextDouble()) {
			cntr++;
			System.out.print("Number " + cntr);
			System.out.println(" was: " + input.nextDouble());
		}

		int[] toCheck = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		double epsilon = 1E-3;
		System.out.format("%.14f", epsilon);

		double a = 2.0;
		double b = Math.sqrt(a);

		if (Math.abs(a - b * b) <= epsilon)
			System.out.println("Equal");
		else
			System.out.println("Not equal");

		boolean found = contains(toCheck, 10, 0, toCheck.length - 1);
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;

		char test = 'a';
		int sizeOf = sizeofChar();





		HashMap<Pentomino, ArrayList<Pentomino>> pentominos = new HashMap<Pentomino, ArrayList<Pentomino>>();

		ArrayList<Pentomino> basicShapes = new ArrayList<Pentomino>();

		Pentomino f = new Pentomino(12, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, true, true);
		basicShapes.add(f);
		Pentomino l = new Pentomino(2, 0, 0, 1, 0, 2, 0, 3, 0, 3, 1, true, true);
		basicShapes.add(l);
		Pentomino n = new Pentomino(3, 0, 1, 1, 0, 1, 1, 2, 0, 3, 0, true, true);
		basicShapes.add(n);
		Pentomino p = new Pentomino(4, 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, true, true);
		basicShapes.add(p);
		Pentomino t = new Pentomino(5, 0, 0, 0, 1, 0, 2, 1, 1, 2, 1, true, false);
		basicShapes.add(t);
		Pentomino u = new Pentomino(6, 0, 0, 0, 2, 1, 0, 1, 1, 1, 2, true, false);
		basicShapes.add(u);
		Pentomino v = new Pentomino(7, 0, 0, 1, 0, 2, 0, 2, 1, 2, 2, true, false);
		basicShapes.add(v);
		Pentomino w = new Pentomino(8, 0, 0, 1, 0, 1, 1, 2, 1, 2, 2, true, false);
		basicShapes.add(w);
		Pentomino y = new Pentomino(10, 0, 1, 1, 0, 1, 1, 2, 1, 3, 1, true, true);
		basicShapes.add(y);

		for (Pentomino basic : basicShapes){
			ArrayList<Pentomino> Rotatable = new ArrayList<Pentomino>();
			Rotatable.add(basic);
			if (basic.isRotatable){
				Pentomino basicRot1 = basic.Rotate();
				Rotatable.add(basicRot1);
				Pentomino basicRot2 = basicRot1.Rotate();
				Rotatable.add(basicRot2);
				Pentomino basicRot3 = basicRot2.Rotate();
				Rotatable.add(basicRot3);
			}

			if (basic.isFlippable){
				Pentomino flipped = basic.Flip();
				Rotatable.add(flipped);
				Pentomino basicFlip1 = flipped.Rotate();
				Rotatable.add(basicFlip1);
				Pentomino basicFlip2 = basicFlip1.Rotate();
				Rotatable.add(basicFlip2);
				Pentomino basicFlip3 = basicFlip2.Rotate();
				Rotatable.add(basicFlip3);
			}

			pentominos.put(basic, Rotatable);
		}

		Pentomino x = new Pentomino(9, 0 ,1, 1, 0, 1, 1, 1, 2, 2, 1, false, false);
		ArrayList<Pentomino> xRots = new ArrayList<Pentomino>();
		xRots.add(x);
		pentominos.put(x, xRots);

		Pentomino i = new Pentomino(1, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0,false, false);
		Pentomino iRot1 = i.Rotate();
		ArrayList<Pentomino> iRots = new ArrayList<Pentomino>();
		iRots.add(i);
		iRots.add(iRot1);
		pentominos.put(i, iRots);

		Pentomino z = new Pentomino(11, 0, 0, 0, 1, 1, 1, 2, 1, 2, 2, false, false);
		Pentomino zRot1 = z.Rotate();
		ArrayList<Pentomino> zRots = new ArrayList<Pentomino>();
		zRots.add(z);
		zRots.add(zRot1);
		Pentomino zRot2 = z.Flip();
		zRots.add(zRot2);
		Pentomino zRot3 = zRot2.Rotate();
		zRots.add(zRot3);
		pentominos.put(z, zRots);


        //f.PrintPento();
        //f.PrintArrayList();

		Scanner in = new Scanner(System.in);
		int heightBoard=0;
		int widthBoard=0;
		int boardSize = heightBoard * widthBoard;
		while (boardSize == 0 || boardSize>60 || boardSize % 5 != 0){
			System.out.println("Please enter the height of the board: ");
			heightBoard = in.nextInt();
			System.out.println("Please enter the width of the board: ");
			widthBoard = in.nextInt();
			boardSize = heightBoard * widthBoard;
			if (boardSize > 60)
				System.out.println("The total board size cannot exceed 60 cells in total");
			if (boardSize % 5 != 0)
				System.out.println("The total board size should be divisible by 5");
		}


		String answer1 = "";
		String answer2 = "";
		String answer3 = "";



		System.out.println("Please enter yes/no for using heuristic for isolated cells: ");
		answer1 = in.next();


		//	System.out.println("Please enter yes/no for using heuristic for double isolated cells: ");
		//	answer2 = in.next();

		System.out.println("Please enter yes/no for using heuristic for the least coordinate: ");
		answer3 = in.next();

		boolean heuristicSingleCell = answer1.equals("yes");
		boolean heurisricDoubleCell = answer2.equals("yes");
		boolean heuristicLeastCoordinate = answer3.equals("yes");

        Board board = new Board(widthBoard, heightBoard, heuristicSingleCell, heurisricDoubleCell, heuristicLeastCoordinate);

        /* Pentomino Visualisation */
        PentominoGrid grid = new PentominoGrid(board);
        JFrame j = new JFrame();
        j.add(grid);

        j.setSize(board.getWidth()*100, board.getHeight()*100);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        long successcounter = 0;
        try {

			long timeStart =	System.currentTimeMillis();
            if (board.checkCell(new Coordinate(0, 0), pentominos, grid)) {
                successcounter++;
				long timeEnd =	System.currentTimeMillis();
				long duration = timeEnd - timeStart;
                System.out.println("success! found a solution in " + duration + " ms");
				System.out.println("success! found " + successcounter +  " solution(s)");
                //System.exit(1);
            }

			long timeEnd =	System.currentTimeMillis();
			long duration = timeEnd - timeStart;
			System.out.println("success! found a solution in " + duration + " ms");
			System.out.println("success! found " + successcounter +  " solution(s)");

        } catch (Exception e){
            e.printStackTrace();


        }



    }


}







