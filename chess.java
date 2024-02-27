import java.util.ArrayList;


class chess {
    public static void main(String[] args) {
        System.out.println("TEST");
    }
}
abstract class Piece {
    private byte locx, locy, modx, mody;
    private boolean color;//this should be true for white and false for black
    private byte[21] 
    public static ID = 0;
    private static pieceID;
    public Piece(byte locx, byte locy,char rank, boolean color){
        this.locx = locx;
        this.locy = locy;
        this.rank = rank;
        this.color = color;
        this.pieceID = ID;
        ID++;
        if (this.color == true){this.modx = 1; this.mody = -1;}
        else {this.modx = -1; this.mody = 1;}
    }

    public byte getID(){return pieceID;}
    public byte getX(){return locx;}
    public byte getY(){return locy;}
    public byte getXY(){return (locx*10)+locy}

    abstract public ArrayList<byte> getMoves();
    
}

class Pawn extends Piece{
    public Pawn(byte locx, byte locy, boolean color){
        super(locx,locy,"P", color);
    }
    public getMoves(){
        ArrayList<byte> moves = new ArrayList<>();
        if (/*no entity in first or second position if first move*/){/*move*/}
    }

}

class Knight extends Piece{
    public Knight(byte locx, byte locy, boolean color){
        super(locx, locy,"N", color);
    }

}

class King extends Piece{
    public King(byte locx, byte locy, boolean color){
        super(locx,locy,"K", color);
    }

}
class Queen extends Piece{
    public Queen(byte locx, byte locy, boolean color){
        super(locx,locy,"Q",color);

    }

}
class Bishop extends Piece{
    public Bishop(byte locx, byte locy, boolean color){
        super(locx, locy, "B",color);        
    }


}
class Rook extends Piece{
    public Rook(byte locx, byte locy, boolean color){
        super(locx,locy,"R",color);
    }
}


class Board{
    public static Piece[][] board = new int[8][8];//I don't think this will do anything. Keeping it for possible future use, should delete if not
    public static byte[] piecePositions = new byte[32];//Keep all current positions of each piece depending on ID. Blacks should be in the back (x>15). And yes, that                                                     is a reference to segregation in america

    public static occupyPosition(Piece piece){//assuming that position of piece has been updated in the object
        piecePosition[piece.getID] = piece.getXY();
    }

    public boolean occupiedByParty(byte X, byte Y, char Party/*party should be A for (A)ll, "B" for (B)lack and "W" for (W)hite*/){
        byte endPosition = quickFormat(X,Y);
        byte start,end;//end should be one bigger than the position of the final element in order to immediately add it to the loop

        switch (party) {
            case "A":
                start = 0;
                end = 32;
                break;
            case "B":
                start = 16;
                end = 32;
                break;
            case "W":
                start = 0;
                end = 16
                break;
        }
        boolean flag = true;
        for (byte i = start; i < end; i++){
            if (piecePositions[i] == endPosition){flag = false;}
        }
        return flag;


    }
    public quickFormat(byte X, byte Y){return (X*10)+Y;}//this is the same function as getXY from pieces by applying to any coordinates
}
