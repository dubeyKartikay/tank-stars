package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.GameLoop;

import java.awt.*;
//import java.awt.Label;

public class PlayScreen extends GameScreen {
    Label label;
    private Table table;
    private ProgressBar player1Health;
    private ProgressBar player2Health;
    private ProgressBar player1fuel;
    private ProgressBar player2fuel;
    private ProgressBar player1power;
    private ProgressBar player2power;
    private Button pauseButton;
    private Texture bg,timeleft;
    private SpriteBatch spriteBatch;
    private ImageButton player1power1;
    private ImageButton player1power2;
    private ImageButton player2power1;
    private ImageButton player2power2;

    private GameLoop gameLoop;

    private static PlayScreen playScreen;

    public static PlayScreen getInstance(Game game){
        if (playScreen == null){
            playScreen = new PlayScreen(game);
        }
        return playScreen;
    }

    public static PlayScreen getInstance(){
        if (playScreen == null){
            return null;
        }
        return playScreen;
    }
    private PlayScreen(final Game game) {
        super(game);
        gameLoop = GameLoop.getInstance(game);
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        bg = new Texture(Gdx.files.internal("img/tempPlayscreen/playScreen.jpg"));

        Skin healthBarSkin = new Skin(Gdx.files.internal("skins/gdx-skins/comic/skin/comic-ui.json"));
        Skin pauseButtonSkin = new Skin(Gdx.files.internal("skins/gdx-skins/arcade/skin/arcade-ui.json"));

        player1Health = new ProgressBar(0,100,1,false,healthBarSkin);
        player2Health = new ProgressBar(0,100,1,false,healthBarSkin);

        pauseButton = new Button(pauseButtonSkin,"red");
        stage.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == 111){
                    System.out.println("HERE");
                    getGame().setPaused(true);
                    getGame().setOverlayScreen(new PauseMenu(getGame()));
                }
                return false;
            }
        });

        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setPaused(true);
                getGame().setOverlayScreen(new PauseMenu(getGame()));
            }
        });
        Texture power = new Texture(Gdx.files.internal("img/power/maxresdefault.jpg"));
        player1power1 = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(power))
        );
        player1power1.setSize(70,70);
        player1power1.setPosition(110, 550);  //hikeButton is an ImageButton
        stage.addActor(player1power1);
        player1power2 = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(power))
        );
        player1power2.setSize(70,70);
        player1power2.setPosition(190, 550);  //hikeButton is an ImageButton
        stage.addActor(player1power2);

        player2power1 = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(power))
        );
        player2power1.setSize(70,70);
        player2power1.setPosition(1100, 550);  //hikeButton is an ImageButton
        stage.addActor(player2power1);
        player2power2 = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(power))
        );
        player2power2.setSize(70,70);
        player2power2.setPosition(1180, 550);  //hikeButton is an ImageButton
        stage.addActor(player2power2);

        player1Health.setValue(50);
        player2Health.setValue(100);
        table.top().padTop(50);

        table.add(player1Health).width(500);
        table.add(pauseButton).padLeft(50).padRight(50);
        table.add(player2Health).width(500);
        stage.addActor(table);

        player1fuel = new ProgressBar(0,100,1,false,healthBarSkin);
        player2fuel= new ProgressBar(0,100,1,false,healthBarSkin);
        player1fuel.setValue(50);
        player2fuel.setValue(100);
        player1fuel.setPosition(20,30);
        stage.addActor(player1fuel);
        player2fuel.setPosition(1205,30);
        stage.addActor(player2fuel);

        player1power = new ProgressBar(0,100,1,false,healthBarSkin);
        player2power= new ProgressBar(0,100,1,false,healthBarSkin);
        player1power.setValue(50);
        player2power.setValue(100);
        player1power.setPosition(20,75);
        stage.addActor(player1power);
        player2power.setPosition(1205,75);
        stage.addActor(player2power);
        timeleft=new Texture(Gdx.files.internal("img/time/timer.jpg"));
        spriteBatch=new SpriteBatch();
        Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));

        label=new Label("sid",mySkin);
        stage.addActor(label);
        label.setPosition(100,100);


    }


    @Override
    public void update(float delta) {
        try {
        gameLoop.update();
        System.out.println("Update");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void draw(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(timeleft, 1265, 658,100,60); //550 is X and 380 is Y position.
        spriteBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());

//        stage.getBatch().begin();
//        stage.getBatch().draw(bg, 0, 0, 1366, 720);
//        stage.getBatch().end();

        stage.draw();
        gameLoop.render();

    }

    @Override
    public void dispose(){
        gameLoop.dispose();
    }
    @Override
    public boolean isDone() {
        return false;
    }
}
