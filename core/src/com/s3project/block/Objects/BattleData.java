/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Objects;

/**
 *
 * @author S34n4e
 */
public class BattleData {
    
    private int enm1;
    private int enm2;
    private int lv1;
    private int lv2;
    private String nameOfBattle;
    private int battleStatus;//0 bloqueada, 1 desbloqueada, 2 completada;
    
    public BattleData(){
    }
    
    public BattleData(int enm1, int enm2, int lv1, int lv2, String nameOfBattle, int battleStatus){
        this.enm1 = enm1;
        this.enm2 = enm2;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.nameOfBattle = nameOfBattle;
        this.battleStatus = battleStatus;
    }

    public int getEnm1() {
        return enm1;
    }

    public int getEnm2() {
        return enm2;
    }

    public int getLv1() {
        return lv1;
    }

    public int getLv2() {
        return lv2;
    }

    public String getNameOfBattle() {
        return nameOfBattle;
    }

    public int getBattleStatus() {
        return battleStatus;
    }
    
    
    
}
