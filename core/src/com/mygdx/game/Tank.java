package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;

import java.io.Serializable;
import java.util.Objects;

public class Tank implements Serializable {
    private int fuel,health;
    private double angle,firepower;
    private Body body;
    private PowerUps powerups;
    private Sprite sprite;
    private Body tankBody;

    private World world;
    private Texture texture;
    private Bullet bullet;
    private Vector2 position=new Vector2();

    public void applyDamage(int damage){
        this.health-=damage;
    }
    private PrismaticJoint bulletJoint;
    public Tank(World world,int variety,String side,int x,int y){
        if(side== "L"){
            setAngle(0);
        }if(side == "R"){
            setAngle(180);
        }
        this.world = world;
//        angle=10;
        firepower=50;
        getPosition().x=x;
        getPosition().y=y;
        this.health=100;
        powerups=new PowerUps();
        BodyDef tank1=new BodyDef();
        tank1.type= BodyDef.BodyType.DynamicBody;

        tank1.position.set(x,y);
        bullet = new Bullet(world,x,y,this);

        FixtureDef tankfixture1=new FixtureDef();
        CircleShape circleShape= new CircleShape();
        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(3,3);
        tankfixture1.shape=polygonShape;
        tankfixture1.density =10;
        tankBody=world.createBody(tank1);
        tankBody.createFixture(tankfixture1);
        circleShape.setRadius(1.5f);
        circleShape.setPosition(new Vector2(3,-3));
        tankfixture1.shape=circleShape;
        tankfixture1.restitution=0;

        tankBody.createFixture(tankfixture1);
        circleShape.setPosition(new Vector2(-3,-3));
        tankfixture1.shape=circleShape;
        tankBody.createFixture(tankfixture1);
        tankfixture1.restitution=0;
        getTankBody().createFixture(tankfixture1);
//        sprite=getSprite(0,"L");
        tankBody.setUserData(this);
        texture=makeTexture(variety,side);
        PrismaticJointDef defJoint = new PrismaticJointDef ();
        defJoint.initialize(tankBody, bullet.getBody(),new Vector2(0,0),new Vector2(0f, 1));
        tankBody.setFixedRotation(true);
        bulletJoint = (PrismaticJoint) world.createJoint(defJoint);
        bulletJoint.setLimits(0,0);
        bulletJoint.enableLimit(true);
    }

    public Texture getTexture() {
        return texture;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getFirepower() {
        return firepower;
    }

    public void setFirepower(double firepower) {
        this.firepower = firepower;
    }

    public Texture makeTexture(int variety, String side){
        String path="img/tanks/";
        if(Objects.equals(side, "L")){
            if(variety==0){
                path+="0";
            } else if (variety==1) {
                path+="1";
            }
            else{
                path+="2";
            }
        }
        else{
            if(variety==0){
                path+="0_leftfacing";
            } else if (variety==1) {
                path+="1_leftfacing";
            }
            else{
                path+="3_leftfacing";
            }
        }
        path+=".png";
        Texture ret=(new Texture(Gdx.files.internal(path)));
        return ret;
    }
    public void setHealth(int health) {
        this.health = health;
    }
//    get
    public int getHealth() {
        return health;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getFuel() {
        return fuel;
    }

    public void refuel(){
        this.fuel=100;
    }
    public void zerofuelOpponentPU(Tank tank){
        tank.setFuel(0);
    }

    public Body getTankBody() {
        return tankBody;
    }

    public void setTankBody(Body tankBody) {
        this.tankBody = tankBody;
    }

    private boolean TouchingGround = false;
    public boolean isTouchingGround(){
        return TouchingGround;
    }

    public void setTouchingGround(boolean touchingGround){
        TouchingGround = touchingGround;
    }
    public void fire(){
        world.destroyJoint(bulletJoint);
//        bullet.getBody().setTransform(2,2,bullet.getBody().getAngle());
        bullet.getBody().setLinearVelocity((float) (getFirepower()*Math.cos(getAngle()*Math.PI/180)),(float) (getFirepower()*Math.sin(getAngle()*Math.PI/180)));
        bullet.setCollidable(true);
        bullet = new Bullet(world, (int) getPosition().x, (int) getPosition().y,this);
        PrismaticJointDef defJoint = new PrismaticJointDef ();
        defJoint.initialize(tankBody, bullet.getBody(),new Vector2(0,0),new Vector2(0f, 1));

        bulletJoint = (PrismaticJoint) world.createJoint(defJoint);
        bulletJoint.setLimits(0,0);
        bulletJoint.enableLimit(true);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
//    public void refill()
//    public void
}
