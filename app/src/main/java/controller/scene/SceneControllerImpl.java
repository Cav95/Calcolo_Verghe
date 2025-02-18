package controller.scene;

import view.View;
import view.scenes.MainMenuScene;

/**
 * Class that implements {@link SceneController} to
 * controll change scene in other controller.
 */
public class SceneControllerImpl implements SceneController {

    private final View mainView;

    /**
     * Creates a new main menu controller.
     * 
     * @param mainView the main view of the application.
     */
    public SceneControllerImpl(final View mainView) {
        this.mainView = mainView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToMainMenuScene() {
        this.mainView.changeScene(new MainMenuScene(new MainMenuControllerImpl(mainView)));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void exitGame() {
        System.exit(0);
    }

    /**
     * Method to get mainView.
     * 
     * @return the main view of the application
     */
    protected View getView() {
        return this.mainView;
    }

}
