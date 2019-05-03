/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Objects;

import com.s3project.block.Main;

/**
 *
 * @author S34n4e
 */
public class Singleton {
    
    private static Main m;
    
    private Singleton() {
            m = new Main();
            m.create();
    }
    
    public static Main m() {
        if(m == null) {
            m = new Main();
            m.create();
        }
        return m;
    }
    
}
