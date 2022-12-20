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

import java.util.ArrayList;

public class GameLoop  extends ApplicationAdapter implements InputProcessor  {
        Game game;
        private SpriteBatch spriteBatch;
        private Array<Body>tmpbodies=new Array<Body>();
        private Batch batch;
        private Sprite sprite1,sprite2;
        private int changeflag,player2fuellvl,player1fuellvl;
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
            debugRenderer= new Box2DDebugRenderer();
            orthographicCamera=new OrthographicCamera((Gdx.graphics.getWidth()/5),Gdx.graphics.getHeight()/5);
            //BALL
            BodyDef tank1=new BodyDef();
            BodyDef tank2= new BodyDef();

            tank1.type= BodyDef.BodyType.DynamicBody;
            tank2.type=BodyDef.BodyType.DynamicBody;

            img=new Texture(Gdx.files.internal("playscreenbg.jpg"));
    //        bg=new Image((img));

            tank1.position.set(-55,20);
            tank2.position.set(40,11);


            FixtureDef wheel1=new FixtureDef();
            FixtureDef wheel2=new FixtureDef();
            FixtureDef bullet1=new FixtureDef();
            FixtureDef tankfixture1=new FixtureDef();

            CircleShape circleShape= new CircleShape();
            circleShape.setRadius(2f);
            FixtureDef fixturetank1=new FixtureDef();
            FixtureDef fixturetank2=new FixtureDef();
            PolygonShape polygonShape=new PolygonShape();
            polygonShape.setAsBox(3,3);
            fixturetank1.density=2.5f;
            fixturetank1.friction=.2f; //0 to 1
            fixturetank1.restitution=.3f;  //bounce back after dropping to ground 0 to 1 if 1 then jumping infinte times same meter as dropped
            fixturetank1.shape=polygonShape;
            fixturetank2.shape=polygonShape;
            fixturetank2.restitution=0;
            fixturetank2.density=2.5f;
            fixturetank2.friction=.1f;

            tankbody1=world.createBody(tank1);
            tankbody1.createFixture(fixturetank1);
            sprite1=new Sprite(new Texture(Gdx.files.internal("img/tanks/0.png")));
            sprite1.setSize(14,14);
            tankbody1.setUserData(sprite1);
            circleShape.setRadius(2f);
            circleShape.setPosition(new Vector2(0,5));
            fixturetank1.shape=circleShape;
            tankbody1.createFixture(fixturetank1);
            circleShape.setRadius(1f);
            circleShape.setPosition(new Vector2(3,-3));
            fixturetank1.shape=circleShape;
            tankbody1.createFixture(fixturetank1);
            circleShape.setPosition(new Vector2(-3,-3));
            fixturetank1.shape=circleShape;
            tankbody1.createFixture(fixturetank1);

            tankbody2=world.createBody(tank2);
            tankbody2.createFixture(fixturetank2);
            fixturetank2.shape=circleShape;
            tankbody2.createFixture(fixturetank2);
            //GROUND
            tank1.type= BodyDef.BodyType.StaticBody;
            tank1.position.set(0,0);
            final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
    //        slot1 = new TextButton("slot1",mySkin);
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

            groundBody=world.createBody(tank1);
            groundBody.createFixture(fixturetank1);
            //BOX
            tank1.type= BodyDef.BodyType.DynamicBody;
            tank1.position.set(2.25f,10);
            player1_cords=tankbody1.getPosition();
            player2_cords=tankbody2.getPosition();
    //        Gdx.input.setInputProcessor(new InputCon);
        }

        public void update(){
            System.out.println("updating");
        }
        public void applymovement(Body tank){
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
    //            if(currentplayer ==0 && player1fuellvl>=0){
    //                tank.applyForceToCenter(100,0,true);
    //            } if(currentplayer ==2 && player2fuellvl>=0){
    //                tank.applyForceToCenter(100,0,true);
    //            }

            }if(Gdx.input.isKeyPressed(Input.Keys.A)){
                if(currentplayer ==0 && player1fuellvl>=0){
                    tank.applyForceToCenter(-100,0,true);
                } if(currentplayer ==1 && player2fuellvl>=0){
                    tank.applyForceToCenter(-100,0,true);
                }
            }if(Gdx.input.isKeyPressed(Input.Keys.D)){
                if(currentplayer ==0 && player1fuellvl>=0){
                    tank.applyForceToCenter(100,0,true);
                } if(currentplayer ==1 && player2fuellvl>=0){
                    tank.applyForceToCenter(100,0,true);
                }

            }if(Gdx.input.isKeyPressed(Input.Keys.S)){
                tank.applyForceToCenter(5,4,true);
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
    //        updateBars();
    //        System.out.println("gameloop");
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            tankbatch.begin();
            tankbatch.draw(img,-137,-72,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
            tankbatch.setProjectionMatrix(orthographicCamera.combined);
            world.getBodies(tmpbodies);
            for(Body body :tmpbodies){
                if(body.getUserData()!=null && body.getUserData() instanceof Sprite){
//                    System.out.println("llll");
                    Sprite flagsprite=(Sprite)body.getUserData();
//                    flagsprite.setPosition(-55,11);
                    System.out.println(body.getPosition().x+" "+body.getPosition().y);
                    flagsprite.setPosition(body.getPosition().x-flagsprite.getWidth()/2,body.getPosition().y-flagsprite.getHeight()/2);
                    flagsprite.draw(tankbatch);
                }
            }
            tankbatch.end();
//            spritebatch.begin();
//            spritebatch.draw(img,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//            spritebatch.end();
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
        public long[] getMovetime() {
            return movetime;
        }
        public void setMovetime(long[] movetime) {
            this.movetime = movetime;
        }
        @Override
        public boolean keyDown(int keycode) {
            if(keycode==Input.Keys.D) {
                System.out.println("D is pressed");
                movetime[0]=System.currentTimeMillis()/50;
            }if(keycode==Input.Keys.A) {
                System.out.println("D is pressed");
                movetime[0]=System.currentTimeMillis()/50;
            }
            return true;
        }
        @Override
        public boolean keyUp(int keycode) {
            if(keycode==Input.Keys.D) {
                System.out.println("D is resleased");
                movetime[1]=System.currentTimeMillis()/50;
                if(currentplayer==0){
                    player1fuellvl-=Math.abs(movetime[1]-movetime[0]);
                }
                else{
                    player2fuellvl-=Math.abs(movetime[1]-movetime[0]);
                }
                changeflag=1;
            }if(keycode==Input.Keys.A) {
                System.out.println("D is resleased");
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
