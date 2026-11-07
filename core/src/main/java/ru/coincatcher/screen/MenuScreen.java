package ru.coincatcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.coincatcher.Main;

public class MenuScreen extends ScreenAdapter {
    private final Main game;
    private final Stage stage;

    public MenuScreen(Main game) {
        this.game = game;
        stage = new Stage(new FitViewport(800, 600));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle =
            new Label.LabelStyle(
                game.assets.font,
                com.badlogic.gdx.graphics.Color.WHITE
            );

        Label title = new Label("COIN CATCHER", labelStyle);

        TextButton play = game.createButton("PLAY");
        TextButton settings = game.createButton("SETTINGS");
        TextButton exit = game.createButton("EXIT");

        table.add(title).padBottom(60).row();
        table.add(play).width(280).height(60).pad(10).row();
        table.add(settings).width(280).height(60).pad(10).row();
        table.add(exit).width(280).height(60).pad(10).row();

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.postRunnable(game::startGame);
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.postRunnable(game::showSettings);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.exit();
            }
        });
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
