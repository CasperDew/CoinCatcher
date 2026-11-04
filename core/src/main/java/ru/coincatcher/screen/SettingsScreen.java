package ru.coincatcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.coincatcher.Main;

public class SettingsScreen extends ScreenAdapter {
    private final Main game;
    private final Stage stage;

    public SettingsScreen(Main game) {
        this.game = game;
        stage = new Stage(new FitViewport(800, 600));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label(
            "SETTINGS",
            new Label.LabelStyle(game.assets.font, Color.WHITE)
        );

        TextButton vsync = game.createButton(
            "VSync: " + (game.isVSyncEnabled() ? "ON" : "OFF")
        );

        TextButton volume = game.createButton(volumeText());
        TextButton back = game.createButton("BACK");

        table.add(title).padBottom(50).row();
        table.add(vsync).width(320).height(60).pad(10).row();
        table.add(volume).width(320).height(60).pad(10).row();
        table.add(back).width(320).height(60).pad(10).row();

        vsync.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean enabled = !game.isVSyncEnabled();
                game.setVSyncEnabled(enabled);
                vsync.setText("VSync: " + (enabled ? "ON" : "OFF"));
            }
        });

        volume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float next = game.getVolume() + 0.25f;

                if (next > 1.01f) {
                    next = 0f;
                }

                game.setVolume(next);
                volume.setText(volumeText());
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.postRunnable(game::showMenu);
            }
        });
    }

    private String volumeText() {
        return "Volume: " +
            Math.round(game.getVolume() * 100) + "%";
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.08f, 0.1f, 0.15f, 1f);
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        if (Gdx.input.getInputProcessor() == stage) {
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
