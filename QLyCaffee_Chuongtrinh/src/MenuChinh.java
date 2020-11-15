import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuChinh{
    public static void main(String[] args){
        createMenu();
    }

    private static void createMenu(){
        // tao frame cho chuong trinh
        final Frame fr = new Frame("Chuong trinh quan ly quan Cafe");
        fr.setLayout(new BorderLayout());
        // tao Menu Bar
        MenuBar menu = new MenuBar();
        // tao cac Menu
        Menu menuFile = new Menu("File");
        Menu menuUpdate = new Menu("Cap Nhat");
        Menu menuProcess = new Menu("Xu Ly");
        Menu menuHelp = new Menu("Help");
        // tao Menu Item cho File
        MenuItem trangThai = new MenuItem("Trang thai Ban");
        trangThai.addActionListener(ae -> new TrangThai("Trang Thai Ban"));
        MenuItem goiMon = new MenuItem("Goi Mon");
        goiMon.addActionListener(ae -> new GoiMon("Goi Mon"));
        MenuItem tinhTien = new MenuItem("Tinh Tien");
        tinhTien.addActionListener(ae -> new TinhTien("Tinh Tien"));
        MenuItem thoat = new MenuItem("Thoat");
        thoat.addActionListener(ae -> System.exit(0));
        menuFile.add(trangThai);
        menuFile.add(goiMon);
        menuFile.add(tinhTien);
        menuFile.addSeparator();//separator |
        menuFile.add(thoat);
        // tao Menu Item cho Update
        MenuItem themBan = new MenuItem("Danh sach Ban");
        themBan.addActionListener(ae -> new ThemBan("Danh sach Ban"));
        MenuItem themThucDon = new MenuItem("Thuc Don");
        themThucDon.addActionListener(ae -> new ThucDon("Thuc Don"));
        menuUpdate.add(themBan);
        menuUpdate.add(themThucDon);
        // tao Menu Item cho Process
        // tao Menu Item cho Help
        MenuItem troGiup = new MenuItem("Tro Giup");
        troGiup.addActionListener(ae -> new TroGiup("Tro Giup"));
        MenuItem about = new MenuItem("About");
        about.addActionListener(ae -> new About("About"));
        menuHelp.add(troGiup);
        menuHelp.add(about);
        // gan cac Menu vao MenuBar
        menu.add(menuFile);
        menu.add(menuUpdate);
        MenuItem thayDoi = new MenuItem("Huy Mon");
        thayDoi.addActionListener(ae -> new HuyMon("Huy Mon"));
        MenuItem ghepBan = new MenuItem("Ghep Ban");
        ghepBan.addActionListener(ae -> new GhepBan("Ghep ban"));
        menuProcess.add(thayDoi);
        menuProcess.add(ghepBan);
        menu.add(menuProcess);
        menu.add(menuHelp);
        // gan MenuBar vao khung
        fr.setMenuBar(menu);
        Label lb = new Label("CHUONG TRINH QUAN LY QUAN CAFE", Label.CENTER);
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
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
    }
}
