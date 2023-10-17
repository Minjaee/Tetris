package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ScorePanel extends JFrame implements ActionListener {
    JButton backButton;
    MainMenu mainMenu;
    private String id;
    private JLabel userIdLabel;
    private JLabel scoreLabel;
    private JTextField userIdField;
    private int userScore;
    private JTable scoreTable;
    private ArrayList<User> topScores = new ArrayList<>(); // 초기화된 ArrayList


    ScorePanel(String id){
        // frame setup
        this.id = id;
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


        fetchTopScores(); // 최고 점수 순위 정보 가져오기
        // JTable을 사용하여 최고 점수 순위와 스코어 표시
        String[] columnNames = {"순위", "사용자 ID", "스코어"};
        // Reinitialize topScores
        topScores = FirebaseUtil.getTopScores(10);

        // JTable 설정
        Object[][] data = new Object[topScores.size()][3];
        for (int i = 0; i < topScores.size(); i++) {
            data[i][0] = i + 1; // 순위
            data[i][1] = topScores.get(i).getId(); // 사용자 ID
            data[i][2] = topScores.get(i).getScore(); // 스코어
        }

        scoreTable = new JTable(data, columnNames);
        scoreTable.setBounds(400, 200, 400, 180);

        // JScrollPane로 JTable 래핑
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(400, 200, 400, 180);

        this.add(scrollPane);

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
    private void fetchTopScores() {
        // FirebaseUtil 클래스를 사용하여 최고 점수 순위 정보 가져오기
        topScores = FirebaseUtil.getTopScores(10);
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
