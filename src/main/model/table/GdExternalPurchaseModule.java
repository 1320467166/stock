package main.model.table;

import main.model.dialog.CreateDataDialog;
import main.model.dialog.EditDataDialog;
import main.service.DatabaseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class GdExternalPurchaseModule extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;

    private int currentPage = 1;
    private int rowsPerPage = 10; // 每页显示的行数
    public GdExternalPurchaseModule() throws SQLException {
        setLayout(new BorderLayout());

        // 创建表格模型并设置列名
        tableModel = new DefaultTableModel();
//        tableModel.addColumn("合同号");
//        tableModel.addColumn("预付款金额");
//        tableModel.addColumn("集装箱号");
//        tableModel.addColumn("运输方式");
//        tableModel.addColumn("称磅重量");
//        tableModel.addColumn("订单重量");
//        tableModel.addColumn("重量差额");
//        tableModel.addColumn("是否发货");
//        tableModel.addColumn("发货时间");
//        tableModel.addColumn("是否到达广西");
//        tableModel.addColumn("到达时间");
        tableModel.addColumn("id");
        tableModel.setColumnIdentifiers(new String[]{
                "日期", "柜号", "净重", "集装箱公司/货名", "地址", "货主", "价格", "金额", "公司净重",
                "扣杂", "质量扣款", "结算数量", "公司金额", "进厂日期", "预付标记", "已结月份标记", "集装箱", "备注"
        });
        // 创建表格并设置模型
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);

        // 创建创建按钮
        createButton = new JButton("创建");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建并显示创建数据的对话框
                CreateDataDialog createDataDialog = new CreateDataDialog((JFrame) SwingUtilities.getWindowAncestor(GdExternalPurchaseModule.this), GdExternalPurchaseModule.this);
                createDataDialog.setVisible(true);
            }
        });

        // 创建编辑按钮
        editButton = new JButton("修改");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Vector<String> rowData = (Vector<String>) tableModel.getDataVector().elementAt(selectedRow);
                    EditDataDialog editDataDialog = new EditDataDialog((JFrame) SwingUtilities.getWindowAncestor(GdExternalPurchaseModule.this), GdExternalPurchaseModule.this, rowData);
                    editDataDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(GdExternalPurchaseModule.this, "请选择要编辑的行", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        // 创建删除按钮
        deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(GdExternalPurchaseModule.this,
                            "确认要删除选中行吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        // 用户确认删除，执行删除操作
                        Vector<String> rowData = (Vector<String>) tableModel.getDataVector().elementAt(selectedRow);
                        String id = rowData.get(0);
                        Boolean flag = deleteData(Integer.valueOf(id));
                        if (flag) {
                            tableModel.removeRow(selectedRow);
                            try {
                                loadExternalPurchaseDataFromDatabase();
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(GdExternalPurchaseModule.this, "删除失败", "警告", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(GdExternalPurchaseModule.this, "删除失败", "警告", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(GdExternalPurchaseModule.this, "请选择要删除的行", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // 创建刷新按钮
        refreshButton = new JButton("刷新");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadExternalPurchaseDataFromDatabase();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        // 将表格和按钮面板添加到模块面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        loadExternalPurchaseDataFromDatabase();
        // 隐藏id列
        TableColumn column = table.getColumnModel().getColumn(0);
        table.getColumnModel().removeColumn(column);
    }

    // 添加数据到表格
    public void addData(String[] rowData) {
        tableModel.addRow(rowData);
    }
    // 在 GdExternalPurchaseModule 类中添加以下方法
    public void updateData(Vector<String> updatedRowData) {
        int rowCount = tableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            Vector<String> rowData = (Vector<String>) tableModel.getDataVector().elementAt(i);

            // 假设合同号是唯一标识符，根据合同号来找到要更新的行
            String contract = updatedRowData.get(0);
            if (rowData.get(0).equals(contract)) {
                // 找到要更新的行，将新数据设置到该行
                for (int j = 0; j < rowData.size(); j++) {
                    rowData.set(j, updatedRowData.get(j));
                }
                // 更新表格的显示
                tableModel.fireTableDataChanged();
                break; // 假设合同号是唯一的，找到后可以结束循环
            }
        }
    }

    /**
     * 查询数据库
     * @throws SQLException
     */
    public void loadExternalPurchaseDataFromDatabase() throws SQLException {
        // 计算当前页的偏移量
        int offset = (currentPage - 1) * rowsPerPage;
        Connection connection = DatabaseService.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 构建查询语句，使用LIMIT和OFFSET来分页
            String query = "SELECT * FROM sys_outer_puchase where is_delete = 0 LIMIT ? OFFSET ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rowsPerPage);
            preparedStatement.setInt(2, offset);
            resultSet = preparedStatement.executeQuery();

            // 清空表格数据
            tableModel.setRowCount(0);

            // 将查询结果填充到表格模型中
            while (resultSet.next()) {
                Vector<String> rowData = new Vector<>();
                // 从结果集中获取数据，并添加到rowData中
                //id
                rowData.add(resultSet.getString("id"));
                //柜号
                rowData.add(resultSet.getString("box_no"));
                //净重
                rowData.add(resultSet.getString("weight"));
                //集装箱公司/货名
                rowData.add(resultSet.getString("box_name"));
                //地址
                rowData.add(resultSet.getString("address"));
                //货主
                rowData.add(resultSet.getString("owner"));
                //价格
                rowData.add(resultSet.getString("price"));
                //金额
                rowData.add(resultSet.getString("amount"));
                //公司净重
                rowData.add(resultSet.getString("company_weight"));

                //扣杂
                rowData.add(resultSet.getString("deduct"));
                //质量扣款
                rowData.add(resultSet.getString("amount_deduct"));
                //结算数量
                rowData.add(resultSet.getString("sum"));
                //公司金额
                rowData.add(resultSet.getString("company_amount"));
                //进厂日期
                rowData.add(resultSet.getString("into_date"));
                //预付标记
                rowData.add(resultSet.getString("pre_mark"));
                //已结月份标记
                rowData.add(resultSet.getString("month"));
                //集装箱
                rowData.add(resultSet.getString("box"));
                //备注
                rowData.add(resultSet.getString("remark"));

                rowData.add(resultSet.getString("order_date"));
                // 添加其他列的数据...


                rowData.add(resultSet.getString("pre_price"));
                rowData.add(resultSet.getString("contact_no"));


                rowData.add(resultSet.getString("method"));
                rowData.add(resultSet.getString("status"));
                rowData.add(resultSet.getString("money"));
                rowData.add(resultSet.getString("delivery_status"));
                rowData.add(resultSet.getString("actual_weight"));
                rowData.add(resultSet.getString("order_weight"));

                // 将rowData添加到表格模型中
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理数据库异常
        } finally {
            // 关闭数据库资源
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据库删除
     */
    public Boolean deleteData(Integer id) {
        try {
            // 更新表格数据

            Connection connection = DatabaseService.getConnection();
            PreparedStatement preparedStatement = null;

            // 构建查询语句，使用LIMIT和OFFSET来分页
            String query = "update sys_outer_puchase set is_delete = 1 where id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    // 上一页按钮的事件处理
    public void previousPage() throws SQLException {
        if (currentPage > 1) {
            currentPage--;
            loadExternalPurchaseDataFromDatabase();
        }
    }

    // 下一页按钮的事件处理
    public void nextPage() throws SQLException {
        // 在实际应用中，你需要确定是否还有更多数据可加载
        // 这里简单地假设总是可以加载下一页
        currentPage++;
        loadExternalPurchaseDataFromDatabase();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("广东外调采购模块");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            GdExternalPurchaseModule module = null;
            try {
                module = new GdExternalPurchaseModule();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            frame.add(module);

            // 示例：向表格添加一行数据
            String[] sampleData = {"123456", "5000", "CN12345", "海运", "1000", "2000", "1000", "是", "2023-10-04", "是", "2023-10-05"};
            module.addData(sampleData);

            frame.setVisible(true);
        });
    }
}
