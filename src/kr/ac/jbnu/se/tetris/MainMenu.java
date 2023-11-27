package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {

    private SoundManager backgroundMusic;
    AboutFrame aboutFrame;
    SettingsFrame settingsFrame;
    JPanel buttonsPanel, aboutPanel, settingsPanel;
    JButton singlePlayerButton;
    JButton multiPlayerButton;
    JButton scoreboardButton;
    JButton aboutButton, settingsButton;

    public MainMenu(String id) {
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);

        //배경음악 setup
        backgroundMusic = new SoundManager("src/sounds/background.wav");
        backgroundMusic.loop();

        //logo setup
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("src/images/logo.png"));
        this.setIconImage(logo.getImage());

        // background image setup
        ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("src/images/background2.png"));

        // settings image setup
        ImageIcon settings = new ImageIcon(getClass().getClassLoader().getResource("src/images/settings.png"));

        // about image setup
        ImageIcon about = new ImageIcon(getClass().getClassLoader().getResource("src/images/about.png"));

        JLabel background_image = new JLabel(image);
        background_image.setBounds(0, 0, 900, 700);

        // set panel for single-, multiplayer and scoreboard buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.black);
        buttonsPanel.setLayout(new GridLayout(3, 1));
        buttonsPanel.setBounds(325, 480, 250, 150);

        //set another panel for the about button
        aboutPanel = new JPanel();
        aboutPanel.setBackground(Color.black);
        aboutPanel.setLayout(new GridLayout(1, 1));
        aboutPanel.setBounds(70, 550, 50, 50);

        //set another panel for the settings button
        settingsPanel = new JPanel();
        settingsPanel.setBackground(Color.black);
        settingsPanel.setLayout(new GridLayout(1, 1));
        settingsPanel.setBounds(780, 550, 50, 50);

        //main menu header setup
        /*
        JLabel header = new JLabel("Tetris");
        header.setBounds(310, 100, 900, 100);
        header.setForeground(Color.yellow);
        header.setFont(new Font("MV Boli", Font.PLAIN, 80));
        */

        //buttons
        singlePlayerButton = new JButton("Single-Player");
        singlePlayerButton.setFont(new Font("MV Boli", Font.BOLD, 21));
        multiPlayerButton = new JButton("Multi-Player");
        multiPlayerButton.setFont(new Font("MV Boli", Font.BOLD, 21));
        scoreboardButton = new JButton("Score List");
        scoreboardButton.setFont(new Font("MV Boli", Font.BOLD, 21));
        aboutButton = new JButton(about);
        settingsButton = new JButton(settings);

        //button action setup
        singlePlayerButton.addActionListener(this);
        singlePlayerButton.setFocusable(false);

        multiPlayerButton.addActionListener(this);
        multiPlayerButton.setFocusable(false);

        scoreboardButton.addActionListener(this);
        scoreboardButton.setFocusable(false);

        buttonsPanel.add(singlePlayerButton);
        buttonsPanel.add(multiPlayerButton);
        buttonsPanel.add(scoreboardButton);

        // about button setup
        aboutButton.addActionListener(this);
        aboutButton.setFocusable(false);
        aboutPanel.add(aboutButton);

        // setting button setup
        settingsButton.addActionListener(this);
        settingsButton.setFocusable(false);
        settingsPanel.add(settingsButton);

        //adding elements to the frame
        // this.add(header); 이거 제목 쓸때 주석 빼셈
        this.add(aboutPanel);
        this.add(buttonsPanel);
        this.add(settingsPanel);
        this.add(background_image);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        singlePlayerButton.addActionListener(new ActionListener() {
        @Override
                public void actionPerformed(ActionEvent e) {
            Gamestart(id);
        }
        });

        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreboardOpen(id);
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {aboutFrameOpen(id);}
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {settingsOpen(id);}
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        //open 2P game mode
        if(e.getSource() == multiPlayerButton) {
            SwingUtilities.invokeLater(() -> {
                TwoPlayer twoPlayer = new TwoPlayer();
                twoPlayer.setVisible(true);
            });
            this.setVisible(false);
            backgroundMusic.stop();
        }
        //open setting window if settingsButton is pressed


    }

    public void Gamestart(String id){
        Tetris game = new Tetris(id);
        this.setVisible(false);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        backgroundMusic.stop();
    }

    public void scoreboardOpen(String id){
        ScorePanel ScorePanel = new ScorePanel(id);
        this.setVisible(false);
        ScorePanel.setLocationRelativeTo(null);
        ScorePanel.setVisible(true);
        backgroundMusic.stop();
    }
    public void aboutFrameOpen(String id){
        AboutFrame AboutFrame = new AboutFrame(id);
        this.setVisible(false);
        AboutFrame.setLocationRelativeTo(null);
        AboutFrame.setVisible(true);
        backgroundMusic.stop();
    }
    public void settingsOpen(String id){
        SettingsFrame settingsFrame = new SettingsFrame(id);
        this.setVisible(false);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
        backgroundMusic.stop();
    }

}
