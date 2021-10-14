import javax.swing.*;
import java.awt.*;

public class GameUI {

    private final String FONT = "Comic Sans MS";

    private final String BG_PNG = "images/bg-res.png";

    private final String PLAY_AGAIN_PNG = "images/again-res.png";

    private final String FAILED_PNG = "images/failed.png";

    private JFrame frame;
    // Create background label
    private JLabel bg = new JLabel();
    // Create the label of the gallows
    private JLabel gallows = new JLabel();
    // Create the label of the word and letters
    private JLabel word = new JLabel();
    // Display outcome/result in the end
    private JLabel result = new JLabel();
    // Play again button
    private JButton playAgain = new JButton();

    // Instance of the game
    private PlayHangman playHangman = new PlayHangman();

    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
                                        "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    // Array of all the buttons that will display each letter of the alphabet
    private AlphabetButton[] alphabetButtons = new AlphabetButton[26];

    public GameUI(JFrame jFrame){
        // Init the JFrame
        frame = jFrame;

        // Background label
        bg.setIcon(new ImageIcon(BG_PNG));
        bg.setBounds(0, 0, 600, 600);

        // Gallows label
        gallows.setIcon(new ImageIcon("steps/step-0-res.png"));
        gallows.setBounds(0, 0, 500, 300); // x:0 because the image has a big void on the left
        gallows.setVerticalAlignment(SwingConstants.CENTER);
        gallows.setHorizontalAlignment(SwingConstants.CENTER);
        gallows.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Word displaying label
        word.setBounds(0, 300, 600, 50);
        word.setText(playHangman.displayString);
        word.setHorizontalAlignment(JLabel.CENTER);
        word.setAlignmentX(Component.CENTER_ALIGNMENT);
        word.setVerticalAlignment(JLabel.CENTER);
        word.setFont(new Font(FONT, Font.BOLD, 30));
        word.setForeground(Color.BLACK);

        // Final outcome label
        result.setIcon(new ImageIcon(FAILED_PNG));
        result.setBounds(0, 0, 600, 300);
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setOpaque(false);
        result.setVisible(false);

        // Play again button
        ImageIcon imageIcon = new ImageIcon(PLAY_AGAIN_PNG);
        playAgain.setIcon(imageIcon);
        playAgain.setBounds(450, 250, 150, 150);
        playAgain.setAlignmentY(Component.TOP_ALIGNMENT);
        playAgain.setAlignmentX(Component.RIGHT_ALIGNMENT);
        playAgain.setBorder(BorderFactory.createEmptyBorder());
        playAgain.setOpaque(false);
        playAgain.setContentAreaFilled(false);
        playAgain.setFocusable(false);
        playAgain.setVisible(false);

        playAgain.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.repaint();
            new MenuUI(frame);
        });

        // Declare the panel containing the letters
        JPanel alphabetPanel = new JPanel();
        initAlphabetButtons();
        for(int i=0; i< alphabet.length; i++)
            alphabetPanel.add(alphabetButtons[i]);

        alphabetPanel.setBounds(2, 400, 580, 150);
        alphabetPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        alphabetPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 0));
        alphabetPanel.setOpaque(false);
        alphabetPanel.setLayout(new GridLayout(4, 6, 10, 10));

        frame.setLayout(null);

        frame.add(result);
        frame.add(gallows);
        frame.add(word);
        frame.add(playAgain);
        frame.add(alphabetPanel);
        frame.add(bg); // added last so that it doesn't cover the other components / e.g. if added 2nd only the gallows would show
        frame.setVisible(true);
    }

    // Initialize the buttons of the array
    private void initAlphabetButtons(){
        for(int i=0; i< alphabet.length; i++){
            alphabetButtons[i] = new AlphabetButton(alphabet[i], playHangman,this);
        }
    }

    // Disable the buttons when the game is over
    public void disableAlphabetButtons(){
        for(AlphabetButton x : alphabetButtons)
            x.disableButton();
    }

    // Set the play again button visible after the end of the game
    public void setPlayAgainVisible(){
        playAgain.setVisible(true);
    }

    // Update the gallows icon with every every mistake
    public void setGallowsIcon(String URL){
        gallows.setIcon(new ImageIcon(URL));
    }

    // Set the visibility of the gallows label
    public void setGallowsVisible(boolean x){
        gallows.setVisible(x);
    }

    // Update the label that displays the word
    public void setWordText(String text){
        word.setText(text);
    }

    // Update result label text and visibility
    public void setResultIcon(String URL){
        result.setIcon(new ImageIcon(URL));
    }
    public void setResultVisibility(boolean x){
        result.setVisible(x);
    }
}

// Create the buttons of the letters to be added in the buttons panel
class AlphabetButton extends JButton{

    private final String VICTORY_GIF = "images/congrats-gif.gif";

    private final String FONT = "Comic Sans MS";

    public AlphabetButton(String letter, PlayHangman playHangman, GameUI gameUI){
        this.setText(letter.toUpperCase());
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 232), 2));
        this.setForeground(Color.BLACK);
        this.setFont(new Font(FONT, Font.BOLD, 20));
        this.setContentAreaFilled(false);

        this.addActionListener(e -> {
            if(playHangman.getLives() < 1) gameUI.setGallowsIcon("steps/step-6-res.png");
            playHangman.nextRound(this.getText());
            boolean wordFound = playHangman.wordFound();
            boolean wasLetterCorrect = playHangman.wasLetterCorrect;
            String displayString = playHangman.displayString;
            boolean gameOver = playHangman.getLives() < 1;

            System.out.println(playHangman.getLives());

            if(!wordFound && !wasLetterCorrect && !gameOver){
                this.setEnabled(false);
                this.setContentAreaFilled(true);
                this.setBackground(Color.RED);
                if(playHangman.getLives() == 5)
                    gameUI.setGallowsIcon("steps/step-1-res.png");
                if(playHangman.getLives() == 4)
                    gameUI.setGallowsIcon("steps/step-2-res.png");
                if(playHangman.getLives() == 3)
                    gameUI.setGallowsIcon("steps/step-3-res.png");
                if(playHangman.getLives() == 2)
                    gameUI.setGallowsIcon("steps/step-4-res.png");
                if(playHangman.getLives() == 1)
                    gameUI.setGallowsIcon("steps/step-5-res.png");
            }
            else if(!wordFound && wasLetterCorrect && !gameOver){
                this.setEnabled(false);
                this.setContentAreaFilled(true);
                this.setBackground(Color.GREEN);
                gameUI.setWordText(displayString);
            }
            else if(wordFound) {
                gameUI.setResultIcon(VICTORY_GIF);
                gameUI.setGallowsVisible(false);
                gameUI.setWordText(playHangman.toString());
                gameUI.setResultVisibility(true);
                gameUI.setPlayAgainVisible();
            }
            else {
                gameUI.setGallowsIcon("steps/step-6-res.png");
                gameUI.disableAlphabetButtons();
                gameUI.setWordText(playHangman.toString());
                gameUI.setResultVisibility(true);
                gameUI.setPlayAgainVisible();
            }
        });
    }

    public void disableButton(){
        this.setEnabled(false);
    }
}