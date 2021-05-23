package com.company;

import java.awt.*;

public class Cell extends Rect { // наследуют класс прямоугольника.
    // Раньше должны были содержать в себе методы обработки значений, но потом это оказалось ненужным, поэтому
    // сейчас отвечают только за перекраску самих себя
    Cell(String value) {
        super(Color.BLACK, Color.WHITE, 50, 50, value, 30);
    }

    // Устанавливает цвет клетки
    public void setSelected(boolean selected) {
        if (selected) setColorText(Color.RED);
        else setColorText(Color.white);
    }
}
