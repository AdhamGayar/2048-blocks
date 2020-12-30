import javax.swing.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame();
        frame.add(new AppUI());
        frame.setTitle("2048 Blocks");
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       /* Board game = new Board();
        System.out.println("\n///////////WELCOME TO 2048 GAME//////////");
        while (true) {
            System.out.println("\nEnter the column number / 1..5");
            int ran = game.randomize();
            System.out.println("Number to be pushed: "+ ran);
            Scanner input = new Scanner(System.in);
            try {
                int choice = input.nextInt();
                if (choice > 0 && choice <= 5) {

                    if (game.isBoardFull(ran)) {
                        System.out.println("**************** GAME OVER ****************");
                        break;
                    } else if (game.isColumnEmpty(choice,ran)) {
                        game.push(ran, choice);
                        game.fullChecker(choice);
                        //game.clear();
                        System.out.println();
                        game.display();
                    } else if (!game.isColumnEmpty(choice,ran)) {
                        //game.clear();
                        System.out.println();
                        game.display();
                        System.out.println("Column is full, Can't push here");
                    }
                } else {
                    System.out.println("The value you entered is out of index");
                    System.out.println("please enter correct value between 1..5");
                }
            }
            catch (InputMismatchException n)
            {
                System.out.println("The value you entered is out of index");
                System.out.println("please enter correct value between 1..5");
            }
        }*/
    }
}