import java.util.ArrayList;


class chess {
    public static void main(String[] args) {
        System.out.println("TEST");
    }
}
abstract class Piece {
    private int locx, locy, modx, mody;
    private boolean color;//this should be true for white and false for black
    private char rank;
    public static int ID = 0;
    private static int pieceID;
    public Piece(int locx, int locy,char rank, boolean color){
        this.locx = locx;
        this.locy = locy;
        this.rank = rank;
        this.color = color;
        this.pieceID = ID;
        ID++;
        if (this.color == true){this.modx = 1; this.mody = -1;}
        else {this.modx = -1; this.mody = 1;}
    }

    public int getID(){return pieceID;}
    public int getX(){return locx;}
    public int getY(){return locy;}

    public int getModx() {return modx;}

    public int getMody() {return mody;}

    public int getXY(){return (locx*1000)+locy;}
    public char getOpponent(){
        if (color){return 'B';}
        else{return 'W';}
    }
    abstract public ArrayList<Integer> getMoves();
    
}

class Pawn extends Piece{
    private boolean firstMove = true;
    public Pawn(int locx, int locy, boolean color){
        super(locx,locy,"P", color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        
        if (!Board.occupiedByParty(this.getX(), this.getY() + (1*this.getModY()), 'A'))    {moves.add(Board.quickFormat(this.getX,this.getY + (1*this.getModY)));}
        
        if (!Board.occupiedByParty(this.getX(), this.getY() + (2*this.getModY()), 'A')&&firstMove){moves.add(Board.quickFormat(this.getX,this.getY + (2*this.getModY)));}
        
        if (!Board.occupiedByParty(this.getX() + 1, this.getY() + (1*this.getModY()), getOpponent())) {moves.add(Board.quickFormat(this.getX + 1,this.getY + (this.getModY)));}
        
        if (!Board.occupiedByParty(this.getX() - 1, this.getY() + (1*this.getModY()), getOpponent())) {moves.add(Board.quickFormat(this.getX - 1,this.getY + (this.getModY)));}

        return moves;
    }

}

class Knight extends Piece{
    public Knight(int locx, int locy, boolean color){
        super(locx, locy,"N", color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();


    }
}

class King extends Piece{
    public King(int locx, int locy, boolean color){
        super(locx,locy,"K", color);
    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = this.getX-1, i <= this.getX + 1; i++){
            for (int j = this.getY -1, j <= this.getY + 1; i++){
                moves.add(Board.quickformat(i,j);

            }

        }
        return moves;

    }
}
class Queen extends Piece{
    public Queen(int locx, int locy, boolean color){
        super(locx,locy,"Q",color);

    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        int y = this.getY();
        int x = this.getX();  
        for (int i = 0; i < 8; i++) {
            moves.add(Board.quickFormat(i,y));
            moves.add(Board.quickFormat(x,i)); 
        }

    }
}
class Bishop extends Piece{
    public Bishop(int locx, int locy, boolean color){
        super(locx, locy, "B",color);        
    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();


    }

}
class Rook extends Piece{
    public Rook(int locx, int locy, boolean color){
        super(locx,locy,"R",color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();


    }
}


class Board{
    public static Piece[][] board = new Piece[8][8];//I don't think this will do anything. Keeping it for possible future use, should delete if not
    public static int[] piecePositions = new int[32];//Keep all current positions of each piece depending on ID. Blacks should be in the back (x>15). And yes, that                                                     is a reference to segregation in america

    public static void occupyPosition(Piece piece){//assuming that position of piece has been updated in the object
        piecePositions[piece.getID()] = piece.getXY();
    }

    public static boolean occupiedByParty(int X, int Y, char party/*party should be A for (A)ll, "B" for (B)lack and "W" for (W)hite*/){
        int endPosition = quickFormat(X,Y);
        int start =-1;
        int end = -1;//end should be one bigger than the position of the final element in order to immediately add it to the loop

        switch (party) {
            case 'A':
                start = 0;
                end = 32;
                break;
            case 'B':
                start = 16;
                end = 32;
                break;
            case 'W':
                start = 0;
                end = 16;
                break;
        }
        boolean flag = true;
        for (int i = start; i < end; i++){
            if (piecePositions[i] == endPosition){flag = false;break;}
        }
        return flag;


    }
    public static int quickFormat(int X, int Y){return (X*1000)+Y;}//this is the same function as getXY from pieces by applying to any coordinates
}
