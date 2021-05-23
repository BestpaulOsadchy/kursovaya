package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TimerUpdated extends Rect implements ActionListener { // наследуем класс прямоугольника
    private final Timer time; // таймер простой
    private int time_min; // минуты
    private int time_sec; // секунды
    private int time_mills; // миллисекунды

    @Override
    public String toString() { // выводит оставшееся 00:00:000
        int last = (int) (Math.random() * 10);
        if (time_mills == 0 && time_min == 0 && time_sec == 0) last = 0;
        if (!time.isRunning()) last = 0;
        String timeString = "";
        if (time_min < 10)
            timeString += "0" + time_min;
        else timeString += time_min;
        if (time_sec < 10)
            timeString += ":0" + time_sec;
        else timeString += ":" + time_sec;
        if (time_mills < 10)
            timeString += ":0" + time_mills + last;
        else timeString += ":" + time_mills + last;
        return timeString;
    }

    TimerUpdated(int timeMin, int timeSec, int timeMills) { // конструктор
        super(Color.BLACK, new Color(50, 150, 58), 500, 50, "00:00:000", 50);
        time_min = timeMin;
        time_sec = timeSec;
        time_mills = timeMills;
        time = new Timer(10, this);
        setText(toString());
    }

    public void timerRestart(int timeMin, int timeSec, int timeMills) { // Запускает/перезапускает таймер
        time_min = timeMin;
        time_sec = timeSec;
        time_mills = timeMills;
        setColorText(new Color(50, 150, 58));
        setSize(50);
        time.restart();
    }

    public boolean isRunning() {
        return time.isRunning();
    } // проверяет, работает ли таймер

    public void stop()
    {
        time.stop();
    } // останавливает таймер

    public void actionPerformed(ActionEvent e) { // отлавливает изменения таймера
        if (time_mills > 0) {
            time_mills--;
            setText(toString());
        }
        else if (time_sec > 0) {
            time_sec--;
            time_mills = 99;
            setText(toString());
        } else if (time_min > 0) {
            time_min--;
            time_sec = 59;
            time_mills = 99;
            setText(toString());
        } else {
            time.stop();
            setColorText(Color.RED);
            setText("Failed");
        }
    }
}