package flappyrect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Md Selim Miah
 * @author Kawsar Ahmed
 * @author Md Mahadi Hasan Nahid
 *
 * North East University Bangladesh (NEUB) nahid@neub.edu.bd
 * @version 1.0
 *
 */
public class FlappyRect implements ActionListener, MouseListener, KeyListener {

    public static FlappyRect flappyRect;
    public final int WIDTH = 1300, HEIGHT = 600;
    public Renderer renderer;
    public Rectangle bird;
    public int ticks, yMotion, score;
    public ArrayList<Rectangle> columns;
    public Random rand;
    public boolean gameOver, started;

    public FlappyRect() {

        System.out.println("" + started);
        System.out.println("" + gameOver);
        JFrame jframe = new JFrame();
        Timer timer = new Timer(25, this);
        renderer = new Renderer();
        jframe.setTitle("SELIM and KAWSAR'S Flappy Rect Game!!!");
        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        jframe.add(renderer);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);

        jframe.addKeyListener(this);
        jframe.setResizable(true);
        jframe.setVisible(true);
        columns = new ArrayList<Rectangle>();
        rand = new Random();
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        timer.start();
    }

    public void addColumn(boolean start) {
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);
        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;
        if (started) {
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= speed;

            }
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                if (column.x + column.width < 0) {
                    columns.remove(column);
                    if (column.y == 0) {
                        addColumn(false);
                    }
                }

            }
            bird.y += yMotion;
            for (Rectangle column : columns) {
                if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
                    score++;
                }
                if (column.intersects(bird)) {
                    gameOver = true;
                    if (bird.x <= column.x) {
                        bird.x = column.x - bird.width;
                    } else {
                        if (column.y != 0) {
                            bird.y = column.y - bird.height;
                        } else if (bird.y < column.height) {
                            bird.y = column.height;
                        }
                    }
                }
            }
            if (bird.y > HEIGHT - 120 || bird.y < 0) {

                gameOver = true;
            }
            if (bird.y + yMotion >= HEIGHT - 120) {
                bird.y = HEIGHT - 120 - bird.height;
            }
        }

        renderer.repaint();
    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void jump() {
        if (gameOver) {
            gameOver = false;
        }
        if (!started) {
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            started = true;

        }
        if (!started) {
            started = true;
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 10;
        }
    }

    public void repaint(Graphics g) throws InterruptedException {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, WIDTH);
        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 150, WIDTH, 150);
        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 150, WIDTH, 20);
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
        for (Rectangle column : columns) {
            paintColumn(g, column);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));
        if (!started) {
            g.drawString("Click to start!!!", 75, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Game Over!!!Please", 100, HEIGHT / 2 - 50);//edit
            //System.out.println("Game Over");
            sleep(100000);//modified

        }
        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 50, 100);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

}
