import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GameFrame extends JFrame {

    //Colors for our GameFrame
    private static final Color BG = new Color(248, 248, 164);
    private static final Color TILE_FILLED_BORDER = new Color(120, 124, 126);
    private static final Color GREEN = new Color(106, 170, 100);
    private static final Color YELLOW = new Color(201, 180, 88);
    private static final Color GRAY = new Color(58, 58, 60);
    private static final Color KEY_DEFAULT = new Color(129, 131, 132);
    
    //Variables used in our GameFrame
    private final int wordLength;
    private final int maxAttempts;
    private final String targetWord;
    private int currentRow = 0;
    private int currentCol = 0;
    private boolean gameOver = false;
    private boolean hintUsed = false;
    private JLabel[][] tiles;
    private final Map<Character, JButton> keyButtons = new HashMap<>();

    //Timer variables for our GameFrame
    private static final int STARTING_SECONDS = 300; 
    private static final int HINT_TIME_PENALTY = 25;
    private int secondsLeft = STARTING_SECONDS;
    private Timer countdownTimer;
    private JLabel timerLabel;
   
    //Constructors
    public GameFrame(int wordLength, int maxAttempts, String targetWord) {
        this.wordLength = wordLength;
        this.maxAttempts = maxAttempts;
        this.targetWord = targetWord;

        // The window frame for our GameFrame
        setTitle("Wordscapes!");
        setSize(650, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        getContentPane().setLayout(new BorderLayout());


        //Timer Label
        timerLabel = new JLabel(formatTime(secondsLeft), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Exit Button
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        //Top panels that contains the exit button, timer label, and our hint button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topPanel.add(exitButton, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.CENTER);
        topPanel.add(createHintButton(), BorderLayout.EAST);
        
         getContentPane().add(topPanel, BorderLayout.NORTH);

         //The tile presented in our GameFrame
        JPanel panel = buildTileGrid();
        getContentPane().add(panel, BorderLayout.CENTER);

        //The keyboard
        JPanel keyboardPanel = buildKeyboard();
        getContentPane().add(keyboardPanel, BorderLayout.SOUTH);

        startCountdown();

        setVisible(true);
    }

    //Method for building the Tile grids
    JPanel buildTileGrid() {
    JPanel grid = new JPanel(new GridLayout(maxAttempts, wordLength, 5, 5));
    grid.setBackground(BG);
    grid.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    tiles = new JLabel[maxAttempts][wordLength];

    for (int row = 0; row < maxAttempts; row++) {
        for (int col = 0; col < wordLength; col++) {
            JLabel tile = new JLabel("", SwingConstants.CENTER);
            tile.setPreferredSize(new Dimension(62, 62));
            tile.setOpaque(true);
            tile.setBackground(Color.WHITE);
            tile.setForeground(Color.BLACK);
            tile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            tile.setFont(new Font("Arial", Font.BOLD, 16));

            tiles[row][col] = tile;
            grid.add(tile);
        }
    }

    //Method to wrap the Tile grids centered in the GameFrame
    JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    wrapper.setBackground(BG);
    wrapper.add(grid);

    return wrapper;
}

    //The method to build the keyboard for our GameFrame
    private JPanel buildKeyboard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] rows = {
                "QWERTYUIOP",
                "ASDFGHJKL",
                "ZXCVBNM"
        };

        for (String rowLetters : rows) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
            rowPanel.setBackground(BG);

            for (char c : rowLetters.toCharArray()) {
                JButton key = new JButton(String.valueOf(c));
                key.setPreferredSize(new Dimension(50, 50));
                key.setBackground(KEY_DEFAULT);
                key.setForeground(Color.WHITE);
                key.setFont(new Font("SANS", Font.BOLD, 12));
                key.setFocusPainted(false);
                key.setBorder(BorderFactory.createEmptyBorder());

                key.addActionListener(e -> typeLetter(c));

                keyButtons.put(c, key);
                rowPanel.add(key);
            }

            if (rowLetters.equals("ASDFGHJKL")) {
                JButton enterKey = new JButton("ENTER");
                enterKey.setPreferredSize(new Dimension(65, 50));
                enterKey.setBackground(KEY_DEFAULT);
                enterKey.setForeground(Color.WHITE);
                enterKey.setFont(new Font("SANS", Font.BOLD, 12));
                enterKey.setFocusPainted(false);
                enterKey.setBorder(BorderFactory.createEmptyBorder());
                enterKey.addActionListener(e -> submitGuess());
                    
                rowPanel.add(enterKey);
            }

             if (rowLetters.equals("QWERTYUIOP")) {
        JButton backspaceKey = new JButton("BACKSPACE");
        backspaceKey.setPreferredSize(new Dimension(65, 50));
        backspaceKey.setBackground(KEY_DEFAULT);
        backspaceKey.setForeground(Color.WHITE);
        backspaceKey.setFont(new Font("SANS", Font.BOLD, 12));
        backspaceKey.setFocusPainted(false);
        backspaceKey.setBorder(BorderFactory.createEmptyBorder());

        backspaceKey.addActionListener(e -> deleteLetter());

        rowPanel.add(backspaceKey);

             }

            panel.add(rowPanel);
        }

        return panel;
    }

    //The method to create the hint button
    JButton createHintButton() {
        JButton hintButton = new JButton("HINT");
        hintButton.setPreferredSize(new Dimension(65, 50));
        hintButton.setBackground(KEY_DEFAULT);
        hintButton.setForeground(Color.WHITE);
        hintButton.setFont(new Font("SANS", Font.BOLD, 12));
        hintButton.setFocusPainted(false);
        hintButton.setBorder(BorderFactory.createEmptyBorder());
        hintButton.addActionListener(e -> useHint());

        return hintButton;
    }

    //Method to handle the typing of letters inside the tiles 
    private void typeLetter(char CANTEEN) {
        if (gameOver) return;
        if (currentCol < wordLength) {
            tiles[currentRow][currentCol].setText(String.valueOf(CANTEEN));
            tiles[currentRow][currentCol].setBorder(BorderFactory.createLineBorder(TILE_FILLED_BORDER, 2));
            currentCol++;
        }
    }

    //The method to handle deleting letters inside the tiles
    private void deleteLetter() {
    if (gameOver) return;
    if (currentCol > 0) {
        currentCol--;
        tiles[currentRow][currentCol].setText("");
        tiles[currentRow][currentCol].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
}

    //The method that handles the guessing of the words typed in by the user
    private void submitGuess() {
    if (gameOver) return;

    if (currentCol < wordLength) {
        return;
    }

    //The method that handles the coloring of the tiles based on the answers of the user
    StringBuilder guessBuilder = new StringBuilder();

    for (int col = 0; col < wordLength; col++) {
        char guessLetter = tiles[currentRow][col].getText().charAt(0);
        char targetLetter = targetWord.charAt(col);
        guessBuilder.append(guessLetter);

        if (guessLetter == targetLetter) {
            tiles[currentRow][col].setBackground(GREEN);
            tiles[currentRow][col].setForeground(Color.WHITE);
        } else if (targetWord.indexOf(guessLetter) != -1) {
            tiles[currentRow][col].setBackground(YELLOW);
            tiles[currentRow][col].setForeground(Color.WHITE);
        } else {
            tiles[currentRow][col].setBackground(GRAY);
            tiles[currentRow][col].setForeground(Color.WHITE);
        }
    }

    //The OptionPane that pops up when the user guesses the word or got the word wrong
    String guess = guessBuilder.toString();

    if (guess.equals(targetWord)) {
        gameOver = true;
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        int answer = JOptionPane.showConfirmDialog(null, "GOOD JOB! Do you wish to go back to the menu?", "You Win!", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            goToMenu();
        }
        return;
    }

    currentRow++;
    currentCol = 0;

    if (currentRow >= maxAttempts) {
        gameOver = true;
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        int answer = JOptionPane.showConfirmDialog(null, "Game Over! The correct word was: " + targetWord + ". Do you wish to go back to the menu?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            goToMenu();
        }
    } 
}

private void goToMenu() {
    if (countdownTimer != null) {
        countdownTimer.stop();
    }
    dispose();
    new MenuFrame().setVisible(true); // adjust to your actual menu class/constructor
}


    //The method for the timer that counts down
     private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void startCountdown() {
        countdownTimer = new Timer(1000, e -> {
            secondsLeft--;
            timerLabel.setText(formatTime(secondsLeft));

            if (secondsLeft <= 0) {
                countdownTimer.stop();
                int answer = JOptionPane.showConfirmDialog(null, "Game Over! The correct word was: " + targetWord + ". Do you wish to go back to the menu?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    goToMenu();
                }
                gameOver = true;
                timerLabel.setText("00:00");
            }
        });
        countdownTimer.start();
    }

    //The OptionPane that pops up when the user used the hint already
    private void useHint() {
        if (hintUsed) {
            JOptionPane.showMessageDialog(null, "Hint already used!", "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //The hint button subtracts 25 seconds from the timer
        hintUsed = true;
        secondsLeft -= HINT_TIME_PENALTY;
        if (secondsLeft < 0) {
            secondsLeft = 0;
        }
        timerLabel.setText(formatTime(secondsLeft));

        char hintLetter = targetWord.charAt(currentCol);
        tiles[currentRow][currentCol].setText(String.valueOf(hintLetter));
        tiles[currentRow][currentCol].setBorder(BorderFactory.createLineBorder(TILE_FILLED_BORDER, 2));
        currentCol++;
    }

    
    public static void main(String[] args) {
        new GameFrame(5, 6, "APPLE");
    }
}