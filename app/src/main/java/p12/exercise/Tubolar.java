package p12.exercise;

public class Tubolar<T> {

    private T lenght;
    private int quantity;

    
    
    public Tubolar(T lenght , int quantity) {
        this.lenght = lenght;
        this.quantity = quantity;
    }
    public void setLenght(T lenght) {
        this.lenght = lenght;

    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public T getLenght() {
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
