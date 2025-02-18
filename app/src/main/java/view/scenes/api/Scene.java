package view.scenes.api;

import javax.swing.JPanel;

/**
 * Interface for the scenes of the application.
 * A scene has a panel and a unique name.
 * The panel is the visual representation of the scene.
 * The name is used to identify the scene.
 */
public interface Scene {

    /**
     * Returns the panel of the scene.
     * @return the panel of the scene
     */
    JPanel getPanel();

    /**
     * Returns the unique name of the scene.
     * @return the unique name of the scene
     */
    String getSceneName();

}
