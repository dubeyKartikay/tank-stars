package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler implements ContactListener {

    private Array<Bullet> bulletDeletionList;

    CollisionHandler(Array<Bullet> bulletDeletionList){

        this.bulletDeletionList = bulletDeletionList;
    }
    @Override
    public void beginContact(Contact contact) {
//        System.out.println("Collision happened");
        int res = classifyCollision(contact);
        if (res >= 1 && res !=3){
            bulletExplode(contact,res);
        }
        if (res == 3){
            System.out.println("TAnk came in contact");

            setTankTouchingGround(contact,true);
        }

    }

    private void setTankTouchingGround(Contact contact,boolean val){
        Object o1 = contact.getFixtureA().getBody().getUserData();
        Object o2 = contact.getFixtureB().getBody().getUserData();
        Tank k = o1.getClass() == Tank.class ? (Tank) o1:(Tank) o2;
        k.setTouchingGround(val);

    }
    private int classifyCollision(Contact contact){
        Object o1 = contact.getFixtureA().getBody().getUserData();
        Object o2 = contact.getFixtureB().getBody().getUserData();

        if (o1 == null || o2 == null){
            return -1;
        }
//        System.out.println("A" + contact.getFixtureA().getBody().getUserData().getClass());
//        System.out.println("B" + contact.getFixtureB().getBody().getUserData().getClass());
        if (o1.getClass() == Bullet.class && o2.getClass() == Bullet.class ){

            return 0;
        }else if ((o1.getClass() == Bullet.class && o2.getClass() == Tank.class) || (o1.getClass() == Tank.class && o2.getClass() == Bullet.class) ){

            return 1;
        }else if ((o1.getClass() == Bullet.class && o2.getClass() == Ground.class) || (o1.getClass() == Ground.class && o2.getClass() == Bullet.class)){
            return 2;
        }
        else if ((o1.getClass() == Tank.class && o2.getClass() == Ground.class) || (o1.getClass() == Ground.class && o2.getClass() == Tank.class)){
            return 3;
        }
        return -1;
    }


    public List<Object> getObjectsInRange(World world,float x, float y, float x2, float y2) {
        final List<Fixture> list = new ArrayList<Fixture>();

        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                System.out.println(fixture.getBody().getUserData().getClass());
                list.add(fixture);
                return true;
            }
        },x,y,x2,y2);
        List<Object> l = new ArrayList<Object>();
        for (Fixture fixture : list) {
            l.add((Object) fixture.getBody().getUserData());
        }
        return l;
    }
    private void bulletExplode(Contact contact,int res){
        Object o1 = contact.getFixtureA().getBody().getUserData();
        Object o2 = contact.getFixtureB().getBody().getUserData();
        Bullet b = o1.getClass() == Bullet.class ? (Bullet) o1:(Bullet) o2;
        Object o = o1.getClass() != Bullet.class ?  o1:o2;
        System.out.println("HERE");
        Array<Body> bodies = new Array<>();
        b.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData().getClass() == Tank.class && body!=b.getTank().getTankBody()){
                float distance  = b.getBody().getPosition().dst(body.getPosition());
                System.out.println(distance);
                if (distance < 25){
                    System.out.println("EXPLOSS");
                    Vector2 vec = new Vector2(body.getPosition().x - b.getBody().getPosition().x,body.getPosition().y - b.getBody().getPosition().y);
                    vec.scl(1/vec.len());
                    vec.scl(b.getDamage()*25/distance);
//                    vec.setLength((b.getDamage()));

                    System.out.println(vec);
//                    vec.scl((float) (b.getDamage()));
                    body.setLinearVelocity(vec);

                    ((Tank)body.getUserData()).applyDamage(b.getDamage());
                }
            }
        }

       if ((o.getClass() == Tank.class && ((Tank)o).getTankBody() != b.getTank().getTankBody()) || o.getClass() == Ground.class){
           bulletDeletionList.add(b);
       }


        //        List<Object> aabb = getObjectsInRange(b.getWorld(),b.getX() - 50,b.getY() - 50,b.getX() + 50,b.getY() + 50);

    }
    @Override
    public void endContact(Contact contact) {
        int res = classifyCollision(contact);
        if (res == 3){
            System.out.println("TAnk ended contact");
            setTankTouchingGround(contact,false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        int res = classifyCollision(contact);
        switch (res){
            case 0:
                contact.setEnabled(false);
                break;

        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
