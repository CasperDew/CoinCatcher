package ru.coincatcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.coincatcher.entity.FallingObject;
import ru.coincatcher.entity.Player;
import ru.coincatcher.entity.Type;

public class GameScreen extends ScreenAdapter {
    public static final float WIDTH = 800;
    public static final float HEIGHT = 600;


    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final Texture playerTexture;
    private final Texture coinTexture;
    private final Texture meteorTexture;
    private final Texture backgroundTexture;

    private final Animation<TextureRegion> coinAnimation;

    private Player player;
    private Array<FallingObject> objects;

    private float spawnTimer;
    private int score;
    private int lives;
    private boolean gameOver;

    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.update((int) WIDTH, (int) HEIGHT, true);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        playerTexture = new Texture(Gdx.files.internal("player.png"));
        coinTexture = new Texture(Gdx.files.internal("coin.png"));
        meteorTexture = new Texture(Gdx.files.internal("meteor.png"));
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        filterTexture(playerTexture);
        filterTexture(coinTexture);
        filterTexture(meteorTexture);
        filterTexture(backgroundTexture);

        // Деление Спрайт-листа на кадры
        TextureRegion[][] regions = TextureRegion.split(coinTexture, 32, 32);
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 8; i++) {
            frames.add(regions[0][i]);
        }

        coinAnimation = new Animation<>(
            0.1f, frames, Animation.PlayMode.LOOP
        );

        resetGame();
    }

    public void resetGame() {
        player = new Player(playerTexture);
        objects = new Array<>();

        score = 0;
        lives = 3;
        spawnTimer = 0f;
        gameOver = false;
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1f / 30f);

        if (!gameOver) {
            update(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            resetGame();
        }

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Фон
        batch.draw(backgroundTexture, 0, 0, WIDTH, HEIGHT);
        // Игрок
        player.render(batch);

        // Падающие объекты
        for (FallingObject object : objects) {
            object.render(batch);
        }

        // Интерфейс
        font.setColor(Color.WHITE);
        font.draw(batch, "Score: " + score, 20, 570);
        font.draw(batch, "Lives: " + lives, 650, 570);

        if (gameOver) {
            font.draw(batch, "GAME OVER", 310, 330);
            font.draw(batch, "Press R to restart", 280, 280);
        }

        batch.end();
    }

    public void update(float delta) {
        player.update(delta);
        spawnTimer += delta;

        if (spawnTimer >= 0.8f) {
            spawnObjects();
            spawnTimer = 0;
        }

        for (int i = objects.size - 1; i >= 0; i--) {
            FallingObject object = objects.get(i);
            object.update(delta);

            if (player.getBounds().overlaps(object.getBounds())) {
                if (object.getType() == Type.COINS) {
                    score += 10;
                } else {
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                    }
                }
                objects.removeIndex(i);
            } else if (object.isOutOfBounds()) {
                objects.removeIndex(i);
            }
        }
    }

    public void spawnObjects() {
        float x = MathUtils.random(0f, WIDTH - 30f);

        Type type = MathUtils.randomBoolean(0.7f) ? Type.COINS : Type.OBSTACLE;

        float speed = MathUtils.random(150f, 280f);

        objects.add(new FallingObject(x, type, speed, coinAnimation, meteorTexture));
    }

    private void filterTexture(Texture texture) {
        texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();

        playerTexture.dispose();
        coinTexture.dispose();
        meteorTexture.dispose();
        backgroundTexture.dispose();
    }
}
