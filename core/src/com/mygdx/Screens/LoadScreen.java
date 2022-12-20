package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoadScreen extends GameScreen{
    SpriteBatch batch;
    Texture img;
    private Table table;
    TextButton slot1;
    TextButton slot2;
    TextButton back;
    private ArrayList<Game> toloadgames;

    LoadScreen(Game game) {
        super(game);
        toloadgames=game.getSavedgames();
        stage = new Stage(new ScreenViewport());
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        slot1 = new TextButton("slot1",mySkin);
        slot2 = new TextButton("slot2",mySkin);

        back = new TextButton("back",mySkin);
        back.setPosition(12,662);
        back.setWidth(80);
        back.setHeight(40);
        back.getLabel().setFontScale(1.5f);
        stage.addActor(back);
        slot1.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                if(toloadgames.get(0)!=null){
                    try {
                        deserialize(0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                getGame().setOverlayScreen( new ConfirmationSaveScreen(getGame()) );
            }
        });
        slot2.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                if(toloadgames.get(1)!=null){
                    try {
                        deserialize(1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                getGame().setOverlayScreen( new ConfirmationSaveScreen(getGame()) );
            }
        });
        if(toloadgames.get(0)!=null){
            int timearr[]=toloadgames.get(0).getTime();
            slot1.getLabel().setText("Slot1-"+Integer.toString(timearr[0])+":"+Integer.toString(timearr[1]));
        }
//        else{
//            slot1.getLabel().setText("SLOT-1");
//        }
        if(toloadgames.get(1)!=null){
            int timearr[]=toloadgames.get(1).getTime();
            slot2.getLabel().setText("Slot2-"+Integer.toString(timearr[0])+":"+Integer.toString(timearr[1]));
        }
//        else{
//            slot2.getLabel().setText("SLOT-2");
//        }
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setCurrScreen(new MainMenu(getGame()));
            }
        });
        slot1.getLabel().setFontScale(3);
        slot2.getLabel().setFontScale(3);

        table.setFillParent(true);
        table.setZIndex(0);
        stage.addActor(table);
        table.setDebug(true);
        table.add(slot1).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(slot2).right().padRight(10).width(300).height(100);

    }

    @Override
    public void update(float delta) {

    }
    public void deserialize(int idx) throws IOException {
        ObjectInputStream ois=null;
        ArrayList<Game>gamedes=new ArrayList<Game>(2);
//        Game returngame;
        try{
            ois=new ObjectInputStream(new FileInputStream("serializefile.txt"));
//            if(toloadgames.get(idx)!=null){
                Game game1=(Game) ois.readObject();
                if(game1.getSaveplace()!=idx){
                    game1=(Game)ois.readObject();
                }
                getGame().setCurrScreen(new PlayScreen(game1));
//            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(ois!=null){
                ois.close();
            }
        }
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
