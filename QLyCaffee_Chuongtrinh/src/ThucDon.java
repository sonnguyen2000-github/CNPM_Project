import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class ThucDon extends Frame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Panel panel2 = new Panel(new GridLayout(3, 2, 10, 10));
	Panel panel3 = new Panel();
	Panel panel4 = new Panel();
	Panel panel5 = new Panel();
	Label lbThucDon = new Label("THUC DON", Label.CENTER);
	Label lbMaDoUong = new Label("Ma do uong:   ");
	Label lbTenDoUong = new Label("Ten do uong: ");
	Label lbDonGia = new Label("Don gia(x1000 VND):        ");
	Label lbSpace = new Label("   ");
	TextField txtMaDoUong = new TextField(10);
	TextField txtTenDoUong = new TextField(20);
	TextField txtDonGia = new TextField(20);
	Button buttMoi = new Button("  Moi  ");
	Button buttThem = new Button("  Them  ");
	Button buttSua = new Button("  Sua  ");
	Button buttThoat = new Button("Quay lai");
	Button buttNext = new Button("Tiep>");
	Button buttPrev = new Button("<Truoc");
	Button buttFirst = new Button("<<Dau");
	Button butttLast = new Button("Cuoi>>");
	DatabaseConnection connection;
	java.sql.Statement stmt;
	ResultSet rs;

	public ThucDon(String title) {
		super(title);
		lbThucDon.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lbThucDon, BorderLayout.NORTH);
		lbMaDoUong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
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
		//
		connection = new DatabaseConnection();
		connection.connect();
		stmt = connection.getStmt();
		//
		try{
			rs = stmt.executeQuery("SELECT * FROM public.\"Thucdon\";");
		}catch(SQLException throwables){
			throwables.printStackTrace();
		}
		txtMaDoUong.setEditable(false);
		add(panel3, BorderLayout.SOUTH);
		setLocation(200, 50);
		pack();
		setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttMoi)
			Moi();
		if (e.getSource() == buttThem)
			Them();
		if (e.getSource() == buttSua)
			Sua();
		if (e.getSource() == buttFirst)
			First();
		if (e.getSource() == buttPrev)
			Pre();
		if (e.getSource() == buttNext)
			Next();
		if (e.getSource() == butttLast)
			Last();
		if (e.getSource() == buttThoat) {
			setVisible(false);
		}

	}

	public void Them() {
		try {
			rs.moveToInsertRow();
			rs.updateString(1, txtMaDoUong.getText());
			rs.updateString(2, txtTenDoUong.getText());
			rs.updateInt(3, Integer.parseInt(txtDonGia.getText()));
			rs.insertRow();
			txtMaDoUong.setEditable(false);
		} catch (Exception e) {
			System.err.println("Error: " + e.toString());
		}
	}

	// p thuc sua, chi cho phep sua 2 truong TenDoUong, va truong DonGia
	// khong duoc thay doi truong DoUongID nham tranh sai sot khi cap nhat DL
	public void Sua() {
		try {
			rs.updateString(2, txtTenDoUong.getText());
			rs.updateInt(3, Integer.parseInt(txtDonGia.getText()));
			rs.updateRow();
		} catch (Exception e) {
			System.err.println("Error: " + e.toString());
		}
	}

	public void Moi() {
		txtMaDoUong.setEditable(true);
		txtMaDoUong.setText("");
		txtTenDoUong.setText("");
		txtDonGia.setText("");
	}

	public void First() {
		try {
			rs.first();
			txtMaDoUong.setText(rs.getString(1));
			txtTenDoUong.setText(rs.getString(2));
			txtDonGia.setText(Integer.toString(rs.getInt(3)));
		} catch (Exception e) {
			System.err.println("Error: " + e.toString());
		}
	}

	public void Pre() {
		try {
			if(!rs.previous()){
				rs.next();
				return;
			}
			txtMaDoUong.setText(rs.getString(1));
			txtTenDoUong.setText(rs.getString(2));
			txtDonGia.setText(Integer.toString(rs.getInt(3)));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void Next() {
		try {
			if(!rs.next()){
				rs.previous();
				return;
			}
			txtMaDoUong.setText(rs.getString(1));
			txtTenDoUong.setText(rs.getString(2));
			txtDonGia.setText(Integer.toString(rs.getInt(3)));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void Last() {
		try {
			rs.last();
			txtMaDoUong.setText(rs.getString(1));
			txtTenDoUong.setText(rs.getString(2));
			txtDonGia.setText(Integer.toString(rs.getInt(3)));
		} catch (Exception e) {
			System.err.println("Error: " + e.toString());
		}
	}
}