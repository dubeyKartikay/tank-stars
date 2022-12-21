package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
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
    private Bullet bullet;
    private Vector2 position=new Vector2();

    private PrismaticJoint bulletJoint;
    public Tank(World world,int variety,int x,int y){
        this.world = world;
        angle=10;
        firepower=50;
        getPosition().x=x;
        getPosition().y=y;
        this.health=100;
        powerups=new PowerUps();
        BodyDef tank1=new BodyDef();
        tank1.type= BodyDef.BodyType.DynamicBody;

        tank1.position.set(x,y);
        bullet = new Bullet(world,x,y);

        FixtureDef tankfixture1=new FixtureDef();
        CircleShape circleShape= new CircleShape();
        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(3,3);
        tankfixture1.shape=polygonShape;
        tankBody=world.createBody(tank1);
        tankBody.createFixture(tankfixture1);
        circleShape.setRadius(1.5f);
        circleShape.setPosition(new Vector2(3,-3));
        tankfixture1.shape=circleShape;
        tankBody.createFixture(tankfixture1);
        circleShape.setPosition(new Vector2(-3,-3));
        tankfixture1.shape=circleShape;
        tankBody.createFixture(tankfixture1);
        getTankBody().createFixture(tankfixture1);
        sprite=getSprite(0,"L");

        PrismaticJointDef defJoint = new PrismaticJointDef ();
        defJoint.initialize(tankBody, bullet.getBody(),new Vector2(0,0),new Vector2(0f, 1));

        bulletJoint = (PrismaticJoint) world.createJoint(defJoint);
        bulletJoint.setLimits(0,0);
        bulletJoint.enableLimit(true);
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

    public Sprite getSprite(int variety, String side){
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
        Sprite ret= new Sprite(new Texture(Gdx.files.internal(path)));
        return ret;
    }
    public void setHealth(int health) {
        this.health = health;
    }

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


    public void fire(){
        world.destroyJoint(bulletJoint);
        bullet.getBody().setLinearVelocity((float) (getFirepower()*Math.cos(getAngle())),(float) (getFirepower()*Math.sin(getAngle())));
        bullet.setCollidable(true);
        bullet = new Bullet(world, (int) getPosition().x, (int) getPosition().y);
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
