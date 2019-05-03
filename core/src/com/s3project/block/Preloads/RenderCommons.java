/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Preloads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.s3project.block.Main;
import com.s3project.block.Objects.Soul;

/**
 *
 * @author S34n4e
 */
public class RenderCommons {

    public void refresh(Main m) {
        Gdx.gl.glClearColor((float) (0.0), (float) (0.0), (float) (0.0), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m.cam.update();
        m.sb.setProjectionMatrix(m.cam.combined);
    }

    double i = 0;
    double j = 0;
    double k = 0;
    double l = 0;
    int move = 0;
    int move2 = 0;
    int move3 = 0;
    int move4 = 0;
    int switch123 = 1;
    int switch1232 = 1;
    int switch1233 = 1;
    int switch1234 = 1;
    String a;
    String b;
    String c;
    String d;

}
