package com.company;

import javax.swing.*;
import java.awt.*;

public class Rect extends JComponent {
    private final Color colorRect;
    private Color colorText;
    private String text;
    private int size;

    public String getText() {
        return text;
    }

    public void setColorText(Color colorText) {
        this.colorText = colorText;
        repaint();
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    public void setSize(int size) {
        this.size = size;
        repaint();
    }

    Rect(Color colorRect, Color colorText, int x, int y, String text, int size) {
        this.colorRect = colorRect;
        this.colorText = colorText;
        Dimension Dim = new Dimension(x, y);
        setPreferredSize(Dim);
        this.size = size;
        this.text = text;
    }

    Rect(Color colorRect, int x, int y) {
        this(colorRect, Color.BLACK, x, y, "", 1);
    }

    // прорисовка фигуры
    public void paintComponent(Graphics g) {
        g.setColor(colorRect);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Arial", Font.PLAIN, size));
        g.setColor(colorText);
        g.drawString(text, (getWidth() - text.length() * size / 2) / 2, (getHeight() + size / 2) / 2);
    }
}
