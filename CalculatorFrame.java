import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * CalculatorFrame
 * 
 * Main GUI window for the Simple Calculator application.
 * Manages layout design, visual styling, button grid arrangement,
 * and user interaction event handling.
 */
public class CalculatorFrame extends JFrame implements ActionListener {

    // Logic Controller
    private final CalculatorLogic logic;

    // GUI Display Components
    private JLabel historyLabel;
    private JTextField displayField;

    // Theme Colors
    private static final Color COLOR_WINDOW_BG   = new Color(15, 23, 42);    // Deep Slate Navy
    private static final Color COLOR_DISPLAY_BG  = new Color(30, 41, 59);   // Dark Slate Container
    private static final Color COLOR_DISPLAY_BORDER = new Color(51, 65, 85);
    private static final Color COLOR_TEXT_PRIMARY   = new Color(248, 250, 252); // High-contrast White
    private static final Color COLOR_TEXT_MUTED     = new Color(148, 163, 184); // Muted Silver/Grey
    private static final Color COLOR_ACCENT_BLUE    = new Color(59, 130, 246);

    /**
     * Constructor initializes the calculator logic, GUI layout, and window settings.
     */
    public CalculatorFrame() {
        this.logic = new CalculatorLogic();
        initializeWindow();
        initializeUI();
        setupKeyboardShortcuts();
    }

    /**
     * Configures primary window properties.
     */
    private void initializeWindow() {
        setTitle("SIMPLE CALCULATOR");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(390, 600);
        setMinimumSize(new Dimension(380, 580));
        setResizable(false);
        setLocationRelativeTo(null); // Center of the screen
        getContentPane().setBackground(COLOR_WINDOW_BG);
    }

