# Simple Calculator Application

An elegant, modern, dark-themed Desktop Calculator built using **Core Java (Java Swing and AWT)** for an **Object-Oriented Programming (OOP)** practical assignment.

---

## 1. Project Overview

The **Simple Calculator** is a standalone, offline graphical desktop application designed with professional UI aesthetics and robust object-oriented software engineering principles. It features custom rounded UI components, responsive color palettes, dual-line expression history tracking, arithmetic precision handling, keyboard navigation, and foolproof error handling.

- **Application Type**: Desktop GUI Application
- **Language**: Java (JDK 8 or higher, fully compatible with modern JDK 17, 21, 26+)
- **GUI Framework**: Java Swing & AWT (Zero external libraries)
- **Architecture**: Object-Oriented Component-Based Architecture (MVC-inspired separation of concerns)
- **Target OS**: Windows 11 / Cross-platform (macOS, Linux)

---

## 2. Key Features

- **Modern Dark UI**: Deep slate navy background (`#0F172A`) with high-contrast typography and subtle borders.
- **Custom-Painted Rounded Buttons**: Smooth anti-aliased edges with interactive hover, active click, and focus state management.
- **Semantic Color Coding**:
  - **Number Buttons (0–9, .)**: Dark Slate Grey (`#2D3440`)
  - **Operator Buttons (+, −, ×, ÷)**: Vibrant Royal Blue (`#2563EB`)
  - **Clear Button (AC)**: Alert Crimson Red (`#DC2626`)
  - **Equals Button (=)**: Accent Emerald Green (`#10B981`)
  - **Special Operations (+/-, %, DEL)**: Cool Slate Grey (`#475569`)
- **Dual-Line Display**:
  - **Upper History Label**: Displays the ongoing operation/expression (e.g. `10 + 20 =`).
  - **Primary Display**: Large 30pt bold text field showing the active input and evaluated answer.
- **Robust Arithmetic Engine**:
  - Implemented using Java's `BigDecimal` to eliminate IEEE 754 floating-point rounding inaccuracies (e.g., `0.1 + 0.2 = 0.3` exactly).
  - Division by zero prevention with clear user messaging: `"Cannot divide by zero"`.
  - Duplicate decimal prevention (`3.14.15` is prevented).
  - Dynamic display font scaling for long numbers and status messages.
- **Full Keyboard Support**:
  - Numbers `0`–`9` and `.`
  - Operators `+`, `-`, `*`, `/`, `%`
  - Calculation evaluation via `Enter` or `=`
  - Single digit erasure via `Backspace` or `Delete`
  - Full reset via `Escape`

---

## 3. Object-Oriented Programming (OOP) Concepts Applied

| OOP Concept | Implementation Details |
|---|---|
| **Encapsulation** | State variables in `CalculatorLogic` (operands, operators, error flags) are `private`. Access is granted exclusively through controlled public methods (`appendDigit`, `setOperator`, `calculateResult`, `getCurrentDisplay`). |
| **Inheritance** | `CalculatorFrame` extends `javax.swing.JFrame`<br>`CalculatorButton` extends `javax.swing.JButton` |
| **Polymorphism** | Method overriding of Swing lifecycle methods such as `paintComponent(Graphics g)` in `CalculatorButton` and action dispatching via `actionPerformed(ActionEvent e)`. |
| **Separation of Concerns** | Separation between Presentation Layer (`CalculatorFrame`), Custom Component Styling (`CalculatorButton`), Business Logic (`CalculatorLogic`), and Bootstrap Entry Point (`SimpleCalculator`). |
| **Event-Driven Architecture** | Uses Java's Delegation Event Model via `ActionListener`, `MouseAdapter`, `InputMap`, and `ActionMap`. |
| **Layout Managers** | Multi-tier layout management utilizing `BorderLayout`, `GridLayout(5, 4, 10, 10)`, and `BoxLayout`. |

---

## 4. Project Folder Structure

```
SimpleCalculator/
│
├── src/
│   ├── SimpleCalculator.java   # Main application entry point
│   ├── CalculatorFrame.java    # Primary JFrame window and layout manager
│   ├── CalculatorLogic.java    # Mathematical engine and state manager
│   └── CalculatorButton.java   # Custom anti-aliased Swing button component
│
└── README.md                   # Comprehensive project documentation
```

