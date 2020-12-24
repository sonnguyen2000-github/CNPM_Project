package model;

public class Table{
    private String id, name, note;

    public Table(){
    }

    public Table(String id, String name, String note){
        this.id = id;
        this.name = name;
        this.note = note;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }
}
