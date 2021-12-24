import javax.swing.*;
import java.awt.*;

public class Coin {
    Image image = new ImageIcon("res/100.png").getImage();
    int x ;
    int y ;
    Road road;

    public Rectangle getRectangle(){
        return new Rectangle(x,y,45, 18);
    }

    public Coin(int x, int y, Road road){
        this.x = x;
        this.y = y;
        this.road = road;
    }
    public void move(){
        x=x-road.hero.v;
    }

}
