package ru.coincatcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.coincatcher.assets.GameAssets;
import ru.coincatcher.screen.GameOverScreen;
import ru.coincatcher.screen.GameScreen;
import ru.coincatcher.screen.MenuScreen;
import ru.coincatcher.screen.SettingsScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    public GameAssets assets;
    private Preferences preferences;

    @Override
    public void create() {
        assets = new GameAssets();
        preferences = Gdx.app.getPreferences("CoinCatcher");

        Gdx.graphics.setVSync(isVSyncEnabled());
        showMenu();
    }

    public void changeScreen(Screen next) {
        Screen previous = getScreen();
        setScreen(next);

        if (previous != null) {
            previous.dispose();
        }
    }

    public void showMenu() {
        changeScreen(new MenuScreen(this));
    }

    public void startGame() {
        changeScreen(new GameScreen(this));
    }

    public void showSettings() {
        changeScreen(new SettingsScreen(this));
    }

    public void showGameOver(int score) {
        changeScreen(new GameOverScreen(this, score));
    }

    public boolean isVSyncEnabled() {
        return preferences.getBoolean("vsync", true);
    }

    public void setVSyncEnabled(boolean enabled) {
        preferences.putBoolean("vsync", enabled);
        preferences.flush();
        Gdx.graphics.setVSync(enabled);
    }

    public float getVolume() {
        return preferences.getFloat("volume", 1f);
    }

    public void setVolume(float volume) {
        volume = Math.max(0f, Math.min(1f, volume));
        preferences.putFloat("volume", volume);
        preferences.flush();

    }

    public TextButton createButton(String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        TextureRegionDrawable background = new TextureRegionDrawable(
            new TextureRegion(assets.buttonTexture)
        );

        style.up = background.tint(Color.WHITE);
        style.down = background.tint(Color.GRAY);
        style.over = background.tint(Color.SKY);
        style.font = assets.font;
        style.fontColor = Color.WHITE;

        return new TextButton(text, style);
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        assets.dispose();
    }
}
