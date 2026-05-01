package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public abstract class FrmBaseManager extends JFrame {

    protected JTable table;
    protected DefaultTableModel tableModel;

    protected JButton btnThem;
    protected JButton btnSua;
    protected JButton btnXoa;
    protected JButton btnXoaTrang;

    protected JPanel pnlInput;
    protected JPanel pnlHeader;

    public FrmBaseManager(String title, String[] columnNames) {
        setTitle(title);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initBaseUI(title, columnNames);
    }

    private void initBaseUI(String titleText, String[] columnNames) {
        // Header
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(true);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel(titleText);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(30, 30, 30));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        // Input Panel (To be filled by subclasses)
        pnlInput = new JPanel();
        pnlInput.setOpaque(true);

        JPanel pnlInputWrapper = new JPanel(new BorderLayout());
        pnlInputWrapper.setOpaque(true);
        pnlInputWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        pnlInputWrapper.add(pnlInput, BorderLayout.CENTER);

        // Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlButtons.setOpaque(true);

        btnThem = createButton("Thêm", new Color(229, 9, 20)); // Netflix Red
        btnSua = createButton("Cập Nhật", new Color(60, 60, 60));
        btnXoa = createButton("Xóa", new Color(60, 60, 60));
        btnXoaTrang = createButton("Làm Mới", new Color(60, 60, 60));

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnXoaTrang);

        pnlInputWrapper.add(pnlButtons, BorderLayout.SOUTH);

        // Combine Top
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(true);
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(pnlInputWrapper, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        // Table
        JPanel pnlTableWrapper = new JPanel(new BorderLayout());
        pnlTableWrapper.setOpaque(true);
        pnlTableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setShowVerticalLines(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        pnlTableWrapper.add(scroll, BorderLayout.CENTER);

        add(pnlTableWrapper, BorderLayout.CENTER);
    }

    protected JTextField createTextField(String placeholder) {
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setOpaque(true);
        JLabel lbl = new JLabel(placeholder);
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTextField txt = new JTextField();
        txt.setPreferredSize(new java.awt.Dimension(150, 35));

        pnlWrapper.add(lbl, BorderLayout.NORTH);
        pnlWrapper.add(txt, BorderLayout.CENTER);
        pnlInput.add(pnlWrapper);
        return txt;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    // Getters for controllers
    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnXoaTrang() {
        return btnXoaTrang;
    }

    public JTable getTable() {
        return table;
    }

}
