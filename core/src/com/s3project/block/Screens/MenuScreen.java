/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.s3project.block.Main;
import com.s3project.block.Objects.Soul;

/**
 *
 * @author S34n4e
 */
public final class MenuScreen implements ApplicationListener, Screen {

    private Main m;
    private int index;
    private boolean isRefreshList;

    public MenuScreen(Main m) {
        super();
        this.m = m;
        create();
    }

    @Override
    public void create() {
        index = 0;
        isRefreshList = true;
        if (m == null) {
            m = new Main();
        }
        m.stage.addActor(createTable(m.skin, "default", 720, 1280, 0, 0));
        m.stage.addActor(createSoulData(m.skin, "default", 720, 1280, 0, 0));
        m.stage.addActor(createTexture(m.skin, "default", 1000, 200));
        m.stage.addActor(createList(m.skin2, "default", 0, 0, 0, 0));
        m.stage.addActor(createScrollPane(m.skin, "default", 0, 0, 0, 0, m.stage.getActors().get(m.stage.getActors().size - 1))); //Actor anterior a este
        m.stage.addActor(createDetailedInfo(m.skin2, "default", 0, 0, 0, 0));
        m.stage.addActor(createWindow(
                m.stage.getActors().get(m.stage.getActors().size - 2),//2 actor anteriores a este
                m.stage.getActors().get(m.stage.getActors().size - 1),//1 actor anterior a este
                m.skin, "default", 680, 1280, 0, 0));
        m.stage.addActor(createBtnUpDown(m.skin, "default", 50, 200, 1080, 0));
        m.stage.addActor(createBtnToParty(m.skin, "default", 50, 200, 880, 0));
        m.stage.addActor(createBtnToTitle(m.skin, "default", 50, 200, 680, 0));
        m.stage.addAction(Actions.fadeIn(0.1f));
    }

    public void setMain(Main m) {
        this.m = m;
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
        //batch.dispose();
    }

    private Actor createWindow(Actor a, Actor b, Skin skin, String adefault, int h, int w, int x, int y) {
        SplitPane temp = new SplitPane(a, b, false, skin);
        temp.setBounds(x, y, w, h);
        return temp;
    }

    private Actor createSoulData(Skin skin, String adefault, int h, int w, int x, int y) {
        Actor temp = new Window("Información de almas", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.setTouchable(Touchable.disabled);
        return temp;
    }

    private Actor createScrollPane(Skin skin, String adefault, int h, int w, int x, int y, Actor child) {
        final Actor temp = new ScrollPane(child, skin, adefault);
        temp.setBounds(x, y, w, h);
        return temp;
    }

    ;
    
    private Actor createList(Skin skin, String adefault, int h, int w, int x, int y) {
        final List temp = new List(skin, adefault);
        temp.setItems(m.g.mySouls);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                if (temp.getSelectedIndex() >= 0) {
                    index = temp.getSelectedIndex();
                } else {
                    index = 0;
                }
            }
        });
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {

                if (isRefreshList) {
                    temp.setItems(m.g.mySouls);
                    isRefreshList = false;
                }

                return false;
            }
        });
        return temp;
    }

    private Actor createDetailedInfo(Skin skin, String adefault, int h, int w, int x, int y) {
        final List temp = new List(skin);
        m.g.mySouls.get(index);
        temp.setBounds(x, y, w, h);
        temp.setColor(0.5f, 0.5f, 0.5f, 0.9f);
        temp.clearItems();
        temp.setTouchable(Touchable.disabled);
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {

                temp.setItems(
                        m.g.mySouls.get(index).getName(),
                        "Nivel " + m.g.mySouls.get(index).getLv() + " \t / Exp. " + m.g.mySouls.get(index).getExp() + "\t / Sig. Nv. " + (m.g.mySouls.get(index).getLv() * 10),
                        "Vitalidad " + m.g.mySouls.get(index).getMaxvit() + " / " + m.g.mySouls.get(index).getVit(),
                        "Ataque " + m.g.mySouls.get(index).getStr(),
                        "Magia " + m.g.mySouls.get(index).getMag(),
                        "Espíritu " + m.g.mySouls.get(index).getEsp(),
                        "Agilidad " + m.g.mySouls.get(index).getAgi(),
                        "Habilidad pasiva: " + m.g.mySouls.get(index).getPassiveCode(),
                        "Resistencia al fuego: " + m.g.mySouls.get(index).getFireRes(),
                        "Resistencia al agua: " + m.g.mySouls.get(index).getWaterRes(),
                        "Resistencia al rayo: " + m.g.mySouls.get(index).getLitRes(),
                        "Habilidades activas: ",
                        m.g.allSkills.get(m.g.mySouls.get(index).getCmdCode1()).getName(),
                        m.g.allSkills.get(m.g.mySouls.get(index).getCmdCode2()).getName(),
                        m.g.allSkills.get(m.g.mySouls.get(index).getCmdCode3()).getName()
                );

                return false;
            }
        });
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {

            }
        });
        return temp;
    }

    private Actor createBtnUpDown(Skin skin, String adefault, int h, int w, int x, int y) {
        final TextButton temp = new TextButton("Iniciar batalla", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                m.changeScreen(2);
                MenuScreen.this.dispose();
            }
        });
        return temp;
    }

    private Actor createBtnToParty(Skin skin, String adefault, int h, int w, int x, int y) {
        final TextButton temp = new TextButton("Equipar alma", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                Soul tempsoul = m.g.mySouls.removeIndex(index);
                m.g.mySouls.insert(0, tempsoul);
                index = 0;
                isRefreshList = true;
            }
        });
        return temp;
    }
    
    private Actor createBtnToTitle(Skin skin, String adefault, int h, int w, int x, int y) {
        final TextButton temp = new TextButton("Regresar al título", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                m.changeScreen(4);
            }
        });
        return temp;
    }

    private Actor createTexture(Skin skin, String adefault, final int x, final int y) {
        final String sprite = m.g.mySouls.get(index).getSprite();
        final Image temp = new Image(m.tex.get2(sprite));
        temp.setTouchable(Touchable.disabled);
        temp.setPosition((x) - (temp.getWidth() / 2), (y) - (temp.getHeight() / 2));
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                temp.setBounds((x) - (temp.getWidth() / 2), (y) - (temp.getHeight() / 2), temp.getPrefWidth(), temp.getPrefHeight());
                temp.setDrawable(new SpriteDrawable(new Sprite(m.tex.get2(m.g.mySouls.get(index).getSprite()))));

                return false;
            }
        });

        return temp;
    }

    private Actor createTable(Skin skin, String adefault, int h, int w, int x, int y) {
        Actor temp = new Table(skin);
        temp.setBounds(x, y, w, h);
        return temp;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        render();
    }

    @Override
    public void hide() {
    }

}
