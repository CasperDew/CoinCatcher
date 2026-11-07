package ru.coincatcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.coincatcher.assets.GameAssets;
import ru.coincatcher.audio.AudioManager;
import ru.coincatcher.config.GameSettings;
import ru.coincatcher.screen.GameOverScreen;
import ru.coincatcher.screen.GameScreen;
import ru.coincatcher.screen.MenuScreen;
import ru.coincatcher.screen.SettingsScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    public GameAssets assets;
    public GameSettings settings;
    public AudioManager audio;

    @Override
    public void create() {
        assets = new GameAssets();
        settings = new GameSettings();
        audio = new AudioManager(assets, settings);

        Gdx.graphics.setVSync(settings.isVSyncEnabled());
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
        return settings.isVSyncEnabled();
    }

    public void setVSyncEnabled(boolean enabled) {
        settings.setVSyncEnabled(enabled);
    }

    public float getVolume() {
        return settings.getVolume();
    }

    public void setVolume(float volume) {
        settings.setVolume(volume);
        audio.updateVolume();
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
        audio.dispose();
        assets.dispose();
        super.dispose();
    }
}
