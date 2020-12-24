package controller;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditUserController implements Initializable{
    private String userId;
    private DatabaseConnection connection;
    private Statement stmt;

    @FXML
    TextField fullName, address, phone;
    @FXML
    DatePicker dob;
    @FXML
    Button deleteBtn, saveBtn, cancelBtn, passwordBtn;

    @FXML
    public void delete(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        fullName.setText("");
        address.setText("");
        phone.setText("");
        //
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        String fullname = fullName.getText();
        LocalDate dob_ = dob.getValue();
        String address_ = address.getText();
        String phone_ = phone.getText();

        try{
            ResultSet rs = stmt.executeQuery(
                    "SELECT username, fullname, dob, address, phone\n" + "FROM public.\"Nguoidung\"\n" + "WHERE username = '" + userId + "';");
            boolean existed = rs.next();
            if(!existed){
                rs.moveToInsertRow();
                rs.updateString(1, userId);
            }
            rs.updateString(2, fullname);
            rs.updateDate(3, Date.valueOf(dob_));
            rs.updateString(4, address_);
            if(!phone_.isEmpty()){
                rs.updateInt(5, Integer.parseInt(phone_));
            }
            if(!existed){
                rs.insertRow();
            }else{
                rs.updateRow();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void changePassword(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ChangePassword.fxml"));
            Stage stage = new Stage();
            stage.setTitle("ĐỔI MẬT KHẨU");
            stage.setScene(new Scene(loader.load(), 190, 150));
            stage.setResizable(false);
            ChangePasswordController controller = loader.getController();
            controller.setUser(userId);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        //
        try{
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void setUserId(String userId) throws SQLException{
        this.userId = userId;
        //
        ResultSet rs = stmt.executeQuery(
                "SELECT fullname, dob, address, phone\n" + "FROM public.\"Nguoidung\"\n" + "WHERE username = '" + userId + "';");
        if(rs.next()){
            fullName.setText(rs.getString(1));
            dob.setValue(rs.getDate(2).toLocalDate());
            address.setText(rs.getString(3));
            phone.setText(rs.getInt(4) + "");
        }
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        //
        dob.setValue(LocalDate.parse("1999-01-01"));
    }
}
