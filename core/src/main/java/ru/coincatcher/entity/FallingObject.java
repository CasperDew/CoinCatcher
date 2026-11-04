package ru.coincatcher.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class FallingObject {
    private final Rectangle bounds;
    private final Type type;
    private final float speed;

    private final Animation<TextureRegion> coinAnimation;
    private final Texture meteorTexture;

    private float stateTime = 0f;
    private float rotation = 0f;

    public FallingObject(float x, Type type, float speed,
                         Animation<TextureRegion> coinAnimation, Texture meteorTexture ) {
        this.type = type;
        this.speed = speed;
        this.coinAnimation = coinAnimation;
        this.meteorTexture = meteorTexture;
        this.bounds = new Rectangle((int) x, 600, 40, 40);
    }

    public void update(float delta) {
        bounds.y -= speed * delta;
        stateTime += delta;
        rotation += 90f * delta;
    }

    public void render(SpriteBatch batch) {
        if (type == Type.COINS) {
            TextureRegion frame = coinAnimation.getKeyFrame(stateTime);
            batch.draw(
                frame,
                bounds.x, bounds.y,
                bounds.width, bounds.height
            );
        } else {
            batch.draw(
                meteorTexture,
                bounds.x, bounds.y,
                bounds.width / 2f,
                bounds.height / 2f,
                bounds.width, bounds.height,
                1f, 1f,
                rotation,
                0, 0,
                meteorTexture.getWidth(),
                meteorTexture.getHeight(),
                false, false
            );
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }

    public boolean isOutOfBounds() {
        return bounds.y + bounds.height < 0;
    }
}
