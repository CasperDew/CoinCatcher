package ru.coincatcher;

import com.badlogic.gdx.Game;
import ru.coincatcher.screen.GameScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        super.dispose();
    }
}
