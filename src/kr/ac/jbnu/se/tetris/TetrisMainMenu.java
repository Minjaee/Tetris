package src.kr.ac.jbnu.se.tetris;

import src.kr.ac.jbnu.se.tetris.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TetrisMainMenu extends JFrame {

    private BufferedImage backgroundImage;
    ImageIcon GameStartIcon=new ImageIcon("src/resources/GameStart.png");
    ImageIcon GameStartIcon2=new ImageIcon("src/resources/GameStart2.png");


    public TetrisMainMenu() {
        setTitle("Tetris");
        setSize(1280, 720); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 프레임 배치

        try {
            // 배경 이미지를 파일로부터 읽어옴
            backgroundImage = ImageIO.read(getClass().getResource("/resources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 배경화면을 그리는 커스텀 패널을 생성하여 프레임에 추가
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        setContentPane(backgroundPanel);


        //게임시작버튼 설정
        JButton GameStartButton = new JButton(GameStartIcon);//
        GameStartButton.setRolloverIcon(GameStartIcon2);
        GameStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tetris tetrisGame = new Tetris();
                tetrisGame.setVisible(true);
                dispose();
            }
        });
        // 여기까지 게임시작 버튼 설정

        setLocationRelativeTo(null);
        setLayout(null);
        GameStartButton.setBounds(100, 150, 250, 115);
        add(GameStartButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TetrisMainMenu mainMenu = new TetrisMainMenu();
                mainMenu.setVisible(true);
            }
        });
    }
}
