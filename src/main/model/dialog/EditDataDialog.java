package main.model.dialog;

import main.model.table.GdExternalPurchaseModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EditDataDialog extends JDialog {
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
    private Vector<String> rowData;

    public EditDataDialog(JFrame parentFrame,GdExternalPurchaseModule gdExternalPurchaseModule, Vector<String> rowData) {
        super(parentFrame, "编辑数据", true);
        this.rowData = rowData;
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
                rowData.set(0, dateField.getText());
                rowData.set(1, containerNumberField.getText());
                rowData.set(2, netWeightField.getText());
                rowData.set(3, cargoInfoField.getText());
                rowData.set(4, addressField.getText());
                rowData.set(5, ownerField.getText());
                rowData.set(6, priceField.getText());
                rowData.set(7, amountField.getText());
                rowData.set(8, companyNetWeightField.getText());
                rowData.set(9, deductionField.getText());
                rowData.set(10, qualityDeductionField.getText());
                rowData.set(11, settlementQuantityField.getText());
                rowData.set(12, companyAmountField.getText());
                rowData.set(13, entryDateField.getText());
                rowData.set(14, advancePaymentCheckBox.getText() );
                rowData.set(15, paidMonthCheckBox.getText());
                rowData.set(16, containerField.getText());
                rowData.set(17, commentField.getText());

                // 更新表格数据
                gdExternalPurchaseModule.updateData(rowData);

                // 关闭对话框
                dispose();
            }
        });

        // 设置输入字段的值为原始数据
        dateField.setText(rowData.get(0));
        containerNumberField.setText(rowData.get(1));
        netWeightField.setText(rowData.get(2));
        cargoInfoField.setText(rowData.get(3));
        addressField.setText(rowData.get(4));
        ownerField.setText(rowData.get(5));
        priceField.setText(rowData.get(6));
        amountField.setText(rowData.get(7));
        companyNetWeightField.setText(rowData.get(8));
        deductionField.setText(rowData.get(9));
        qualityDeductionField.setText(rowData.get(10));
        settlementQuantityField.setText(rowData.get(11));
        companyAmountField.setText(rowData.get(12));
        entryDateField.setText(rowData.get(13));
        advancePaymentCheckBox.setText(rowData.get(14));
        paidMonthCheckBox.setText(rowData.get(15));
        containerField.setText(rowData.get(16));
        commentField.setText(rowData.get(17));

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
