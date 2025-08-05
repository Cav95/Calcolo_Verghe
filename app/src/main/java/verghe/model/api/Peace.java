package verghe.model.api;

/**
 * Peace class representing a peace with its code, description, quantity, and
 * material.
 */
public record Peace(String code, String description, Integer quantity, String material, Integer lenght) {

    /**
     * Constructor for Peace with default length of 0.
     *
     * @param code        the code of the peace
     * @param description the description of the peace
     * @param quantity    the quantity of the peace
     * @param material    the material of the peace
     */
    public Peace(String code, String description, Integer quantity, String material) {
        this(code, description, quantity, material, 0);
    }

}
