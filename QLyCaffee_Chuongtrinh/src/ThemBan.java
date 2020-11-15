import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

class ThemBan extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Panel panel2 = new Panel(new GridLayout(3, 2, 10, 10));
    Panel panel3 = new Panel();
    Panel panel4 = new Panel();
    Panel panel5 = new Panel();
    Label lbThemBan = new Label("DANH SACH BAN", Label.CENTER);
    Label lbMaBan = new Label("Ma ban:     ");
    Label lbTenBan = new Label("Ten ban:   ");
    Label lbGhiChu = new Label("Ghi chu:   ");
    Label lbSpace = new Label("   ");
    TextField txtMaBan = new TextField(5);
    TextField txtTenBan = new TextField(20);
    TextField txtGhiChu = new TextField(20);
    Button buttThem = new Button(" Them ");
    Button buttMoi = new Button(" Moi ");
    Button buttNext = new Button("Tiep>");
    Button buttPrev = new Button("<Truoc");
    Button buttFirst = new Button("<<Dau");
    Button butttLast = new Button("Cuoi>>");
    Button buttThoat = new Button(" Quay lai ");
    DatabaseConnection connection;
    java.sql.Statement stmt;
    ResultSet rs;

    public ThemBan(String title){
        super(title);
        lbThemBan.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbThemBan, BorderLayout.NORTH);
        lbMaBan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(lbMaBan);
        txtMaBan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(txtMaBan);
        lbTenBan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(lbTenBan);
        txtTenBan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(txtTenBan);
        lbGhiChu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(lbGhiChu);
        txtGhiChu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panel2.add(txtGhiChu);
        panel5.add(panel2);
        panel4.add(lbSpace);
        panel5.add(panel4);
        add(panel5, BorderLayout.CENTER);
        buttThem.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttThem);
        buttMoi.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttMoi);
        buttFirst.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttFirst);
        buttPrev.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttPrev);
        buttNext.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttNext);
        butttLast.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(butttLast);
        buttThoat.setFont(new Font("Arial", Font.PLAIN, 16));
        panel3.add(buttThoat);
        add(panel3, BorderLayout.SOUTH);
        buttThem.addActionListener(this);
        buttMoi.addActionListener(this);
        buttFirst.addActionListener(this);
        buttPrev.addActionListener(this);
        buttNext.addActionListener(this);
        butttLast.addActionListener(this);
        buttThoat.addActionListener(this);
        //
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        try{
            rs = stmt.executeQuery("SELECT * FROM public.\"Ban\" order by maban;");
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        setLocation(200, 50);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttThem)
            Them();
        if(e.getSource() == buttMoi)
            Moi();
        if(e.getSource() == buttFirst)
            First();
        if(e.getSource() == buttPrev)
            Pre();
        if(e.getSource() == buttNext)
            Next();
        if(e.getSource() == butttLast)
            Last();
        if(e.getSource() == buttThoat)
            setVisible(false);
    }

    public void Them(){
        try{
            rs.moveToInsertRow();
            rs.updateString(1, txtMaBan.getText());
            rs.updateString(2, txtTenBan.getText());
            rs.updateString(3, txtGhiChu.getText());
            rs.insertRow();
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public void Moi(){
        txtMaBan.setText("");
        txtTenBan.setText("");
        txtGhiChu.setText("");
    }

    public void First(){
        try{
            rs.first();
            txtMaBan.setText(rs.getString(1));
            txtTenBan.setText(rs.getString(2));
            txtGhiChu.setText(rs.getString(3));
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public void Pre(){
        try{
            if(!rs.previous()){

                rs.next();
            }
            txtMaBan.setText(rs.getString(1));
            txtTenBan.setText(rs.getString(2));
            txtGhiChu.setText(rs.getString(3));
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Next(){
        try{
            if(!rs.next()){
                rs.previous();
                return;
            }
            txtMaBan.setText(rs.getString(1));
            txtTenBan.setText(rs.getString(2));
            txtGhiChu.setText(rs.getString(3));
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Last(){
        try{
            rs.last();
            txtMaBan.setText(rs.getString(1));
            txtTenBan.setText(rs.getString(2));
            txtGhiChu.setText(rs.getString(3));
        }catch(Exception e){
            System.err.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
}