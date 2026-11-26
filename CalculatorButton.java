import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CalculatorButton
 * 
 * Custom Swing button component providing a sleek modern dark-themed appearance
 * with smooth anti-aliased rounded corners, responsive hover/pressed states,
 * and distinct color schemes based on button types.
 */
public class CalculatorButton extends JButton {

    public enum ButtonType {
        NUMBER,     // Dark grey background
        OPERATOR,   // Blue accent background
        CLEAR,      // Red danger background
        EQUALS,     // Green success background
        SPECIAL     // Charcoal/slate background
    }

    private final ButtonType type;
    private Color defaultBg;
    private Color hoverBg;
    private Color pressedBg;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private final int cornerRadius = 16;

    /**
     * Parameterized Constructor
     * 
     * @param text The button label
     * @param type The functional category of the button
     */
    public CalculatorButton(String text, ButtonType type) {
        super(text);
        this.type = type;

        // Initialize component visual properties
        initColors();
        setFont(new Font("Segoe UI", Font.BOLD, 19));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(65, 55));

        // Add mouse listener for smooth hover and press animations
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    /**
     * Sets distinct color palettes according to the button type.
     */
    private void initColors() {
        switch (type) {
            case NUMBER:
                defaultBg = new Color(45, 52, 64);      // Dark slate grey
                hoverBg   = new Color(59, 68, 84);      // Lighter slate
                pressedBg = new Color(34, 40, 50);      // Deep dark slate
                break;

            case OPERATOR:
                defaultBg = new Color(37, 99, 235);     // Modern Royal Blue
                hoverBg   = new Color(29, 78, 216);     // Darker blue
                pressedBg = new Color(30, 64, 175);     // Deep royal blue
                break;

            case CLEAR:
                defaultBg = new Color(220, 38, 38);     // Crimson Red
                hoverBg   = new Color(185, 28, 28);     // Darker red
                pressedBg = new Color(153, 27, 27);     // Deep burgundy red
                break;

            case EQUALS:
                defaultBg = new Color(16, 185, 129);    // Vibrant Emerald Green
                hoverBg   = new Color(5, 150, 105);     // Darker emerald
                pressedBg = new Color(4, 120, 87);      // Deep emerald
                break;

            case SPECIAL:
                defaultBg = new Color(71, 85, 105);     // Cool Grey
                hoverBg   = new Color(100, 116, 139);   // Lighter cool grey
                pressedBg = new Color(51, 65, 85);      // Deep cool grey
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Determine active background color based on interaction state
        Color currentBg;
        if (isPressed) {
            currentBg = pressedBg;
        } else if (isHovered) {
            currentBg = hoverBg;
        } else {
            currentBg = defaultBg;
        }

        // Draw smooth rounded background
        g2.setColor(currentBg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw centered text
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    public ButtonType getType() {
        return type;
    }
}
