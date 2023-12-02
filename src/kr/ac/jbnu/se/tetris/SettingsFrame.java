package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame implements ActionListener {
    JButton backButton;
    MainMenu mainMenu;
    private SoundManager backgroundMusic;
    private JSlider volumeSlider; // 볼륨 조절 슬라이더 추가
    private String id;
    private SoundManager buttonClickSound; // 버튼 클릭시 효과음을 위한 인스턴스

    public SettingsFrame(String id){
        buttonClickSound = new SoundManager("src/sounds/button_click.wav"); // 버튼 클릭 초기화
        this.id = id;
        //frame setup
        this.setTitle("Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);// window dimensions
        this.getContentPane().setBackground(Color.white);

        //배경음악 setup
        backgroundMusic = new SoundManager("src/sounds/background.wav");
        backgroundMusic.loop();

        // logo setup
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("src/images/logo.png"));
        this.setIconImage(logo.getImage());

        JLabel text = new JLabel();
        text.setText("Volume Setting");
        text.setForeground(Color.black);
        text.setFont(new Font ("MV Boli", Font.PLAIN, 40));
        text.setBounds(300, 100, 900, 100);

        //backButton setup
        backButton = new JButton("Back to Main-Menu");
        backButton.setFocusable(false);
        backButton.setBounds(370, 580, 150, 50);
        backButton.addActionListener(this);

        // 볼륨 조절 슬라이더 초기화
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100); // 볼륨 범위 설정
        volumeSlider.setBounds(345, 280, 200, 50);

        this.add(backButton);
        this.add(volumeSlider);
        this.add(text);
        this.setLayout(null);
        this.setVisible(true);

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int sliderValue = volumeSlider.getValue(); // 1부터 10까지의 값
                float volume = (float) sliderValue / 100.0f; // 슬라이더 값을 0.1부터 1.0까지의 범위로 변환
                backgroundMusic.setVolume(volume);
            }
        });

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        buttonClickSound.play(); // 효과음 재생

        // goes back to MainMenu when button back is pressed
        if (e.getSource() == backButton){
            mainMenu = new MainMenu(id);
            this.setVisible(false);
            mainMenu.setVisible(true);
            backgroundMusic.stop();
        }
    }
}
