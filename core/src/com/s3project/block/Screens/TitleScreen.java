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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.s3project.block.Main;

/**
 *
 * @author S34n4e
 */
public final class TitleScreen implements ApplicationListener, Screen {

    private Main m;

    public TitleScreen(Main m) {
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
        m.stage.addActor(createHub(m.tex.getBG("bg2"), 1280, 720, 0, 0, Color.WHITE));
        m.stage.addActor(createButtonImg(m.tex.get("circleBattle"), 200, 200, 206, 360, Color.WHITE, 6));
        m.stage.addActor(createButtonImg(m.tex.get("circleTeam"), 200, 200, 718, 180, Color.WHITE, 3));
        m.stage.addActor(createButtonImg(m.tex.get("circleHelp"), 200, 200, 462, 180, Color.WHITE, 4));
        m.sound(1);
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

    private Actor createButtonImg(TextureAtlas.AtlasRegion img, int w, int h, int x, int y, Color color, final int choice) {
        final Image temp = new Image(img);
        temp.setBounds(x, y, w, h);
        temp.setColor(color);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                temp.setColor(Color.GRAY);
                m.changeScreen(choice);
            }
        });
        return temp;
    }

}
