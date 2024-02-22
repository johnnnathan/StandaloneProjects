import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


class BoardFrame extends JFrame{
    public static JFrame frame = new JFrame();
    public static BoardPanel boardPanel = new BoardPanel();

    public static void main(String[] args) throws InterruptedException {
        frame.setSize(400,400);
        frame.setResizable(true);
        frame.setTitle("Falling Sand");
        frame.add(boardPanel);
        frame.setLayout(new BorderLayout());

        // Add the panel to the frame, specifying BorderLayout.CENTER
        frame.add(boardPanel, BorderLayout.CENTER);


        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    boardPanel.SandLogic();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    frame.repaint();
                }
            }
        });
        thread.start();

    }



}
class BoardPanel extends JPanel{
    private static int X,Y;
    Color color = new Color(255, 191, 0);

    public static int boardDimension = 100;
    public static boolean[][] board = new boolean[boardDimension][boardDimension];

    int pixelSize = 10;
    public BoardPanel(){
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                X = e.getX();
                Y = e.getY();
                int positionX = X/pixelSize;
                int positionY = Y/pixelSize;
                board[positionX][positionY] = true;
                repaint();
            }
            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                X = e.getX();
                Y = e.getY();
                int positionX = X/pixelSize;
                int positionY = Y/pixelSize;
                board[positionX][positionY] = true;
                repaint();

            }

        });
    }

    @Override
    protected void paintComponent(Graphics g){



        for (int i = 0; i < boardDimension; i++){
            for (int j = 0; j < boardDimension; j++){
                if (board[i][j]){
                    g.setColor(color);
                    g.fillRect(i*pixelSize,j*pixelSize,pixelSize,pixelSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(i*pixelSize,j*pixelSize,pixelSize,pixelSize);
                }
            }
        }




    }

    public void SandLogic(){
        for (int i = 0; i < boardDimension; i++){
            for (int j = boardDimension-1; j > 0; j--){
                try{
                    if (board[i][j] == true){

                        if (board[i][j+1]== false){board[i][j] = false; board[i][j+1] = true;}
                        else if (board[i-1][j+2] == false || board[i+1][j+2] == false) {
                            if (board[i-1][j+2] == false && board[i+1][j+2] == true){
                                board[i][j] = false;
                                board[i-1][j] = true;
                            } else if (board[i-1][j+2] == true && board[i+1][j+2] == false) {
                                board[i][j] = false;
                                board[i+1][j] = true;
                            }
                            else {
                                Random random = new Random();
                                if (random.nextInt()>0.5){
                                    board[i][j] = false;
                                    board[i+1][j] = true;
                                }
                                else{
                                    board[i][j] = false;
                                    board[i-1][j] = true;
                                }
                            }

                        }

                    }
                }
                catch(ArrayIndexOutOfBoundsException e){}


            }

        }

    }


}