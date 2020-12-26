package controller;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable{
    @FXML
    PasswordField oldPassword, newPassword;
    @FXML
    Button saveBtn, cancelBtn;
    private DatabaseConnection connection;
    private Statement stmt;
    private User user;

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        String old_ = oldPassword.getText();
        String new_ = newPassword.getText();
        if(old_.isEmpty() || new_.isEmpty()){
            return;
        }
        try{
            ResultSet rs = stmt.executeQuery(
                    "SELECT username, password\n" + "FROM public.\"User\"\n" + "WHERE username = '" +
                    user.getUsername() + "';");
            if(rs.next()){
                String password = rs.getString(2);
                if(!old_.equals(password)){
                    throw new Exception("\nSai cmnr");
                }
                rs.updateString(2, new_);
                rs.updateRow();

                user.setPassword(new_);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Đổi mật khẩu thành công.");
                alert.show();
                cancel(event);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            error();
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

    public void error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("LỖI");
        alert.setHeaderText("");
        alert.setContentText("Mật khẩu không đúng. Vui lòng thử lại.");
        alert.showAndWait();
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
    }
}
