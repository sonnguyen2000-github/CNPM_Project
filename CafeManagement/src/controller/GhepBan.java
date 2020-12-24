package controller;

import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GhepBan extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Panel panelSub = new Panel(new GridLayout(5, 2));
    Panel panelSpace = new Panel();
    Panel panelCenter = new Panel();
    Panel panelSouth = new Panel();
    Label lbSpace = new Label("  ");
    Label lbChinh = new Label("GHÉP BÀN", Label.CENTER);
    Label lbBanGhep1 = new Label("Bàn cần ghép 1:     ");
    Label lbBanGhep2 = new Label("Bàn cần ghép 2:     ");
    Label lbBanGhep3 = new Label("Bàn sau khi ghép:   ");
    Choice chBanGhep1 = new Choice();
    Choice chMaBan1 = new Choice();
    Choice chMaBan2 = new Choice();
    Choice chBanGhep2 = new Choice();
    Choice chMaBan3 = new Choice();
    Choice chBanGhep3 = new Choice();
    Button buttLuu = new Button("Lưu");
    Button buttThoat = new Button("Quay lại");
    DatabaseConnection connection;
    java.sql.Statement stmt;
    ResultSet rs;

    public GhepBan(String title){
        super(title);
        lbChinh.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbChinh, BorderLayout.NORTH);
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Ban\" order by maban;");
            rs.next();
            while(!rs.isAfterLast()){
                chMaBan1.addItem(rs.getString(1));
                chBanGhep1.addItem(rs.getString(2));
                rs.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Ban\" order by maban;");
            rs.next();
            while(!rs.isAfterLast()){
                chMaBan2.addItem(rs.getString(1));
                chBanGhep2.addItem(rs.getString(2));
                rs.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Ban\" order by maban;");
            rs.next();
            while(!rs.isAfterLast()){
                chMaBan3.addItem(rs.getString(1));
                chBanGhep3.addItem(rs.getString(2));
                rs.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        panelSub.add(lbBanGhep1);
        panelSub.add(chBanGhep1);
        panelSub.add(new Label());
        panelSub.add(new Label());
        panelSub.add(lbBanGhep2);
        panelSub.add(chBanGhep2);
        panelSub.add(new Label());
        panelSub.add(new Label());
        panelSub.add(lbBanGhep3);
        panelSub.add(chBanGhep3);
        panelSpace.add(lbSpace);
        panelCenter.add(panelSub);
        panelCenter.add(panelSpace);
        add(panelCenter, BorderLayout.CENTER);
        panelSouth.add(buttLuu);
        panelSouth.add(buttThoat);
        add(panelSouth, BorderLayout.SOUTH);
        buttLuu.addActionListener(this);
        buttThoat.addActionListener(this);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setLocation(250, 50);
        setSize(400, 250);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttLuu)
            Luu();
        if(e.getSource() == buttThoat){
            setVisible(false);
        }
    }

    public void Luu(){
        try{
            String Ban1 = chMaBan1.getItem(chBanGhep1.getSelectedIndex());
            String Ban2 = chMaBan2.getItem(chBanGhep2.getSelectedIndex());
            String Ban3 = chMaBan3.getItem(chBanGhep3.getSelectedIndex());
            rs = stmt.executeQuery("SELECT * FROM public.\"Goimon\" where maban = '" + Ban1 + "' or " + "maban = '" + Ban2 + "' order by maban ;");
            // bien k dung de luu lai so ban ghi cau rs truoc khi co su thay doi
            // bien n dung de khoi tao ma goi cho ban ghi moi khi trong rs co hai
            // ban ghi co DoUongID giong nhau, voi so luong la tong so luong cua
            // hai ban ghi tren.
            while(rs.next()){
                rs.updateString(2, Ban3);
                rs.updateRow();
            }

        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}
