import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CalculatorLogic
 * 
 * Handles all calculation logic, state management, and mathematical operations
 * for the Simple Calculator application.
 * Follows Object-Oriented Principles (Encapsulation, Data Hiding).
 */
public class CalculatorLogic {

    private String currentInput;
    private String expressionHistory;
    private BigDecimal firstOperand;
    private String pendingOperator;
    private boolean startNewInput;
    private boolean hasError;

    /**
     * Constructor initializes calculator state to default values.
     */
    public CalculatorLogic() {
        reset();
    }

    /**
     * Resets all internal state variables to default values.
     */
    public void reset() {
        this.currentInput = "0";
        this.expressionHistory = "";
        this.firstOperand = null;
        this.pendingOperator = null;
        this.startNewInput = false;
        this.hasError = false;
    }

    /**
     * Appends a numeric digit (0-9) to the current input.
     * 
     * @param digit The digit character string to append.
     */
    public void appendDigit(String digit) {
        if (hasError) {
            reset();
        }

        if (startNewInput) {
            currentInput = digit.equals("0") ? "0" : digit;
            startNewInput = false;
            return;
        }

        if (currentInput.equals("0")) {
            if (!digit.equals("0")) {
                currentInput = digit;
            }
        } else {
            // Prevent display overflow by capping input length at 15 digits
            if (currentInput.length() < 15) {
                currentInput += digit;
            }
        }
    }

    /**
     * Appends a decimal point to the current input, avoiding duplicates.
     */
    public void appendDecimal() {
        if (hasError) {
            reset();
            currentInput = "0.";
            startNewInput = false;
            return;
        }

        if (startNewInput) {
            currentInput = "0.";
            startNewInput = false;
            return;
        }

        if (!currentInput.contains(".")) {
            currentInput += ".";
        }
    }

    /**
     * Toggles the sign of the current number (+/-).
     */
    public void toggleSign() {
        if (hasError || currentInput.equals("0") || currentInput.equals("0.")) {
            return;
        }

        if (currentInput.startsWith("-")) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = "-" + currentInput;
        }
    }

    /**
     * Calculates the percentage of the current input.
     * If an operation is in progress (e.g. 100 + 10%), it computes 10% of 100.
     * Otherwise, it divides the current number by 100.
     */
    public void applyPercentage() {
        if (hasError) {
            return;
        }

        try {
            BigDecimal currentVal = new BigDecimal(currentInput);
            BigDecimal percentageFactor = new BigDecimal("0.01");

            if (firstOperand != null && pendingOperator != null) {
                BigDecimal percentVal = firstOperand.multiply(currentVal).multiply(percentageFactor);
                currentInput = formatNumber(percentVal);
            } else {
                BigDecimal result = currentVal.multiply(percentageFactor);
                currentInput = formatNumber(result);
                expressionHistory = formatNumber(currentVal) + "% =";
                startNewInput = true;
            }
        } catch (Exception e) {
            setErrorState("Invalid Operation");
        }
    }

    /**
     * Deletes the last digit of the current input (DEL).
     */
    public void deleteLastDigit() {
        if (hasError) {
            reset();
            return;
        }

        if (startNewInput) {
            return;
        }

        if (currentInput.length() > 1) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.equals("-") || currentInput.isEmpty()) {
                currentInput = "0";
            }
        } else {
            currentInput = "0";
        }
    }

    /**
     * Sets the mathematical operator (+, −, ×, ÷) and prepares for the second operand.
     * If an operation was already pending, it calculates the intermediate result first.
     * 
     * @param operator The arithmetic operator symbol.
     */
    public void setOperator(String operator) {
        if (hasError) {
            return;
        }

        // Standardize operator symbol
        String normalizedOp = normalizeOperator(operator);

        // If the user already entered an operand and operator, calculate chained result
        if (pendingOperator != null && !startNewInput) {
            calculateIntermediate();
            if (hasError) {
                return;
            }
        }

        try {
            firstOperand = new BigDecimal(currentInput);
            pendingOperator = normalizedOp;
            expressionHistory = formatNumber(firstOperand) + " " + pendingOperator;
            startNewInput = true;
        } catch (Exception e) {
            setErrorState("Invalid Input");
        }
    }

    /**
     * Evaluates the calculation when Equals (=) is pressed.
     */
    public void calculateResult() {
        if (hasError) {
            return;
        }

        if (pendingOperator == null || firstOperand == null) {
            expressionHistory = currentInput + " =";
            startNewInput = true;
            return;
        }

        try {
            BigDecimal secondOperand = new BigDecimal(currentInput);

            // Handle division by zero
            if (pendingOperator.equals("÷") && secondOperand.compareTo(BigDecimal.ZERO) == 0) {
                expressionHistory = formatNumber(firstOperand) + " ÷ 0 =";
                setErrorState("Cannot divide by zero");
                return;
            }

            BigDecimal result = executeOperation(firstOperand, secondOperand, pendingOperator);

            expressionHistory = formatNumber(firstOperand) + " " + pendingOperator + " " + formatNumber(secondOperand) + " =";
            currentInput = formatNumber(result);
            firstOperand = result;
            pendingOperator = null;
            startNewInput = true;
        } catch (ArithmeticException ae) {
            setErrorState("Calculation Error");
        } catch (Exception e) {
            setErrorState("Invalid Input");
        }
    }

    /**
     * Calculates an intermediate result for chained operations (e.g. 5 + 3 + 2).
     */
    private void calculateIntermediate() {
        try {
            BigDecimal secondOperand = new BigDecimal(currentInput);

            if (pendingOperator.equals("÷") && secondOperand.compareTo(BigDecimal.ZERO) == 0) {
                expressionHistory = formatNumber(firstOperand) + " ÷ 0 =";
                setErrorState("Cannot divide by zero");
                return;
            }

            BigDecimal result = executeOperation(firstOperand, secondOperand, pendingOperator);
            firstOperand = result;
            currentInput = formatNumber(result);
        } catch (Exception e) {
            setErrorState("Error");
        }
    }

    /**
     * Executes the specific binary arithmetic operation.
     */
    private BigDecimal executeOperation(BigDecimal a, BigDecimal b, String op) {
        switch (op) {
            case "+":
                return a.add(b);
            case "−":
            case "-":
                return a.subtract(b);
            case "×":
            case "*":
                return a.multiply(b);
            case "÷":
            case "/":
                // 10 decimal digits of precision with standard Half-Up rounding
                return a.divide(b, 10, RoundingMode.HALF_UP);
            default:
                return b;
        }
    }

    /**
     * Formats a BigDecimal into a clean human-readable string without trailing zeros.
     */
    public String formatNumber(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        // Strip trailing zeros and avoid scientific notation for standard numbers
        BigDecimal stripped = value.stripTrailingZeros();
        if (stripped.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return stripped.toPlainString();
    }

    /**
     * Sets error state and updates display.
     */
    private void setErrorState(String errorMessage) {
        this.hasError = true;
        this.currentInput = errorMessage;
        this.firstOperand = null;
        this.pendingOperator = null;
        this.startNewInput = true;
    }

    /**
     * Normalizes keyboard or display operators to unified symbols.
     */
    private String normalizeOperator(String op) {
        switch (op) {
            case "*":
                return "×";
            case "/":
                return "÷";
            case "-":
                return "−";
            default:
                return op;
        }
    }

    // --- Getters for GUI Rendering ---

    public String getCurrentDisplay() {
        return currentInput;
    }

    public String getExpressionHistory() {
        return expressionHistory;
    }

    public boolean hasError() {
        return hasError;
    }
}
