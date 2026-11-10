import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MenuFrame extends JFrame implements ActionListener {

    // Variables for buttons and background 
    private JButton btn4LetterWords;
    private JButton btn5LetterWords;
    private JButton btn6LetterWords;
    private JButton btnExit;
    private Image backgroundImage;

    // Our Window frame for our application
public MenuFrame() {
    setTitle("WORDSCAPES");
    setSize(600, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);

    //Background Image of our MenuFrame
    ImageIcon image = new ImageIcon("1327.jpg");
    setIconImage(image.getImage());

    try {
            backgroundImage = ImageIO.read(getClass().getResource("/brick.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
     

 
    JPanel panel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };
    panel.setOpaque(false);
    
    // Title Label of our MenuFrame
    JLabel titleLabel = new JLabel("WORDSCAPES", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 56));
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
    panel.add(titleLabel, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel();
    centerPanel.setOpaque(false);
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setOpaque(false);

    // Our category buttons
    btn4LetterWords = createMenuButton("4 LETTER WORDS");
    btn5LetterWords = createMenuButton("5 LETTER WORDS");
    btn6LetterWords = createMenuButton("6 LETTER WORDS");

        // The Dimension and spacing of each category
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(btn4LetterWords);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        centerPanel.add(btn5LetterWords);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        centerPanel.add(btn6LetterWords);
        centerPanel.add(Box.createVerticalGlue());

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        southPanel.setOpaque(false);

        // Exit button to close the app
        btnExit = new JButton("EXIT");
        btnExit.setPreferredSize(new Dimension(100, 36));
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBackground(Color.RED);
        btnExit.setForeground(Color.WHITE);
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(this);

        southPanel.add(btnExit);
        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }


        // Method to create buttons for the menuframe
        private JButton createMenuButton(String text) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(260, 56));
            button.setMaximumSize(new Dimension(260, 56));
            button.setFont(new Font("Arial", Font.PLAIN, 22));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(this);
            return button;
    }

        // Method to handle the clicking actions of the buttons for the menuframe
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn4LetterWords) {
                openGame(4, 6, "WORD");
            } else if (e.getSource() == btn5LetterWords) {
                openGame(5, 6, "PLANT");
            } else if (e.getSource() == btn6LetterWords) {
                openGame(6, 6, "PUZZLE");
            } else if (e.getSource() == btnExit) {
                System.exit(0);
            }
        }

        // Method to open the gameframe and pick which category you chose
        private void openGame(int wordLength, int maxAttempts, String targetWord) {
            new GameFrame(wordLength, maxAttempts, targetWord);
            dispose();
    }
}