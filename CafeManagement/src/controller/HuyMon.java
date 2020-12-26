package controller;

import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HuyMon extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Panel panelCenter = new Panel();
    Panel panelSub = new Panel(new GridLayout(3, 2, 10, 10));
    Panel panelSouth = new Panel();
    Panel panelSpace = new Panel();
    Label lbSpace = new Label(" ");
    Label lbThemMon = new Label("THAY ĐỔI MÓN", Label.CENTER);
    Label lbTenBan = new Label("Tên Bàn:");
    Label lbTenDouong = new Label("Đồ uống:");
    Label lbSoluong = new Label("Số lượng thêm:");
    Choice chBanID = new Choice();
    Choice chTenBan = new Choice();
    Choice chDoUongID = new Choice();
    Choice chTenDoUong = new Choice();
    TextField txtSoLuong = new TextField(20);
    Button buttLuu = new Button("   Lưu   ");
    Button buttHuy = new Button(" Huỷ bàn ");
    Button buttThoat = new Button(" Quay lại ");
    DatabaseConnection connection;
    java.sql.Statement stmt;
    ResultSet rs;

    public HuyMon(String title){
        super(title);
        lbThemMon.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbThemMon, BorderLayout.NORTH);
        panelSub.add(lbTenBan);
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Table\" order by maban;");
            rs.next();
            while(!rs.isAfterLast()){
                chBanID.addItem(rs.getString(1));
                chTenBan.addItem(rs.getString(2));
                rs.next();
            }
        }catch(SQLException se){
            System.err.println("Error: " + se.getMessage());
        }
        panelSub.add(chTenBan);
        panelSub.add(lbTenDouong);
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Drink\";");
            rs.next();
            while(!rs.isAfterLast()){
                chDoUongID.addItem(rs.getString(1));
                chTenDoUong.addItem(rs.getString(2));
                rs.next();
            }
        }catch(SQLException se){
            System.err.println("Error: " + se.getMessage());
        }
        panelSub.add(chTenDoUong);
        panelSub.add(lbSoluong);
        panelSub.add(txtSoLuong);
        panelCenter.add(panelSub);
        panelSpace.add(lbSpace);
        panelCenter.add(panelSpace);
        add(panelCenter, BorderLayout.CENTER);
        panelSouth.add(buttLuu);
        panelSouth.add(buttHuy);
        panelSouth.add(buttThoat);
        add(panelSouth, BorderLayout.SOUTH);
        buttLuu.addActionListener(this);
        buttThoat.addActionListener(this);
        buttHuy.addActionListener(this);
        setFont(new Font("Arial", Font.PLAIN, 13));
        setLocation(200, 50);
        pack();
        setVisible(true);
        setResizable(false);
    }

    // phuong thuc xu ly su kien
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttLuu)
            Luu();
        else if(e.getSource() == buttHuy)
            Huy();
        else if(e.getSource() == buttThoat){
            setVisible(false);
        }
    }

    // ham su thay doi tren du lieu ma nguoi dung nhap vao
    public void Luu(){
        try{
            // rs chua cac ban ghi tren bang Goi_mon
            String Ban = chBanID.getItem(chTenBan.getSelectedIndex());
            String DoUong = chDoUongID.getItem(chTenDoUong.getSelectedIndex());
            rs = stmt.executeQuery("SELECT * FROM public.\"Order\"\n" + "\tWHERE maban ='" + Ban + "'  and madouong ='" + DoUong + "' ;");
            if(!rs.next())
                return;
            rs.updateInt(4, (rs.getInt(4) + (Integer.parseInt(txtSoLuong.getText()))));
            rs.updateRow();// cap nhat lai ban ghi co trong rs va bang Goi_mon
            txtSoLuong.setText("");
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public void Huy(){
        String ban = chBanID.getItem(chTenBan.getSelectedIndex());
        String doUong = chDoUongID.getItem(chTenDoUong.getSelectedIndex());
        try{
            stmt.executeUpdate("DELETE FROM public.\"Order\"\n" + "\tWHERE maban ='" + ban + "'  and madouong ='" + doUong + "' ;");
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }

    }
}
