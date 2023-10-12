package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame implements ActionListener {
    JButton backButton;

    MainMenu mainMenu;

    public SettingsFrame(){
        //frame setup
        this.setTitle("Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);// window dimensions
        this.getContentPane().setBackground(Color.white);

        // logo setup
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("images/logo.png"));
        this.setIconImage(logo.getImage());


        //backButton setup
        backButton = new JButton("Back to Main-Menu");
        backButton.setFocusable(false);
        backButton.setBounds(370, 580, 150, 50);
        backButton.addActionListener(this);

        this.setLayout(null);
        this.add(backButton);
        this.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        // goes back to MainMenu when button back is pressed
        if (e.getSource() == backButton){
            mainMenu = new MainMenu();
            this.setVisible(false);
            mainMenu.setVisible(true);
        }
    }
}