---

## 5. System Requirements

- **Java Development Kit (JDK)**: JDK 8 or higher (JDK 17, 21, or 26 recommended)
- **Editor / IDE**: VS Code, IntelliJ IDEA, Eclipse, or NetBeans
- **Operating System**: Windows 10/11, macOS, or Linux

---

## 6. How to Compile and Run

### Method 1: Using VS Code Terminal or PowerShell

1. Open your terminal in the project root directory (`simple-calculator`):
   ```powershell
   cd "C:\Users\Baswaraj Tammewar\Downloads\simple-calculator\simple-calculator"
   ```

2. Compile all source files into a `bin` directory:
   ```powershell
   javac -d bin src/*.java
   ```

3. Run the application:
   ```powershell
   java -cp bin SimpleCalculator
   ```

### Method 2: One-Line Run Command (PowerShell)

```powershell
javac -d bin src/*.java; java -cp bin SimpleCalculator
```

### Method 3: In Command Prompt (CMD)

```cmd
javac -d bin src\*.java && java -cp bin SimpleCalculator
```

---

## 7. Calculator Layout Reference

The 20 buttons are arranged in a standard 4-column by 5-row responsive grid:

| Row | Column 1 | Column 2 | Column 3 | Column 4 |
|:---:|:---:|:---:|:---:|:---:|
| **1** | `AC` *(All Clear)* | `+/-` *(Toggle Sign)* | `%` *(Percentage)* | `÷` *(Divide)* |
| **2** | `7` | `8` | `9` | `×` *(Multiply)* |
| **3** | `4` | `5` | `6` | `−` *(Subtract)* |
| **4** | `1` | `2` | `3` | `+` *(Add)* |
| **5** | `0` | `.` *(Decimal)* | `DEL` *(Backspace)* | `=` *(Calculate)* |

---

## 8. Sample Test Cases and Calculations

| Test Case | Inputs | Expected Output | Actual Result | Status |
|---|---|---|---|:---:|
| **Addition** | `10 + 20 =` | `30` | `30` | PASS |
| **Subtraction** | `50 − 20 =` | `30` | `30` | PASS |
| **Multiplication** | `5 × 6 =` | `30` | `30` | PASS |
| **Division** | `100 ÷ 5 =` | `20` | `20` | PASS |
| **Percentage (Standalone)** | `10 %` | `0.1` | `0.1` | PASS |
| **Percentage (In Expression)** | `200 + 10 % =` | `220` | `220` | PASS |
| **Decimals** | `12.5 + 7.5 =` | `20` | `20` | PASS |
| **Division by Zero** | `10 ÷ 0 =` | `Cannot divide by zero` | `Cannot divide by zero` | PASS |
| **Clear Functionality** | `999 -> AC` | `0` | `0` | PASS |
| **Delete Functionality** | `123 -> DEL` | `12` | `12` | PASS |
| **Sign Toggle** | `45 -> +/-` | `-45` | `-45` | PASS |
| **Decimal Protection** | `3 . 1 . 4` | `3.14` | `3.14` | PASS |
| **Chained Operations** | `10 + 20 + 30 =` | `60` | `60` | PASS |

---

## 9. Code Architecture Summary

### `SimpleCalculator.java`
Contains the `main` method. It initializes the native system Look & Feel and uses `SwingUtilities.invokeLater()` to safely instantiate `CalculatorFrame` on the Swing Event Dispatch Thread (EDT).

### `CalculatorFrame.java`
Extends `JFrame` and implements `ActionListener`. Constructs the header with application title, subtitle, and calculator icon, mounts the dual-line display panel, builds the 4-column button grid, and handles action dispatching as well as keyboard shortcuts.

### `CalculatorLogic.java`
The core computational model. Stores operands and state, parses high-precision numbers with `BigDecimal`, handles edge cases, formats results, and catches runtime mathematical exceptions.

### `CalculatorButton.java`
Extends `JButton`. Overrides `paintComponent` to render smooth anti-aliased rounded rectangles with state-based background shading (normal, hover, and pressed) for a high-end desktop app feel.
