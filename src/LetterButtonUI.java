import javax.swing.*;
import java.awt.*;

public class LetterButtonUI extends JButton {

    public LetterButtonUI(String letter){
        this.setText(letter.toUpperCase());
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 232), 2));
        this.setForeground(Color.BLACK);
        this.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        this.setContentAreaFilled(false);

        this.addActionListener(e -> {
            this.setEnabled(false);
            this.setContentAreaFilled(true);
            this.setBackground(Color.GREEN);
        });
    }
}
