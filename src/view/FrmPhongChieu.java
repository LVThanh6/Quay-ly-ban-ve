package view;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import controller.PhongChieu_Controller;
import model.PhongChieu;
import model.Ghe;

public class FrmPhongChieu extends FrmBaseManager {

    private JTextField txtmaphongchieu;
    private JTextField txttenphong;
    private JTextField txtsoluongghe;
    private JTextField txtdinhdangphong;
    
    private JTextField txtSoHang;
    private JTextField txtGheMoiHang;
    private JButton btnAutoGenerate;

    private JTextField txtMaGheSelected;
    private JComboBox<String> cbLoaiGheSelected;
    private JComboBox<String> cbTrangThaiSelected;
    private JButton btnSaveSeat;
    private Ghe currentEditingGhe;

    private JPanel pnlSeatMap;
    private JScrollPane scrollSeatMap;
    private List<JButton> seatButtons = new ArrayList<>();
    
    private PhongChieu_Controller controller;

    public FrmPhongChieu() {
        super("Quản lý Phòng Chiếu", new String[]{"Mã Phòng", "Số Ghế", "Định Dạng"});
        setupExtendedUI();
    }

    private void setupExtendedUI() {
        // Cấu hình lại pnlInput để không bị mất field
        pnlInput.removeAll();
        pnlInput.setLayout(new GridLayout(2, 4, 10, 5)); // 2 hàng, 4 cột để hiển thị đủ
        
        txtmaphongchieu = createTextField("Mã Phòng");
        txttenphong = createTextField("Tên Phòng");
        txtsoluongghe = createTextField("Tổng Ghế");
        txtdinhdangphong = createTextField("Định Dạng");
        
        txtSoHang = createTextField("Số Hàng");
        txtGheMoiHang = createTextField("Ghế mỗi hàng (VD: 10 hoặc 8,10,10,8)");
        
        // Nút bấm
        btnAutoGenerate = new JButton("Tạo Sơ Đồ ⚡");
        btnAutoGenerate.setBackground(new Color(0, 150, 136));
        btnAutoGenerate.setForeground(Color.BLACK);
        btnAutoGenerate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Vùng sơ đồ và chi tiết
        pnlSeatMap = new JPanel();
        pnlSeatMap.setBackground(new Color(240, 240, 240));
        scrollSeatMap = new JScrollPane(pnlSeatMap);
        
        JPanel pnlSeatDetail = new JPanel();
        pnlSeatDetail.setPreferredSize(new Dimension(220, 0));
        pnlSeatDetail.setBorder(BorderFactory.createTitledBorder("CHI TIẾT GHẾ"));
        pnlSeatDetail.setLayout(new BoxLayout(pnlSeatDetail, BoxLayout.Y_AXIS));
        
        txtMaGheSelected = new JTextField();
        txtMaGheSelected.setEditable(false);
        cbLoaiGheSelected = new JComboBox<>(new String[]{"Thường", "VIP"});
        cbTrangThaiSelected = new JComboBox<>(new String[]{"Trống", "Bảo trì"});
        btnSaveSeat = new JButton("Lưu Thay Đổi 💾");
        btnSaveSeat.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSaveSeat.setBackground(new Color(229, 9, 20));
        btnSaveSeat.setForeground(Color.WHITE);
        
        addDetailControl(pnlSeatDetail, "Mã ghế:", txtMaGheSelected);
        addDetailControl(pnlSeatDetail, "Loại ghế:", cbLoaiGheSelected);
        addDetailControl(pnlSeatDetail, "Trạng thái:", cbTrangThaiSelected);
        pnlSeatDetail.add(Box.createVerticalStrut(20));
        pnlSeatDetail.add(btnSaveSeat);

        JPanel pnlBottomLayout = new JPanel(new BorderLayout());
        pnlBottomLayout.add(scrollSeatMap, BorderLayout.CENTER);
        pnlBottomLayout.add(pnlSeatDetail, BorderLayout.EAST);
        pnlBottomLayout.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "SƠ ĐỒ GHẾ PHÒNG CHIẾU", 
            TitledBorder.CENTER, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14)));

        // SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        Component tableComp = ((BorderLayout)getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
        remove(tableComp);
        
        splitPane.setTopComponent(tableComp);
        splitPane.setBottomComponent(pnlBottomLayout);
        splitPane.setDividerLocation(180);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Đảm bảo nút AutoGenerate nằm trong pnlButtons
        pnlButtons.add(btnAutoGenerate);
    }

    private void addDetailControl(JPanel parent, String label, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout(0, 2));
        p.setMaximumSize(new Dimension(200, 50));
        p.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        p.add(lbl, BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        parent.add(p);
    }

    public void setController(PhongChieu_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
        btnAutoGenerate.addActionListener(controller);
        btnSaveSeat.addActionListener(e -> saveCurrentSeat());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String maPC = table.getValueAt(row, 0).toString();
                    controller.loadSeatMap(maPC);
                    txtmaphongchieu.setText(maPC);
                    txtsoluongghe.setText(table.getValueAt(row, 1).toString());
                    txtdinhdangphong.setText(table.getValueAt(row, 2).toString());
                    
                    // Reset các ô nhập sơ đồ khi chọn phòng khác
                    txtSoHang.setText("");
                    txtGheMoiHang.setText("");
                }
            }
        });
    }

    private void saveCurrentSeat() {
        if (currentEditingGhe != null) {
            currentEditingGhe.setLoaiGhe(cbLoaiGheSelected.getSelectedItem().toString());
            currentEditingGhe.setTrangThai(cbTrangThaiSelected.getSelectedItem().toString());
            controller.updateSingleSeat(currentEditingGhe);
        }
    }

    public void displaySeatMap(List<Ghe> dsGhe) {
        pnlSeatMap.removeAll();
        seatButtons.clear();
        currentEditingGhe = null;
        clearSeatDetail();
        
        if (dsGhe.isEmpty()) {
            pnlSeatMap.setLayout(new FlowLayout());
            pnlSeatMap.add(new JLabel("Chưa có sơ đồ ghế. Hãy nhập Số hàng/Ghế mỗi hàng và nhấn 'Tạo Sơ Đồ'."));
        } else {
            int maxRow = 0, maxCol = 0;
            for (Ghe g : dsGhe) {
                maxRow = Math.max(maxRow, g.getHangGhe().charAt(0) - 'A' + 1);
                maxCol = Math.max(maxCol, g.getCotGhe());
            }

            pnlSeatMap.setLayout(new GridLayout(maxRow, maxCol, 5, 5));
            for (int r = 0; r < maxRow; r++) {
                String rowChar = String.valueOf((char)('A' + r));
                for (int c = 1; c <= maxCol; c++) {
                    Ghe found = null;
                    for (Ghe g : dsGhe) {
                        if (g.getHangGhe().equalsIgnoreCase(rowChar) && g.getCotGhe() == c) {
                            found = g; break;
                        }
                    }

                    if (found != null) {
                        JButton btn = createSeatButton(found);
                        pnlSeatMap.add(btn);
                        seatButtons.add(btn);
                    } else {
                        // Nếu hàng này có ít ghế hơn các hàng khác, để trống hoặc placeholder
                        JPanel empty = new JPanel();
                        empty.setOpaque(false);
                        pnlSeatMap.add(empty);
                    }
                }
            }
        }
        pnlSeatMap.revalidate(); pnlSeatMap.repaint();
    }

    private JButton createSeatButton(Ghe ghe) {
        JButton btn = new JButton(ghe.getMaGhe());
        btn.setPreferredSize(new Dimension(55, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        updateSeatButtonStyle(btn, ghe);
        btn.addActionListener(e -> showSeatDetail(ghe, btn));
        return btn;
    }

    private void updateSeatButtonStyle(JButton btn, Ghe ghe) {
        if (ghe.getTrangThai().equals("Bảo trì")) {
            btn.setBackground(new Color(158, 158, 158));
            btn.setForeground(Color.BLACK);
        } else if (ghe.getLoaiGhe().equals("VIP")) {
            btn.setBackground(new Color(255, 160, 0));
            btn.setForeground(Color.BLACK);
        } else {
            btn.setBackground(new Color(33, 150, 243));
            btn.setForeground(Color.BLACK);
        }
    }

    private void showSeatDetail(Ghe ghe, JButton btn) {
        currentEditingGhe = ghe;
        txtMaGheSelected.setText(ghe.getMaGhe());
        cbLoaiGheSelected.setSelectedItem(ghe.getLoaiGhe());
        cbTrangThaiSelected.setSelectedItem(ghe.getTrangThai());
        
        for(JButton b : seatButtons) {
            b.setBorder(BorderFactory.createEmptyBorder());
        }
        btn.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    private void clearSeatDetail() {
        txtMaGheSelected.setText("");
        cbLoaiGheSelected.setSelectedIndex(0);
        cbTrangThaiSelected.setSelectedIndex(0);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        btnAutoGenerate.setVisible(!readOnly);
        btnSaveSeat.setVisible(!readOnly);
    }

    public JTextField getTxtMaPhongChieu() { return txtmaphongchieu; }
    public JTextField getTxtTenPhong() { return txttenphong; }
    public JTextField getTxtSoLuongGhe() { return txtsoluongghe; }
    public JTextField getTxtDinhDangPhong() { return txtdinhdangphong; }
    public JTextField getTxtSoHang() { return txtSoHang; }
    public JTextField getTxtGheMoiHang() { return txtGheMoiHang; }
    public JButton getBtnAutoGenerate() { return btnAutoGenerate; }

    public void updateTable(List<PhongChieu> list) {
        tableModel.setRowCount(0);
        for (PhongChieu item : list) {
            tableModel.addRow(new Object[]{item.getMaPhongChieu(), item.getSoLuongGhe(), item.getDinhDangPhong()});
        }
    }
}
