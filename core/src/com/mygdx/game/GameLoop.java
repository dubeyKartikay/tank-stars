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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screens.PauseMenu;

public class GameLoop  extends ApplicationAdapter implements InputProcessor  {
        Game game;
        private SpriteBatch spriteBatch;
        private Array<Body>tmpbodies=new Array<Body>();
        private Batch batch;
        private Sprite sprite1,sprite2;

        private Array<Bullet> bulletDeletionList = new Array<>();
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
            world= new World(new Vector2(0,-50),true);
            world.setContactListener(new CollisionHandler(bulletDeletionList));
            game.getPlayer1().initTank(world,-55,20);
            game.getPlayer2().initTank(world,40,14);
            debugRenderer= new Box2DDebugRenderer();
            orthographicCamera=new OrthographicCamera((Gdx.graphics.getWidth()/5),Gdx.graphics.getHeight()/5);
            //BALL
            tankbody1=game.getPlayer1().getTank().getTankBody();
            tankbody2=game.getPlayer2().getTank().getTankBody();

            img=new Texture(Gdx.files.internal("playscreenbg.jpg"));
    //        bg=new Image((img));
            BodyDef tank1=new BodyDef();
//            BodyDef tank2= new BodyDef();
//
            tank1.type= BodyDef.BodyType.DynamicBody;
//            tank2.type=BodyDef.BodyType.DynamicBody;
//            tank1.position.set(-55,20);
//            tank2.position.set(40,14);
//
//            FixtureDef wheel1=new FixtureDef();
//            FixtureDef wheel2=new FixtureDef();
//            FixtureDef bullet1=new FixtureDef();
            FixtureDef tankfixture1=new FixtureDef();
//
//
//            CircleShape circleShape= new CircleShape();
//            PolygonShape polygonShape=new PolygonShape();
//            polygonShape.setAsBox(3,3);
//            tankfixture1.shape=polygonShape;
//            tankbody1=world.createBody(tank1);
//            tankbody2=world.createBody(tank2);
//            tankbody1.createFixture(tankfixture1);
//            tankbody2.createFixture(tankfixture1);
//            circleShape.setRadius(2f);
//            circleShape.setPosition(new Vector2(0,5));
//            tankfixture1.shape=circleShape;
//            tankbody1.createFixture(tankfixture1);
//            tankbody2.createFixture(tankfixture1);
//            circleShape.setRadius(1.5f);
//            circleShape.setPosition(new Vector2(3,-3));
//            tankfixture1.shape=circleShape;
//            tankbody1.createFixture(tankfixture1);
//            tankbody2.createFixture(tankfixture1);
//            circleShape.setPosition(new Vector2(-3,-3));
//            tankfixture1.shape=circleShape;
//            tankbody1.createFixture(tankfixture1);
//            tankbody2.createFixture(tankfixture1);
//
//            Sprite tank1sprite=game.getPlayer1().getTank().getSprite(0,"L");
//            tank1sprite.setSize(14,14);
//            tankbody1.setUserData(tank1sprite);
//
//            Sprite tank2sprite=game.getPlayer2().getTank().getSprite(0,"R");
//            tank2sprite.setSize(14,14);
//            tankbody2.setUserData(tank2sprite);

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
//                System.out.println("W is pressed");
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
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            tankbatch.begin();
            tankbatch.draw(img,-137,-72,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
            tankbatch.setProjectionMatrix(orthographicCamera.combined);
            world.getBodies(tmpbodies);
//            for(Body body :tmpbodies){
//                if(body.getUserData()!=null && body.getUserData() instanceof Sprite){
                    Sprite flagsprite1=game.getPlayer1().getTank().getSprite(0,"L");
                    flagsprite1.setPosition(tankbody1.getPosition().x-flagsprite1.getWidth()/2,tankbody1.getPosition().y-flagsprite1.getHeight()/2);
//                    flagsprite1.setSize(100,100);
//                    flagsprite1.draw(tankbatch);
//                }
//            }
            tankbatch.end();
//            System.out.println("angle 1 : "+game.getPlayer1().getTank().getAngle());
            debugRenderer.render(world,orthographicCamera.combined);
            world.step(TIMESTEP,VECLOCITYIT,POSITIONIT);
            if (bulletDeletionList.size > 0){
                for (Bullet b :
                        bulletDeletionList) {
                    b.clear();
                }
                bulletDeletionList.clear();
            }
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
//                System.out.println("D is pressed");
                movetime[0]=System.currentTimeMillis()/50;
            }if(keycode==Input.Keys.A) {
//                System.out.println("D is pressed");
                movetime[0]=System.currentTimeMillis()/50;
            }
            return true;
        }
        @Override
        public boolean keyUp(int keycode) {
            if(keycode==Input.Keys.D) {
//                System.out.println("D is resleased");
                movetime[1]=System.currentTimeMillis()/50;
                if(currentplayer==0){
                    player1fuellvl-=Math.abs(movetime[1]-movetime[0]);
                }
                else{
                    player2fuellvl-=Math.abs(movetime[1]-movetime[0]);
                }
                changeflag=1;
            }if(keycode==Input.Keys.A) {
//                System.out.println("D is resleased");
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
