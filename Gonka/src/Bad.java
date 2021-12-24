import javax.swing.*;
import java.awt.*;


abstract class Bad {

    Image image;
    int x ;
    int y ;
    int v ;
    Road road;
    int sizeX;
    int sizeY;
    public abstract Rectangle getRectangle();

    public void move(){
        x = x - road.hero.v + v;

    }
}
interface Factory{
    abstract Bad getBad();
}
class BigBad extends Bad{
    BigBad(int x, int y, int v, Road road){
        super.image = new ImageIcon("res/bigBad.png").getImage();
        super.x = x;
        super.y = y;
        super.v = v;
        super.road = road;
        super.sizeX = 430;
        super.sizeY = 152;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x+10,y+10,400, 130);
    }
    public static Factory factory(int x, int y, int v, Road road){
        return new Factory() {
            @Override
            public Bad getBad() {
                return new BigBad(x,y,v,road);
            }
        };
    }
}
class SmallBad extends Bad{
    SmallBad(int x, int y, int v, Road road){
        super.image = new ImageIcon("res/bad.png").getImage();
        super.x = x;
        super.y = y;
        super.v = v;
        super.road = road;
        super.sizeX = 236;
        super.sizeY = 92;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x+10,y+10,200, 70);
    }
    public static Factory factory(int x, int y, int v, Road road){
        return new  Factory() {
            @Override
            public Bad getBad() {
                return new SmallBad(x,y,v,road);
            }
        };
    }
}

