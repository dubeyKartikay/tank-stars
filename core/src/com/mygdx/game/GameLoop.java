package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Constants;
import com.mygdx.Screens.PauseMenu;
import com.mygdx.Screens.PlayScreen;

import java.util.ArrayList;

public class GameLoop  extends ApplicationAdapter implements InputProcessor{
    Game game;
    private SpriteBatch spriteBatch;
    private Texture texture1,texture2;
    private Array<Body>tmpbodies=new Array<Body>();
    private Batch batch;
    private Sprite sprite1,sprite2;
    private int changeflag,player2fuellvl,player1fuellvl;
    private Array<Bullet> bulletDeletionList = new Array<>();
    final private float TIMESTEP=1/60f;
    private long[] movetime={0,0};
    final private int VECLOCITYIT=8,POSITIONIT=3;
    private Bullet bullet_player1,bullet_player2;
    private int currentplayer;
    private Vector2 player1_cords,player2_cords;
    private Stage stage;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body groundBody,tankbody1,tankbody2;

    private SpriteBatch spritebatch,tankbatch;
    private Texture groundTexture,img;
    private static GameLoop gameLoop;
    private OrthographicCamera orthographicCamera;
    private Vector2 groundVertices[];

    public void setPlayer1fuellvl(int player1fuellvl) {
        this.player1fuellvl = player1fuellvl;
    }

    public void setPlayer2fuellvl(int player2fuellvl) {
        this.player2fuellvl = player2fuellvl;
    }

    public int getPlayer1fuellvl() {
        return player1fuellvl;
    }

    public int getPlayer2fuellvl() {
        return player2fuellvl;
    }

    public static GameLoop getInstance(Game game){
        if (gameLoop == null){
            gameLoop = new GameLoop(game);
        }
        return gameLoop;
    }

    public int getCurrentplayer() {
        return currentplayer;
    }

    public int getChangeflag() {
        return changeflag;
    }

    public void setChangeflag(int changeflag) {
        this.changeflag = changeflag;
    }

    public static GameLoop getInstance(){
        if (gameLoop == null){
            return null;
        }
        return gameLoop;
    }
    private GameLoop(Game game){
        player1fuellvl=100;
        player2fuellvl=100;
        changeflag=0;
        Gdx.input.setInputProcessor(this);
        this.game=game;
        currentplayer=0;
        spritebatch= new SpriteBatch();
        tankbatch=new SpriteBatch();
        world= new World(new Vector2(0,-10),true);
        world= new World(new Vector2(0,-50),true);
        world.setContactListener(new CollisionHandler(bulletDeletionList));
        game.getPlayer1().initTank(world,-55,20);
        game.getPlayer2().initTank(world,40,14);
        texture1=game.getPlayer1().getTank().getTexture();
        texture2=game.getPlayer2().getTank().getTexture();
        debugRenderer= new Box2DDebugRenderer();
        orthographicCamera=new OrthographicCamera((Gdx.graphics.getWidth()/5),Gdx.graphics.getHeight()/5);
        //BALL
        tankbody1=game.getPlayer1().getTank().getTankBody();
        tankbody2=game.getPlayer2().getTank().getTankBody();

        img=new Texture(Gdx.files.internal("playscreenbg.jpg"));
        BodyDef tank1=new BodyDef();

        tank1.type= BodyDef.BodyType.DynamicBody;
        FixtureDef tankfixture1=new FixtureDef();
//
        //GROUND
        tank1.type= BodyDef.BodyType.StaticBody;
        tank1.position.set(0,0);
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        //        slot1 = new TextButton("slot1",mySkin);
        //shape
        ChainShape chainShape=new ChainShape();
        chainShape.createChain(new Vector2[]{new Vector2(-136,100),new Vector2(-136,14),
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
                new Vector2(136,-23),new Vector2(136,200)
        });
        tankfixture1.shape=chainShape;
        tankfixture1.friction=.5f;
        tankfixture1.restitution=0;

        groundBody=world.createBody(tank1);
        groundBody.createFixture(tankfixture1);
        groundBody.setUserData(new Ground());
        //BOX
        tank1.type= BodyDef.BodyType.DynamicBody;
        tank1.position.set(2.25f,10);
//            player1_cords=tankbody1.getPosition();
//            player2_cords=tankbody2.getPosition();
        //        Gdx.input.setInputProcessor(new InputCon);
    }

