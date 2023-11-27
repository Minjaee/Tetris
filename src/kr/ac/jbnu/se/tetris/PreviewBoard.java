package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;

public class PreviewBoard extends JPanel {

    private Board mainBoard;
    private Shape holdPiece; // HOLD된 블록을 저장하는 필드 추가

    public PreviewBoard(Board mainBoard) {
        this.mainBoard = mainBoard;
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        mainBoard.drawPreview(g, 185, 150);
        drawHoldPiece(g, 185, 300);  // 추가된 부분
    }
    public void updatePreview() {
        repaint();
    }

    public void setHoldPiece(Shape holdPiece) {
        this.holdPiece = holdPiece;
        updatePreview();
    }

    // ... 이전 코드 생략 ...

    public void drawHoldPiece(Graphics g, int offsetX, int offsetY) {
        if (holdPiece != null) {
            for (int i = 0; i < 4; ++i) {
                int x = offsetX + i % 2 * mainBoard.squareWidth();
                int y = offsetY + i / 2 * mainBoard.squareHeight();
                mainBoard.drawSquare(g, x, y, holdPiece.getShape());
            }
        }
    }
}