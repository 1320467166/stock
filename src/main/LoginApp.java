package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginApp extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginApp() {
        setTitle("登录界面");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // 添加用户名标签和文本框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);

        // 添加密码标签和密码框
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);

        // 添加登录按钮
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);

        // 将组件添加到面板
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // 用于占位，美化界面
        panel.add(loginButton);

        // 添加面板到窗口
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            // 这里你可以添加登录逻辑，例如验证用户名和密码是否正确
            if (username.equals("admin") && password.equals("admin")) {
                // 登录成功，关闭当前窗体
                dispose();

                // 打开新的窗体，这里使用了另一个示例窗体，你可以根据需求替换为实际的窗体
                MainApp mainApp = null;
                try {
                    mainApp = new MainApp();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mainApp.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "登录失败，请检查用户名和密码", "错误", JOptionPane.ERROR_MESSAGE);
            }

            // 清空密码框
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginApp loginApp = new LoginApp();
            loginApp.setVisible(true);
        });
    }
}
