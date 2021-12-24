import javax.swing.*;


public class Main {

    public static void main(String[] args) {

        JTextField userName = new JTextField(1);
        JFrame frame = new JFrame("Gonka");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 770);
        frame.add(new Road());
        frame.setVisible(true);


    }

}
