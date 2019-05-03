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
public class BuffDebuff {
    
    private int id;
    private Skill.Buff buff;
    private Skill.Debuff debuff;
    private int remainingTurns;
    private boolean enhanced;

    public BuffDebuff() {
    }

    public BuffDebuff(int id, Skill.Buff name, int remainingTurns, boolean enhanced) {
        this.id = id;
        this.debuff = Skill.Debuff.NO;
        this.buff = name;
        this.remainingTurns = remainingTurns;
        this.enhanced = enhanced;
    }
    
    public BuffDebuff(int id, Skill.Debuff name, int remainingTurns, boolean enhanced) {
        this.id = id;
        this.buff = Skill.Buff.NO;
        this.debuff = name;
        this.remainingTurns = remainingTurns;
        this.enhanced = enhanced;
    }
    
    public void setEnhanced(boolean enhanced) {
        this.enhanced = enhanced;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    public int getId() {
        return id;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public Skill.Buff getBuff() {
        return buff;
    }

    public Skill.Debuff getDebuff() {
        return debuff;
    }

    public void setBuff(Skill.Buff buff) {
        this.buff = buff;
    }

    public void setDebuff(Skill.Debuff debuff) {
        this.debuff = debuff;
    }
    
    public boolean isEnhanced() {
        return enhanced;
    }
    
    
    
}
