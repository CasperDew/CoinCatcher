package ru.coincatcher.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Player {
    private final Rectangle bounds;
    private final Texture texture;

    private final float speed = 350f;
    private float rotation = 0f;

    public Player(Texture texture) {
        this.texture = texture;
        bounds = new Rectangle(368, 30, 64, 64);
    }

    public void update(float delta) {
        float direction = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction -= 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction += 1f;
        }

        bounds.x += direction * speed * delta;
        bounds.x = MathUtils.clamp(bounds.x, 0, 800 - bounds.width);

        // Наклон корабля
        float targetRotation = - direction * 15f;
        rotation = MathUtils.lerp(rotation, targetRotation, Math.min(1f, 10f * delta));
    }

    public void render(SpriteBatch batch) {
        batch.draw(
            texture,
            bounds.x, bounds.y,
            bounds.width / 2f,
            bounds.height / 2f,
            bounds.width, bounds.height,
            1f, 1f,
            rotation,
            0, 0,
            texture.getWidth(),
            texture.getHeight(),
            false, false
        );
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
