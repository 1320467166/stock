package main.model.dialog;

import main.model.table.GdExternalPurchaseModule;
import main.service.DatabaseService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

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
                if (addData(rowData)) {
                    // 将新数据添加到表格
                    gdExternalPurchaseModule.addData(rowData);
                } else {
                    JOptionPane.showMessageDialog(gdExternalPurchaseModule, "添加失败", "警告", JOptionPane.WARNING_MESSAGE);
                }
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

    public Boolean addData(String[] rowData) {
        try {
            // 更新表格数据

            Connection connection = DatabaseService.getConnection();
            PreparedStatement preparedStatement = null;

            // 构建查询语句，使用LIMIT和OFFSET来分页
            String query = "INSERT INTO sys_outer_puchase (order_date, box_no, address, pre_price, contact_no, owner, box_name, `method`, status," +
                    " money, delivery_status, actual_weight, order_weight, weight, amount, price, company_weight, amount_deduct, deduct," +
                    " sum, company_amount, into_date, pre_mark, `month`, box, remark) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(rowData[0]));
            preparedStatement.setString(2, rowData[1]);
            preparedStatement.setString(3, rowData[2]);
            preparedStatement.setString(4, rowData[3]);
            preparedStatement.setString(5, rowData[4]);
            preparedStatement.setString(6, rowData[5]);
            preparedStatement.setString(7, rowData[6]);
            preparedStatement.setString(8, rowData[7]);
            preparedStatement.setString(9, rowData[8]);
            preparedStatement.setString(10, rowData[9]);
            preparedStatement.setString(11, rowData[10]);
            preparedStatement.setString(12, rowData[11]);
            preparedStatement.setString(13, rowData[12]);
            preparedStatement.setString(14, rowData[13]);
            preparedStatement.setString(15, rowData[14]);
            preparedStatement.setString(16, rowData[15]);
            preparedStatement.setString(17, rowData[16]);
            preparedStatement.setString(18, rowData[17]);
//            preparedStatement.setString(19, rowData[18]);
//            preparedStatement.setString(20, rowData[19]);
//            preparedStatement.setString(21, rowData[20]);
//            preparedStatement.setString(22, rowData[21]);
//            preparedStatement.setString(23, rowData[22]);
//            preparedStatement.setString(24, rowData[23]);
//            preparedStatement.setString(25, rowData[24]);
//            preparedStatement.setString(26, rowData[25]);
            preparedStatement.execute();
            return true;
        } catch (Exception exception) {
            return false;
        }

    }
}
