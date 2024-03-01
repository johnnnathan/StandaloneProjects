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
    public char getColor(){
        if (color){return 'W';}
        return 'B';

    }
    public int getModX() {return modx;}

    public int getModY() {return mody;}

    public int getXY(){return (locx*1000)+locy;}
    public char getOpponent(){
        if (color){return 'B';}
        return 'W';
    }
    abstract public ArrayList<Integer> getMoves();
    
}

class Pawn extends Piece{
    private boolean firstMove = true;
    public Pawn(int locx, int locy, boolean color){
        super(locx,locy, 'P', color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        
        if (!Board.occupiedByParty(this.getX(), this.getY() + (1*this.getModY()), 'A'))    {moves.add(Board.quickFormat(this.getX(),this.getY() + (1*this.getModY())));}
        
        if (!Board.occupiedByParty(this.getX(), this.getY() + (2*this.getModY()), 'A')&&firstMove){moves.add(Board.quickFormat(this.getX(),this.getY() + (2*this.getModY())));}
        
        if (!Board.occupiedByParty(this.getX() + 1, this.getY() + (1*this.getModY()), getOpponent())) {moves.add(Board.quickFormat(this.getX() + 1,this.getY() + (this.getModY())));}
        
        if (!Board.occupiedByParty(this.getX() - 1, this.getY() + (1*this.getModY()), getOpponent())) {moves.add(Board.quickFormat(this.getX() - 1,this.getY() + (this.getModY())));}

        return moves;
    }

}

class Knight extends Piece{
    public Knight(int locx, int locy, boolean color){
        super(locx, locy, 'N', color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();


    }
}

class King extends Piece{
    public King(int locx, int locy, boolean color){
        super(locx,locy, 'K', color);
    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = this.getX() - 1; i <= this.getX() + 1; i++){
            for (int j = this.getY() -1; j <= this.getY() + 1; i++){
                if (this.getX()!=i && this.getY()!=j && !Board.occupiedByParty(i,j,this.getColor())){moves.add(Board.quickFormat(i,j));}
            }
        }
        return moves;

    }
}
class Queen extends Piece{
    public Queen(int locx, int locy, boolean color){
        super(locx,locy, 'Q',color);

    }

    public ArrayList<Integer> getMoves(){//shouldn't go through pieces, shouldn't go outside boundaries
        ArrayList<Integer> moves = new ArrayList<>();
        boolean[] flags = new boolean[2];
        int y = this.getY();
        int x = this.getX();  
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++){
                if (!flags[j] && !Board.occupiedByParty(i+x,y,'A')){moves.}

            }
        }

    }
}
class Bishop extends Piece{
    public Bishop(int locx, int locy, boolean color){
        super(locx, locy, 'B',color);
    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        

        int[][] moveMods = {{1,1},{1,-1},{-1,1},{-1,-1}};
        boolean[] moveFlags = {true,true,true,true};

        for (int i = 1; i < 8; i++){
            for (int j = 0; j < 4;j++){
                int tempX = this.getX() + i*moveMods[j][0];
                int tempY = this.getY() + i*moveMods[j][1];
                if (moveFlags[j]){
                    if (!Board.occupiedByParty(tempX,tempY, this.getColor())){moveFlags[j] = false;break;}
                    if (Board.isOnBoard(tempX)){moves.add(Board.quickFormat(tempX,tempY));}
                    else{moveFlags[j] = false;break;}
                    if (Board.occupiedByParty(tempX,tempY,this.getOpponent())){moveFlags[j] = false;break;}
                }

            }


        }




        return moves;
    }

}
class Rook extends Piece{
    public Rook(int locx, int locy, boolean color){
        super(locx,locy, 'R',color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        //I don't know of any better way to do this that won't be more complicated. Check horizontal and vertical positive and negative squares and break once you meet another piece. Could make a function that takes the row or collumn and takes the minimum and maximum values where pieces exist but will be too complicated
        //I can make a function that takes in two variables and a modifier and is able to calculate it that way much more concise, I know I should do It now but I just want to see if the code works before optimizing it. It would have a stationary value and one that changes depending on the modifier, that being + or -
        for (int i = 1; i < 8;i++){//Check positions left of piece
        
            int tempX = this.getX()-i;
            int tempY = this.getY();
            if (!Board.occupiedByParty(tempX,tempY,this.getColor())){break;}
            if (Board.isOnBoard(tempX)){moves.add(Board.quickFormat(tempX,tempY));}
            else{break;}
            if (Board.occupiedByParty(tempX, tempY, this.getOpponent())){break;}
        }
        for (int i = 1; i < 8;i++){//Check positions right of piece
            int tempX = this.getX()+i;
            int tempY = this.getY();
            if (!Board.occupiedByParty(tempX,tempY,this.getColor())){break;}
            if (Board.isOnBoard(tempX)){moves.add(Board.quickFormat(tempX,tempY));}
            else{break;}
            if (Board.occupiedByParty(tempX, tempY, this.getOpponent())){break;}
        }
        for (int i = 1; i < 8; i++){//check positions below piece
            int tempX = this.getX();
            int tempY = this.getY()-i;
            if (!Board.occupiedByParty(tempX, tempY, this.getColor())){break;}
            if (Board.isOnBoard(tempY)){moves.add(Board.quickFormat(tempX, tempY));}
            else{break;}
            if (Board.occupiedByParty(tempX, tempY, this.getOpponent())){break;}

        }
        for (int i = 1; i < 8; i++){//check positions above piece
            int tempX = this.getX();
            int tempY = this.getY()+i;
            if (!Board.occupiedByParty(tempX, tempY, this.getColor())){break;}
            if (Board.isOnBoard(tempY)){moves.add(Board.quickFormat(tempX,tempY));}
            else{break;}
            if (Board.occupiedByParty(tempX, tempY, this.getOpponent())){break;}


        }

        return moves;


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
    public static boolean isOnBoard(int x){if (x>=0 && x<=7){return true;}return false;}
    public static boolean isOnBoard(int x, int y){if (x>=0 && x<=7 && y>=0 && y<=7){return true;}return false;}
}
