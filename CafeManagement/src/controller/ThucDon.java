package controller;

import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ThucDon extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Panel panel2 = new Panel(new GridLayout(3, 2, 10, 10));
    Panel panel3 = new Panel();
    Panel panel4 = new Panel();
    Panel panel5 = new Panel();
    Label lbThucDon = new Label("THỰC ĐƠN", Label.CENTER);
    Label lbMaDoUong = new Label("Mã đồ uống:   ");
    Label lbTenDoUong = new Label("Tên đồ uống: ");
    Label lbDonGia = new Label("Đơn giá(x1000 VND):        ");
    Label lbSpace = new Label("   ");
    TextField txtMaDoUong = new TextField(10);
    TextField txtTenDoUong = new TextField(20);
    TextField txtDonGia = new TextField(20);
    Button buttMoi = new Button("  Mới  ");
    Button buttThem = new Button("  Thêm  ");
    Button buttSua = new Button("  Sửa  ");
    Button buttXoa = new Button("  Xoá  ");
    Button buttThoat = new Button("Quay lại");
    Button buttNext = new Button("Tiếp>");
    Button buttPrev = new Button("<Trước");
    Button buttFirst = new Button("<<Đầu");
    Button butttLast = new Button("Cuối>>");
    DatabaseConnection connection;
    Statement stmt;
    ResultSet rs;

    public ThucDon(String title){
        super(title);
        lbThucDon.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbThucDon, BorderLayout.NORTH);
        lbMaDoUong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txtMaDoUong.setEditable(false);
        panel2.add(lbMaDoUong);
        txtMaDoUong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(txtMaDoUong);
        lbTenDoUong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(lbTenDoUong);
        txtTenDoUong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(txtTenDoUong);
        lbDonGia.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(lbDonGia);
        txtDonGia.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(txtDonGia);
        panel5.add(panel2);
        panel4.add(lbSpace);
        panel5.add(panel4);
        add(panel5, BorderLayout.CENTER);
        buttThem.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttMoi);
        buttMoi.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttThem);
        buttSua.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttSua);
        buttFirst.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttFirst);
        buttPrev.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttPrev);
        buttNext.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttNext);
        butttLast.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(butttLast);
        buttXoa.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttXoa);
        buttThoat.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttThoat);

        buttMoi.addActionListener(this);
        buttThem.addActionListener(this);
        buttSua.addActionListener(this);
        buttThoat.addActionListener(this);
        buttFirst.addActionListener(this);
        buttPrev.addActionListener(this);
        buttNext.addActionListener(this);
        butttLast.addActionListener(this);
        buttXoa.addActionListener(this);
        //
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        //
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Drink\";");
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        add(panel3, BorderLayout.SOUTH);
        setLocation(200, 50);
        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == buttMoi){
            Moi();
        }
        if(source == buttThem){
            Them();
        }
        if(source == buttSua){
            try{
                Sua();
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }
        if(source == buttFirst){
            First();
        }
        if(source == buttPrev){
            Pre();
        }
        if(source == buttNext){
            Next();
        }
        if(source == butttLast){
            Last();
        }
        if(source == buttXoa){
            Delete();
        }
        if(source == buttThoat){
            try{
                connection.close();
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
            setVisible(false);
        }

    }

    public void Them(){
        try{
            rs.moveToInsertRow();
            rs.updateString(1, txtMaDoUong.getText());
            rs.updateString(2, txtTenDoUong.getText());
            rs.updateInt(3, Integer.parseInt(txtDonGia.getText()));
            rs.insertRow();
            txtMaDoUong.setEditable(false);
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
        }
    }

    // p thuc sua, chi cho phep sua 2 truong TenDoUong, va truong DonGia
    // khong duoc thay doi truong DoUongID nham tranh sai sot khi cap nhat DL
    public void Sua() throws SQLException{
        rs.updateString(2, txtTenDoUong.getText());
        rs.updateInt(3, Integer.parseInt(txtDonGia.getText()));
        rs.updateRow();

    }

    public void Moi(){
        txtMaDoUong.setEditable(true);
        txtMaDoUong.setText("");
        txtTenDoUong.setText("");
        txtDonGia.setText("");
    }

    public void First(){
        try{
            rs.first();
            txtMaDoUong.setText(rs.getString(1));
            txtTenDoUong.setText(rs.getString(2));
            txtDonGia.setText(Integer.toString(rs.getInt(3)));
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
        }
    }

    public void Pre(){
        try{
            if(!rs.previous()){
                rs.next();
                return;
            }
            txtMaDoUong.setText(rs.getString(1));
            txtTenDoUong.setText(rs.getString(2));
            txtDonGia.setText(Integer.toString(rs.getInt(3)));
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void Next(){
        try{
            if(!rs.next()){
                rs.previous();
                return;
            }
            txtMaDoUong.setText(rs.getString(1));
            txtTenDoUong.setText(rs.getString(2));
            txtDonGia.setText(Integer.toString(rs.getInt(3)));
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void Last(){
        try{
            rs.last();
            txtMaDoUong.setText(rs.getString(1));
            txtTenDoUong.setText(rs.getString(2));
            txtDonGia.setText(Integer.toString(rs.getInt(3)));
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
        }
    }

    public void Delete(){
        try{
            if(rs == null){
                return;
            }
            rs.deleteRow();
            txtMaDoUong.setText("");
            txtTenDoUong.setText("");
            txtDonGia.setText("");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}