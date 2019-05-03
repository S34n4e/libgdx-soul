/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Objects;

import com.badlogic.gdx.utils.Array;

/**
 *
 * @author S34n4e
 */
public class Soul {

    //Database
    private int id;
    private String name;
    private String sprite;
    private int vitbase;
    private int strbase;
    private int magbase;
    private int espbase;
    private int agibase;

    private int cmdCode1;
    private int cmdCode2;
    private int cmdCode3;
    private Passive passiveCode;

    private int fireRes; //100 base???
    private int waterRes;
    private int litRes;

    //Calculables
    private Soul soulPassive1;
    private Soul soulPassive2;
    private Soul soulPassive3;

    private Array<BuffDebuff> buffs;
    private Array<BuffDebuff> debuffs;

    private short lv;
    private int exp;
    private int maxvit;
    private int vit;
    private int str;
    private int mag;
    private int esp;
    private int agi;

    public Soul() {
        this.buffs = new Array<>();
        this.debuffs = new Array<>();
    }

    public final void calc(short level) {
        this.lv = level;
        this.vit = (int) (vitbase * lv * 0.25) - lv + 200;
        this.maxvit = (int) (vitbase * lv * 0.25) - lv + 200;
        this.str = (int) (strbase * lv * 0.02) - lv + 50;
        this.mag = (int) (magbase * lv * 0.02) - lv + 50;
        this.esp = (int) (espbase * lv * 0.02) - lv + 50;
        this.agi = (int) (agibase * lv * 0.02) - lv + 50;
    }
    
    public final void recover(){
        this.vit = this.maxvit;
    }

    public Soul(int id, String name, String sprite, int vitbase, int strbase, int magbase, int espbase, int agibase, int cmdCode1, int cmdCode2, int cmdCode3, Passive passiveCode, int fireRes, int waterRes, int litRes, int lv) {
        this.id = id;
        this.name = name;
        this.sprite = sprite;
        this.vitbase = vitbase;
        this.strbase = strbase;
        this.magbase = magbase;
        this.espbase = espbase;
        this.agibase = agibase;
        this.cmdCode1 = cmdCode1;
        this.cmdCode2 = cmdCode2;
        this.cmdCode3 = cmdCode3;
        this.passiveCode = passiveCode;
        this.fireRes = fireRes;
        this.waterRes = waterRes;
        this.litRes = litRes;
        this.lv = (short) lv;
        this.exp = 0;
        calc((short) lv);
        this.buffs = new Array<>();
        this.debuffs = new Array<>();
        //dmg formula: STAT * MOVEPWR *0.02 * (fireRes*0.01)

    }

    public Soul(int vitbase, int strbase, int magbase, int espbase, int agibase, String name, int lv, int cmdCode1, int cmdCode2, int cmdCode3, Passive passiveCode, Soul soulPassive1, Soul soulPassive2, Soul soulPassive3, int fireRes, int waterRes, int litRes, String sprite) {
        this.vitbase = vitbase;
        this.strbase = strbase;
        this.magbase = magbase;
        this.espbase = espbase;
        this.agibase = agibase;
        this.name = name;
        this.lv = (short) lv;
        this.exp = 0;
        this.cmdCode1 = cmdCode1;
        this.cmdCode2 = cmdCode2;
        this.cmdCode3 = cmdCode3;
        this.passiveCode = passiveCode;
        this.soulPassive1 = soulPassive1;
        this.soulPassive2 = soulPassive2;
        this.soulPassive3 = soulPassive3;
        this.fireRes = fireRes;
        this.waterRes = waterRes;
        this.litRes = litRes;
        this.sprite = sprite;
        calc((short) lv);
        this.buffs = new Array<>();
        this.debuffs = new Array<>();
    }

    public enum Passive {
        ATK10, MAG10, ESP10, AGI10, FIRE10, WATER10, LIGHTNING10, NULLPOISON, NULLSLEEP, NULLDISABLE, NULLBLIND, NO
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSprite() {
        return sprite;
    }

    public int getVitbase() {
        return vitbase;
    }

    public int getStrbase() {
        return strbase;
    }

    public int getMagbase() {
        return magbase;
    }

    public int getEspbase() {
        return espbase;
    }

    public int getAgibase() {
        return agibase;
    }

    public int getCmdCode1() {
        return cmdCode1;
    }

    public int getCmdCode2() {
        return cmdCode2;
    }

    public int getCmdCode3() {
        return cmdCode3;
    }

    public Passive getPassiveCode() {
        return passiveCode;
    }

