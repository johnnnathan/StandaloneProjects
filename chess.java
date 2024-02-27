class chess {
    public static void main(String[] args) {
        
    }
}
class Piece {
    private byte locx, locy, modx, mody;
    private boolean color;//this should be true for white and false for black
    private byte[21] 
    public Piece(byte locx, byte locy,char rank, boolean color){
        this.locx = locx;
        this.locy = locy;
        this.rank = rank;
        this.color = color;
        if (this.color == true){this.modx = 1; this.mody = -1;}
        else {this.modx = -1; this.mody = 1;}

    }

    public getMoves(byte locx, byte locy, char rank, byte modx, byte mody){
        byte[] possibleMoves = new byte[21]
        switch (rank) {
            case "K":
                for (int i = locx - 1; i <= locx + 1; i++){
                    for (int j = locy - 1; i <= locy + 1; j++){
                        if (i>=0 && i <= 7 && j >=0 && j<=7){}

                    }

                }
                break;

            default:
                break;
        }

    }
}
