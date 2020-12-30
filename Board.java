public class Board {

    private int[][] board = new int[8][5];
    private int[][] previousBoard = new int[8][5];
    private int powerCounter = 1;
    Board() // constructor zero-ing the Board
    {
        zeroBoard();
    }
    public void zeroBoard() //zero-ing the Board method
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = 0;
            }
        }
    }
    public int[][] getBoard()
    {
        return board;
    }
    public int randomize()  // Generate random value with boundaries and constraints
    {
        int[] randomBlocks = new int[6];
        if(getMaxValue()>=Math.pow(2,powerCounter+7))
        {powerCounter++;}
        for (int i = 0; i < 6; i++) {
            randomBlocks[i] = (int) Math.pow(2,powerCounter);
            powerCounter++;
        }
        powerCounter-=6;
        int rand = (int) (Math.random() * (randomBlocks[5] - randomBlocks[0] + 1) + randomBlocks[0]);
        while(rand!=randomBlocks[0] && rand!=randomBlocks[1] && rand !=randomBlocks[2] && rand !=randomBlocks[3] && rand !=randomBlocks[4] && rand !=randomBlocks[5])
        {
            rand = (int) (Math.random() * (randomBlocks[5] - randomBlocks[0] + 1) + randomBlocks[0]);
        }
        return rand;
    }
    public void push(int rand, int x) { //takes input and push it to the board
        for (int i = 0; i < 8; i++) {
            if (board[i][x - 1] == 0) {
                board[i][x - 1] = rand;
                break;
            }
        }
    }
    public boolean isBoardFull(int rand)  // Board-full checker -- GAME OVER --
    {
        for (int i = 0; i < 5; i++) {
            if (board[6][i] == 0 || board[6][i] == rand) {
                return false;
            }
        }

        return true;
    }
    public boolean isColumnEmpty(int x, int rand )  // Column full checker
    {
        if (board[6][x - 1] == 0 || board[6][x-1] == rand)
            return true;
        return false;
    }
    private void currentColumnSimilarityChecker(int x)
    {
            for (int i = 7; i >= 0; i--) {
                if(i - 1 >= 0 && x > 1 && x < 5 && board[i][x - 1] == board[i][x - 2] && board[i][x - 1] == board[i][x] && board[i][x - 1] == board[i - 1][x - 1]) //Similarity in left, right, up blocks checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                    board[i][x - 2] = 0;
                    board[i - 1][x - 1] = 0;

                }
                else if (i - 1 >= 0 && x > 1 && board[i][x - 1] == board[i][x - 2] && board[i][x - 1] == board[i - 1][x - 1]) //Similarity in left triangle of blocks checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 2] = 0;
                    board[i - 1][x - 1] = 0;
                }
                else if (x < 5 && i - 1 >= 0 && board[i][x - 1] == board[i][x] && board[i][x - 1] == board[i - 1][x - 1]) //Similarity in right triangle of blocks checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                    board[i - 1][x - 1] = 0;
                }
                else if (i - 1 >= 0 && board[i][x - 1] == board[i - 1][x - 1]) { //Similarity in 2 rows checker and handler
                    board[i - 1][x - 1] += board[i - 1][x - 1];
                    board[i][x - 1] = 0;
                }
                else if (x < 5 && x > 1 && board[i][x - 1] == board[i][x] && board[i][x - 1] == board[i][x - 2]) //Similarity in 3 column checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                    board[i][x - 2] = 0;
                }

                else if (x < 5 && board[i][x - 1] == board[i][x]) {  //Similarity in right column checker and handler
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                } else if (x > 1 && board[i][x - 1] == board[i][x - 2]) //Similarity in left column checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 2] = 0;
                }
            }
    }
    private void similarityChecker() {  //All similarity cases checker and handler
        for (int j = 5; j >=1; j--) {
            for (int i = 7; i >= 0; i--) {
                if (i - 1 >= 0 && board[i][j - 1] == board[i - 1][j - 1]) { //Similarity in 2 rows checker and handler
                    board[i - 1][j - 1] += board[i - 1][j - 1];
                    board[i][j - 1] = 0;
                }
                else if (j < 5 && board[i][j - 1] == board[i][j]) {  //Similarity in right column checker and handler
                    board[i][j - 1] += board[i][j - 1];
                    board[i][j] = 0;
                } else if (j > 1 && board[i][j - 1] == board[i][j - 2]) //Similarity in left column checker and handler
                {
                    board[i][j - 1] += board[i][j - 1];
                    board[i][j - 2] = 0;
                }
            }
        }
    }
    private void zeroRemover() // zero above value checker and remover
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5 ; j++) {
                if (i - 1 >= 0 && board[i - 1][j] == 0 && board[i][j] != 0) {
                    board[i - 1][j] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }
    public void fullChecker(int x) //double checker if needed
    {
        currentColumnSimilarityChecker(x);
        zeroRemover();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                previousBoard[i][j]=board[i][j];
            }
        }
        similarityChecker();
        zeroRemover();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 5; j++) {
                    if(previousBoard[i][j]!=board[i][j])
                    {
                        fullChecker(x);
                    }
                    else {}
                }
            }

    }
    public int getMaxValue()  // return maximum value in the board
    {
        int maxValue = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if(board[i][j] >= maxValue )
                    maxValue = board[i][j];
            }
        }
        return maxValue;
    }
    public void display()  // displays the board to the user
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(board[i][j] + "\t\t");
            }
            System.out.println();
        }
    }
}