package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Game;
import sun.tools.jconsole.Tab;

public class TankSelect extends GameScreen{

    Table table;
    Texture bg;

    Image currPlayer1Tank;
    Image currPlayer2Tank;

     int currPlayer1TankInd = 0;
    int currPlayer2TankInd = 0;
    TextButton p1l;
    TextButton p1r;
    TextButton p2r;
    TextButton p2l;

    TextButton play;

    TextButton back;

    TankSelect(Game game){
        super(game);
        bg = new Texture(Gdx.files.internal("img/tanksel.png"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        currPlayer1Tank = new Image( new Texture(Gdx.files.internal("img/tanks/"+currPlayer1TankInd+".png")) );
        currPlayer2Tank = new Image( new Texture(Gdx.files.internal("img/tanks/"+currPlayer2TankInd+".png")) );
        Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        p1l = new TextButton("<",mySkin);
//        p1l.setPosition(100,500);
        p1r = new TextButton(">",mySkin);
//        p1r.setPosition(400,500);
        p2l = new TextButton("<",mySkin);
//        p2l.setPosition(900,500);
        p2r = new TextButton(">",mySkin);

        play = new TextButton("Play",mySkin);

        play.getLabel().setFontScale(3);

        back = new TextButton("Back",mySkin);
        back.getLabel().setFontScale(1.5f);
//        p2r.setPosition(1300,500);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        p1l.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currPlayer1TankInd--;
                if(currPlayer1TankInd<0){
                    currPlayer1TankInd=2;
                }
                currPlayer1Tank.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("img/tanks/"+(currPlayer1TankInd)%3+".png"))));

            }
        });
        p2l.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currPlayer2Tank.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("img/tanks/"+(++currPlayer2TankInd)%3+".png"))));

            }
        });

        p1r.addListener(new ClickListener(){


            @Override
            public void clicked(InputEvent event, float x, float y) {
                currPlayer1TankInd--;
                if(currPlayer1TankInd<0){
                    currPlayer1TankInd=2;
                }
                currPlayer1Tank.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("img/tanks/"+(currPlayer1TankInd)%3+".png"))));

            }
        });

        p2r.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currPlayer2Tank.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("img/tanks/"+(++currPlayer2TankInd)%3+".png"))));

            }
        });
        back.addListener(
                new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getGame().setCurrScreen(new MainMenu(getGame()));
                    }
                }
        );

        play.addListener(
                new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    getGame().setCurrScreen(new PlayScreen(getGame()));
                    }
                }
        );
        back.setPosition(12,662);
        back.setWidth(80);
        back.setHeight(40);
        stage.addActor(back);
        table.padTop(200);
        table.add(p1l).width(50).height(50);
        table.add(currPlayer1Tank).width(500).maxHeight(500);
        table.add(p1r).width(50).height(50).padRight(50);

        table.add(p2l).width(50).height(50).padLeft(50);
        table.add(currPlayer2Tank).width(500).maxHeight(500);
        table.add(p2r).width(50).height(50);
        table.row();
        table.add(play).width(300).height(80).colspan(10).padBottom(20);



    }

    @Override
    public void update(float delta) {


    }

    @Override
    public void draw(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0, 1366, 720);
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
