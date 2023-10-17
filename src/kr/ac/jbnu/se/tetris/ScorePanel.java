package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScorePanel extends JFrame implements ActionListener {
    JButton backButton;
    MainMenu mainMenu;
    private String id;

    ScorePanel(){
        // frame setup
        this.setTitle("Tetris"); // app title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);// window dimensions
        this.getContentPane().setBackground(Color.white);

        // logo setup
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("src/images/logo.png"));
        this.setIconImage(logo.getImage());



        //backButton setup
        backButton = new JButton("Back to Main-Menu");
        backButton.setFocusable(false);
        backButton.setBounds(370, 580, 150, 50);
        backButton.addActionListener(this);


        //adding elements to the frame
        this.setLayout(null);
        this.add(backButton);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //go back to main-menu if backButton pressed
        if(e.getSource() == backButton){
            mainMenu = new MainMenu(id);
            this.setVisible(false);
            mainMenu.setVisible(true);
        }
    }
}
