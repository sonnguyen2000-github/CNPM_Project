package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageUser implements Initializable{
    private DatabaseConnection connection;
    private Statement stmt;

    @FXML
    Button refreshBtn, quitBtn;
    @FXML
    ListView<String> userList, employeeList;

    @FXML
    public void listHandler(MouseEvent event){
        ListView<String> listView = (ListView<String>) event.getSource();
        String userId = listView.getSelectionModel().getSelectedItem();
        if(userId == null){
            return;
        }
        try{
            if(event.getButton() == MouseButton.SECONDARY){
                delete(userId);
            }else{
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                    edit(userId);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public void quit(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
        //
        try{
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @FXML
    public void refresh(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        try{
            refesh();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        //
        try{
            refesh();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        //
        employeeList.setDisable(true);
    }

    public void setAdmin(boolean admin){
        employeeList.setDisable(!admin);
    }

    public void delete(String userId) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XÁC NHẬN");
        alert.setHeaderText("XOÁ NGƯỜI DÙNG " + userId + " ?");
        alert.setContentText("");
        Optional<ButtonType> rs = alert.showAndWait();
        if(rs.isPresent() && rs.get() == ButtonType.OK){
            stmt.executeUpdate("DELETE FROM public.\"Nguoidung\"\n" + "WHERE username = '" + userId + "';");
            //
            userList.getItems().remove(userList.getSelectionModel().getSelectedIndex());
        }
    }

    public void edit(String userId) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditUser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Chỉnh sửa thông tin");
        stage.setScene(new Scene(loader.load(), 320, 213));
        stage.setResizable(false);
        EditUser controller = loader.getController();
        controller.setUserId(userId);
        stage.show();


    }

    public void refesh() throws SQLException{
        ObservableList<String> users = FXCollections.observableArrayList();
        ObservableList<String> employees = FXCollections.observableArrayList();
        userList.setItems(users);
        employeeList.setItems(employees);
        //
        ResultSet rs = stmt.executeQuery(
                "SELECT username, password, priority\n" + "FROM public.\"Nguoidung\"\n" + "WHERE priority = 3;");
        while(rs.next()){
            users.add(rs.getString(1));
        }
        //
        rs = stmt.executeQuery(
                "SELECT username, password, priority\n" + "FROM public.\"Nguoidung\"\n" + "WHERE priority = 2;");
        while(rs.next()){
            employees.add(rs.getString(1));
        }
        //
    }
}