    /**
     * Builds and arranges all visual panels inside the frame.
     */
    private void initializeUI() {
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout(0, 16));
        mainContainer.setBackground(COLOR_WINDOW_BG);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // 1. Header Section
        mainContainer.add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Center Container (Display + Keypad)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 16));
        centerPanel.setBackground(COLOR_WINDOW_BG);

        centerPanel.add(createDisplayPanel(), BorderLayout.NORTH);
        centerPanel.add(createButtonGridPanel(), BorderLayout.CENTER);

        mainContainer.add(centerPanel, BorderLayout.CENTER);

        add(mainContainer);
    }

    /**
     * Creates the Header Section with title, subtitle, and calculator badge.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(COLOR_WINDOW_BG);

        // Title and Subtitle Box
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(COLOR_WINDOW_BG);

        JPanel titleRow = new JPanel(new BorderLayout(8, 0));
        titleRow.setBackground(COLOR_WINDOW_BG);

        JLabel iconLabel = new JLabel("🖩");
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
        iconLabel.setForeground(COLOR_ACCENT_BLUE);

        JLabel titleLabel = new JLabel("SIMPLE CALCULATOR");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titleLabel.setForeground(COLOR_TEXT_PRIMARY);

        titleRow.add(iconLabel, BorderLayout.WEST);
        titleRow.add(titleLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel("Perform Basic Mathematical Operations");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(COLOR_TEXT_MUTED);

        titleBox.add(titleRow);
        titleBox.add(Box.createVerticalStrut(3));
        titleBox.add(subtitleLabel);

        headerPanel.add(titleBox, BorderLayout.WEST);

        // Bottom border accent line
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_DISPLAY_BORDER));

        return headerPanel;
    }

    /**
     * Creates the dual-line Display Section (History + Current Input).
     */
    private JPanel createDisplayPanel() {
        // Custom rounded panel for screen effect
        JPanel displayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_DISPLAY_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(COLOR_DISPLAY_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setOpaque(false);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        displayPanel.setPreferredSize(new Dimension(350, 95));

        // Sub-label for calculation history/expression
        historyLabel = new JLabel(" ");
        historyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        historyLabel.setForeground(COLOR_TEXT_MUTED);
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        historyLabel.setAlignmentX(1.0f);

        // Main primary display field
        displayField = new JTextField("0");
        displayField.setFont(new Font("Segoe UI", Font.BOLD, 30));
        displayField.setForeground(COLOR_TEXT_PRIMARY);
        displayField.setBackground(COLOR_DISPLAY_BG);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBorder(null);
        displayField.setOpaque(false);
        displayField.setFocusable(false);
        displayField.setAlignmentX(1.0f);

        displayPanel.add(historyLabel);
        displayPanel.add(Box.createVerticalStrut(4));
        displayPanel.add(displayField);

        return displayPanel;
    }

    /**
     * Creates the 5-Row x 4-Column Calculator Button Grid.
     */
    private JPanel createButtonGridPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(5, 4, 10, 10));
        gridPanel.setBackground(COLOR_WINDOW_BG);

        // Button definitions according to specifications:
        // Row 1: AC, +/-, %, ÷
        // Row 2: 7, 8, 9, ×
        // Row 3: 4, 5, 6, −
        // Row 4: 1, 2, 3, +
        // Row 5: 0, ., DEL, =

        Object[][] buttonData = {
            {"AC",  CalculatorButton.ButtonType.CLEAR},
            {"+/-", CalculatorButton.ButtonType.SPECIAL},
            {"%",   CalculatorButton.ButtonType.SPECIAL},
            {"÷",   CalculatorButton.ButtonType.OPERATOR},

            {"7",   CalculatorButton.ButtonType.NUMBER},
            {"8",   CalculatorButton.ButtonType.NUMBER},
            {"9",   CalculatorButton.ButtonType.NUMBER},
            {"×",   CalculatorButton.ButtonType.OPERATOR},

            {"4",   CalculatorButton.ButtonType.NUMBER},
            {"5",   CalculatorButton.ButtonType.NUMBER},
            {"6",   CalculatorButton.ButtonType.NUMBER},
            {"−",   CalculatorButton.ButtonType.OPERATOR},

            {"1",   CalculatorButton.ButtonType.NUMBER},
            {"2",   CalculatorButton.ButtonType.NUMBER},
            {"3",   CalculatorButton.ButtonType.NUMBER},
            {"+",   CalculatorButton.ButtonType.OPERATOR},

            {"0",   CalculatorButton.ButtonType.NUMBER},
            {".",   CalculatorButton.ButtonType.NUMBER},
            {"DEL", CalculatorButton.ButtonType.CLEAR},
            {"=",   CalculatorButton.ButtonType.EQUALS}
        };

        for (Object[] item : buttonData) {
            String text = (String) item[0];
            CalculatorButton.ButtonType type = (CalculatorButton.ButtonType) item[1];

            CalculatorButton button = new CalculatorButton(text, type);
            button.setActionCommand(text);
            button.addActionListener(this);
            gridPanel.add(button);
        }

        return gridPanel;
    }

    /**
     * Handles all button clicks and coordinates with CalculatorLogic.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        processCommand(command);
    }

    /**
     * Dispatches user commands to logic and updates the visual display.
     */
    public void processCommand(String command) {
        switch (command) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                logic.appendDigit(command);
                break;

            case ".":
                logic.appendDecimal();
                break;

            case "+/-":
                logic.toggleSign();
                break;

            case "%":
                logic.applyPercentage();
                break;

            case "DEL":
                logic.deleteLastDigit();
                break;

            case "AC":
                logic.reset();
                break;

            case "+":
            case "−":
            case "-":
            case "×":
            case "*":
            case "÷":
            case "/":
                logic.setOperator(command);
                break;

            case "=":
                logic.calculateResult();
                break;

            default:
                break;
        }

        updateDisplay();
    }

    /**
     * Refreshes GUI display labels from the current logic state.
     */
    private void updateDisplay() {
        String displayText = logic.getCurrentDisplay();
        String historyText = logic.getExpressionHistory();

        // Adjust font size dynamically if message is long (e.g. error message)
        if (displayText.length() > 14) {
            displayField.setFont(new Font("Segoe UI", Font.BOLD, 19));
        } else if (displayText.length() > 10) {
            displayField.setFont(new Font("Segoe UI", Font.BOLD, 24));
        } else {
            displayField.setFont(new Font("Segoe UI", Font.BOLD, 30));
        }

        // Highlight error text with red accent
        if (logic.hasError()) {
            displayField.setForeground(new Color(248, 113, 113));
        } else {
            displayField.setForeground(COLOR_TEXT_PRIMARY);
        }

        displayField.setText(displayText);
        historyLabel.setText(historyText.isEmpty() ? " " : historyText);
    }

    /**
     * Enables seamless keyboard interaction for numbers, operators, and keys.
     */
    private void setupKeyboardShortcuts() {
        JComponent root = getRootPane();
        InputMap inputMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = root.getActionMap();

        String[][] keyMappings = {
            {"0", "0"}, {"1", "1"}, {"2", "2"}, {"3", "3"}, {"4", "4"},
            {"5", "5"}, {"6", "6"}, {"7", "7"}, {"8", "8"}, {"9", "9"},
            {".", "."}, {"PLUS", "+"}, {"MINUS", "−"}, {"MULTIPLY", "×"},
            {"DIVIDE", "÷"}, {"SLASH", "÷"}, {"EQUALS", "="}, {"PERCENT", "%"}
        };

        // Number keys and basic operators
        for (int i = 0; i <= 9; i++) {
            final String num = String.valueOf(i);
            inputMap.put(KeyStroke.getKeyStroke("typed " + num), "key_" + num);
            inputMap.put(KeyStroke.getKeyStroke("NUMPAD" + num), "numpad_" + num);

            AbstractAction action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    processCommand(num);
                }
            };
            actionMap.put("key_" + num, action);
            actionMap.put("numpad_" + num, action);
        }

        // Decimal point
        inputMap.put(KeyStroke.getKeyStroke("typed ."), "key_dot");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0), "key_decimal");
        AbstractAction dotAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCommand(".");
            }
        };
        actionMap.put("key_dot", dotAction);
        actionMap.put("key_decimal", dotAction);

        // Operator keys
        bindKey(inputMap, actionMap, "typed +", "+");
        bindKey(inputMap, actionMap, KeyEvent.VK_ADD, "+");
        bindKey(inputMap, actionMap, "typed -", "−");
        bindKey(inputMap, actionMap, KeyEvent.VK_SUBTRACT, "−");
        bindKey(inputMap, actionMap, "typed *", "×");
        bindKey(inputMap, actionMap, KeyEvent.VK_MULTIPLY, "×");
        bindKey(inputMap, actionMap, "typed /", "÷");
        bindKey(inputMap, actionMap, KeyEvent.VK_DIVIDE, "÷");
        bindKey(inputMap, actionMap, "typed %", "%");

        // Equals and Enter
        bindKey(inputMap, actionMap, KeyEvent.VK_ENTER, "=");
        bindKey(inputMap, actionMap, "typed =", "=");

        // Backspace / DEL
        bindKey(inputMap, actionMap, KeyEvent.VK_BACK_SPACE, "DEL");
        bindKey(inputMap, actionMap, KeyEvent.VK_DELETE, "DEL");

        // Escape / AC
        bindKey(inputMap, actionMap, KeyEvent.VK_ESCAPE, "AC");
    }

    private void bindKey(InputMap im, ActionMap am, String keyStrokeStr, String command) {
        im.put(KeyStroke.getKeyStroke(keyStrokeStr), "cmd_" + keyStrokeStr);
        am.put("cmd_" + keyStrokeStr, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCommand(command);
            }
        });
    }

    private void bindKey(InputMap im, ActionMap am, int keyCode, String command) {
        im.put(KeyStroke.getKeyStroke(keyCode, 0), "cmd_code_" + keyCode);
        am.put("cmd_code_" + keyCode, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCommand(command);
            }
        });
    }
}
