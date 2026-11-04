package ru.coincatcher.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import org.w3c.dom.Text;

public class GameAssets implements Disposable {

    public static final String PLAYER = "player.png";
    public static final String COIN = "coin.png";
    public static final String METEOR = "meteor.png";
    public static final String BACKGROUND = "background.png";

    private final AssetManager manager = new AssetManager();

    public final BitmapFont font;
    public final Animation<TextureRegion> coinAnimation;
    public final Texture buttonTexture;

    public GameAssets() {
        manager.load(PLAYER, Texture.class);
        manager.load(COIN, Texture.class);
        manager.load(METEOR, Texture.class);
        manager.load(BACKGROUND, Texture.class);
        manager.finishLoading();

        for (String path : new String[]{
            PLAYER, COIN, METEOR, BACKGROUND
        }) {
            manager.get(path, Texture.class).setFilter(
                Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest
            );
        }

        font = new BitmapFont();
        font.getData().setScale(1.8f);

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

    @Override
    public void dispose() {
        manager.dispose();
        font.dispose();
        buttonTexture.dispose();
    }
}