    public int getFireRes() {
        int modifier = fireRes;
        switch (isBuffActive(Skill.Buff.BARFIRE, false, false)) {
            case 1:
                modifier = (int) (modifier * 0.7);
                break;
            case 2:
                modifier = (int) (modifier * 0.5);
                break;
        }
        return modifier;
    }

    public int getWaterRes() {
        int modifier = waterRes;
        switch (isBuffActive(Skill.Buff.BARWATER, false, false)) {
            case 1:
                modifier = (int) (modifier * 0.7);
                break;
            case 2:
                modifier = (int) (modifier * 0.5);
                break;
        }
        return modifier;
    }

    public int getLitRes() {
        int modifier = litRes;
        switch (isBuffActive(Skill.Buff.BARLIT, false, false)) {
            case 1:
                modifier = (int) (modifier * 0.7);
                break;
            case 2:
                modifier = (int) (modifier * 0.5);
                break;
        }
        return modifier;
    }

    public Soul getSoulPassive1() {
        return soulPassive1;
    }

    public Soul getSoulPassive2() {
        return soulPassive2;
    }

    public Soul getSoulPassive3() {
        return soulPassive3;
    }

    public Array<BuffDebuff> getBuffs() {
        return buffs;
    }

    public Array<BuffDebuff> getDebuffs() {
        return debuffs;
    }

    public short getLv() {
        return lv;
    }

    public int getExp() {
        return exp;
    }

    public int getTNL() {
        return ((lv * 10) - exp);
    }

    public int getMaxvit() {
        return maxvit;
    }

    public int getVit() {
        return vit;
    }

    public int getStr() {
        int modifier = str;
        switch (isBuffActive(Skill.Buff.ATK30, false, false)) {
            case 1:
                modifier = (int) (str * 1.3);
                break;
            case 2:
                modifier = (int) (str * 1.5);
                break;
        }
        switch (isDebuffActive(Skill.Debuff.ATK, false, false)) {
            case 1:
                modifier = (int) (modifier / 1.3);
                break;
            case 2:
                modifier = (int) (modifier / 1.5);
                break;
        }
        return modifier;
    }

    public int getMag() {
        int modifier = mag;
        switch (isBuffActive(Skill.Buff.MAG30, false, false)) {
            case 1:
                modifier = (int) (mag * 1.3);
                break;
            case 2:
                modifier = (int) (mag * 1.5);
                break;
        }
        switch (isDebuffActive(Skill.Debuff.MAG, false, false)) {
            case 1:
                modifier = (int) (modifier / 1.3);
                break;
            case 2:
                modifier = (int) (modifier / 1.5);
                break;
        }
        return modifier;
    }

    public int getEsp() {
        int modifier = esp;
        switch (isBuffActive(Skill.Buff.ESP30, false, false)) {
            case 1:
                modifier = (int) (esp * 1.3);
                break;
            case 2:
                modifier = (int) (esp * 1.5);
                break;
        }
        switch (isDebuffActive(Skill.Debuff.ESP, false, false)) {
            case 1:
                modifier = (int) (modifier / 1.3);
                break;
            case 2:
                modifier = (int) (modifier / 1.5);
                break;
        }
        return modifier;
    }

