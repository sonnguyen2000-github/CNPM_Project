import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class GoiMon extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Panel panelCenter = new Panel();
    Panel panelSub = new Panel(new GridLayout(4, 3, 10, 10));
    Panel panelSouth = new Panel();
    Panel panelSpace = new Panel();
    Label lbSpace = new Label(" ");
    Label lbGoiMon = new Label("GOI MON", Label.CENTER);
    Label lbMaGoi = new Label("Luot goi:");
    Label lbBan = new Label("Ban:");
    Label lbDoUong = new Label("Do uong:");
    Label lbSoLuong = new Label("So luong:");
    TextField txtMaGoi = new TextField("Vd: 23 la Ban 2,Luot goi 3");
    Choice chBan = new Choice();
    Choice chTenBan = new Choice();
    Choice chDoUong = new Choice();
    Choice chTenDoUong = new Choice();
    TextField txtSoLuong = new TextField();
    Button buttLuu = new Button("   Luu   ");
    Button buttThoat = new Button(" Quay lai ");
    java.sql.Statement stmt;
    DatabaseConnection connection;
    ResultSet rs;

    public GoiMon(String title){
        super(title);
        lbGoiMon.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbGoiMon, BorderLayout.NORTH);
        panelSub.add(lbMaGoi);
        panelSub.add(txtMaGoi);
        panelSub.add(lbBan);
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Ban\" order by maban");
            rs.next();
            while(!rs.isAfterLast()){
                chBan.addItem(rs.getString(1));
                chTenBan.addItem(rs.getString(2));
                rs.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Thucdon\"");
            rs.next();
            while(!rs.isAfterLast()){
                chDoUong.addItem(rs.getString(1));
                chTenDoUong.addItem(rs.getString(2));
                rs.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        panelSub.add(chTenBan);
        panelSub.add(lbDoUong);
        panelSub.add(chTenDoUong);
        panelSub.add(lbSoLuong);
        panelSub.add(txtSoLuong);
        panelCenter.add(panelSub);
        panelSpace.add(lbSpace);
        panelCenter.add(panelSpace);
        add(panelCenter, BorderLayout.CENTER);
        panelSouth.add(buttLuu);
        add(panelSouth, BorderLayout.SOUTH);
        panelSouth.add(buttThoat);
        add(panelSouth, BorderLayout.SOUTH);
        buttLuu.addActionListener(this);
        buttThoat.addActionListener(this);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setLocation(180, 50);
        setSize(700, 250);
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
            rs = stmt.executeQuery("SELECT * FROM public.\"Goimon\"");
            rs.moveToInsertRow();
            rs.updateString(1, txtMaGoi.getText());
            int n = chTenBan.getSelectedIndex();
            rs.updateString(2, chBan.getItem(n));
            int m = chTenDoUong.getSelectedIndex();
            rs.updateString(3, chDoUong.getItem(m));
            rs.updateInt(4, Integer.parseInt(txtSoLuong.getText()));
            rs.insertRow();
            txtMaGoi.setText("");
            txtSoLuong.setText("");
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
        }
    }
}
