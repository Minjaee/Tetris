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

		setSize(360, 720);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 중앙에 프레임 배치
	}

	public JLabel getStatusBar() {
		return statusbar;
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