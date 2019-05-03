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
public class Skill {

    private int id;
    private String name;
    private String description;
    private int power;
    private Type type;
    private Target target;
    private Stat stat;
    private Element element;
    private Buff buff1;
    private Buff buff2;
    private Debuff deb1;
    private Debuff deb2;
    private Cooldown cdw;
    private int chargeTime;
    private boolean ready;
    private int sound;
    private int animation;
    private final int maxAtb = 50000;

    public Skill() {
    }

    public Skill(int id, String name, String description, int power, Type type, Target target, Stat stat, Element element,
            Buff buff1, Buff buff2, Debuff deb1, Debuff deb2, Cooldown cdw, int sound, int animation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.power = power;
        this.type = type;
        this.target = target;
        this.stat = stat;
        this.element = element;
        this.buff1 = buff1;
        this.buff2 = buff2;
        this.deb1 = deb1;
        this.deb2 = deb2;
        this.cdw = cdw;
        this.sound = sound;
        this.animation = animation;
        this.chargeTime = 1;
        this.ready = false;

    }

    public int getCdwTime(int maxAtb) {
        switch (cdw) {
            case VFAST:
                return (int) (maxAtb * 0.25) - 1;
            case FAST:
                return (int) (maxAtb * 0.5) - 1;
            case SLOW:
                return (int) (maxAtb * 0.75) - 1;
            case VSLOW:
                return (int) (maxAtb * 1.0) - 1;
        }
        return 0;
    }

    public enum Element {
        FIRE, WATER, LIGHTNING, NO
    }

    public enum Type {
        DMG, DMGDEBUFF, DMGBUFF, HEAL, HEALBUFF, DMGHEAL, DRAIN, BUFF, DEBUFF, NO
    }

    public enum Cooldown {
        VSLOW, SLOW, FAST, VFAST
    }

    public enum Target {
        USER, FOE, FOES, ALLY, ALLIES, ALL, NO
    }

    public enum Stat {
        VIT, ATK, MAG, ESP, AGI, NO
    }

    public enum Buff {
        ATK30, MAG30, ESP30, AGI30, BARFIRE, BARWATER, BARLIT, REGEN, ASTRA, NO
    }

    public enum Debuff {
        ATK, MAG, ESP, AGI, POISON, BLIND, SLEEP, DISABLE, NO
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setBuff1(Buff buff1) {
        this.buff1 = buff1;
    }

    public void setBuff2(Buff buff2) {
        this.buff2 = buff2;
    }

    public void setDeb1(Debuff deb1) {
        this.deb1 = deb1;
    }

    public void setDeb2(Debuff deb2) {
        this.deb2 = deb2;
    }

    public void setCdw(Cooldown cdw) {
        this.cdw = cdw;
    }

    public void setChargeTime(int chargeTime) {
        this.chargeTime = chargeTime;
        if (this.chargeTime > maxAtb) {
            this.chargeTime = maxAtb;
        }
    }

    public void charge(int speedModifier, int battleSpd) {
        speedModifier = speedModifier / 10;
        if (speedModifier == 0) {
            battleSpd = 3;
        }
        switch (battleSpd) {
            case 0:
            default:
                break;
            case 3:
                if (isReady()) {
                    chargeTime = maxAtb - 10;
                    setReady(false);
                }
                break;
            case 1:
                switch (cdw) {
                    case VFAST:
                    default:
                        this.chargeTime = chargeTime + (speedModifier + 100) / 2;
                        break;
                    case FAST:
                        this.chargeTime = chargeTime + (speedModifier + 80) / 2;
                        break;
                    case SLOW:
                        this.chargeTime = chargeTime + (speedModifier + 60) / 2;
                        break;
                    case VSLOW:
                        this.chargeTime = chargeTime + (speedModifier + 40) / 2;
                        break;
                }
                break;
            case 2:
                switch (cdw) {
                    case VFAST:
                    default:
                        this.chargeTime = chargeTime + speedModifier + 100;
                        break;
                    case FAST:
                        this.chargeTime = chargeTime + speedModifier + 80;
                        break;
                    case SLOW:
                        this.chargeTime = chargeTime + speedModifier + 60;
                        break;
                    case VSLOW:
                        this.chargeTime = chargeTime + speedModifier + 40;
                        break;
                }
                break;
        }

        if (this.chargeTime > maxAtb) {
            this.ready = true;
            this.chargeTime = maxAtb;
        } else if (this.chargeTime < maxAtb) {
            this.ready = false;
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPower() {
        return power;
    }

    public Type getType() {
        return type;
    }

    public Target getTarget() {
        return target;
    }

    public Stat getStat() {
        return stat;
    }

    public Element getElement() {
        return element;
    }

    public Buff getBuff1() {
        return buff1;
    }

    public Buff getBuff2() {
        return buff2;
    }

    public Debuff getDeb1() {
        return deb1;
    }

    public Debuff getDeb2() {
        return deb2;
    }

    public Cooldown getCdw() {
        return cdw;
    }

    public int getChargeTime() {
        return chargeTime;
    }

    public boolean isReady() {
        return ready;
    }

    public int getSound() {
        return sound;
    }

    public int getAnimation() {
        return animation;
    }

    public Skill copy() {
        Skill skill = new Skill(id, name, description, power, type, target, stat, element, buff1, buff2, deb1, deb2, cdw, sound, animation);
        return skill;
    }

    public String getCooldownText() {
        switch (cdw) {
            case VFAST:
                return "1 (Muy rápido)";
            case FAST:
                return "2 (Rápido)";
            case SLOW:
                return "3 (Lento)";
            case VSLOW:
                return "4 (Muy lento)";
        }
        return "-";
    }

}
