package main;

import main.model.table.GdExternalPurchaseModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainApp extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton gdExternalPurchaseButton;
    private JButton gdSitePurchaseButton;
    private JButton contractPurchaseButton;
    private GdExternalPurchaseModule gdExternalPurchaseModule; // 新增模块

    public MainApp() throws SQLException {
        setTitle("主窗体");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建卡片布局
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 创建三个模块面板
        gdExternalPurchaseModule = new GdExternalPurchaseModule(); // 创建广东外调采购模块
        JPanel gdSitePurchasePanel = createPanel("广东场地采购模块");
        JPanel contractPurchasePanel = createPanel("合同订单采购模块");

        // 将模块面板添加到卡片面板
        cardPanel.add(gdExternalPurchaseModule, "gdExternalPurchase"); // 添加广东外调采购模块
        cardPanel.add(gdSitePurchasePanel, "gdSitePurchase");
        cardPanel.add(contractPurchasePanel, "contractPurchase");

        // 创建左侧的标签按钮
        gdExternalPurchaseButton = new JButton("广东外调采购");
        gdSitePurchaseButton = new JButton("广东场地采购");
        contractPurchaseButton = new JButton("合同订单采购");

        // 添加按钮点击事件监听器
        gdExternalPurchaseButton.addActionListener(this);
        gdSitePurchaseButton.addActionListener(this);
        contractPurchaseButton.addActionListener(this);

        // 创建左侧标签面板
        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new GridLayout(3, 1));
        tagPanel.add(gdExternalPurchaseButton);
        tagPanel.add(gdSitePurchaseButton);
        tagPanel.add(contractPurchaseButton);

        // 创建主面板，包括标签面板和卡片面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tagPanel, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // 添加主面板到窗体
        add(mainPanel);
    }

    private JPanel createPanel(String moduleName) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(moduleName);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gdExternalPurchaseButton) {
            cardLayout.show(cardPanel, "gdExternalPurchase");
        } else if (e.getSource() == gdSitePurchaseButton) {
            cardLayout.show(cardPanel, "gdSitePurchase");
        } else if (e.getSource() == contractPurchaseButton) {
            cardLayout.show(cardPanel, "contractPurchase");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp mainApp = null;
            try {
                mainApp = new MainApp();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            mainApp.setVisible(true);
        });
    }
}
