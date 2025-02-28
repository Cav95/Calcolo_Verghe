package CalcoloTubolare.model.api;

public class Tubolar<T> {

    final private T lenght;
    private int quantity;

    public Tubolar(T lenght, int quantity) {
        this.lenght = lenght;
        this.quantity = quantity;
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
        return "Tubolar [Lenght=" + getLenght() + ", Quantity=" + getQuantity() + "]";
    }

}
