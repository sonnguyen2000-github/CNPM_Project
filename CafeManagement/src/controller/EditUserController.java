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
import model.User;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditUserController implements Initializable{
    @FXML
    TextField fullName, address, phone;
    @FXML
    DatePicker dob;
    @FXML
    Button deleteBtn, saveBtn, cancelBtn, passwordBtn;
    private DatabaseConnection connection;
    private Statement stmt;
    private User user;

    @FXML
    public void delete(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        fullName.setText("");
        address.setText("");
        phone.setText("");
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
                    "SELECT username, fullname, dob, address, phone\n" + "FROM public.\"User\"\n" +
                    "WHERE username = '" + user.getUsername() + "';");
            if(rs.next()){
                rs.updateString(2, fullname);
                rs.updateDate(3, Date.valueOf(dob_));
                rs.updateString(4, address_);
                rs.updateString(5, phone_);
                rs.updateRow();

                user.setFullname(fullname);
                user.setPhone(phone_);
                user.setDob(Date.valueOf(dob_));
                user.setAddress(address_);
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
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            ChangePasswordController controller = loader.getController();
            controller.setUser(user);
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

    public void setUser(User user){
        this.user = user;
        fullName.setText(user.getFullname());
        phone.setText(user.getPhone());
        dob.setValue(user.getDob().toLocalDate());
        address.setText(user.getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
    }
}
