package ru.coincatcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.coincatcher.Main;

public class GameOverScreen extends ScreenAdapter {
    private final Main game;
    private final Stage stage;

    public GameOverScreen(Main game, int score) {
        this.game = game;

        boolean newRecord = game.settings.updateHighScore(score);
        int highScore = game.settings.getHighScore();

        stage = new Stage(new FitViewport(800, 600));

        Label.LabelStyle labelStyle =
            new Label.LabelStyle(game.assets.font, Color.WHITE);
        Label.LabelStyle labelStyleGameOver =
            new Label.LabelStyle(game.assets.font, Color.RED);
        Label.LabelStyle labelStyleRecord =
            new Label.LabelStyle(game.assets.font, Color.YELLOW);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("GAME OVER", labelStyleGameOver);
        Label result = new Label("Score: " + score, labelStyle);

        Label record = new Label(
            "Best: " + highScore,
            labelStyleRecord
        );

        TextButton restart = game.createButton("RESTART");
        TextButton menu = game.createButton("MAIN MENU");

        table.add(title).padBottom(30).row();
        table.add(result).padBottom(50).row();
        table.add(record).padBottom(50).row();
        table.add(restart).width(280).height(60).pad(10).row();
        table.add(menu).width(280).height(60).pad(10).row();

        restart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.postRunnable(game::startGame);
            }
        });

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.postRunnable(game::showMenu);
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
