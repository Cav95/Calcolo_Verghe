package verghe.model.api;

/**
 * Tubolar class representing a tubular with its code, length, quantity,
 * description, and material.
 */
public class Tubolar {

    private String code;

    final private int lenght;
    private int quantity;
    private String description;
    private String material;

    /**
     * Constructor for Tubolar.
     * 
     * @param lenght   the length of the tubular.
     * @param quantity the quantity of the tubular.
     */
    public Tubolar(int lenght, int quantity) {
        this.lenght = lenght;
        this.quantity = quantity;
    }

    /**
     * Constructor for Tubolar with additional parameters.
     * 
     * @param code        the code of the tubular.
     * @param lenght      the length of the tubular.
     * @param quantity    the quantity of the tubular.
     * @param description the description of the tubular.
     * @param material    the material of the tubular.
     */
    public Tubolar(String code, int lenght, int quantity, String description, String material) {
        this.code = code;
        this.lenght = lenght;
        this.quantity = quantity;
        this.description = description;
        this.material = material;
    }

    /**
     * Sets the quantity of the tubular.
     * 
     * @param quantity the new quantity of the tubular.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the length of the tubular.
     * 
     * @return the length of the tubular.
     */
    public int getLenght() {
        return lenght;
    }

    /**
     * Gets the quantity of the tubular.
     * 
     * @return the quantity of the tubular.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the code of the tubular.
     * 
     * @return the code of the tubular.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the material of the tubular.
     * 
     * @return the material of the tubular.
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Gets the code of the tubular.
     * 
     * @return the code of the tubular.
     */
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Tubolar [Lenght=" + getLenght() + ", Quantity=" + getQuantity() + "]";
    }

}
