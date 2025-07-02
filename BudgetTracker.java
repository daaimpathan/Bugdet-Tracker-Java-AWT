import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetTracker extends Frame implements ActionListener {

    private Label incomeLabel, expenseLabel, balanceLabel;
    private TextField incomeText, expenseText;
    private Button addIncomeButton, addExpenseButton;
    private TextArea transactionArea;
    private double balance;
    private List<String> transactions;

    public BudgetTracker() {
        setTitle("Simple Budget Tracker");
        setLayout(new FlowLayout());
        setSize(400, 400);
        setLocationRelativeTo(null); // Center the frame
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                System.exit(0);
            }
        });

        incomeLabel = new Label("Income:");
        incomeText = new TextField(10);
        addIncomeButton = new Button("Add Income");
        addIncomeButton.addActionListener(this);

        expenseLabel = new Label("Expense:");
        expenseText = new TextField(10);
        addExpenseButton = new Button("Add Expense");
        addExpenseButton.addActionListener(this);

        balanceLabel = new Label("Balance: ₹0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        transactionArea = new TextArea(10, 30);
        transactionArea.setEditable(false);
        transactions = new ArrayList<>();

        add(incomeLabel);
        add(incomeText);
        add(addIncomeButton);
        add(expenseLabel);
        add(expenseText);
        add(addExpenseButton);
        add(balanceLabel);
        add(new Label("Transactions:"));
        add(transactionArea);

        balance = 0.0;
        updateBalanceLabel();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addIncomeButton) {
            try {
                double income = Double.parseDouble(incomeText.getText());
                balance += income;
                transactions.add("Income: +₹" + String.format("%.2f", income));
                updateTransactionArea();
                updateBalanceLabel();
                incomeText.setText("");
            } catch (NumberFormatException e) {
                transactionArea.append("Invalid income amount.\n");
            }
        } else if (ae.getSource() == addExpenseButton) {
            try {
                double expense = Double.parseDouble(expenseText.getText());
                if (expense <= balance) {
                    balance -= expense;
                    transactions.add("Expense: -₹" + String.format("%.2f", expense));
                    updateTransactionArea();
                    updateBalanceLabel();
                    expenseText.setText("");
                } else {
                    transactionArea.append("Insufficient balance.\n");
                }
            } catch (NumberFormatException e) {
                transactionArea.append("Invalid expense amount.\n");
            }
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: ₹" + String.format("%.2f", balance));
    }

    private void updateTransactionArea() {
        transactionArea.setText("");
        for (String transaction : transactions) {
            transactionArea.append(transaction + "\n");
        }
    }

    public static void main(String[] args) {
        new BudgetTracker();
    }
}