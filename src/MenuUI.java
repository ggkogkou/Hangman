import javax.swing.*;
import java.awt.*;

// Class for the menu frame
public class MenuUI extends JFrame {
    JFrame frame = new JFrame("Play Hangman");

    private final String LOGO_PNG = "images/logo.png";

    private final String BG_PNG = "images/bg-res.png";

    private final String FONT = "Comic Sans MS";

    // Background label
    JLabel bg = new JLabel();
    // Create the Play button
    JButton play = new JButton("Play");
    // Create the logo label
    JLabel logo = new JLabel();

    public MenuUI(){
        // Background label
        bg.setIcon(new ImageIcon(BG_PNG));
        bg.setBounds(0, 0, 600,600);

        // Play button
        play.setFont(new Font(FONT, Font.BOLD, 20));
        play.setBackground(new Color(153, 153, 153));
        play.setForeground(Color.BLACK);
        play.setBounds(100, 400, 400, 100);
        play.setFocusable(false);
        play.setOpaque(false);
        play.setBorder(BorderFactory.createEtchedBorder(2, new Color(9, 91, 232), new Color(9, 91, 232)));

        // Add usability to the button
        play.addActionListener(e -> {
            // Remove the components and prepare the frame for the game screen UI
            frame.getContentPane().removeAll();
            frame.repaint();
            new GameUI(frame);
        });

        // Logo label
        logo.setText("Hangman game");
        logo.setIcon(new ImageIcon(LOGO_PNG));
        logo.setHorizontalTextPosition(JLabel.CENTER);
        logo.setVerticalTextPosition(JLabel.BOTTOM);
        logo.setFont(new Font(FONT, Font.BOLD, 43));
        logo.setForeground(Color.BLACK);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setBounds(100, 50, 400, 300); // set bounds because the frame layout is null

        // Set layout to null and add the components to the frame
        frame.setLayout(null);
        frame.add(play);
        frame.add(logo);
        frame.add(bg);

        // Customize the frame
        frame.setIconImage(new ImageIcon(LOGO_PNG).getImage());
        frame.setResizable(false);
        frame.setSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Second constructor for the play again button
    public MenuUI(JFrame jFrame){
        new GameUI(jFrame);
    }
}
