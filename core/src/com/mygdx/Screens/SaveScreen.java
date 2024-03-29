package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;
import com.mygdx.game.Tank;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;

public class SaveScreen extends GameScreen{
    private  SpriteBatch batch;
    private Texture img;
    private ArrayList<Game> savedScreengames;
    private ArrayList<TextButton> slotbuttons;
    private Table table;

    private TextButton back;

    SaveScreen(Game game){
        super(game);
        savedScreengames=game.getSavedgames();
        slotbuttons=new ArrayList<TextButton>();
        stage = new Stage(new ScreenViewport());
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        slotbuttons.add(new TextButton("slot1",mySkin));
        slotbuttons.add(new TextButton("slot2",mySkin));

        back = new TextButton("back",mySkin);
        back.setPosition(12,662);
        back.setWidth(80);
        back.setHeight(40);
        back.getLabel().setFontScale(1.5f);
        stage.addActor(back);

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setOverlayScreen(new PauseMenu(getGame()));
            }
        });

        slotbuttons.get(0).getLabel().setFontScale(3);
        slotbuttons.get(1).getLabel().setFontScale(3);
        slotbuttons.get(0).addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                    try {
                        serialize(0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int timearr[]=savedScreengames.get(0).getTime();
                    slotbuttons.get(0).getLabel().setText("Slot1-"+Integer.toString(timearr[0])+":"+Integer.toString(timearr[1]));

                getGame().setOverlayScreen( new ConfirmationSaveScreen(getGame()) );
            }
        });slotbuttons.get(1).addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                    try {
                        serialize(1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int timearr[]=savedScreengames.get(1).getTime();
                    slotbuttons.get(1).getLabel().setText("Slot1-"+Integer.toString(timearr[0])+":"+Integer.toString(timearr[1]));
                getGame().setOverlayScreen( new ConfirmationSaveScreen(getGame()) );
            }
        });

        table.setFillParent(true);
        table.setZIndex(0);
        stage.addActor(table);
        table.setDebug(true);
        table.add(slotbuttons.get(0)).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(slotbuttons.get(1)).right().padRight(10).width(300).height(100);
    }
    public void serialize(int idx) throws IOException {
        Game gamecurrent = getGame();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("serializefile.txt"));
            gamecurrent.setSaveplace(idx);
            Calendar now = Calendar.getInstance();
            gamecurrent.setTime(new int[]{now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)});
//
            Tank t1=gamecurrent.getPlayer1().getTank();
            Tank t2=gamecurrent.getPlayer2().getTank();
            oos.writeObject(getGame());
//                oos.writeObject(new Game(t1.getHealth(),t2.getHealth(),t1.getPosition().x,t1.getPosition().y,t2.getPosition().x,t2.getPosition().y,gamecurrent.getPlayer1().getTankIndex(), gamecurrent.getPlayer2().getTankIndex()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(oos!=null){
                oos.close();
            }
        }
    }
    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {
        float delta2 = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(img, 0, 0, 1366, 720);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
