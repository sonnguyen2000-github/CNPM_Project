package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

class TinhTien extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String code;
    long sum;
    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    TextArea taChinh = new TextArea();
    TextField userName = new TextField("Khách hàng");
    Label lbTinhTien = new Label("TÍNH TIỀN");
    Label lbNhap = new Label("Nhập mã bàn:                 ");
    Label lbTong = new Label("Tổng số tiền:                ");
    Choice chNhap = new Choice();
    Button buttTinh = new Button("Tính");
    Button buttTraBan = new Button("Trả Bàn");
    Button buttThoat = new Button("Quay lại");
    Button buttIn = new Button("Hoá đơn");
    Label lbKqua = new Label();
    DatabaseConnection connection;
    java.sql.Statement stmt;
    ResultSet rs;

    public TinhTien(String title){
        super(title);
        setLayout(gb);
        taChinh.setEditable(false);
        //
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        try{
            rs = stmt.executeQuery("SELECT maban\n" + "\tFROM public.\"Ban\" order by maban;");
            rs.next();
            while(!rs.isAfterLast()){
                chNhap.addItem(rs.getString(1));
                rs.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        lbTinhTien.setFont(new Font("Tahoma", Font.BOLD, 20));
        addComponent(lbTinhTien, 1, 2, 1, 1);
        addComponent(userName, 1, 3, 1, 1);
        addComponent(lbNhap, 2, 1, 1, 1);
        addComponent(chNhap, 2, 2, 1, 1);
        addComponent(lbTong, 3, 1, 1, 1);
        addComponent(lbKqua, 3, 2, 1, 1);
        addComponent(taChinh, 4, 1, 6, 4);
        addComponent(buttTinh, 10, 1, 1, 1);
        addComponent(buttTraBan, 10, 2, 1, 1);
        addComponent(buttThoat, 10, 3, 1, 1);
        addComponent(buttIn, 10, 4, 1, 1);
        buttTinh.addActionListener(this);
        buttTraBan.addActionListener(this);
        buttThoat.addActionListener(this);
        buttIn.addActionListener(this);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setLocation(200, 50);
        pack();
        setVisible(true);
        setResizable(false);
    }

    public void addComponent(Component c, int row, int col, int nrow, int ncol){
        gbc.gridy = row;
        gbc.gridx = col;
        gbc.gridheight = nrow;
        gbc.gridwidth = ncol;
        gb.setConstraints(c, gbc);
        add(c);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == buttTinh){
            tong();
        }else{
            if(ae.getSource() == buttThoat){
                setVisible(false);
            }else{
                if(ae.getSource() == buttTraBan){
                    xoa();
                }else{
                    if(ae.getSource() == buttIn){
                        try{
                            in();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void tong(){
        String s = chNhap.getItem(chNhap.getSelectedIndex());
        sum = 0;

        try{
            rs = stmt.executeQuery(
                    "SELECT Goimon.maban, Thucdon.gia, Goimon.soluong, Thucdon.gia*Goimon.soluong as tong,Thucdon.ten as menu FROM public.\"Thucdon\" as thucdon INNER JOIN (public.\"Ban\" as ban INNER JOIN public.\"Goimon\" as goimon ON ban.maban = goimon.maban) ON thucdon.madouong = Goimon.madouong order by ban.maban");
            taChinh.setText("        Tên đồ uống\t\t  Đơn giá\t\t    Số lượng\t\t Giá\n\n");
            while(rs.next()){
                if(rs.getString(1).equals(s)){
                    sum += (rs.getInt(4));
                    taChinh.append("  " + String.format("%18s", rs.getString(5)) + "\t\t");
                    taChinh.append("  " + rs.getString(2) + ".000  VND\t\t\t");
                    taChinh.append("  " + rs.getString(3) + "\t\t");
                    taChinh.append(" " + rs.getInt(2) * rs.getInt(3) + ".000 VND");
                    taChinh.append("\n");
                }
            }
            lbKqua.setText(sum + ".000 VND");
        }catch(SQLException e){
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        // xoa tat ca cac ban ghi do trong Goimon
    }

    public void xoa(){
        String s = chNhap.getItem(chNhap.getSelectedIndex());
        try{
            rs = stmt.executeQuery("SELECT distinct maban,magoi \n" + "\tFROM public.\"Goimon\";");
            while(rs.next()){
                if(rs.getString(1).equals(s)){
                    rs.deleteRow();
                }
            }
            //
            String username = userName.getText();
            Random rand = new Random();
            StringBuilder key = new StringBuilder();
            for(int i = 0; i < 3; i++){
                key.append(rand.nextInt(i + 8));
            }
            Calendar today = Calendar.getInstance();
            String code = username + key + today.get(Calendar.DAY_OF_MONTH) + today.get(Calendar.MONTH);
            stmt.executeUpdate(
                    "INSERT INTO public.\"Lichsu\"(\n" + "\tusername, id_order, date)\n" + "\tVALUES ('" + username + "', '" + code + "', '" + today.getTime() + "');");

            lbKqua.setText("");
            taChinh.setText("");
            //
        }catch(SQLException se){
            se.printStackTrace();
            System.err.println("Error: " + se.getMessage());
        }
    }

    public void in() throws IOException{
        FileDialog saver = new FileDialog(this, "HOÁ ĐƠN", FileDialog.SAVE);
        File hoadon = new File(
                "E:\\OneDrive - Hanoi University of Science and Technology\\20201\\CNPM\\BTL\\hoadon.txt");
        FileWriter writer = new FileWriter(hoadon);
        //
        String username = userName.getText();
        //
        writer.write(
                "Khách hàng: " + username + "\nMã order: " + code + "\n" + taChinh.getText() + "\t\t\t\t\t\t\t\t\t   Tống: " + lbKqua.getText() + "\n\t\t\t\t\t" + new Date().toString());
        writer.close();
        saver.setFile(hoadon.getAbsolutePath());
        saver.setDirectory(hoadon.getPath());
        saver.setVisible(true);
    }
    /*
     *
     */
}
