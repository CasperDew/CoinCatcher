package ru.coincatcher.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GameSettings {
    private Preferences preferences;

    public GameSettings() {
        preferences = Gdx.app.getPreferences("CoinCatcher");
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
        preferences.putFloat(
            "volume",
            MathUtils.clamp(volume, 0f, 1f)
        );
        preferences.flush();
    }

    public int getHighScore() {
        return preferences.getInteger("highScore", 0);
    }

    public boolean updateHighScore(int score) {
        if(score <= getHighScore()) {
            return false;
        }
        preferences.putInteger("highScore", score);
        preferences.flush();
        return true;
    }
}
