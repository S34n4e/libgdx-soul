/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Preloads;

import com.badlogic.gdx.math.Vector2;
import com.s3project.block.Main;

/**
 *
 * @author S34n4e
 */
public class LocateCommons {
    
    private Vector2 newPoints;
    private float screenX;
    private float screenY;
    
    public void init(Main m, float screenX, float screenY, int pointer, int button){
        newPoints = new Vector2(screenX, screenY);
        newPoints = m.view.unproject(newPoints);
        this.screenX = (int) newPoints.x;
        this.screenY = (int) newPoints.y;
        System.out.println( "\t X: " + this.screenX + "\t  Y: " + this.screenY + " " + pointer + " " + button);
    }
    
    public boolean insideXandY(int x1, int x2, int y1, int y2) {
        //System.out.println(" Between" + x1 + " " + x2 + " " + y1 + " " + y2 + " with x and y " + screenX + " " + screenY);
        if(screenX > x1 && screenX < x2 && screenY > y1 && screenY < y2){
        return true;
        }
        return false;
    }
    
    
    
}
