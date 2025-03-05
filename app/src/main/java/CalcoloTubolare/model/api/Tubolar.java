package CalcoloTubolare.model.api;

public class Tubolar {

    final private int lenght;
    private int quantity;

    public Tubolar(int lenght, int quantity) {
        this.lenght = lenght;
        this.quantity = quantity;
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
        return "Tubolar [Lenght=" + getLenght() + ", Quantity=" + getQuantity() + "]";
    }

}
