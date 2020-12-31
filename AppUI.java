import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import static javax.security.auth.callback.ConfirmationCallback.YES_NO_CANCEL_OPTION;

public class AppUI extends JPanel{
    private Board game = new Board();
    private int ran = game.randomize();
    private int blockSide = 30;
    private int boardLR = 190 , boardTop = 580 , boardBottom = 20;
    private int x=boardTop/9+2, y = boardTop/9+2;
    JLabel scoreLabel = new JLabel();
    int score =0;
    AppUI()
    {
        setBorder(BorderFactory.createEmptyBorder(boardTop, boardLR,boardBottom, boardLR));
        setBackground(new Color(35, 80, 126));
        super.setLayout(new BorderLayout());
        setMainPanel();
        try {
            readFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setMainPanel()
    {

        ImageIcon arrow = new ImageIcon("resources\\images\\arrow2.png");
        int z=y*8;
        JButton btn1 = new JButton(arrow);
        add(createButton(btn1,x , z));
        JButton btn2 = new JButton(arrow);
        add(createButton(btn2,x *2, z));
        JButton btn3 = new JButton(arrow);
        add(createButton(btn3,x *3, z));
        JButton btn4 = new JButton(arrow);
        add(createButton(btn4,x *4, z));
        JButton btn5 = new JButton(arrow);
        add(createButton(btn5,x *5, z));

        JButton newGamebtn = new JButton(new ImageIcon("resources\\images\\newgame.png"));
        add(createButton(newGamebtn,x , z+y));
        newGamebtn.setBounds((x - 10) - (blockSide),(z+y-boardBottom) - blockSide,3*blockSide,blockSide+5);

        JButton undo = new JButton(new ImageIcon("resources\\images\\undo.png"));
        add(createButton(undo,x , z+y));
        undo.setBounds((x*4) - 10 - blockSide,(z+y-boardBottom) - blockSide,2*blockSide,blockSide+5);


        scoreLabel.setLayout(null);
        scoreLabel.setBounds(boardLR*2-80,boardTop-boardBottom-17,3*blockSide,blockSide+10);
        scoreLabel.setFont(new Font("SansSerif",Font.BOLD,18));
        scoreLabel.setForeground(Color.lightGray);
        add(scoreLabel);

        JLabel dummyLabel = new JLabel();
        add(dummyLabel);

        buttonaction(btn1,1);
        buttonaction(btn2,2);
        buttonaction(btn3,3);
        buttonaction(btn4,4);
        buttonaction(btn5,5);
        buttonlistner(btn1,"arrow.png", "arrow2.png");
        buttonlistner(btn2,"arrow.png", "arrow2.png");
        buttonlistner(btn3,"arrow.png", "arrow2.png");
        buttonlistner(btn4,"arrow.png", "arrow2.png");
        buttonlistner(btn5,"arrow.png", "arrow2.png");
        buttonlistner(undo,"undo (1).png", "undo.png");

        newGamebtn.addActionListener(actionEvent -> {

            int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a New Game?",
                    "WARNING", JOptionPane.YES_NO_OPTION);
            if(n!=YES_NO_CANCEL_OPTION) {
                game.zeroBoard();
                score = 0;
                repaint();
            }
        });

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.undo();
                repaint();
            }
        });
    }
    private void buttonaction(JButton button,int number)
    {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pushBlock(number);
                repaint();
            }
        });
    }
    private void buttonlistner(JButton button,String path1, String path2)
    {
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                button.setIcon(new ImageIcon("resources\\images\\" + path1));
            }
            public void mouseExited(MouseEvent me){
                button.setIcon(new ImageIcon("resources\\images\\" + path2));
            }
        });
    }
    private void pushBlock(int choice)
    {
        if (game.isBoardFull(ran)) {
            JOptionPane.showMessageDialog(null, "GAME OVER!");
            game.zeroBoard();
            score=0;
        } else if (game.isColumnEmpty(choice,ran)) {
            game.push(ran, choice);
            game.fullChecker(choice);
            score=score+ran;
            ran = game.randomize();
        } else if (!game.isColumnEmpty(choice,ran)) {
            JOptionPane.showMessageDialog(null, "Column is full");
        }
    }
    private JButton createButton(JButton button,int x, int y) {
        button.setFocusPainted(false);
        button.setLayout(null);
        button.setBounds((x - 10) - blockSide,(y-boardBottom) - blockSide+5,2*blockSide,blockSide+10);
        button.setBackground(new Color(217, 235, 203));
        button.setContentAreaFilled(false);
        return button;
    }
    //Drawer
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         DrawBlock(g ,ran,x*3, boardTop);
        scoreLabel.setText(String.valueOf(score));
        int[][] board = game.getBoard();
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 5; j++) {
                if (board[i - 1][j - 1] != 0) {
                    DrawBlock(g, board[i - 1][j - 1],x * j, y * i);
                }
            }
        }
        try {
            printToFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void printToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("resources\\images\\data.txt"));
        int[][] board = game.getBoard();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                writer.write(board[i][j] + "\t\t");
            }
            writer.write("\n");
        }
        writer.write("\n"+score);
        writer.close();
    }
    private void readFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources\\images\\data.txt"));
        int[][] board = game.getBoard();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                String val = scanner.next();
                board[i][j] = (Integer.parseInt(val));
            }
        }
        String val = scanner.next();
        score = (Integer.parseInt(val));
    }
    private void DrawBlock(Graphics2D graphics2D ,int value, int x , int y)
    {
        graphics2D.setColor(new Color(14, 47, 86));
        graphics2D.fillRoundRect((x - 10) - blockSide,(y-boardBottom) - blockSide,2*blockSide,2*blockSide,17,17);
        graphics2D.setColor(Color.lightGray);

        graphics2D.setFont(new Font("SansSerif", Font.BOLD, 18));

        FontMetrics fm = graphics2D.getFontMetrics();
        double t_width = fm.getStringBounds(String.valueOf(value), graphics2D).getWidth();
        graphics2D.drawString(String.valueOf(value),(int) ((x - 10) - t_width / 2),((y-boardBottom) + fm.getMaxAscent() / 2));

    }
}
