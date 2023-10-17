package src.kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import javax.swing.JOptionPane; // 게임 종료 시 재시작 혹은 메뉴로 돌아갈 것인지 물어보게 하기 위한 모듈
public class Board extends JPanel implements ActionListener {

	final int BoardWidth = 10;
	final int BoardHeight = 22;

	Timer timer;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	int numLinesRemoved = 0;
	int curX = 0;
	int curY = 0;
	JLabel statusbar;
	Shape curPiece;
	Tetrominoes[] board;
	Shape nextPiece; // 다음 블록 미리 보기 변수 생성
	int rotationCount = 0; // 도형의 회전 횟수를 저장할 변수 추가
	private String id;
	private String pw;

	private boolean isGameOver = false; // 게임오버상태인지 아닌지 확인할 변수 추가
	//preview Board 기능
	private PreviewBoard previewBoard;

	private Tetris tetrisParent; // Board 클래스가 Tetris 클래스에 접근할 수 있도록 멤버 변수 생성


	public void setPreviewBoard(PreviewBoard previewBoard) {
		this.previewBoard = previewBoard;
	} ///


	public Board(Tetris parent) {

		this.tetrisParent = parent; // 멤버 추가

		setFocusable(true);
		curPiece = new Shape();
		timer = new Timer(400, this);
		timer.start();

		statusbar = parent.getStatusBar();
		board = new Tetrominoes[BoardWidth * BoardHeight];
		addKeyListener(new TAdapter());
		clearBoard();
	}

	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	// overiding을 위해 protected로 받음
	protected int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth;
	}

	protected int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight;
	}


	Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x];
	}
	//

	public void start() {
		if (isPaused)
			return;

		nextPiece = new Shape();
		nextPiece.setRandomShape();

		isStarted = true;
		isFallingFinished = false;
		isGameOver = false;   // 게임을 다시 시작할 때 isGameOver를 false로 초기화
		numLinesRemoved = 0;
		clearBoard();

		newPiece();
		timer.start();
	}

	private void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
			statusbar.setText("paused");
		} else {
			timer.start();
			statusbar.setText(String.valueOf(numLinesRemoved));
		}
		repaint();
	}
	// 미리보기 패널에 라우팅
	public Shape getNextPiece() {
		return nextPiece;
	}
	// 도형 미리보기 시각화
	public void drawPreview(Graphics g, int offsetX, int offsetY) {
		if (nextPiece.getShape() == Tetrominoes.NoShape) {
			return;
		}
		Font newFont = new Font("SansSerif", Font.BOLD, 18); // 폰트명, 스타일, 크기 지정
		g.setFont(newFont);

		g.setColor(Color.white);  // 글자색 변경
		g.drawString("다음 블럭:", offsetX + -150, offsetY + 10);  // 텍스트 위치 지정

		for (int i = 0; i < 4; ++i) {
			int x = offsetX + nextPiece.x(i) * squareWidth();
			int y = offsetY + nextPiece.y(i) * squareHeight();
			drawSquare(g, x, y, nextPiece.getShape());
		}
	}
	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();


		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape){
					if (isGameOver) { // isGameOver 가 true일 때, 색상이 변경되도록 함수 수정
						drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), Tetrominoes.GrayShape);
					} else {
						drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape);
					}
				}
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.x(i);
				int y = curY - curPiece.y(i);
				drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}

		// 고스트 블록 그리기
		if (isStarted && !isPaused) {
			int ghostY = curY;
			while (ghostY > 0) {
				if (!tryMove(curPiece, curX, ghostY - 1, false))
					break;
				--ghostY;
			}

			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.x(i);
				int y = ghostY - curPiece.y(i);
				drawGhostSquare(g, 0 + x * squareWidth(),
						boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}

		// 격자 무늬 그리기
		g.setColor(Color.GRAY);  // 격자 선의 색상 설정

		// 가로선 그리기
		for (int i = 0; i < BoardHeight; i++) {
			g.drawLine(0, boardTop + i * squareHeight(),
					BoardWidth * squareWidth(), boardTop + i * squareHeight());
		}

		// 세로선 그리기
		for (int j = 0; j < BoardWidth; j++) {
			g.drawLine(j * squareWidth(), boardTop,
					j * squareWidth(), boardTop + BoardHeight * squareHeight());
		}
	}

	// 고스트 블록을 그리기 위한 메서드
	private void drawGhostSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102, 255),
				new Color(102, 204, 102, 255), new Color(102, 102, 204, 255),
				new Color(204, 204, 102, 255), new Color(204, 102, 204, 255),
				new Color(102, 204, 204, 255), new Color(218, 170, 0, 255),
				new Color(128, 128, 128)};  // GrayShape 추가함
		Color color = colors[shape.ordinal()];
		g.setColor(color);
		g.drawRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
	}


	private void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	private void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	private void pieceDropped() {
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y * BoardWidth) + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void newPiece() {
		curPiece.setShape(nextPiece.getShape()); // 현재 조각을 다음 조각으로 설정
		nextPiece.setRandomShape(); // 다음 조각을 랜덤으로 생성
		rotationCount = 0; // 도형이 생성될 때 회전 횟수를 0으로 초기화

		if (previewBoard != null) {
			previewBoard.repaint(); // PreviewBoard의 paintComponent를 호출하여 그리기 업데이트
		}

		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();

		if (!tryMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			isGameOver = true; // 게임 오버 시 isGameOver변수를 false에서 true로 변경
			statusbar.setText("game over");
			repaint(); // gameover시 회색으로 변경

			gameOverAction();  // 게임 오버 액션 추가
		}
	}

	// 게임오버 상황에서의 액션
	private void gameOverAction() {
		int option = JOptionPane.showOptionDialog(this, "게임이 종료되었습니다. 다시 시작하시겠습니까?", "게임 종료",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"다시 시작", "메인 메뉴"}, "default");
		if (option == JOptionPane.YES_OPTION) {
			start();  // 게임 재시작
		} else {
			openMainFrame();// 메인 메뉴로 돌아가게 하는 메서드.
		}
	}

	private void openMainFrame() {

		tetrisParent.closeFrame(); // Tetris 닫아버리기

		MainMenu mainMenu = new MainMenu(id);  // 메인 메뉴 인스턴스 생성
		mainMenu.setLocationRelativeTo(null);
		mainMenu.setVisible(true);
	}

	private boolean tryMove(Shape newPiece, int newX, int newY) {
		return tryMove(newPiece, newX, newY, true);
	}

	private boolean tryMove(Shape newPiece, int newX, int newY, boolean actualMove) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
				return false;
			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}

		if (actualMove) {
			curPiece = newPiece;
			curX = newX;
			curY = newY;
			repaint();
		}
		return true;
	}
	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
		}
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0), new Color(128, 128, 128)}; //gray색 추가

		Color color = colors[shape.ordinal()];

		if (shape == Tetrominoes.GrayShape) {
			color = new Color(128, 128, 128); // Gray Shape를 회색으로 정의
		}
			g.setColor(color);
			g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

			g.setColor(color.brighter());
			g.drawLine(x, y + squareHeight() - 1, x, y);
			g.drawLine(x, y, x + squareWidth() - 1, y);

			g.setColor(color.darker());
			g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
			g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
		}

		class TAdapter extends KeyAdapter {
			public void keyPressed(KeyEvent e) {

				if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {
					return;
				}

				int keycode = e.getKeyCode();

				if (keycode == 'p' || keycode == 'P') {
					pause();
					return;
				}

				if (isPaused)
					return;

				switch (keycode) {
					case KeyEvent.VK_LEFT:
						tryMove(curPiece, curX - 1, curY);
						break;
					case KeyEvent.VK_RIGHT:
						tryMove(curPiece, curX + 1, curY);
						break;
					// 도형 횟수 제한 (현재 4번 0회~3회)
					case KeyEvent.VK_DOWN:
						if (rotationCount < 3) {   // 횟수제한 변경
							tryMove(curPiece.rotateRight(), curX, curY);
							rotationCount++;
						}
						break;
					case KeyEvent.VK_UP:
						if (rotationCount < 3) {   // 횟수제한 변경
							tryMove(curPiece.rotateLeft(), curX, curY);
							rotationCount++;
						}
						break;
					case KeyEvent.VK_SPACE:
						dropDown();
						break;
					case 'd':
						oneLineDown();
						break;
					case 'D':
						oneLineDown();
						break;
				}

			}
		}
	}