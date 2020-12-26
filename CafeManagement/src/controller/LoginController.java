package controller;

import database.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    //
    @FXML
    Button loginBtn, signupBtn;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    private DatabaseConnection connection;
    private Statement stmt;

    @FXML
    public void loginByClick(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            error();
            return;
        }
        try{
            login();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Có lỗi hệ thống khi đăng nhập.");
        alert.setContentText(
                "Hãy kiểm tra lại tài khoản và mật khẩu của bạn.\nNếu chưa có tài khoản vui lòng chọn chức năng Đăng ký.");
        alert.showAndWait();
    }

    public void login() throws SQLException{
        String userName = username.getText();
        String passWord = password.getText();
        //
        ResultSet rs = stmt.executeQuery(
                "SELECT username, password,  priority, fullname, phone, dob, address\n" + "FROM public.\"User\"\n" +
                "WHERE username = '" + userName + "' and password = '" + passWord + "';");
        if(rs.next()){
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passWord);
            user.setPriority(rs.getInt(3));
            user.setFullname(rs.getString(4));
            user.setPhone(rs.getString(5));
            user.setDob(rs.getDate(6));
            user.setAddress(rs.getString(7));
            start(user);
            close();
        }else{
            error();
        }
    }

    public void start(User user){
        MenuChinh.createMenu(user);
        Platform.setImplicitExit(false);
    }

    public void close() throws SQLException{
        connection.close();
        //
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
        //
    }

    @FXML
    public void loginByKey(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                error();
                return;
            }
            try{
                login();
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }
    }

    @FXML
    public void signup(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Signup.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Signing up");
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.show();

            close();
        }catch(Exception e){
            e.printStackTrace();
        }

        //        String userName_ = username.getText();
        //        String password_ = password.getText();
        //        if(userName_.isEmpty() || password_.isEmpty()){
        //            error();
        //        }else{
        //            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        //            dialog.setTitle("Người dùng mới");
        //            dialog.setHeaderText("");
        //            dialog.setContentText("Nhấn nút tương ứng nếu bạn là khách hàng hoặc nhân viên.");
        //            //
        //            ButtonType employee = new ButtonType("Nhân viên");
        //            ButtonType customer = new ButtonType("Khách hàng");
        //            dialog.getButtonTypes().clear();
        //            dialog.getButtonTypes().addAll(customer, employee);
        //            //
        //            Optional<ButtonType> result = dialog.showAndWait();
        //            int priority_ = 0;
        //            if(result.isPresent()){
        //                if(result.get().equals(customer)){
        //                    priority_ = 3;
        //                }else{
        //                    if(result.get().equals(employee)){
        //                        priority_ = 2;
        //                    }
        //                }
        //            }
        //            //
        //            try{
        //                ResultSet rs = stmt.executeQuery(
        //                        "SELECT username, password, priority, fullname, dob, address, phone\n" +
        //                        "FROM public.\"User\";");
        //                rs.moveToInsertRow();
        //                rs.updateString(1, userName_);
        //                rs.updateString(2, password_);
        //                rs.updateInt(3, priority_);
        //                rs.updateString(4, "");
        //                rs.updateDate(5, Date.valueOf("1999-01-01"));
        //                rs.updateString(6, "");
        //                rs.updateInt(7, 0);
        //                rs.insertRow();
        //            }catch(Exception e){
        //                error();
        //                e.printStackTrace();
        //            }
        //        }
    }

    public void setOnClose(){
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(-1);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
    }
}
