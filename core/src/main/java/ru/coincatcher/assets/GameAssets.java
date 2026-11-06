package ru.coincatcher.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameAssets implements Disposable {

    // icons
    public static final String PLAYER = "icon/player.png";
    public static final String COIN = "icon/coin.png";
    public static final String METEOR = "icon/meteor.png";
    public static final String BACKGROUND = "icon/background.png";

    // audio
    private static final String COIN_SOUND = "audio/coin.wav";
    private static final String HIT_SOUND = "audio/hit.wav";
    private static final String BACKGROUND_MUSIC = "audio/background.ogg";

    private final AssetManager manager = new AssetManager();

    public final BitmapFont font;
    public final Animation<TextureRegion> coinAnimation;
    public final Texture buttonTexture;

    public GameAssets() {
        manager.load(PLAYER, Texture.class);
        manager.load(COIN, Texture.class);
        manager.load(METEOR, Texture.class);
        manager.load(BACKGROUND, Texture.class);
        manager.load(COIN_SOUND, Sound.class);
        manager.load(HIT_SOUND, Sound.class);
        manager.load(BACKGROUND_MUSIC, Music.class);
        manager.finishLoading();

        for (String path : new String[]{
            PLAYER, COIN, METEOR, BACKGROUND
        }) {
            manager.get(path, Texture.class).setFilter(
                Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest
            );
        }

        // Кириллица
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/game.ttf")
            );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 28;
        parameter.characters =
            FreeTypeFontGenerator.DEFAULT_CHARS +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

        font = generator.generateFont(parameter);
        generator.dispose();

        // --------

        Texture coinTexture = getTexture(COIN);
        TextureRegion[][] regions = TextureRegion.split(coinTexture, 32, 32);

        Array<TextureRegion> frames =  new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(regions[0][i]);
        }

        coinAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.16f, 0.27f, 0.46f, 1f);
        pixmap.fill();

        buttonTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public Texture getTexture(String path) {
        return manager.get(path, Texture.class);
    }

    public Sound getCoinSound() {
        return manager.get(COIN_SOUND, Sound.class);
    }

    public Sound getHitSound() {
        return manager.get(HIT_SOUND, Sound.class);
    }

    public Music getBackgroundMusic() {
        return manager.get(BACKGROUND_MUSIC, Music.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
        font.dispose();
        buttonTexture.dispose();
    }
}
