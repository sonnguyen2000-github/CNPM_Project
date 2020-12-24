package object;

import java.util.Date;

public class History{
    private String username, orderId;
    private Date date;

    public History(){
    }

    public History(String username, String orderId, Date date){
        this.username = username;
        this.orderId = orderId;
        this.date = date;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
