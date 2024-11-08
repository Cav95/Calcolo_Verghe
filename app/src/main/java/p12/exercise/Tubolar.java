package p12.exercise;

public class Tubolar {

    private int lenght;
    private int quantity;
    
    public void setLenght(int lenght) {
        this.lenght = lenght;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getLenght() {
        return lenght;
    }
    public int getQuantity() {
        return quantity;
    }
    @Override
    public String toString() {
        return "Tubolar [getLenght()=" + getLenght() + ", getQuantity()=" + getQuantity() + "]";
    }
    

    
}
