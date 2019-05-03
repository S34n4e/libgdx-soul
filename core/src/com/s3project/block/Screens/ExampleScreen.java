/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.s3project.block.Main;
import com.s3project.block.Preloads.BattleLogic;

/**
 *
 * @author S34n4e
 */
public class ExampleScreen extends InputAdapter implements Screen, InputProcessor {

    private Main m;

    @Override
    public boolean keyUp(int keycode) {
        System.out.println(keycode + " keycode");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        m.lc.init(m, screenX, screenY, pointer, button);
        
        if (m.lc.insideXandY(0, 100, 0, 100)) {
            System.out.println("Zona presionada");
        }
        return false;
    }

    public ExampleScreen(Main main) {
        super();
        m = main;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        m.rc.refresh(m);
        m.sb.begin();
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
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}
