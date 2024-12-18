
package src.Game.Main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main extends JFrame {

    public Main(){
        init();
    }

    private void init(){
        setTitle("Galaxy-plane 2D Game");
        setSize(1366, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        PlanGame planGame = new PlanGame();
        add(planGame);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                planGame.start();
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public static void main(String[] args){
        Main main = new Main();
        main.setVisible(true);
    }

}