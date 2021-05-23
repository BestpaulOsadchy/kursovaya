package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HackConnectApp {
    private final Cell[][] mapCells = new Cell[10][8]; // таблица случайных чисел
    private String IP; // "IP-адрес"
    private final Location location = new Location(); // координаты выбранного набора чисел
    private final TimerUpdated time = new TimerUpdated(1, 0, 0); // таймер выполнения
    private Timer moveTimer; // таймер для передвижения клеток

    private static class Location {
        int x;
        int y;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Location() {
            this(0, 0);
        }

        private void setDefaultLocation() {
            x = 3;
            y = 3;
        }
    }

    public void start() {
        SwingUtilities.invokeLater(this::initGUI);
    } // запускает приложение

    private void initGUI() { // отрисовщик окна
        JFrame.setDefaultLookAndFeelDecorated(true); // устанавливает стиль окна
        JFrame frame = new JFrame("HackConnect.exe"); // создаем окно с заданным заголовком
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // устанавливает выход по кнопке
        Container contentPane = frame.getContentPane();
        // создаем таблицу из элементов для вывода на экран
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbConstraints = new GridBagConstraints();
        JPanel panel = new JPanel(gbLayout); // панель для вывода
        panel.setBackground(Color.black); // установить фону черный увет

        // рисуем рамки
        Rect o = new Rect(Color.blue, 10, 10);
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbLayout.setConstraints(o, gbConstraints);
        panel.add(o);
        Rect o1 = new Rect(Color.blue, 800, 10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbLayout.setConstraints(o1, gbConstraints);
        panel.add(o1);
        Rect o2 = new Rect(Color.blue, 10, 10);
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 0;
        gbLayout.setConstraints(o2, gbConstraints);
        panel.add(o2);
        Rect o3 = new Rect(Color.blue, 10, 250);
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        gbLayout.setConstraints(o3, gbConstraints);
        panel.add(o3);
        Rect o4 = new Rect(Color.blue, 10, 250);
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 1;
        gbLayout.setConstraints(o4, gbConstraints);
        panel.add(o4);
        Rect o5 = new Rect(Color.blue, 10, 420);
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbLayout.setConstraints(o5, gbConstraints);
        panel.add(o5);
        Rect o6 = new Rect(Color.blue, 10, 420);
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 2;
        gbLayout.setConstraints(o6, gbConstraints);
        panel.add(o6);
        Rect o9 = new Rect(Color.blue, 10, 10);
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        gbLayout.setConstraints(o9, gbConstraints);
        panel.add(o9);
        Rect o10 = new Rect(Color.blue, 800, 10);
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 3;
        gbLayout.setConstraints(o10, gbConstraints);
        panel.add(o10);
        Rect o11 = new Rect(Color.blue, 10, 10);
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 3;
        gbLayout.setConstraints(o11, gbConstraints);
        panel.add(o11);

        // Верхняя панель: таймер, картинка, IP-адрес
        GridBagLayout gbLayout1 = new GridBagLayout();
        GridBagConstraints gbConstraints1 = new GridBagConstraints();
        JPanel panel1 = new JPanel(gbLayout1);
        panel1.setBackground(Color.BLACK);

        // Устанавливаем иконку приложения
        ImageIcon img = new ImageIcon("HackConnect.png");
        frame.setIconImage(img.getImage());

        // Нижняя панель - числа
        GridBagLayout gbLayout2 = new GridBagLayout();
        GridBagConstraints gbConstraints2 = new GridBagConstraints();
        JPanel panel2 = new JPanel(gbLayout2);
        panel2.setBackground(Color.BLACK);
        // Заполнение таблицы чисел
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 10; x++) {
                String random = String.valueOf((int) (Math.random() * 100));
                if (random.length() < 2) random = "0" + random;
                mapCells[x][y] = new Cell(random);
                gbConstraints2.gridx = x;
                gbConstraints2.gridy = y;
                gbLayout2.setConstraints(mapCells[x][y], gbConstraints2);
                panel2.add(mapCells[x][y]); // добавление на панель
            }
        // установка стандартного положения выбранных клетки
        location.setDefaultLocation();
        moveSelected(); // выделение красным

        // вывод картинки
        BufferedImage myPicture = null;
        try {
            final String dir = System.getProperty("user.dir");
            myPicture = ImageIO.read(new File(dir + "\\logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert myPicture != null;
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        gbConstraints1.insets.set(20, 0, 0, 0); // отступ от верхнего края
        gbConstraints1.gridx = 0;
        gbConstraints1.gridy = 0;
        gbLayout1.setConstraints(picLabel, gbConstraints1);
        panel1.add(picLabel); // добавление на панель
        //
        IP = getIP(); // берем случайные значения для IP из таблицы
        // вывод IP
        Rect RectIP = new Rect(Color.BLACK, Color.RED, 300, 55, IP, 55);
        gbConstraints1.gridx = 0;
        gbConstraints1.gridy = 1;
        gbConstraints1.insets.set(35, 0, 0, 0);
        gbLayout1.setConstraints(RectIP, gbConstraints1);
        panel1.add(RectIP); // добавление на панель
        // таймер
        gbConstraints1.gridx = 0;
        gbConstraints1.gridy = 2;
        gbConstraints1.insets.set(10, 0, 0, 0);
        gbLayout1.setConstraints(time, gbConstraints1);
        time.addMouseListener(new MouseAdapter() { // обработчик нажатий мыши
            public void mousePressed(MouseEvent me) { // если нажимаем мышь
                for (int y = 0; y < 8; y++)
                    for (int x = 0; x < 10; x++) {
                        String random = String.valueOf((int) (Math.random() * 100));
                        if (random.length() < 2) random = "0" + random;
                        mapCells[x][y].setText(random);
                        location.setDefaultLocation();
                        moveSelected();
                    }
                IP = getIP();
                RectIP.setText(IP);
                time.timerRestart(1, 0, 0);
                moveTimer.restart();
            }
        });
        panel1.add(time);

        frame.addKeyListener(new KeyAdapter() { // обработчик нажатия клавиатуры
            public void keyReleased(KeyEvent e) {
                if (time.isRunning()) {
                    String key = KeyEvent.getKeyText(e.getKeyCode());
                    switch (key) {
                        case "Enter":
                            check(); // проверить правильность
                            moveTimer.stop(); // остановить движение клеток
                            break;
                        case "Up":
                            moveUp(); // движение вверх
                            break;
                        case "Down":
                            moveDown();
                            break;
                        case "Right":
                            moveRight();
                            break;
                        case "Left":
                            moveLeft();
                            break;
                    }
                }
            }
        });

        // таймер для передвижение клеток
        ActionListener cellMower = evt -> {
            if (time.isRunning())
                moveCell(); // двигает все клетки
        };
        moveTimer = new Timer(1500, cellMower);
        // Добавление верхней панель
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 1;
        gbConstraints.anchor = GridBagConstraints.NORTH;
        gbLayout.setConstraints(panel1, gbConstraints);
        panel.add(panel1);
        // Добавление нижней панели
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 2;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        gbLayout.setConstraints(panel2, gbConstraints);
        panel.add(panel2);

        contentPane.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // Перекраска клеток так, чтобы не вылезти за границы массива
    private void moveSelected() {
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 10; x++)
                mapCells[x][y].setSelected(false);
        mapCells[location.x][location.y].setSelected(true);
        if (location.x + 1 < 10) mapCells[location.x + 1][location.y].setSelected(true);
        else if (location.y < 7) mapCells[(location.x + 1) % 10][location.y + 1].setSelected(true);
        else mapCells[(location.x + 1) % 10][0].setSelected(true);
        if (location.x + 2 < 10) mapCells[location.x + 2][location.y].setSelected(true);
        else if (location.y < 7) mapCells[(location.x + 2) % 10][location.y + 1].setSelected(true);
        else mapCells[(location.x + 2) % 10][0].setSelected(true);
        if (location.x + 3 < 10) mapCells[location.x + 3][location.y].setSelected(true);
        else if (location.y < 7) mapCells[(location.x + 3) % 10][location.y + 1].setSelected(true);
        else mapCells[(location.x + 3) % 10][0].setSelected(true);
    }

    // Передвижение местоположения
    private void moveLeft() {
        if (location.x > 0) {
            location.x--;
        } else if (location.y > 0) {
            location.y--;
            location.x = 9;
        } else {
            location.y = 7;
            location.x = 9;
        }
        moveSelected();
    }

    private void moveRight() {
        if (location.x < 9) {
            location.x++;
        } else if (location.y < 7) {
            location.y++;
            location.x = 0;
        } else {
            location.y = 0;
            location.x = 0;
        }
        moveSelected();
    }

    private void moveUp() {
        if (location.y > 0)
            location.y--;
        else
            location.y = 7;
        moveSelected();
    }

    private void moveDown() {
        if (location.y < 7)
            location.y++;
        else
            location.y = 0;
        moveSelected();
    }

    // Проверка правильности
    private void check() {
        String text = mapCells[location.x][location.y].getText() + ".";
        if (location.x + 1 > 9)
            text += mapCells[(location.x + 1) % 10][(location.y + 1) % 10].getText() + "." +
                    mapCells[(location.x + 2) % 10][(location.y + 1) % 10].getText() + "." +
                    mapCells[(location.x + 3) % 10][(location.y + 1) % 10].getText();
        else if (location.x + 2 > 9)
            text += mapCells[location.x + 1][location.y % 10].getText() + "." +
                    mapCells[(location.x + 2) % 10][(location.y + 1) % 10].getText() + "." +
                    mapCells[(location.x + 3) % 10][(location.y + 1) % 10].getText();
        else if (location.x + 3 > 9)
            text += mapCells[location.x + 1][location.y % 10].getText() + "." +
                    mapCells[location.x + 2][location.y % 10].getText() + "." +
                    mapCells[(location.x + 3) % 10][(location.y + 1) % 10].getText();
        else text += mapCells[location.x + 1][location.y].getText() + "." +
                    mapCells[location.x + 2][location.y].getText() + "." +
                    mapCells[location.x + 3][location.y].getText();
        if (IP.equals(text)) {
            time.setText("      Successfully connected to the host");
            time.setSize(20);
        } else {
            time.setColorText(Color.RED);
            time.setText("Failed");
        }
        time.stop();
    }

    // Отвечает за то, чтобы клетки двигались по таймеру
    private void moveCell() {
        String text1 = mapCells[0][0].getText();
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 10; x++) {
                if (x + 1 < 10)
                    mapCells[x][y].setText(mapCells[x + 1][y].getText());
                else mapCells[x][y].setText(mapCells[(x + 1) % 10][(y + 1) % 8].getText());
            }
        mapCells[9][7].setText(text1);
    }

    // Получает случайный IP из таблицы
    private String getIP() {
        int rand = (int) (Math.random() * 76);
        return mapCells[rand % 10][rand / 10].getText() + "." + mapCells[(rand + 1) % 10][(rand + 1) / 10].getText()
                + "." + mapCells[(rand + 2) % 10][(rand + 2) / 10].getText() + "." + mapCells[(rand + 3) % 10][(rand + 3) / 10].getText();
    }
}