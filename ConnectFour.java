
import java.util.Scanner;


public class ConnectFour{

	// Global variables
	private final static int WIDTH = 7, HEIGHT = 6;
	private final static int MAT_HEIGHT = 5;  // The matrix starts at 0 and the height is 5.
	private final static char BLANK = '.';
	private static String winMessage = null; // If not null- the game is over
	private static int row = 0, column = 0;
	private static char players='x';  
	private static int turn = 1; // To switch between players
	public static char[][]board = new char[WIDTH][HEIGHT];

	public static void main(String[] args){

		initBoard();
		// If the board is not full and the isWinner function returns false - Keep playing
		while (!isBoardFull() && !isWinner(players, winMessage)){
			System.out.println("\n"+players+"'s turn");

			do{
				userInput();
			}
			// Loop while per turn and check if there is a win and finally replacing the player.
			while (!placePlayers(column));
			winMessage = checkWin(players, row, column);
			displayBoard();
			players = switchUser(players);
		}
	} 


	// Initialization Board Array
	private static void initBoard(){
		for(int width=0; width < WIDTH; width++){
			for(int height=0; height < HEIGHT; height++){
				board[width][height]=BLANK;
			}
		}	
	}

	// Check's if board array is full, returns: boolean (if true, board is full)
	private static boolean isBoardFull(){
		for(int width = 0; width < WIDTH ; width++){
			for(int height = 0 ; height < HEIGHT ; height++){
				if(board[width][height] == BLANK){
					return false;
				}
			}
		}
		System.out.println("GAME OVER: The board is full");
		return true;	
	}

	// Check's if there is a Winner, returns: boolean (if true, there is a winner)
	private static boolean isWinner(char players, String winMessage){
		if(winMessage == null){
			return false;
		}
		else{
			System.out.println("\n"+players+" won the game ("+winMessage+")!");
			return true;
		}
	}

	// Input from the user to enter a column, returns: column
	
	private static int userInput(){
		int playerInput;
		boolean flag = false;

		Scanner input = new Scanner(System.in);

		System.out.println("\nEnter a column (Int num between 1-7):");

		while(flag == false){
			while (!input.hasNextInt()) {
				//tell user to enter in an integer value and repeats until they enter valid input.	
				System.out.println("Enter an integer, please!");
				input.nextLine();
			}

			playerInput = input.nextInt() - 1; 
			// Valid range
			if (playerInput < WIDTH && playerInput >= 0){
				column = playerInput;
				flag = true;
			}
			else  {
				System.out.println("Not a valid column, try (Int num between 1-7)");
				flag = false;
			}
		}
		return column;
	}

	// places users players in the column of their choice, returns: boolean
	private static boolean placePlayers(int column){
		for(int height = 0; height < HEIGHT; height++){
			if(board[column][MAT_HEIGHT] != BLANK){
				System.out.println("That column is full! Please select another one.");
				return false;
			}
			else if (board[column][height] == BLANK){
				board[column][height] = players;
				return true;
			}
		}

		return false;

	}

	// checkWinConditions (vertical, horizontal, diagonal, and anti diagonal), returns: boolean
	private static String checkWin(char players, int row, int column){
		if(checkVertical(players, column) == true){
			winMessage = "on a vertical";
		}
		else if(checkHorizontal(players, row) == true){
			winMessage = "on a horizontal";
		}
		else if(checkDiagonal(players, row, column) == true){
			winMessage="on a diagonal";
		}
		else if(checkVertical(players, column) == true && checkHorizontal(players, row) == true){
			winMessage="on a vertical & a horizontal";
		}
		else if(checkVertical(players, column) == true && checkDiagonal(players, row, column) == true){
			winMessage="on a vertical & a diagonal";
		}
		else if(checkHorizontal(players, row) == true && checkDiagonal(players, row, column) == true){
			winMessage="on a horizontal & a diagonal";	
		}
		else if(checkVertical(players, column) == true && checkHorizontal(players, row) == true && checkDiagonal(players, row, column) == true){
			winMessage="on a vertical & a horizontal & a diagonal (OH BABY A TRIPLE! OH YEAH!)";
		}
		else{
			winMessage=null;
		}

		return winMessage;

	}
	
	//check for 4 up and down
	private static boolean checkVertical(char players, int column){
		
		for(int row = 0; row < WIDTH - 3; row++){
			for(int col = 0; col < HEIGHT ; col++){
				if (board[row][col] == players   && 
					board[row+1][col] == players &&
					board[row+2][col] == players &&
					board[row+3][col] == players){
					return true;
				}
			}
		}

		return false;

	}
	
	//check for 4 across
	private static boolean checkHorizontal(char players, int row){
		
		for( row = 0; row< WIDTH; row++){
			for (int col = 0;col < HEIGHT - 3;col++){
				if (board[row][col] == players   && 
					board[row][col+1] == players &&
					board[row][col+2] == players &&
					board[row][col+3] == players){
					return true;
				}
			}			
		}

		return false;

	}



	private static boolean checkDiagonal(char players, int row, int column){
		//check upward diagonal
		for( row = 3; row < WIDTH ; row++){
			for(int col = 0; col < HEIGHT - 3; col++){
				if (board[row][col] == players   && 
						board[row-1][col+1] == players &&
						board[row-2][col+2] == players &&
						board[row-3][col+3] == players){
					return true;
				}
			}
		}
		//check downward diagonal
		for( row = 0; row < board.length - 3; row++){
			for(int col = 0; col < board[0].length - 3; col++){
				if (board[row][col] == players   && 
						board[row+1][col+1] == players &&
						board[row+2][col+2] == players &&
						board[row+3][col+3] == players){
					return true;
				}
			}
		}
		return false;
	}

	// Display board
	private static void displayBoard(){
		for(int height = HEIGHT-1; height >= 0; height--){
			for(int width = 0; width < WIDTH; width++){
				System.out.print(board[width][height]);
			}
			System.out.println("");
		}	
	}

	// switch users, return: the char of the player
	private static char switchUser(char players){
		if(winMessage==null){
			turn++;
			if(turn%2 == 1){
				players='x';
				return players;
			}
			else{
				players='o';
				return players;
			}
		}
		else{
			//return's current players if they've won the game
			return players;
		}
	}

}