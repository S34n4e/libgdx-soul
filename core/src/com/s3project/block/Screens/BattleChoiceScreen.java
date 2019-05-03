/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.s3project.block.Main;
import com.s3project.block.Objects.Soul;

/**
 *
 * @author S34n4e
 */
public class BattleChoiceScreen implements ApplicationListener, Screen {

    private Main m;
    private int index;

    public BattleChoiceScreen(Main m) {
        super();
        this.m = m;
        create();
    }

    @Override
    public void resize(int width, int height) {
        m.view.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }

    public void setMain(Main m) {
        this.m = m;
    }

    @Override
    public void create() {
        if (m == null) {
            m = new Main();
        }
        index = 0;
        m.stage.addActor(createHub(m.tex.getBG("bg2"), 1280, 720, 0, 0, Color.WHITE));
        m.stage.addActor(createLayoutList(m.skin, "default", 200 * m.g.allBattles.size, 220, 0, 480));
        //m.stage.addActor(createBtnToBattle(m.tex.get3(m.g.allSouls.get(1).getName()), 200, 200, 0, 0, Color.WHITE));
        m.stage.addActor(createBackButton(m.skin, "default", 150, 50, 0, 0));
        m.stage.addActor(createBtnToBattle(m.skin, "default", 150, 50, 150, 0));
    }

    @Override
    public void render() {
        m.rc.refresh(m);
        m.sb.begin();
        m.stage.draw();
        m.stage.act(Gdx.graphics.getDeltaTime());
        m.sb.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        render();
    }

    @Override
    public void hide() {
    }

        /*Metodo repetido en BattleScreen*/
    public Image createHub(TextureAtlas.AtlasRegion a, int w, int h, int x, int y, Color color) {
        Image temp = new Image(a);
        temp.setPosition((x), (y));
        temp.setSize(w, h);
        temp.setColor(color);
        temp.setTouchable(Touchable.disabled);
        return temp;
    }
    
    public TextButton createBtnToBattle(Skin skin, String adefault, int w, int h, int x, int y){
        TextButton temp = new TextButton("Al combate",skin, adefault);
        temp.setPosition(x,y);
        temp.setSize(w, h);
        temp.addListener(new ClickListener(){
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                m.battleCode = index;
                m.changeScreen(2);
            }
        });
        return temp;
    }
    
    /*Metodo igual en ChangeSoulScreen*/
    private Actor createLayoutList(Skin skin, String adefault, int w, int h, int x, int y) {

        Table temp = new Table(skin);
        temp.setBounds(x, y, w, h);
        Soul tempSoul;
        for (int i = 0; i < m.g.allBattles.size; i++) {
            tempSoul = m.g.allSouls.get(m.g.allBattles.get(i).getEnm1());
            temp.add(createPortrait(m.tex.get3(tempSoul.getSprite()), 200, 200, 0, 0, Color.WHITE, i)).expand(200, 200);
        }
        ScrollPane sp = new ScrollPane(temp, skin);
        sp.setBounds(0, y, 1280, h);
        sp.setScrollingDisabled(false, true);
        return sp;
    }
    
    /*Metodo igual en ChangeSoulScreen*/
    private Actor createPortrait(TextureAtlas.AtlasRegion img, int w, int h, int x, int y, Color color, final int choice) {
        final Image temp = new Image(img);
        temp.setBounds(x, y, w, h);
        temp.setColor(color);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                index = choice;
            }
        });
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                if (choice == index) {
                    temp.setColor(Color.WHITE);
                } else {
                    temp.setColor(Color.GRAY);
                }
                return false;
            }
        });
        return temp;
    }
    
    public void getBattleNum(int i) {
        switch(i) {
            case 1:
                
                break;
            default:
                
                break;
        }
    }
    
    /*repetido en ChangeSoulScreen*/
    private Actor createBackButton(Skin skin, String adefault, int w, int h, int x, int y) {
        TextButton temp = new TextButton("Volver", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                m.changeScreen(4);
            }
        });
        return temp;
    }
    
}
