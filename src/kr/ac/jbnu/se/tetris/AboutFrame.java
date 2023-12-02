package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AboutFrame extends JFrame implements ActionListener {
    JButton backButton;
    MainMenu mainMenu;
    private String id;
    private SoundManager buttonClickSound; // 버튼 클릭시 효과음을 위한 인스턴스


    AboutFrame(String id){
        buttonClickSound = new SoundManager("src/sounds/button_click.wav"); // 버튼 클릭 초기화

        this.id = id;

        // frame setup
        this.setTitle("Tetris"); // app title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);// window dimensions
        this.getContentPane().setBackground(Color.white);

        // logo setup
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("src/images/logo.png"));
        this.setIconImage(logo.getImage());

        //text declaration
        JLabel text = new JLabel();
        text.setText(
                "<html>This game was developed by:<br>" +
                "김민재<br>" +
                "황주원<br>" +
                "성재현");


        //text parameters setup
        text.setForeground(Color.black);
        text.setFont(new Font ("MV Boli", Font.PLAIN, 40));
        text.setBounds(0, 0, 900, 500);
        text.setHorizontalTextPosition(JLabel.CENTER);
        text.setHorizontalAlignment(JLabel.CENTER);

        //backButton setup
        backButton = new JButton("Back to Main-Menu");
        backButton.setFocusable(false);
        backButton.setBounds(370, 580, 150, 50);
        backButton.addActionListener(this);


        //adding elements to the frame
        this.setLayout(null);
        this.add(text);
        this.add(backButton);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonClickSound.play(); // 효과음 재생
        //go back to main-menu if backButton pressed
        if(e.getSource() == backButton){
            mainMenu = new MainMenu(id);
            this.setVisible(false);
            mainMenu.setVisible(true);
        }
    }
}
