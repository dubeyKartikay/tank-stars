package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.Constants;

public class GameLoop {
    Game game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body groundBody;
    private SpriteBatch batch;
    private Texture groundTexture;
    private static GameLoop gameLoop;

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
        this.game = game;
        // Set up the camera and debug renderer
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);

        debugRenderer = new Box2DDebugRenderer();

        // Create a new Box2D world
        world = new World(new Vector2(0, -9.81f), true);

        // Create a ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, 0);
        groundBody = world.createBody(groundBodyDef);

        // Create a shape for the ground body
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {
                new Vector2(-10* Constants.getPPM(), 1* Constants.getPPM()),
                new Vector2(-9* Constants.getPPM(), 2* Constants.getPPM()),
                new Vector2(-8* Constants.getPPM(), 1* Constants.getPPM()),
                new Vector2(-7* Constants.getPPM(), 0* Constants.getPPM()),
                new Vector2(-6* Constants.getPPM(), 1* Constants.getPPM()),
                new Vector2(6* Constants.getPPM(), 1* Constants.getPPM()),
                new Vector2(7* Constants.getPPM(), 2* Constants.getPPM()),
                new Vector2(8* Constants.getPPM(), 1* Constants.getPPM()),
                new Vector2(9* Constants.getPPM(), 0* Constants.getPPM()),
                new Vector2(10* Constants.getPPM(), 1* Constants.getPPM())
        });
        groundVertices = new Vector2[groundShape.getVertexCount()];
        for (int i = 0; i < groundShape.getVertexCount(); i++) {
            groundVertices[i] = new Vector2();
            groundShape.getVertex(i,groundVertices[i]);
        }
        // Create a fixture for the ground body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        groundBody.createFixture(fixtureDef);

        // Dispose of the shape when we're done
        groundShape.dispose();

        // Load the ground texture
        groundTexture = new Texture("textures/ground.png");

        // Create a sprite batch for rendering
        batch = new SpriteBatch();
    }

    public void update(){

    }

    private void drawGround(){
        Vector2[] vertices = this.groundVertices;
        for (int i = 0; i < vertices.length - 1; i++) {
            Vector2 v1 = vertices[i];
            Vector2 v2 = vertices[i + 1];
            batch.draw(groundTexture, v1.x, v1.y - 0.5f, v2.x - v1.x, v2.y);
        }
    }
    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        camera.position.set(groundBody.getPosition().x, groundBody.getPosition().y, 0);
        camera.update();
        // Draw the ground texture
        Vector2[] vertices = this.groundVertices;
        for (int i = 0; i < vertices.length - 1; i++) {
            Vector2 v1 = vertices[i];
            Vector2 v2 = vertices[i + 1];
            batch.begin();
            batch.draw(groundTexture,Gdx.graphics.getWidth()/2 + v1.x,Gdx.graphics.getHeight()/2 + (v1.y - 0.5f), v2.x - v1.x,Gdx.graphics.getHeight()/2 + (v1.y - 0.5f));
            batch.end();
        }

        // Render the Box2D debug data
//        debugRenderer.render(world, camera.combined);
    }
    public void dispose () {
        // Dispose of resources when they are no longer needed
        groundTexture.dispose();
        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

}
