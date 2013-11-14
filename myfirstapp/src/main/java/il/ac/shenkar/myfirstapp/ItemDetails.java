package il.ac.shenkar.myfirstapp;


public class ItemDetails {

    public ItemDetails(String item)
    {
        this.name=item;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    private String name ;


}