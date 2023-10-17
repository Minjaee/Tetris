package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;


public class ScorePanel extends JFrame implements ActionListener {
    JButton backButton;
    MainMenu mainMenu;
    private String id;
    private JLabel userIdLabel;
    private JLabel scoreLabel;
    private JTextField userIdField;
    private int userScore;

    ScorePanel(String id){
        // frame setup
        this.setTitle("Tetris"); // app title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);// window dimensions
        this.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);

        // logo setup
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("src/images/logo.png"));
        this.setIconImage(logo.getImage());

        // 사용자 ID 레이블
        userIdLabel = new JLabel("사용자 ID:");
        userIdLabel.setBounds(100, 100, 100, 30);
        this.add(userIdLabel);

        // 사용자 ID 입력 필드
        userIdField = new JTextField(id);
        userIdField.setBounds(200, 100, 150, 30);
        this.add(userIdField);

        // 스코어 레이블
        scoreLabel = new JLabel("스코어: " + userScore);
        scoreLabel.setBounds(100, 150, 100, 30);
        this.add(scoreLabel);

        // 스코어 불러오기 버튼
        JButton loadScoreButton = new JButton("Enter");
        loadScoreButton.setBounds(280, 150, 70, 30);
        loadScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchUserScore(); // 사용자 스코어 불러오기
                updateScoreLabel(); // 스코어 레이블 업데이트
            }
        });

        //backButton setup
        backButton = new JButton("Back to Main-Menu");
        backButton.setFocusable(false);
        backButton.setBounds(370, 580, 150, 50);
        backButton.addActionListener(this);


        //adding elements to the frame
        this.setLayout(null);
        this.add(backButton);
        this.setVisible(true);
        this.add(loadScoreButton);

        // Firebase에서 사용자 스코어 가져오기
        fetchUserScore();

        // 스코어 레이블 업데이트
        updateScoreLabel();

    }

    // Firebase에서 사용자 스코어 가져오기
    private void fetchUserScore() {
        id = userIdField.getText(); // 사용자가 입력한 ID를 가져옴
            userScore = FirebaseUtil.getUserScore(id);
            updateScoreLabel();
        }


    // 가져온 사용자 스코어로 스코어 레이블 업데이트
    private void updateScoreLabel() {
        scoreLabel.setText("스코어: " + userScore);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //go back to main-menu if backButton pressed
        if(e.getSource() == backButton){
            this.setVisible(false);
        }
    }
}
