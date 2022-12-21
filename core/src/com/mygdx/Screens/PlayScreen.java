
package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Bullet;
import com.mygdx.game.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLoop;
import com.mygdx.game.Tank;
import com.sun.imageio.plugins.tiff.TIFFMetadataFormat;
import org.w3c.dom.Text;

import java.awt.*;
//import java.awt.Label;
//public class Controller{
//
//    public float power = 50f;
//    public float angle = 0f;
//
//}
public class PlayScreen extends GameScreen {
    private Texture img;

    private ProgressBar player1Health;
    private ProgressBar player2Health;
    private Stage stage;
    private  Table table;
    private  SpriteBatch spritebatch;
    private ProgressBar player1fuel;
    private ProgressBar player2fuel;
    private Button pauseButton;
    private GameLoop gameLoop;
    Texture img2;
    private ProgressBar powerbar;
    private Label anglelabel,currentplayerlable;
    PlayScreen(final Game game) {
        super(game);
//        gameLoop =GameLoop.getInstance(game);
        gameLoop = new GameLoop(game);
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        img2=new Texture(Gdx.files.internal("playscreenbg.jpg"));
        Skin healthBarSkin = new Skin(Gdx.files.internal("skins/gdx-skins/comic/skin/comic-ui.json"));
        Skin pauseButtonSkin = new Skin(Gdx.files.internal("skins/gdx-skins/arcade/skin/arcade-ui.json"));
        Skin angleskin=new Skin(Gdx.files.internal("skins/gdx-skins/metal/skin/metal-ui.json"));

        anglelabel= new Label(Double.toString(game.getPlayer1().getTank().getAngle()),angleskin);
        anglelabel.setPosition(620,90);
        anglelabel.setSize(40,40);
        anglelabel.setFontScale(3);
        stage.addActor(anglelabel);

        currentplayerlable= new Label("PLAYER 1 CHANCE",angleskin);
        currentplayerlable.setPosition(400,550);
        currentplayerlable.setSize(300,50);
        currentplayerlable.setFontScale(5);
        stage.addActor(currentplayerlable);

        player1Health = new ProgressBar(0,100,1,false,healthBarSkin);
        player2Health = new ProgressBar(0,100,1,false,healthBarSkin);
        powerbar= new ProgressBar(0,100,1,false,healthBarSkin);
        powerbar.setValue((float) game.getPlayer1().getTank().getFirepower());
        powerbar.setWidth(270);
        powerbar.setPosition(525,30);
        stage.addActor(powerbar);

        pauseButton = new Button(pauseButtonSkin,"red");
        table.setPosition(10,310);
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
                System.out.println("PASIrdf");
                getGame().setPaused(true);
                getGame().setOverlayScreen(new PauseMenu(getGame()));
            }});
        table.add(player1Health).width(500);
        table.add(pauseButton).padLeft(50).padRight(50);
        table.add(player2Health).width(500);
        stage.addActor(table);
        spritebatch=new SpriteBatch();
        player1fuel = new ProgressBar(0,100,1,false,healthBarSkin);
        player2fuel= new ProgressBar(0,100,1,false,healthBarSkin);
        player1fuel.setValue(100);
        player2fuel.setValue(100);
        player1fuel.setPosition(20,30);
        stage.addActor(player1fuel);
        player2fuel.setPosition(1205,30);
        stage.addActor(player2fuel);

    }
    //     int update_counter=0;
    @Override
    public void update(float delta) {
        gameLoop.update();
        try {
//            update_counter+=1;
//            if(update_counter>100){
//                updateBars();
//                update_counter=0;
//            }
//        Thread.sleep(500);
//            System.out.println("Update");
//            updateBars();
//            System.out.println(player1_cords.x+": "+player1_cords.y);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void updateBars(){
        player1Health.setValue(getGame().getPlayer1().getTank().getHealth());
        player2Health.setValue(getGame().getPlayer2().getTank().getHealth());
        if(gameLoop.getCurrentplayer()==0){
            powerbar.setValue((float) getGame().getPlayer1().getTank().getFirepower());
            anglelabel.setText((int)getGame().getPlayer1().getTank().getAngle()+" degree");
        }
        else{
            powerbar.setValue((float) getGame().getPlayer2().getTank().getFirepower());
//            double
            anglelabel.setText(((int)getGame().getPlayer2().getTank().getAngle())+" degree");
        }
        if(gameLoop.getChangeflag()==1){
            gameLoop.setChangeflag(0);
            if(gameLoop.getCurrentplayer()==0){
                currentplayerlable.setText("PLAYER 1 CHANCE");
                player1fuel.setValue(gameLoop.getPlayer1fuellvl());
//                powerbar.setValue(getGame().getPlayer1().getTank().get)
            }
            else{
                currentplayerlable.setText("PLAYER 2 CHANCE");
                player2fuel.setValue(gameLoop.getPlayer2fuellvl());

            }
        }

    }
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        updateBars();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameLoop.render();

        stage.draw();

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
