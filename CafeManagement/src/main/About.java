package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends Frame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Label lbAbout = new Label("ABOUT", Label.CENTER);
    Label lbChuongTrinh = new Label("Café Management V2.0", Label.CENTER);
    Label lb1 = new Label("Chương trình được thiết kế bởi:", Label.CENTER);
    Label lb2 = new Label("GVHD: ", Label.CENTER);
    Label lb3 = new Label("SVTH: ", Label.CENTER);
    Label lb4 = new Label("           ", Label.CENTER);
    Label lb5 = new Label("             ", Label.CENTER);
    Button buttThoat = new Button("  Quay lại  ");

    public About(String title){
        Panel panelCenter = new Panel(new GridLayout(6, 1));
        lbAbout.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(lbAbout, BorderLayout.NORTH);
        lbChuongTrinh.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelCenter.add(lbChuongTrinh);
        lb1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelCenter.add(lb1);
        lb2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelCenter.add(lb2);
        lb3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelCenter.add(lb3);
        lb4.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelCenter.add(lb4);
        lb5.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        panelCenter.add(lb5);
        add(panelCenter, BorderLayout.CENTER);
        buttThoat.addActionListener(this);
        add(buttThoat, BorderLayout.SOUTH);
        setBounds(200, 100, 400, 250);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == buttThoat)
            setVisible(false);
    }
}