    public int getAgi() {
        int modifier = agi;
        switch (isBuffActive(Skill.Buff.AGI30, false, false)) {
            case 1:
                modifier = (int) (agi * 1.3);
                break;
            case 2:
                modifier = (int) (agi * 1.5);
                break;
        }
        switch (isDebuffActive(Skill.Debuff.AGI, false, false)) {
            case 1:
                modifier = (int) (modifier / 1.3);
                break;
            case 2:
                modifier = (int) (modifier / 1.5);
                break;
        }
        return modifier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setVitbase(int vitbase) {
        this.vitbase = vitbase;
    }

    public void setStrbase(int strbase) {
        this.strbase = strbase;
    }

    public void setMagbase(int magbase) {
        this.magbase = magbase;
    }

    public void setEspbase(int espbase) {
        this.espbase = espbase;
    }

    public void setAgibase(int agibase) {
        this.agibase = agibase;
    }

    public void setCmdCode1(int cmdCode1) {
        this.cmdCode1 = cmdCode1;
    }

    public void setCmdCode2(int cmdCode2) {
        this.cmdCode2 = cmdCode2;
    }

    public void setCmdCode3(int cmdCode3) {
        this.cmdCode3 = cmdCode3;
    }

    public void setPassiveCode(Passive passiveCode) {
        this.passiveCode = passiveCode;
    }

    public void setFireRes(int fireRes) {
        this.fireRes = fireRes;
    }

    public void setWaterRes(int waterRes) {
        this.waterRes = waterRes;
    }

    public void setLitRes(int litRes) {
        this.litRes = litRes;
    }

    public void setSoulPassive1(Soul soulPassive1) {
        this.soulPassive1 = soulPassive1;
    }

    public void setSoulPassive2(Soul soulPassive2) {
        this.soulPassive2 = soulPassive2;
    }

    public void setSoulPassive3(Soul soulPassive3) {
        this.soulPassive3 = soulPassive3;
    }

    public void setBuffs(Array<BuffDebuff> buffs) {
        this.buffs = buffs;
    }

    public void setDebuffs(Array<BuffDebuff> debuffs) {
        this.debuffs = debuffs;
    }

    public void setLv(int lv) {
        this.lv = (short) lv;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setMaxvit(int maxvit) {
        this.maxvit = maxvit;
    }

    public void setVit(int vit) {
        this.vit = vit;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setMag(int mag) {
        this.mag = mag;
    }

    public void setEsp(int esp) {
        this.esp = esp;
    }

    public void setAgi(int agi) {
        this.agi = agi;
    }

    /**
     * @return int es el dmg/heal final hecho a este soul
     */
    public int damage(int damage) {
        if (!this.isAlive()) {
            damage = 0;
        }
        this.setVit(this.getVit() - damage);
        if (this.getVit() < 0) {
            this.setVit(0);
        } else if (this.getVit() > this.getMaxvit()) {
            this.setVit(maxvit);
        }
        isDebuffActive(Skill.Debuff.SLEEP, false, true);
        return damage;
    }

    public Soul copy() {
        //Soul soul = new Soul(id, name, sprite, vitbase, strbase, magbase, espbase, agibase, cmdCode1, cmdCode2, cmdCode3, passiveCode, fireRes, waterRes, litRes, lv);
        Soul soul = new Soul(vitbase, strbase, magbase, espbase, agibase, name, lv, cmdCode1, cmdCode2, cmdCode3, passiveCode, soulPassive1, soulPassive2, soulPassive3, fireRes, waterRes, litRes, sprite);
        return soul;
    }

    public String toInfo() {
        return getName() + " LV" + getLv()
                + "\n VIT " + getVit()
                + "\n ATK " + getStr()
                + "\n MAG " + getMag()
                + "\n ESP " + getEsp()
                + "\n AGI " + getAgi();
    }

    public String toString() {
        return getName() + " Nv." + getLv() + "\n";
    }

    public boolean isAlive() {
        return (getVit() > 0);
    }

    public int isBuffActive(Skill.Buff buff, boolean heal, boolean levelDown) { // return 0 is false, return 1 is true, return 2 is true and enhanced

        if (buffs != null) {
            for (int i = 0; i < buffs.size; i++) {
                if (buffs.get(i).getBuff() == buff && buffs.get(i).isEnhanced()) {//Si tiene buff enhanced
                    if (levelDown) {
                        buffs.get(i).setEnhanced(false);
                    }
                    if (heal) {
                        buffs.removeIndex(i);
                    }
                    return 2;
                } else if (buffs.get(i).getBuff() == buff) {// Si tiene buff
                    if (heal || levelDown) {
                        buffs.removeIndex(i);
                    }
                    return 1;
                }
            }
        }
        return 0;
    }

    public int isDebuffActive(Skill.Debuff debuff, boolean heal, boolean levelDown) { // return 0 is false, return 1 is true, return 2 is true and enhanced
        if (debuffs != null) {
            for (int i = 0; i < debuffs.size; i++) {
                if (debuffs.get(i).getDebuff() == debuff && debuffs.get(i).isEnhanced()) {
                    if (levelDown) {
                        debuffs.get(i).setEnhanced(false);
                    }
                    if (heal) {
                        debuffs.removeIndex(i);
                    }
                    return 2;
                } else if (debuffs.get(i).getDebuff() == debuff) {
                    if (heal || levelDown) {
                        debuffs.removeIndex(i);
                    }
                    return 1;
                }
            }
        }
        return 0;
    }

    public void moreExp(short enmALv, short enmBLv) { //NIVEL SE ESTA OBTENIENDO AL TRIPLE!!!
        setExp(getExp() + (enmALv + enmBLv)*3);
        while (getExp() >= (getLv() * 10)) {
            if (getExp() >= (getLv() * 10)) {
                setExp(getExp() - (getLv() * 10));
                setLv(getLv() + 1);
                calc(getLv());
            }
        }
    }
    
    public int getCmd(int cmdNum) {
        switch(cmdNum) {
            case 1:
            default:
                return cmdCode1;
            case 2:
                return cmdCode2;
            case 3:
                return cmdCode3;
        }
    }

}
