import java.util.ArrayList;
import java.util.Scanner;


class chess {
    public static char currentColor;
    public static Piece currentPiece;
    public static boolean gameActive = true;

    public static void main(String[] args) {
        GameLoop();
    }

    public static void GameLoop(){
        Scanner scanner = new Scanner(System.in);
        int decodedMove;
        currentColor = 'W';
        Board.initializeBoardNormal();
        while (gameActive){
            Board.drawBoard();
            System.out.println("Make your move");
            decodedMove = Board.decodeMove(scanner.nextLine());
            if (matchMove(decodedMove)){
                Board.removePiece(decodedMove, invertColor(currentColor));
                currentPiece.setX(decodedMove/1000);
                currentPiece.setY(decodedMove%10);
                currentColor = invertColor(currentColor);
            }


        }
        currentColor = invertColor(currentColor);
        if (currentColor == 'W') {
            System.out.println("White won");
        }else {
            System.out.println("Black Won");
        }


    }
    public static char invertColor(char Color){if (Color == 'W'){return 'B';}return 'W';}

    public static boolean matchMove(int move){
        int[] sae = Board.findStartAndEnd(currentColor);
        for (int i = sae[0]; i < sae[1]; i++){
            Piece piece = Board.piecePositions[i];
            switch (piece.getRank()) {
              case 'P':
                piece = (Pawn) piece;
                break;
              case 'K':
                piece = (King) piece;
                break;
              case 'N':
                piece = (Knight) piece;
                break;
              case 'Q':
                piece = (Queen) piece;
                break;
              case 'R':
                  piece = (Rook) piece;
                  break;
              case 'B':
                  piece = (Bishop) piece;
                  break;
              default:
                  System.out.println("Something broke");
                  break;

            }
            
            ArrayList<Integer> moves = piece.getMoves();
            System.out.println(moves);
            for (int tempMove: moves){
                if (tempMove == move){
                    currentPiece = Board.piecePositions[i];
                    return (true);

                }

            }
            System.out.println("move was not possible");
        }
        return (false);


    }
    public void setCurrentColor(char color){currentColor = color;}
    public char getCurrentColor(){return currentColor;}




}
abstract class Piece {
    private int locx, locy, modx, mody;
    private final boolean color;//this should be true for white and false for black
    private final char rank;
    public static int ID = 0;
    private static int pieceID;
    public Piece(int locx, int locy,char rank, boolean color){
        this.locx = locx;
        this.locy = locy;
        this.rank = rank;
        this.color = color;
        this.pieceID = ID;
        ID++;
        if (this.color == true){this.modx = -1; this.mody = 1;}
        else {this.modx = 1; this.mody = -1;}
    }


    public char getRank(){return rank;}
    public int getID(){return pieceID;}
    public int getX(){return locx;}
    public int getY(){return locy;}
    public void setX(int x){this.locx = x;}
    public void setY(int y){this.locy = y;}
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
        int x = this.getX();
        int y = this.getY();

        if (!Board.occupiedByParty(x, y + (this.getModY()), 'A') && Board.isOnBoard(x,y+this.getModY())){moves.add(Board.quickFormat(x,y + (this.getModY())));}
        
        if (!Board.occupiedByParty(x, y + (2*this.getModY()), 'A') && Board.isOnBoard(x,y+(2*this.getModY())) && firstMove){moves.add(Board.quickFormat(x,y + (2*this.getModY())));}
        
        if (Board.occupiedByParty(x + 1, y + (this.getModY()), getOpponent()) && Board.isOnBoard(x+1,y + this.getModY())) {moves.add(Board.quickFormat(x + 1,y + (this.getModY())));}
        
        if (Board.occupiedByParty(x - 1, y + (this.getModY()), getOpponent()) && Board.isOnBoard(x-1,y+this.getModY())) {moves.add(Board.quickFormat(x - 1,y + (this.getModY())));}

        return moves;
    }

}

class Knight extends Piece{
    public Knight(int locx, int locy, boolean color){
        super(locx, locy, 'N', color);
    }
    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        
        int[][] sets = {{-2,-1},{-1,-2},{-2,1},{1,-2},{2,1},{1,2},{-1,2},{2,-1}};

