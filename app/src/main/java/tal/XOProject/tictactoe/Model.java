package tal.XOProject.tictactoe;

public class Model {
    private char[][] board;
    private final int SIZE = 3;
    private char winner;//'x' or 'o' or 'd'
    private int counter;

    public Model(){
        board = new char[SIZE][SIZE];
    }

    public void startGame() {
        //איפוס לוח
        //איפוס של המנצח
        counter = 0;
        winner = ' ';
        for (int i=0;i<SIZE;i++){
            for (int k=0;k<SIZE;k++){
                board[i][k]=' ';
            }
        }
    }
    public boolean isEmptyPlace(int loc){
        int row = loc/SIZE; //0/1/2
        int col = loc%SIZE; //0/1/2
        if(board[row][col]=='x'||board[row][col]=='o'){
            return false;
        }
        return true;
    }
    // מקבל מיקום בלוח ותתרגם אותו למיקום במערך בצורת מיקום במערך דו מימדי ותציב לי משתמש במיקום
    public void setPlace(int loc,char player){
        //loc=0-8 ==>0/1/2  player=x/o
        int row = loc/SIZE; //0/1/2
        int col = loc%SIZE; //0/1/2
        //הצבת שחקן במערך
        board[row][col]= player;
        //קידום טור
        counter++;
    }

    //winner check ==> returns true if gameover
    public boolean gameOver()
    {
        if (counter<5)
        {return false;}
        // 5-9
        for (int i = 0;i<SIZE;i++)
        {
            if (board[i][0]!=' '&& board[i][0]==board[i][1]&& board[i][1]==board[i][2])
            {
                winner = board[i][0];
                return true;
            }
        }
        for (int i = 0;i<SIZE && winner==' ';i++)
        {
            if (board[0][i]!=' '&& board[0][i]==board[1][i]&& board[1][i]==board[2][i])
            {
                winner = board[0][i];
                return true;
            }
        }
         if (board[0][0]!=' '&& board[0][0]==board [1][1]&& board[1][1]==board[2][2])
         {
             winner=board[0][0];
             return true;
         }
         if (board[0][2]!=' '&& board[0][2]==board[1][1]&& board[1][1]==board[2][0])
         {
             winner=board[2][0];
             return true;
         }
        if (counter==9)
        {
            winner = 'd';
            return true;
        }
         return false;
    }

    public char getWinner() {
        return winner;
    }
}
