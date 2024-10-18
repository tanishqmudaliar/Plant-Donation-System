import javax.swing.*;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("Home Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton donateButton = new JButton("Donate");
        donateButton.setBounds(50, 50, 80, 25);
        panel.add(donateButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(150, 50, 80, 25);
        panel.add(buyButton);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}