        for (int i = 0; i < sets.length; i++){
            int x = this.getX() + sets[i][0];
            int y = this.getY() + sets[i][1];
            if (!Board.occupiedByParty(x, y,this.getColor())&&Board.isOnBoard(x, y)){moves.add(Board.quickFormat(x,y));}

        }
        return moves;


    }
}

class King extends Piece{
    public King(int locx, int locy, boolean color){
        super(locx,locy, 'K', color);
    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = this.getX() - 1; i <= this.getX() + 1; i++){
            for (int j = this.getY() -1; j <= this.getY() + 1; j++){
                if (this.getX()!=i && this.getY()!=j && !Board.occupiedByParty(i,j,this.getColor()) && Board.isOnBoard(i,j)){moves.add(Board.quickFormat(i,j));}
            }
        }
        return moves;

    }
}
class Queen extends Piece {
    public Queen(int locx, int locy, boolean color) {
        super(locx, locy, 'Q', color);

    }

    public ArrayList<Integer> getMoves() {//shouldn't go through pieces, shouldn't go outside boundaries

        ArrayList<Integer> moves = new ArrayList<>();

        int[][] moveMods = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        boolean[] moveFlags = {true, true, true, true};

        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                int tempX = this.getX() + i * moveMods[j][0];
                int tempY = this.getY() + i * moveMods[j][1];
                if (moveFlags[j]) {
                    if (Board.occupiedByParty(tempX, tempY, this.getColor())) {
                        moveFlags[j] = false;
                        break;
                    }
                    if (Board.isOnBoard(tempX)) {
                        moves.add(Board.quickFormat(tempX, tempY));
                    } else {
                        moveFlags[j] = false;
                        break;
                    }
                    if (Board.occupiedByParty(tempX, tempY, this.getOpponent())) {
                        moveFlags[j] = false;
                        break;
                    }
                }
            }
        }


        int x = this.getX();
        int y = this.getY();

        moves.addAll(getMovesSubsidiary(x, y, 0, -1));
        moves.addAll(getMovesSubsidiary(x, y, 0, +1));
        moves.addAll(getMovesSubsidiary(x, y, -1, 0));
        moves.addAll(getMovesSubsidiary(x, y, +1, 0));


        return moves;
    }

    public ArrayList<Integer> getMovesSubsidiary(int X, int Y, int incrementX, int incrementY) {
        ArrayList<Integer> moves = new ArrayList<>();


        for (int i = 1; i < 8; i++) {
            X += incrementX;
            Y += incrementY;
            if (Board.occupiedByParty(X, Y, this.getColor())) {
                break;
            }
            if (Board.isOnBoard(X, Y)) {
                moves.add(Board.quickFormat(X, Y));
            } else {
                break;
            }
            if (Board.occupiedByParty(X, Y, this.getOpponent())) {
                break;
            }

        }
        return moves;
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
                    if (Board.occupiedByParty(tempX,tempY, this.getColor())){moveFlags[j] = false;break;}
                    if (Board.isOnBoard(tempX)){moves.add(Board.quickFormat(tempX,tempY));}
                    else{moveFlags[j] = false;break;}
                    if (Board.occupiedByParty(tempX,tempY,this.getOpponent())){moveFlags[j] = false;break;}
                }

            }


        }




        return moves;
    }

}
class Rook extends Piece {
    public Rook(int locx, int locy, boolean color) {
        super(locx, locy, 'R', color);
    }

    public ArrayList<Integer> getMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        //I don't know of any better way to do this that won't be more complicated. Check horizontal and vertical positive and negative squares and break once you meet another piece. Could make a function that takes the row or collumn and takes the minimum and maximum values where pieces exist but will be too complicated
        //I can make a function that takes in two variables and a modifier and is able to calculate it that way much more concise, I know I should do It now but I just want to see if the code works before optimizing it. It would have a stationary value and one that changes depending on the modifier, that being + or -

        int x = this.getX();
        int y = this.getY();

        moves.addAll(getMovesSubsidiary(x, y, 0, -1));
        moves.addAll(getMovesSubsidiary(x, y, 0, +1));
        moves.addAll(getMovesSubsidiary(x, y, -1, 0));
        moves.addAll(getMovesSubsidiary(x, y, +1, 0));


