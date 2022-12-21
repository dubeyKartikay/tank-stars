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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Bullet;
import com.mygdx.game.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLoop;
import com.mygdx.game.Tank;
import com.sun.imageio.plugins.tiff.TIFFMetadataFormat;
import org.w3c.dom.Text;

import java.awt.*;

public class WinScreen extends GameScreen{
    private SpriteBatch spriteBatch;
    private Texture img;
    private Stage stage;
    private Table table;
//    private TextButton button;
    private int playerwon;
    private Label label,label2;

    public WinScreen(Game game, int playerwon) {
        super(game);
        this.playerwon=playerwon;
        img = new Texture("badlogic.jpg");
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        Skin angleskin=new Skin(Gdx.files.internal("skins/gdx-skins/metal/skin/metal-ui.json"));
        label=new Label("PLAYER "+playerwon+" WON ",angleskin);
        label.setPosition(525,550);
        label.setFontScale(4);
        label2=new Label("Press enter to continue",angleskin);
        label2.setFontScale(4);
        label2.setPosition(525,440);
        stage=new Stage(new ScreenViewport());
        stage.addActor(label);
        stage.addActor(label2);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            getGame().setCurrScreen(new MainMenu(getGame()));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(img, 0, 0, 1366, 720);
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
