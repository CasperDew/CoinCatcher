package ru.coincatcher.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import ru.coincatcher.assets.GameAssets;
import ru.coincatcher.config.GameSettings;

public class AudioManager implements Disposable {
    private final Sound coinSound;
    private final Sound hitSound;
    private final Music backgroundMusic;
    private final GameSettings settings;

    public AudioManager(
        GameAssets assets,
        GameSettings settings
    ) {
        this.settings = settings;

        coinSound = assets.getCoinSound();
        hitSound = assets.getHitSound();
        backgroundMusic = assets.getBackgroundMusic();

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(settings.getVolume());
    }

    public void playCoin() {
        coinSound.play(settings.getVolume());
    }

    public void playHit() {
        hitSound.play(settings.getVolume());
    }

    public void startMusic() {
        backgroundMusic.setVolume(settings.getVolume());

        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }
    public void pauseMusic() {
        backgroundMusic.pause();
    }

    public void updateVolume() {
        backgroundMusic.setVolume(settings.getVolume());
    }
    @Override
    public void dispose() {

    }
}