        return moves;


    }

    public ArrayList<Integer> getMovesSubsidiary(int X, int Y, int incrementX, int incrementY) {
        ArrayList<Integer> moves = new ArrayList<>();


        for (int i = 1; i < 8; i++) {
            X += incrementX;
            Y += incrementY;
            if (Board.occupiedByParty(X, Y, this.getColor())) {
                break;
            }
            if (Board.isOnBoard(X, Y)) {
                moves.add(Board.quickFormat(X, Y));
            } else {
                break;
            }
            if (Board.occupiedByParty(X, Y, this.getOpponent())) {
                break;
            }

        }
        return moves;
    }

}
class Board{
    public static Piece[] piecePositions = new Piece[32];//Keep all pieces depending on ID. Blacks should be in the back (x>15). And yes, th                                                                              //at is a reference to segregation in america

    public static void initializeBoardNormal(){
      initializeColorNormal(1,0,true,4,3);
      initializeColorNormal(6,7,false,3,4);

    }
    public static void initializeColorNormal(int pawnPosition, int otherPosition, boolean flag, int kingx, int queenx){
      for (int i = 0; i < 8; i++){
        piecePositions[Piece.ID] =  new Pawn(i,pawnPosition,flag);
      }
      piecePositions[Piece.ID] =  new Rook(0,otherPosition,flag);
      piecePositions[Piece.ID] =  new Rook(7,otherPosition,flag);

      piecePositions[Piece.ID] =  new Knight(1,otherPosition,flag);
      piecePositions[Piece.ID] =  new Knight(6,otherPosition,flag);

      piecePositions[Piece.ID] =  new Bishop(2,otherPosition,flag);
      piecePositions[Piece.ID] =  new Bishop(5,otherPosition,flag);

      piecePositions[Piece.ID] =  new Queen(queenx,otherPosition,flag);
      piecePositions[Piece.ID] =  new King(kingx,otherPosition,flag);
    }

    public static boolean occupiedByParty(int X, int Y, char party/*party should be A for (A)ll, "B" for (B)lack and "W" for (W)hite*/){
        int endPosition = quickFormat(X,Y);

        int[]sae = findStartAndEnd(party);
        boolean flag = false;
        for (int i = sae[0]; i < sae[1]; i++){
            int position = Board.quickFormat(piecePositions[i].getX(),piecePositions[i].getY());
            if (position == endPosition){flag = true;break;}
        }
        return flag;


    }

    public static int[] findStartAndEnd(char party){
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
        return(new int[]{start, end});



    }

    public static int getPiecePositionFormated(Piece piece){
        int x = piece.getX();
        int y = piece.getY();
        return Board.quickFormat(x,y);


    }

    public static int decodeMove(String move){
        char charx = move.charAt(0);
        charx = Character.toUpperCase(charx);
        int y = Character.getNumericValue(move.charAt(1));
        y--;
        int x = Character.getNumericValue(charx-17);
        if (isOnBoard(x,y)){return quickFormat(x,y);}
        else{return 999;}


    }

    public static void removePiece(int move, char party){
        int[]sea = findStartAndEnd(party);
        for (int i = sea[0]; i < sea[1]; i++){
            int tempMove = getPiecePositionFormated(piecePositions[i]);
            if (tempMove == move){
                if (piecePositions[i].getRank() == 'K'){chess.gameActive = false;}
                piecePositions[i] = null;
            }

        }


    }
    public static void drawBoard(){
        char[][] board = new char[8][8];

        for (int i = 0; i < 32; i++){
            if(piecePositions[i]!=null){
                int x = piecePositions[i].getX();
                int y = piecePositions[i].getY();
                board[x][y] = piecePositions[i].getRank();
            }

        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] == '\u0000'){board[i][j] = 'O';}


            }


        }
        for (int i = 7; i > -1 ; i--){
            for (int j = 0; j < 8; j++){
                System.out.print(board[j][i]);
            }
            System.out.println();
        }
    }

    public static int quickFormat(int X, int Y){return (X*1000)+Y;}//this is the same function as getXY from pieces by applying to any coordinates
    public static boolean isOnBoard(int x){if (x>=0 && x<=7){return true;}return false;}
    public static boolean isOnBoard(int x, int y){if (x>=0 && x<=7 && y>=0 && y<=7){return true;}return false;}
}
