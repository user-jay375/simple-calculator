import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * SimpleCalculator
 * 
 * Application entry point for the Simple Calculator desktop GUI program.
 * Initializes look and feel, and safely boots the calculator window
 * on the Swing Event Dispatch Thread (EDT).
 * 
 * College OOP Practical Assignment Deliverable.
 */
public class SimpleCalculator {

    public static void main(String[] args) {
        // Apply system look and feel if available for seamless OS window borders
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to standard Java cross-platform look and feel
        }

        // Launch the GUI safely on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            frame.setVisible(true);
        });
    }
}
