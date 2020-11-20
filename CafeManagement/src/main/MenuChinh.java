package main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuChinh{
    public static void createMenu(String username, int priority){
        //
        String role = "CUSTOMER";
        switch(priority){
            case 1:
                role = "ADMIN";
                break;
            case 2:
                role = "EMPLOYEE";
                break;
            case 3:
        }
        //tao frame cho chuong trinh
        final JFrame fr = new JFrame(role + ": " + username);
        fr.setLayout(new BorderLayout());
        fr.setResizable(false);
        // tao Menu Bar
        MenuBar menu = new MenuBar();
        // tao cac Menu
        Menu menuFile = new Menu("File");
        Menu menuUpdate = new Menu("Cập nhật");
        Menu menuProcess = new Menu("Xử lý");
        Menu menuManage = new Menu("Người dùng");
        Menu menuHelp = new Menu("Help");
        // tao Menu Item cho File
        MenuItem trangThai = new MenuItem("Trạng thái bàn");
        trangThai.addActionListener(ae -> new TrangThai("Trạng thái bàn"));
        menuFile.add(trangThai);
        MenuItem goiMon = new MenuItem("Gọi món");
        goiMon.addActionListener(ae -> new GoiMon("Gọi món"));
        menuFile.add(goiMon);
        if(priority <= 2){
            MenuItem tinhTien = new MenuItem("Tính tiền");
            tinhTien.addActionListener(ae -> new TinhTien("Tính tiền"));
            menuFile.add(tinhTien);
            //
            MenuItem cuoiNgay = new MenuItem("Cuối ngày");
            cuoiNgay.addActionListener(e -> {
                DatabaseConnection connection = new DatabaseConnection();
                connection.connect();
                Statement stmt = connection.getStmt();
                try{
                    stmt.executeUpdate("DELETE FROM public.\"Goimon\";");
                }catch(SQLException throwables){
                    throwables.printStackTrace();
                }
            });
            menuFile.add(cuoiNgay);
        }
        //
        menuFile.addSeparator();//separator ---------
        //
        MenuItem thoat = new MenuItem("Thoát");
        thoat.addActionListener(ae -> Platform.runLater(() -> {
            try{
                FXMLLoader loader = new FXMLLoader(MenuChinh.class.getResource("Login.fxml"));
                Stage stage = new Stage();
                stage.setTitle("LOGIN");
                stage.setScene(new Scene(loader.load(), 533, 344));
                stage.setResizable(false);
                Login controller = loader.getController();
                controller.setOnClose();
                stage.show();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            fr.setVisible(false);
        }));
        menuFile.add(thoat);
        // tao Menu Item cho Update
        MenuItem themBan = new MenuItem("Danh sách bàn");
        themBan.addActionListener(ae -> new ThemBan("Danh sách bàn"));
        MenuItem themThucDon = new MenuItem("Thực đơn");
        themThucDon.addActionListener(ae -> new ThucDon("Thực đơn"));
        menuUpdate.add(themBan);
        menuUpdate.add(themThucDon);
        // tao Menu Item cho UserManager
        MenuItem userInfo = new MenuItem("Thông tin");
        userInfo.addActionListener(ae -> Platform.runLater(() -> {
            try{
                FXMLLoader loader = new FXMLLoader(MenuChinh.class.getResource("EditUser.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Chỉnh sửa thông tin");
                stage.setScene(new Scene(loader.load(), 320, 213));
                stage.setResizable(false);
                EditUser controller = loader.getController();
                controller.setUserId(username);
                stage.show();
                //
            }catch(Exception e){
                e.printStackTrace();
            }
        }));
        if(priority != 3){
            MenuItem userManage = new MenuItem("Quản lý");
            userManage.addActionListener(ae -> Platform.runLater(() -> {
                try{
                    FXMLLoader loader = new FXMLLoader(MenuChinh.class.getResource("ManageUser.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Quản lý người dùng");
                    stage.setScene(new Scene(loader.load(), 167, 242));
                    stage.setResizable(false);
                    //
                    boolean admin = (priority == 1);
                    ManageUser controller = loader.getController();
                    controller.setAdmin(admin);
                    //
                    stage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }));
            menuManage.add(userManage);
        }
        menuManage.add(userInfo);
        // tao Menu Item cho Help
        MenuItem troGiup = new MenuItem("Trợ giúp");
        troGiup.addActionListener(ae -> new TroGiup("Trợ giúp"));
        MenuItem about = new MenuItem("About");
        about.addActionListener(ae -> new About("About"));
        menuHelp.add(troGiup);
        menuHelp.add(about);
        // gan cac Menu vao MenuBar
        menu.add(menuFile);
        menu.add(menuUpdate);
        MenuItem thayDoi = new MenuItem("Huỷ món");
        thayDoi.addActionListener(ae -> new HuyMon("Huỷ món"));
        MenuItem ghepBan = new MenuItem("Ghép bàn");
        ghepBan.addActionListener(ae -> new GhepBan("Ghép bàn"));
        menuProcess.add(thayDoi);
        menuProcess.add(ghepBan);
        menu.add(menuProcess);
        menu.add(menuManage);
        menu.add(menuHelp);
        // gan MenuBar vao khung
        fr.setMenuBar(menu);
        Label lb = new Label("CHƯƠNG TRÌNH QUẢN LÝ CAFÉ 2.0", Label.CENTER);
        lb.setFont(new Font("Times Roman", Font.BOLD, 30));
        //
        Label timer = new Label("", Label.CENTER);
        timer.setFont(new Font("Arial", Font.PLAIN, 20));
        fr.add(lb, BorderLayout.CENTER);
        fr.add(timer, BorderLayout.SOUTH);
        fr.setBounds(0, 0, 800, 570);
        fr.setVisible(true);
        fr.setFont(new Font("Arial", Font.PLAIN, 13));
        // xu ly su kien dong cua so ung dung
        fr.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        //Timer
        Thread timerThread = new Thread(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy HH:mm:ss");
            while(true){
                timer.setText(dateFormat.format(new Date()));
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        timerThread.start();
        //
    }
}

