import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class Main extends JFrame {

    private Bank bank;
    private User currentUser;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JPanel loginPanel;
    private JPanel customerPanel;

    //
    public Main() {
        bank = new Bank();
        loadDummyData();

        setTitle("Alamein Banking Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createCustomerPanel();

        mainPanel.add(loginPanel, "LOGIN_SCREEN");
        mainPanel.add(customerPanel, "CUSTOMER_SCREEN");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN_SCREEN");
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel titleLabel = new JLabel("Welcome to Alamein Bank", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Username:"));
        JTextField userField = new JTextField();
        inputPanel.add(userField);

        inputPanel.add(new JLabel("Password:"));
        JPasswordField passField = new JPasswordField();
        inputPanel.add(passField);

        JButton loginButton = new JButton("Login");
        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                User foundUser = bank.findUser(username);

                if (foundUser != null && foundUser.login(password)) {
                    currentUser = foundUser;
                    userField.setText("");
                    passField.setText("");
                    errorLabel.setText("");

                    if (currentUser instanceof Customer) {
                        cardLayout.show(mainPanel, "CUSTOMER_SCREEN");
                    } else {
                        errorLabel.setText("Admin/Employee dashboards not built yet!");
                    }
                } else {
                    errorLabel.setText("Invalid username or password.");
                }
            }
        });

        loginPanel.add(titleLabel);
        loginPanel.add(inputPanel);
        loginPanel.add(loginButton);
        loginPanel.add(errorLabel);
    }

    private void createCustomerPanel() {
        customerPanel = new JPanel(new BorderLayout(10, 10));
        customerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Customer Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JButton logoutButton = new JButton("Logout");
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        JTextArea displayArea = new JTextArea("Your account information will appear here.\n(In a full app, you would write a method to print all account balances here.)");
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel transactionPanel = new JPanel(new GridLayout(2, 4, 5, 5));

        transactionPanel.add(new JLabel("Account #:", SwingConstants.RIGHT));
        JTextField accNumField = new JTextField();
        transactionPanel.add(accNumField);

        transactionPanel.add(new JLabel("Amount $:", SwingConstants.RIGHT));
        JTextField amountField = new JTextField();
        transactionPanel.add(amountField);

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        transactionPanel.add(depositBtn);
        transactionPanel.add(withdrawBtn);

        logoutButton.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN_SCREEN");
        });

        depositBtn.addActionListener(e -> {
            try {
                String accNum = accNumField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String txId = "TXN_" + UUID.randomUUID().toString().substring(0, 5);

                boolean success = bank.processTransaction(accNum, "Deposit", amount, null, txId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Deposit Successful!");
                    amountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Deposit Failed. Check account number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for the amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        withdrawBtn.addActionListener(e -> {
            try {
                String accNum = accNumField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String txId = "TXN_" + UUID.randomUUID().toString().substring(0, 5);

                boolean success = bank.processTransaction(accNum, "Withdrawal", amount, null, txId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Withdrawal Successful!");
                    amountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Withdrawal Failed. Check balance/limits.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for the amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        customerPanel.add(topPanel, BorderLayout.NORTH);
        customerPanel.add(scrollPane, BorderLayout.CENTER);
        customerPanel.add(transactionPanel, BorderLayout.SOUTH);
    }

    private void loadDummyData() {
        Customer john = new Customer("U001", "john123", "password", "John Doe", "john@email.com", "123 Main St");
        CheckingAccount johnChecking = new CheckingAccount("10001", john);

        bank.createUser(john);
        bank.createAccount(johnChecking, john);

        bank.processTransaction("10001", "Deposit", 500.0, null, "INIT_01");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main app = new Main();
                app.setVisible(true);
            }
        });
    }
}