package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.Constants;
import com.mygdx.Screens.PauseMenu;

public class GameLoop {
    Game game;
    final private float TIMESTEP=1/60f;

    final private int VECLOCITYIT=8,POSITIONIT=3;
    private Bullet bullet_player1,bullet_player2;
    private int currentplayer;
    private Vector2 player1_cords,player2_cords;
    private Stage stage;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body groundBody,tankbody1,tankbody2;

    private SpriteBatch spritebatch;
    private Texture groundTexture,img;
    private static GameLoop gameLoop;
    private OrthographicCamera orthographicCamera;
    private Vector2 groundVertices[];

    public static GameLoop getInstance(Game game){
        if (gameLoop == null){
            gameLoop = new GameLoop(game);
        }
        return gameLoop;
    }

    public static GameLoop getInstance(){
        if (gameLoop == null){
            return null;
        }
        return gameLoop;
    }
    private GameLoop(Game game){
        this.game=game;
        currentplayer=0;
        spritebatch= new SpriteBatch();
        world= new World(new Vector2(0,-10),true);
        debugRenderer= new Box2DDebugRenderer();
        orthographicCamera=new OrthographicCamera((Gdx.graphics.getWidth()/5),Gdx.graphics.getHeight()/5);


        img=new Texture(Gdx.files.internal("playscreenbg.jpg"));
        game.getPlayer1().initTank(world);
        game.getPlayer2().initTank(world);

        tankbody1=game.getPlayer1().getTank().getTankBody();
        tankbody2=game.getPlayer2().getTank().getTankBody();

        makeGround();
        player1_cords=tankbody1.getPosition();
        player2_cords=tankbody2.getPosition();
    }

    private void makeGround() {
        BodyDef ground = new BodyDef();
        FixtureDef groundFixturedef = new FixtureDef();
        ground.type= BodyDef.BodyType.StaticBody;
        ground.position.set(0,0);
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
        groundFixturedef.shape=chainShape;
        groundFixturedef.friction=.5f;
        groundFixturedef.restitution=0;

        groundBody=world.createBody(ground);
        groundBody.createFixture(groundFixturedef);
    }

    public void update(){
        handlePause();
        movement();
    }

    private void handlePause() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setPaused(true);
            game.setOverlayScreen(new PauseMenu(game));
        }
    }

    public void applymovement(Body tank){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
//            Vector2 vel = tank.getLinearVelocity();
//
//            tank.applyForceToCenter(1000,0,true);
        }if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Vector2 vel = tank.getLinearVelocity();
            if (vel.x<=-20){
                return;
            }
            tank.applyForceToCenter(-50000,0,true);
        }if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Vector2 vel = tank.getLinearVelocity();
            if (vel.x>=20){
                return;
            }
            tank.applyForceToCenter(50000,0,true);
        }if(Gdx.input.isKeyPressed(Input.Keys.S)){
//            tank.applyForceToCenter(50,4,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            int flag=getPlayerBullet().getFire_power();
            flag-=10;
            getPlayerBullet().setFire_power(flag);
        }if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            int flag=getPlayerBullet().getFire_power();
            flag+=10;
            getPlayerBullet().setFire_power(flag);
        }
        Vector2 vecc = tank.getLinearVelocity();
        System.out.println(vecc.x);
    }

    public Bullet getPlayerBullet(){
        if(currentplayer==0){
            return bullet_player1;
        }
        return bullet_player2;
    }

    public void movement(){

        if(currentplayer==0){
            applymovement(tankbody1);
        }
        else{
            applymovement(tankbody2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            if(currentplayer==0){
                currentplayer=1;
            }
            else{
                currentplayer=0;
            }
        }

    }

    public void render(){
        Gdx.gl.glClearColor(0,0,0,1);

//        updateBars();
//        System.out.println("gameloop");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spritebatch.begin();
        spritebatch.draw(img,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spritebatch.end();
//        stage.draw();
        debugRenderer.render(world,orthographicCamera.combined);
        world.step(TIMESTEP,VECLOCITYIT,POSITIONIT);

        // Render the Box2D debug data
//        debugRenderer.render(world, camera.combined);
    }
    public void dispose () {
        // Dispose of resources when they are no longer needed
        groundTexture.dispose();
        spritebatch.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

}
