package ru.coincatcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.coincatcher.Main;
import ru.coincatcher.assets.GameAssets;
import ru.coincatcher.entity.FallingObject;
import ru.coincatcher.entity.Player;
import ru.coincatcher.entity.Type;

public class GameScreen extends ScreenAdapter {
    public static final float WIDTH = 800;
    public static final float HEIGHT = 600;

    private final Main game;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch batch;

    private Player player;
    private Array<FallingObject> objects = new Array<>();

    private float spawnTimer = 0f;
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;

    public GameScreen(Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.update((int) WIDTH, (int) HEIGHT, true);

        batch = new SpriteBatch();

        player = new Player(game.assets.getTexture(GameAssets.PLAYER));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1f / 30f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.showMenu();
            return;
        }

        if (!gameOver) {
            update(delta);
        }

        if (gameOver) {
            game.showGameOver(score);
            return;
        }

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Фон
        Texture background = game.assets.getTexture(GameAssets.BACKGROUND);
        batch.draw(background, 0, 0, WIDTH, HEIGHT);

        // Игрок
        player.render(batch);

        // Падающие объекты
        for (FallingObject object : objects) {
            object.render(batch);
        }

        // Интерфейс
        game.assets.font.setColor(Color.WHITE);
        game.assets.font.draw(batch, "Score: " + score, 20, 570);
        game.assets.font.draw(batch, "Lives: " + lives, 650, 570);

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

                if (gameOver) {
                    break;
                }

            } else if (object.isOutOfBounds()) {
                objects.removeIndex(i);
            }
        }
    }

    public void spawnObjects() {
        float x = MathUtils.random(0f, WIDTH - 30f);

        Type type = MathUtils.randomBoolean(0.7f) ? Type.COINS : Type.OBSTACLE;

        float speed = MathUtils.random(150f, 280f);

        objects.add(new FallingObject(
            x,
            type,
            speed,
            game.assets.coinAnimation,
            game.assets.getTexture(GameAssets.METEOR)));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