    public void update(){
        System.out.println("updating");
    }
    public void applymovement(Body tank){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            // System.out.println("W is pressed");
            if(currentplayer==0){
                game.getPlayer1().getTank().setAngle(game.getPlayer1().getTank().getAngle()+0.1);
            }
            else{
                game.getPlayer2().getTank().setAngle(game.getPlayer2().getTank().getAngle()+0.1);
            }

        }if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if(currentplayer==0){
                game.getPlayer1().getTank().setAngle(game.getPlayer1().getTank().getAngle()-0.1);
            }
            else{
                game.getPlayer2().getTank().setAngle(game.getPlayer2().getTank().getAngle()-0.1);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            if(currentplayer ==0 && player1fuellvl>=0){
                if(tank.getLinearVelocity().x > -20) {
                    tank.setLinearVelocity((float) (tank.getLinearVelocity().x - 1), (float) (tank.getLinearVelocity().y));
                }
            } if(currentplayer ==1 && player2fuellvl>=0){
                if(tank.getLinearVelocity().x > -20) {
                    tank.setLinearVelocity((float) (tank.getLinearVelocity().x - 1), (float) (tank.getLinearVelocity().y));
                }
            }
        }if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if(currentplayer ==0 && player1fuellvl>=0){
                if(tank.getLinearVelocity().x < 20) {
                    tank.setLinearVelocity((float) (tank.getLinearVelocity().x + 1), (float) (tank.getLinearVelocity().y));
                }
            } if(currentplayer ==1 && player2fuellvl>=0){
                if(tank.getLinearVelocity().x < 20) {
                    tank.setLinearVelocity((float) (tank.getLinearVelocity().x + 1), (float) (tank.getLinearVelocity().y));
                }
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            if(currentplayer==0){
                game.getPlayer1().getTank().setAngle(game.getPlayer1().getTank().getAngle()+0.1);
            }
            else{
                game.getPlayer2().getTank().setAngle(game.getPlayer2().getTank().getAngle()+0.1);
            }
        }if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            if(currentplayer==0){
                game.getPlayer1().getTank().setFirepower(game.getPlayer1().getTank().getFirepower()+1);
            }
            else{
                game.getPlayer2().getTank().setFirepower(game.getPlayer2().getTank().getFirepower()+1);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            if(currentplayer==0){
                game.getPlayer1().getTank().setFirepower(game.getPlayer1().getTank().getFirepower()-1);
            }
            else{
                game.getPlayer2().getTank().setFirepower(game.getPlayer2().getTank().getFirepower()-1);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)){
            tank.setLinearVelocity(0,0);
            if(currentplayer==0){

                game.getPlayer1().getTank().fire();
            }
            else{
                game.getPlayer2().getTank().fire();
            }
        }


    }

    public Bullet getPlayerBullet(){
        if(currentplayer==0){
            return bullet_player1;
        }
        return bullet_player2;
    }

    public void movement(){
        game.getPlayer1().getTank().getPosition().set(tankbody1.getPosition());
        game.getPlayer2().getTank().getPosition().set(tankbody2.getPosition());
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setPaused(true);
            game.setOverlayScreen(new PauseMenu(game));
        }
        if(currentplayer==0){
            applymovement(tankbody1);
        }
        else{
            applymovement(tankbody2);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            changeflag=1;
            if(currentplayer==0){
                player1fuellvl=100;
                currentplayer=1;

            }
            else{
                currentplayer=0;
                player2fuellvl=100;
            }
        }
    }
    public void render(){
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClearColor(0,0,0,1);
        movement();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tankbatch.begin();
        tankbatch.draw(img,-137,-72,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        tankbatch.setProjectionMatrix(orthographicCamera.combined);
//        Sprite flagsprite1=game.getPlayer1().getTank().getSprite();
        tankbatch.draw(texture1,tankbody1.getPosition().x-7,tankbody1.getPosition().y-8,17,17);
        tankbatch.draw(texture2,tankbody2.getPosition().x-9,tankbody2.getPosition().y-8,17,17);
        tankbatch.end();
        debugRenderer.render(world,orthographicCamera.combined);
        world.step(TIMESTEP,VECLOCITYIT,POSITIONIT);
        if (bulletDeletionList.size > 0){
            for (Bullet b :
                    bulletDeletionList) {
                b.clear();
            }
            bulletDeletionList.clear();
        }
    }
    public void dispose () {
        // Dispose of resources when they are no longer needed
        groundTexture.dispose();
        spritebatch.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
    public long[] getMovetime() {
        return movetime;
    }
    public void setMovetime(long[] movetime) {
        this.movetime = movetime;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.D) {
            // System.out.println("D is pressed");
            movetime[0]=System.currentTimeMillis()/50;
        }if(keycode==Input.Keys.A) {
            // System.out.println("D is pressed");
            movetime[0]=System.currentTimeMillis()/50;
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        if(keycode==Input.Keys.D) {
            // System.out.println("D is resleased");
            movetime[1]=System.currentTimeMillis()/50;
            if(currentplayer==0){
                player1fuellvl-=Math.abs(movetime[1]-movetime[0]);
            }
            else{
                player2fuellvl-=Math.abs(movetime[1]-movetime[0]);
            }
            changeflag=1;
        }if(keycode==Input.Keys.A) {
            // System.out.println("D is resleased");
            movetime[1]=System.currentTimeMillis()/50;
            if(currentplayer==0){
                player1fuellvl-=Math.abs(movetime[1]-movetime[0]);
            }
            else{
                player2fuellvl-=Math.abs(movetime[1]-movetime[0]);
            }
            changeflag=1;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
