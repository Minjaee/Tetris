package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;

public class PreviewBoard extends JPanel {

    private Board mainBoard;

    public PreviewBoard(Board mainBoard) {
        this.mainBoard = mainBoard;
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(Color.GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        mainBoard.drawPreview(g, 185, 150);
    }

    public void updatePreview() {
        repaint();
    }
}