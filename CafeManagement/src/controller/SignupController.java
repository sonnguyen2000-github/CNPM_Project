package controller;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignupController implements Initializable{
    @FXML
    TextField usernameTxt, passwordTxt, fullnameTxt, phoneTxt, addressTxt;
    @FXML
    DatePicker dobPkr;
    @FXML
    Button signupBtn, cancelBtn;
    @FXML
    CheckBox customerCk, employeeCk;
    @FXML
    Label checkUsernameLb;
    private DatabaseConnection connection;
    private Statement stmt;

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            close();
        }catch(Exception throwables){
            throwables.printStackTrace();
        }
    }

    public void close() throws Exception{
        connection.close();

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Stage login = new Stage();
        login.setTitle("LOGIN");
        login.setScene(new Scene(loader.load()));
        login.setResizable(false);
        LoginController controller = loader.getController();
        controller.setOnClose();
        login.show();
    }

    @FXML
    public void signup(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        if(usernameTxt.getText().isEmpty() || passwordTxt.getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText("USERNAME và PASSWORD không được để trống.");
            alert.show();
        }else if(checkUsername()){
            try{
                User user = new User();
                user.setUsername(usernameTxt.getText());
                user.setPassword(passwordTxt.getText());
                user.setPriority(customerCk.isSelected() ? 3 : 2);
                user.setFullname(fullnameTxt.getText());
                user.setPhone(phoneTxt.getText());
                user.setDob(Date.valueOf(dobPkr.getValue()));
                user.setAddress(addressTxt.getText());

                ResultSet rs = stmt.executeQuery("SELECT username, password,  priority," +
                                                 " fullname, phone, dob, address FROM public.\"User\"");
                rs.moveToInsertRow();
                rs.updateString(1, user.getUsername());
                rs.updateString(2, user.getPassword());
                rs.updateInt(3, user.getPriority());
                rs.updateString(4, user.getFullname());
                rs.updateInt(5, Integer.parseInt(user.getPhone()));
                rs.updateDate(6, user.getDob());
                rs.updateString(7, user.getAddress());
                rs.insertRow();

                System.out.println("Insert new user successfully");
                close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText("USERNAME đã tồn tại, vui lòng chọn tên khác.");
            alert.show();
        }
    }

    @FXML
    public boolean checkUsername(){
        try{
            String tempUsername = usernameTxt.getText();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"User\" WHERE username = '" + tempUsername + "'");
            if(rs.next()){
                checkUsernameLb.setText("*username đã tồn tại.");
                checkUsernameLb.setTextFill(Paint.valueOf("RED"));
                return false;
            }else{
                checkUsernameLb.setText("*username hợp lệ");
                checkUsernameLb.setTextFill(Paint.valueOf("LIME"));
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    public void switchCk(MouseEvent event){
        CheckBox checkBox = (CheckBox) event.getSource();
        if(checkBox.equals(customerCk)){
            employeeCk.setSelected(!customerCk.isSelected());
        }else if(checkBox.equals(employeeCk)){
            customerCk.setSelected(!employeeCk.isSelected());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();

        dobPkr.setValue(LocalDate.of(1999, 12, 14));
    }
}
