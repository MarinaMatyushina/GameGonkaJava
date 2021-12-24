import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;




public class Road extends JPanel implements ActionListener, Runnable {

    Image image = new ImageIcon("res/road2.png").getImage();
    Hero hero = new Hero();
    Timer mainTimer = new Timer(20, this);

    Thread badsFactory = new Thread(this);
    List<Bad> bads = new ArrayList<Bad>();

    boolean gameOver = false;

    Thread coinsFactory = new Thread(this);
    List<Coin> coins = new ArrayList<Coin>();

    int money = 0;

    City city = new City(0,0,this);

    Thread audioThread = new Thread(new AudioThread());

    Player audioBoom;
    Image imageGameOver = new ImageIcon("res/gameOver.png").getImage();

    {
        try {
            audioBoom = new Player(new FileInputStream("res/boom.mp3"));
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Road(){
        mainTimer.start();
        audioThread.start();
        badsFactory.start();
        coinsFactory.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void run() {
        while(true){
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(3000));
                Bad b;
                if (rand.nextInt()%3==0) {
                    b = BigBad.factory(1500, rand.nextInt(600), rand.nextInt(20), this).getBad();
                }
                else {
                     b = SmallBad.factory(1500,rand.nextInt(600), rand.nextInt(20), this).getBad();
                }


                if (bads.isEmpty()) {
                    bads.add(b);
                }

                else if ( b.x > bads.get(bads.size()-1).x + b.sizeX || b.y > bads.get(bads.size()-1).y + b.sizeY ) {
                    if (b.y < b.sizeY + 50 && b.y < hero.MAX_Y + b.sizeY) {
                        bads.add(b);
                    }
                }
                coins.add(new Coin(1500, rand.nextInt(434),this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            hero.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            hero.keyReleased(e);
        }
    }

    public void paint(Graphics graphics){

        graphics = (Graphics2D) graphics;
        graphics.drawImage(city.image, hero.layerCity1, 0, null);
        graphics.drawImage(city.image, hero.layerCity2, 0, null);
        graphics.drawImage(image, hero.layerX1, hero.layerY, null);
        graphics.drawImage(image, hero.layerX2, hero.layerY, null);
        graphics.drawImage(hero.image, hero.x, hero.y, null);

        int v = (200/hero.MAX_V)*hero.v;
        graphics.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.ITALIC, 20);
        graphics.setFont(font);
        graphics.drawString("Скорость: "+ v + " км/ч", 20, 20);
        graphics.drawString("Счет: "+ money + " руб", 1300, 20);

        if (gameOver){
            Image imageBoom = new ImageIcon("res/boom1.png").getImage();
            graphics.drawImage(imageBoom, hero.x+150, hero.y, null);
        }

        Iterator<Bad> i = bads.iterator();
        while (i.hasNext()){
            Bad b = i.next();
            if (b.y <= hero.MIN_Y || b.x<= -236 || b.y>= hero.MAX_Y + 90 - b.sizeY){
                i.remove();
            } else{
                b.move();
                graphics.drawImage(b.image, b.x, b.y, null);
            }
        }
        Iterator<Coin> k = coins.iterator();
        while (k.hasNext()){
            Coin coin = k.next();
            if (coin.y <= hero.MIN_Y || coin.x<= -40 ){
                k.remove();
            } else{
                coin.move();
                graphics.drawImage(coin.image, coin.x, coin.y, null);
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        hero.move();
        repaint();
        testCollisionWithBad();
        testCollisionWithCoin();
    }

    private void testCollisionWithBad() {
        Iterator<Bad> i = bads.iterator();
        while (i.hasNext()){
            Bad b = i.next();
            if (hero.getEllipse().intersects(b.getRectangle())){
                gameOver = true;
                audioThread.stop();
                try {
                    audioBoom.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
                getGraphics().drawImage(imageGameOver, 0, 0, null);
                JOptionPane.showInternalMessageDialog(null, "GameOver\nСчет: "+ money + " руб.");
                System.exit(1);
            }
        }
    }
    private void testCollisionWithCoin() {
        Iterator<Coin> i = coins.iterator();
        while (i.hasNext()){
            Coin c = i.next();
            if (hero.getEllipse().intersects(c.getRectangle())){
                money+=100;
                i.remove();
            }
        }
    }
}
