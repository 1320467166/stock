package main.model.dialog;

import main.model.table.GdExternalPurchaseModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateDataDialog extends JDialog {
    private JTextField dateField;
    private JTextField containerNumberField;
    private JTextField netWeightField;
    private JTextField cargoInfoField;
    private JTextField addressField;
    private JTextField ownerField;
    private JTextField priceField;
    private JTextField amountField;
    private JTextField companyNetWeightField;
    private JTextField deductionField;
    private JTextField qualityDeductionField;
    private JTextField settlementQuantityField;
    private JTextField companyAmountField;
    private JTextField entryDateField;
    private JTextField advancePaymentCheckBox;
    private JTextField paidMonthCheckBox;
    private JTextField containerField;
    private JTextField commentField;

    private JButton saveButton;

    private GdExternalPurchaseModule gdExternalPurchaseModule;

    public CreateDataDialog(JFrame parentFrame, GdExternalPurchaseModule gdExternalPurchaseModule) {
        super(parentFrame, "创建数据", true);
        this.gdExternalPurchaseModule = gdExternalPurchaseModule;
        setSize(400, 600);
        setLocationRelativeTo(parentFrame);

        // 创建输入字段和组件，以匹配新的列名
        dateField = new JTextField(20);
        containerNumberField = new JTextField(20);
        netWeightField = new JTextField(20);
        cargoInfoField = new JTextField(20);
        addressField = new JTextField(20);
        ownerField = new JTextField(20);
        priceField = new JTextField(20);
        amountField = new JTextField(20);
        companyNetWeightField = new JTextField(20);
        deductionField = new JTextField(20);
        qualityDeductionField = new JTextField(20);
        settlementQuantityField = new JTextField(20);
        companyAmountField = new JTextField(20);
        entryDateField = new JTextField(20);
        advancePaymentCheckBox = new JTextField(20);
        paidMonthCheckBox = new JTextField(20);
        containerField = new JTextField(20);
        commentField = new JTextField(20);

        // 创建保存按钮
        saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的数据
                String[] rowData = new String[]{
                        dateField.getText(),
                        containerNumberField.getText(),
                        netWeightField.getText(),
                        cargoInfoField.getText(),
                        addressField.getText(),
                        ownerField.getText(),
                        priceField.getText(),
                        amountField.getText(),
                        companyNetWeightField.getText(),
                        deductionField.getText(),
                        qualityDeductionField.getText(),
                        settlementQuantityField.getText(),
                        companyAmountField.getText(),
                        entryDateField.getText(),
                        advancePaymentCheckBox.getText(),
                        paidMonthCheckBox.getText(),
                        containerField.getText(),
                        commentField.getText()
                };

                // 将新数据添加到表格
                gdExternalPurchaseModule.addData(rowData);

                // 关闭对话框
                dispose();
            }
        });

        // 创建输入面板，将输入字段和组件添加到面板中
        JPanel inputPanel = new JPanel(new GridLayout(18, 2));
        inputPanel.add(new JLabel("日期:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("柜号:"));
        inputPanel.add(containerNumberField);
        inputPanel.add(new JLabel("净重:"));
        inputPanel.add(netWeightField);
        inputPanel.add(new JLabel("集装箱公司/货名:"));
        inputPanel.add(cargoInfoField);
        inputPanel.add(new JLabel("地址:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("货主:"));
        inputPanel.add(ownerField);
        inputPanel.add(new JLabel("价格:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("金额:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("公司净重:"));
        inputPanel.add(companyNetWeightField);
        inputPanel.add(new JLabel("扣杂:"));
        inputPanel.add(deductionField);
        inputPanel.add(new JLabel("质量扣款:"));
        inputPanel.add(qualityDeductionField);
        inputPanel.add(new JLabel("结算数量:"));
        inputPanel.add(settlementQuantityField);
        inputPanel.add(new JLabel("公司金额:"));
        inputPanel.add(companyAmountField);
        inputPanel.add(new JLabel("进厂日期:"));
        inputPanel.add(entryDateField);
        inputPanel.add(new JLabel("预付标记:"));
        inputPanel.add(advancePaymentCheckBox);
        inputPanel.add(new JLabel("已结月份标记:"));
        inputPanel.add(paidMonthCheckBox);
        inputPanel.add(new JLabel("集装箱:"));
        inputPanel.add(containerField);
        inputPanel.add(new JLabel("备注:"));
        inputPanel.add(commentField);

        // 创建按钮面板，将保存按钮添加到面板中
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        // 将输入面板和按钮面板添加到对话框
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setGdExternalPurchaseModule(GdExternalPurchaseModule gdExternalPurchaseModule) {
        this.gdExternalPurchaseModule = gdExternalPurchaseModule;
    }
}
