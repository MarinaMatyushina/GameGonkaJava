import javax.swing.*;
import java.awt.*;

public class City {
    Image image = new ImageIcon("res/city3.jpg").getImage();
    int x;
    int y;
    Road road;

    public City(int x, int y, Road road){
        this.x=x;
        this.y = y;
        this.road = road;
    }

}
