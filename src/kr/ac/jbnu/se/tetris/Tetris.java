package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;

import javax.swing.*;

public class Tetris extends JFrame {

	JLabel statusbar;

	public Tetris() {

		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);
		Board board = new Board(this);
		add(board);
		board.start();

		setSize(200, 400);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		SoundManager backgroundMusic = new SoundManager("sounds/background.wav");
	}

	public JLabel getStatusBar() {
		return statusbar;
	}

	public static void main(String[] args) {
			MainMenu mainMenu = new MainMenu();
	}
}