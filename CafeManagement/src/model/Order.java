package model;

public class Order{
    private String orderId;
    private String drinkId;
    private String tableId;
    private String drink;
    private String table;
    private int amount;

    public Order(){
    }

    public Order(String orderId, String drinkId, String tableId, int amount){
        this.orderId = orderId;
        this.drinkId = drinkId;
        this.tableId = tableId;
        this.amount = amount;
    }

    public String getDrink(){
        return drink;
    }

    public void setDrink(String drink){
        this.drink = drink;
    }

    public String getTable(){
        return table;
    }

    public void setTable(String table){
        this.table = table;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getDrinkId(){
        return drinkId;
    }

    public void setDrinkId(String drinkId){
        this.drinkId = drinkId;
    }

    public String getTableId(){
        return tableId;
    }

    public void setTableId(String tableId){
        this.tableId = tableId;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}
