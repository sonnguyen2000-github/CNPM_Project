package controller;

import database.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class TrangThai extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int n = 0;// dung de duyet cac ban
    Panel panelSouth = new Panel();
    Label lbChinh = new Label("Trạng thái bàn", Label.CENTER);
    TextArea txaChinh = new TextArea();
    Button buttXem = new Button("   Xem   ");
    Button buttThoat = new Button("  Quay lại  ");
    Choice chMaBan = new Choice();
    Choice chTenBan = new Choice();
    DatabaseConnection connection;
    java.sql.Statement stmt;
    ResultSet rs;

    public TrangThai(String title){
        super(title);
        lbChinh.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbChinh, BorderLayout.NORTH);
        txaChinh.setEditable(false);
        txaChinh.setText("\tTên Bàn\t\tTrạng thái\n\n");
        add(txaChinh, BorderLayout.CENTER);
        panelSouth.add(buttXem);
        panelSouth.add(buttThoat);
        add(panelSouth, BorderLayout.SOUTH);
        buttXem.addActionListener(this);
        buttThoat.addActionListener(this);
        //
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        //
        setFont(new Font("Arial", Font.PLAIN, 14));
        setLocation(200, 50);
        setSize(400, 400);
        setResizable(false);
        setVisible(true);
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Table\" order by maban;");
            while(rs.next()){
                chMaBan.addItem(rs.getString(1));
                chTenBan.addItem(rs.getString(2));
                n++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttXem)
            Xem();
        if(e.getSource() == buttThoat){
            setVisible(false);
        }
    }

    public void Xem(){
        txaChinh.setText("");
        try{
            rs = stmt.executeQuery("SELECT distinct maban FROM public.\"Order\" order by maban;");
            for(int i = 0; i < n; i++){
                String s = chMaBan.getItem(i);
                boolean flag = false;
                rs.beforeFirst();
                while(rs.next()){
                    if(rs.getString(1).equals(s)){
                        flag = true;
                        break;
                    }
                }
                if(flag)
                    txaChinh.append("\t" + chTenBan.getItem(i) + "\t\t\tĐã dùng");
                else
                    txaChinh.append("\t" + chTenBan.getItem(i) + "\t\t\tCòn trống");
                txaChinh.append("\n");
            }
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
}
