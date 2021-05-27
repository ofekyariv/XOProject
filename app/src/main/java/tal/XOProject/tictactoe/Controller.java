package tal.XOProject.tictactoe;

public class Controller {
    private Model model;
    private char player;

    public Controller(){
        model = new Model();
        startGame();
    }

    public void startGame() {
        player='x';
        model.startGame();
    }
    //בקבלת מיקום שמשתמש בחר, יש החלפת שחקן והחזרת השחקן הקודם
    public char userSelect(int loc){
        char prev = player;
        model.setPlace(loc,player);
        if (player=='x') {player='o';}
        else player='x';
        return prev;
    }
    //check if gameover
    public boolean checkforwinner(){
        return model.gameOver();
    }

    public boolean checkEmptyPlace(int loc){
        return model.isEmptyPlace(loc);
    }

    public char getPlayer() {
        return player;
    }

    public char getWinner() {
        return model.getWinner();
    }
}
