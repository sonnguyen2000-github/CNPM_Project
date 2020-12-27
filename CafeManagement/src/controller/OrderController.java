package controller;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Order;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderController implements Initializable{
    @FXML
    TextField orderIdTxt, tableTxt, drinkTxt, amountTxt;
    @FXML
    Button addBtn, saveBtn, cancelBtn;
    @FXML
    TableView<Order> orderTable;
    @FXML
    TableColumn<Order, String> orderIdCol, tableCol, drinkCol;
    @FXML
    TableColumn<Order, Integer> amountCol;
    private Map<String, String> tableConvert, drinkConvert;
    private DatabaseConnection connection;
    private Statement stmt;

    @FXML
    public void add(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        if(tableTxt.getText().isEmpty() || drinkTxt.getText().isEmpty() || orderIdTxt.getText().isEmpty()){
            return;
        }
        try{
            Order order = new Order();
            order.setOrderId(orderIdTxt.getText());
            order.setTableId(tableConvert.get(tableTxt.getText()));
            order.setTable(tableTxt.getText());
            order.setDrinkId(drinkConvert.get(drinkTxt.getText()));
            order.setDrink(drinkTxt.getText());
            order.setAmount(Integer.parseInt(amountTxt.getText()));

            orderTable.getItems().add(order);
            clearTxt();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void clearTxt(){
        orderIdTxt.setText((Integer.parseInt(orderIdTxt.getText()) + 1) + "");
        drinkTxt.clear();
        amountTxt.clear();
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        if(orderTable.getItems().isEmpty()){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("XÁC NHẬN ORDER ?");
        alert.setContentText("");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.isPresent() && optional.get() == ButtonType.OK){
            try{
                ResultSet rs = stmt
                        .executeQuery("SELECT magoi, maban, madouong, soluong\n" + "\tFROM public.\"Order\";");
                rs.moveToInsertRow();
                for(Order order : orderTable.getItems()){
                    rs.updateString(1, order.getOrderId());
                    rs.updateString(2, order.getTableId());
                    rs.updateString(3, order.getDrinkId());
                    rs.updateInt(4, order.getAmount());
                    rs.insertRow();
                }
                clearTxt();
                clearTable();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void clearTable(){
        orderIdTxt.setText(String.valueOf(1));
        tableTxt.clear();
        orderTable.getItems().clear();
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }finally{
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void editTable(MouseEvent event){
        if(orderTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if(event.getButton() == MouseButton.SECONDARY){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("XOÁ THỰC ĐƠN ĐÃ CHỌN ?");
            alert.setContentText("");
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.isPresent() && optional.get() == ButtonType.OK){
                orderTable.getItems().remove(orderTable.getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        orderTable.setItems(FXCollections.observableArrayList());

        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tableCol.setCellValueFactory(new PropertyValueFactory<>("table"));
        drinkCol.setCellValueFactory(new PropertyValueFactory<>("drink"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tableConvert = new LinkedHashMap<>();
        drinkConvert = new LinkedHashMap<>();

        connection = new DatabaseConnection();
        connection.connect();
        stmt = connection.getStmt();
        /**/
        try{
            ResultSet rs = stmt
                    .executeQuery("SELECT maban, tenban, ghichu\n" + "\tFROM public.\"Table\" order by maban;");
            while(rs.next()){
                tableConvert.put(rs.getString(2), rs.getString(1));
            }
            rs = stmt.executeQuery("SELECT madouong, ten, gia\n" + "\tFROM public.\"Drink\";");
            while(rs.next()){
                drinkConvert.put(rs.getString(2), rs.getString(1));
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        /**/
        TextFields.bindAutoCompletion(tableTxt, tableConvert.keySet());
        TextFields.bindAutoCompletion(drinkTxt, drinkConvert.keySet());
    }
}
