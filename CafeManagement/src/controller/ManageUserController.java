package controller;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable{
    @FXML
    Button refreshBtn, quitBtn;
    @FXML
    ListView<User> customerList, employeeList;
    private DatabaseConnection connection;
    private Statement stmt;

    @FXML
    public void listHandler(MouseEvent event){
        if(event.getSource() instanceof ListView<?> && event.getButton() == MouseButton.SECONDARY){
            ListView<User> listView = (ListView<User>) event.getSource();
            User user = listView.getSelectionModel().getSelectedItem();
            if(user != null){
                listView.getContextMenu().show(listView, event.getX(), event.getY());
            }
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

    public void refesh() throws SQLException{
        ObservableList<User> customers = customerList.getItems();
        ObservableList<User> employees = employeeList.getItems();

        customers.clear();
        employees.clear();

        //
        ResultSet rs = stmt.executeQuery(
                "SELECT username, password, priority, fullname, phone, dob, address\n" + "FROM public.\"User\"\n" +
                "WHERE priority = 3;");
        while(rs.next()){
            User user = new User();
            user.setUsername(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setPriority(3);
            user.setFullname(rs.getString(4));
            user.setPhone(rs.getString(5));
            user.setDob(rs.getDate(6));
            user.setAddress(rs.getString(7));
            customers.add(user);
        }
        //
        rs = stmt.executeQuery(
                "SELECT username, password, priority, fullname, phone, dob, address\n" + "FROM public.\"User\"\n" +
                "WHERE priority = 2;");
        while(rs.next()){
            User user = new User();
            user.setUsername(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setPriority(2);
            user.setFullname(rs.getString(4));
            user.setPhone(rs.getString(5));
            user.setDob(rs.getDate(6));
            user.setAddress(rs.getString(7));
            employees.add(user);
        }
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();

        employeeList.setItems(FXCollections.observableArrayList());
        customerList.setItems(FXCollections.observableArrayList());

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Chỉnh sửa");
        editMenuItem.setOnAction(event -> {
            ListView<User> userListView = (ListView<User>) event.getSource();
            User user = userListView.getSelectionModel().getSelectedItem();
            try{
                edit(user);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
        MenuItem deleteMenuItem = new MenuItem("Xoá");
        deleteMenuItem.setOnAction(event -> {
            ListView<User> userListView = (ListView<User>) event.getSource();
            User user = userListView.getSelectionModel().getSelectedItem();
            try{
                delete(user);
            }catch(SQLException e){
                e.printStackTrace();
            }
        });
        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        employeeList.setContextMenu(contextMenu);
        customerList.setContextMenu(contextMenu);

        try{
            refesh();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        //
        employeeList.setDisable(true);
    }

    public void edit(User user) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EditUser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Chỉnh sửa thông tin");
        stage.setScene(new Scene(loader.load(), 320, 213));
        stage.setResizable(false);
        EditUserController controller = loader.getController();
        controller.setUser(user);
        stage.show();


    }

    public void delete(User user) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XÁC NHẬN");
        alert.setHeaderText("XOÁ NGƯỜI DÙNG " + user.getUsername() + " ?");
        alert.setContentText("");
        Optional<ButtonType> rs = alert.showAndWait();
        if(rs.isPresent() && rs.get() == ButtonType.OK){
            stmt.executeUpdate("DELETE FROM public.\"User\"\n" + "WHERE username = '" + user.getUsername() + "';");
            //
            customerList.getItems().remove(customerList.getSelectionModel().getSelectedIndex());
        }
    }

    public void setAdmin(boolean admin){
        employeeList.setDisable(!admin);
    }
}
