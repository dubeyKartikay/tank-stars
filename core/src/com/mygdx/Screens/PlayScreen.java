
package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.imageio.plugins.tiff.TIFFMetadataFormat;
import java.awt.*;
//import java.awt.Label;

public class PlayScreen extends GameScreen {
    Texture img;
    private ProgressBar player1Health;
    private ProgressBar player2Health;
    final private float TIMESTEP=1/60f;
    private Stage stage;
    final private int VECLOCITYIT=8,POSITIONIT=3;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera orthographicCamera;TextButton slot1;
    Image bg;
    Table table;
    SpriteBatch spritebatch;
    private ProgressBar player1fuel;
    private ProgressBar player2fuel;
    private Button pauseButton;
    PlayScreen(final Game game) {
        super(game);
//        gameLoop = GameLoop.getInstance(game);
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        Skin healthBarSkin = new Skin(Gdx.files.internal("skins/gdx-skins/comic/skin/comic-ui.json"));
        Skin pauseButtonSkin = new Skin(Gdx.files.internal("skins/gdx-skins/arcade/skin/arcade-ui.json"));

        player1Health = new ProgressBar(0,100,1,false,healthBarSkin);
        player2Health = new ProgressBar(0,100,1,false,healthBarSkin);
        player1Health.setValue(40);
        player1Health.setColor(Color.GREEN);
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
        player1fuel.setValue(50);
        player2fuel.setValue(100);
        player1fuel.setPosition(20,30);
        stage.addActor(player1fuel);
        player2fuel.setPosition(1205,30);
        stage.addActor(player2fuel);


//        <-----------------------WORLD----------------------------------------------------------------------------->

        world= new World(new Vector2(0,-10),true);
        debugRenderer= new Box2DDebugRenderer();
        orthographicCamera=new OrthographicCamera((Gdx.graphics.getWidth()/5),Gdx.graphics.getHeight()/5);
        //BALL
        BodyDef tank1=new BodyDef();
        BodyDef tank2= new BodyDef();

        tank1.type= BodyDef.BodyType.DynamicBody;
        tank2.type=BodyDef.BodyType.DynamicBody;

        img=new Texture(Gdx.files.internal("playscreenbg.jpg"));
        bg=new Image((img));

        tank1.position.set(-55,8);
        tank2.position.set(40,9);

        CircleShape circleShape= new CircleShape();
        circleShape.setRadius(2f);
        FixtureDef fixturetank1=new FixtureDef();
        FixtureDef fixturetank2=new FixtureDef();
        fixturetank1.density=2.5f;
        fixturetank1.friction=.25f; //0 to 1
        fixturetank1.restitution=.75f;  //bounce back after dropping to ground 0 to 1 if 1 then jumping infinte times same meter as dropped
        fixturetank1.shape=circleShape;
        fixturetank2.shape=circleShape;
//        stage.addActor(slot1);
        world.createBody(tank1).createFixture(fixturetank1);
        world.createBody(tank2).createFixture(fixturetank2);
        //GROUND
        tank1.type= BodyDef.BodyType.StaticBody;
        tank1.position.set(0,0);
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        slot1 = new TextButton("slot1",mySkin);
        //shape
        ChainShape chainShape=new ChainShape();
        chainShape.createChain(new Vector2[]{new Vector2(-134,14),
                new Vector2(-130, 14),
                new Vector2(-107, -12),
                new Vector2(-79, -12),
                new Vector2(-62, 8),
                new Vector2(-45,8),
                new Vector2(-30, -14),
                new Vector2(12, -14),
                new Vector2(32, 9),
                new Vector2(66, 9),
                new Vector2(80, -7),
                new Vector2(97,-7),
                new Vector2(112,-23),
                new Vector2(148,-23)
        });
        fixturetank1.shape=chainShape;
        fixturetank1.friction=.5f;
        fixturetank1.restitution=0;

        world.createBody(tank1).createFixture(fixturetank1);
        //BOX
        tank1.type= BodyDef.BodyType.DynamicBody;
        tank1.position.set(2.25f,10);
        PolygonShape polygonShape= new PolygonShape();
        polygonShape.setAsBox(0.5f,1f);
        fixturetank1.shape=polygonShape;
        fixturetank1.friction=.75f;
        fixturetank1.restitution=.1f;
        fixturetank1.density=5;
        world.createBody(tank1).createFixture(fixturetank1);
    }
    @Override
    public void update(float delta) {
        try {

//        Thread.sleep(500);
            System.out.println("Update");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spritebatch.begin();
        spritebatch.draw(img,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spritebatch.end();
        stage.draw();
        debugRenderer.render(world,orthographicCamera.combined);
        world.step(TIMESTEP,VECLOCITYIT,POSITIONIT);
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
