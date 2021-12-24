import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;

public class Hero {

    public static final int MIN_V = 0;
    public static final int MAX_V = 100;
    public static final int MIN_Y = 173;
    public static final int MAX_Y = 562;

    Image imageNorm = new ImageIcon("res/heroCat.png").getImage();
    Image imageUp = new ImageIcon("res/heroCat2.png").getImage();
    Image imageDown = new ImageIcon("res/heroCat3.png").getImage();
    Image image = imageNorm;

    int v=0; //скорость
    int a=0; //ускорение
    int s=0; //длина пути
    int x = 70; //координаты
    int y = 85;
    int dy = 0;
    int layerX1 = 0; //координата дороги первый слой
    int layerX2 = 1150;
    int layerY=128;

    int layerCity1=0;
    int layerCity2=3156;


    public Rectangle getRectangle(){
        return new Rectangle(x+5,y+5,217, 90);
    }

    public Ellipse2D getEllipse(){
        Ellipse2D ellipse2D = new Ellipse2D() {
            @Override
            public double getX() {
                return x;
            }

            @Override
            public double getY() {
                return y;
            }

            @Override
            public double getWidth() {
                return 222;
            }

            @Override
            public double getHeight() {
                return 96;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void setFrame(double x, double y, double w, double h) {

            }

            @Override
            public Rectangle2D getBounds2D() {
                return null;
            }
        };
        return ellipse2D;
    }

    public void move(){
        s += v;
        v += a;
        if (v<0) { v = 0;}
        if (v<= MIN_V) { v = MIN_V; }
        if (v>= MAX_V) { v = MAX_V; }
        y -= dy;
        if (y <= MIN_Y) { y = MIN_Y; }
        if (y>= MAX_Y) { y = MAX_Y; }
        if (layerX2 - v <=0){
            layerX1 = 0;
            layerX2 = 1150;
        }
        layerX1 -= v;
        layerX2 -= v;

        if (layerCity2 - v/30 <=0){
            layerCity1 = 0;
            layerCity2 = 3156;
        }
        layerCity1 -= v/30;
        layerCity2 -= v/30;

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D){
            a = 1;
        }
        if (key == KeyEvent.VK_A){
            a = -1;
        }
        if (key == KeyEvent.VK_W){
            dy = 10;
            image = imageUp;
        }
        if (key == KeyEvent.VK_S ){
            dy = -10;
            image = imageDown;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_A){
            a = 0;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S){
            dy = 0;
            image = imageNorm;
        }
    }

}
