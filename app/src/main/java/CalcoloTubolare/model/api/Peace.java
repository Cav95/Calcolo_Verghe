package CalcoloTubolare.model.api;

/**
 * Peace class representing a peace with its code, description, quantity, and material.
 */
public record Peace(String code, String description, Integer quantity, String material , Integer lenght) {

    public Peace(String code, String description, Integer quantity, String material) {
        this(code, description, quantity, material, 0);
    }

}
