package CalcoloTubolare.model.api;

public class Tubolar {

    private String code;

    final private int lenght;
    private int quantity;
    private String description;
    private String material;

    public Tubolar(String code, int lenght, int quantity) {
        this.code = code;
        this.lenght = lenght;
        this.quantity = quantity;
    }

    public Tubolar(String code, int lenght, int quantity, String description, String material) {
        this.code = code;
        this.lenght = lenght;
        this.quantity = quantity;
        this.description = description;
        this.material = material;
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

    public String getDescription() {
        return description;
    }

    public String getMaterial() {
        return material;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Tubolar [Lenght=" + getLenght() + ", Quantity=" + getQuantity() + "]";
    }

}
