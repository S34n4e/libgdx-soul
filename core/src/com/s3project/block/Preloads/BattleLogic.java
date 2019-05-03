/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Preloads;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.s3project.block.Objects.BuffDebuff;
import com.s3project.block.Objects.Skill;
import com.s3project.block.Objects.Soul;

/**
 *
 * @author S34n4e
 */
public class BattleLogic {

    //Mis 2 souls y los del rival
    public Soul soulA;
    public Soul soulB;
    public Soul soulC;
    public Soul soulD;
    //3 habilidades en cada ArraySkill, aqui se lleva el ATB.
    public Array<Skill> skillA;
    public Array<Skill> skillB;
    public Array<Skill> skillC;
    public Array<Skill> skillD;
    //En realidad el ATB deberia ser una gran barra xD
    public Integer atbA;
    public Integer atbB;
    public Integer atbC;
    public Integer atbD;
    //Max ATB
    public final int maxAtb = 100000;
    //allSkills
    private Array<Skill> allSkills;
    //Strings con log info de la batalla
    public String who;
    public String with;
    public String dmgA;
    public String dmgB;
    public String dmgC;
    public String dmgD;
    public String totalDmg;
    public boolean isSkillBeingUsed;
    public boolean canAct;
    public boolean isPlayerBox;

    public int usesLeft(boolean isP1) {
        if (isP1) {
            return atbA / (maxAtb / 4);
        }
        return atbB / (maxAtb / 4);
    }

    public BattleLogic(Soul soulA, Soul soulB, Soul soulC, Soul soulD, Array<Skill> allSkills) {
        this.soulA = soulA;
        this.soulB = soulB;
        this.soulC = soulC;
        this.soulD = soulD;
        this.allSkills = allSkills;
        canAct = true;
        skillA = new Array<Skill>();
        skillB = new Array<Skill>();
        skillC = new Array<Skill>();
        skillD = new Array<Skill>();
        skillA.setSize(3);
        skillB.setSize(3);
        skillC.setSize(3);
        skillD.setSize(3);
        skillA.set(0, allSkills.get(soulA.getCmdCode1()).copy());
        skillA.set(1, allSkills.get(soulA.getCmdCode2()).copy());
        skillA.set(2, allSkills.get(soulA.getCmdCode3()).copy());
        skillB.set(0, allSkills.get(soulB.getCmdCode1()).copy());
        skillB.set(1, allSkills.get(soulB.getCmdCode2()).copy());
        skillB.set(2, allSkills.get(soulB.getCmdCode3()).copy());
        skillC.set(0, allSkills.get(soulC.getCmdCode1()).copy());
        skillC.set(1, allSkills.get(soulC.getCmdCode2()).copy());
        skillC.set(2, allSkills.get(soulC.getCmdCode3()).copy());
        skillD.set(0, allSkills.get(soulD.getCmdCode1()).copy());
        skillD.set(1, allSkills.get(soulD.getCmdCode2()).copy());
        skillD.set(2, allSkills.get(soulD.getCmdCode3()).copy());
        atbA = 0;
        atbB = 0;
        atbC = 0;
        atbD = 0;
        who = "NO";
        with = "NO";
        totalDmg = "-";
        dmgA = "-";
        dmgB = "-";
        dmgC = "-";
        dmgD = "-";
        isSkillBeingUsed = false;
        isPlayerBox = true;
    }

    public void loadSkillATB() {
        passingSkillLists(skillA);
        passingSkillLists(skillB);
        passingSkillLists(skillC);
        passingSkillLists(skillD);

    }

    private void passingSkillLists(Array<Skill> temp) {
        loadingSkillList(temp, 0);
        loadingSkillList(temp, 1);
        loadingSkillList(temp, 2);
    }

    private void loadingSkillList(Array<Skill> temp, int index) {
        if (temp.get(index).isReady() == false) {
            temp.get(index).setChargeTime(temp.get(index).getChargeTime() - 1);
        }
        if (temp.get(index).getChargeTime() <= 0) {
            temp.get(index).setReady(true);
        }
    }

    public void skillInput(boolean isSoul1, int skillSlot, boolean isPlayer, int objetiveSlot) {
        Soul tempsoul; // User
        Soul tempsoulaim1; // Objective 1
        Soul tempsoulaim2; //If it's AOE, Objective 2
        Integer whichAtb; //User Num
        int aimN1 = objetiveSlot; //Objective 1
        int aimN2 = 0; // Objective 2
        Skill tempSk;
        
        totalDmg = "";
        if (isPlayer) {
            isPlayerBox = true;
            if (isSoul1) {
                tempsoul = this.soulA;
                whichAtb = 1;
            } else {
                tempsoul = this.soulB;
                whichAtb = 2;
            }
        } else {
            isPlayerBox = false;
            if (isSoul1) {
                tempsoul = this.soulC;
                whichAtb = 3;
            } else {
                tempsoul = this.soulD;
                whichAtb = 4;
            }
        }
        tempSk = allSkills.get(codeForSkill(skillSlot, tempsoul));
        
        //Si es de curacion entonces apuntar a si mismo, si es de daño apuntar al enemigo
        switch(tempSk.getType()){
            case BUFF:
            case HEAL:
            case HEALBUFF:
                if(isPlayer && isSoul1) {//isSoul1 heal
                    if(objetiveSlot == 3 || objetiveSlot == 4){
                    aimN1 = 1;
                    }
                } else if (isPlayer && !isSoul1) { //isSoul2 heal
                    if(objetiveSlot == 3 || objetiveSlot == 4){
                    aimN1 = 2;
                    }
                } else if (!isPlayer && isSoul1) { //isSoul3 heal
                    if(objetiveSlot == 1 || objetiveSlot == 2){
                    aimN1 = 3;
                    }
                } else if (!isPlayer && !isSoul1) { //isSoul4 heal
                    if(objetiveSlot == 1 || objetiveSlot == 2){
                    aimN1 = 4;
                    }
                }
                break;
            default:
                if(isPlayer && isSoul1) {//dmg a soul3
                    if(objetiveSlot == 1 || objetiveSlot == 2){
                    aimN1 = 3;
                    }
                } else if (isPlayer && !isSoul1) { //dmg a soul4
                    if(objetiveSlot == 1 || objetiveSlot == 2){
                    aimN1 = 4;
                    }
                } else if (!isPlayer && isSoul1) { //dmg a soul1
                    if(objetiveSlot == 3 || objetiveSlot == 4){
                    aimN1 = 1;
                    }
                } else if (!isPlayer && !isSoul1) { //dmg a soul2
                    if(objetiveSlot == 3 || objetiveSlot == 4){
                    aimN1 = 2;
                    }
                }
                break;
        }
        
        switch (aimN1) { //objetivos luego de saber si es al aliado o al enemigo
            case 1:
                if (soulA.isAlive()) {
                    tempsoulaim1 = this.soulA;
                    tempsoulaim2 = this.soulB;
                    aimN2 = 2;
                } else {
                    tempsoulaim1 = this.soulB;
                    tempsoulaim2 = this.soulA;
                }
                break;
            case 2:
                if (soulB.isAlive()) {
                    tempsoulaim1 = this.soulB;
                    tempsoulaim2 = this.soulA;
                    aimN2 = 1;
                } else {
                    tempsoulaim1 = this.soulA;
                    tempsoulaim2 = this.soulB;
                }
                break;
            case 3:
                if (soulC.isAlive()) {
                    tempsoulaim1 = this.soulC;
                    tempsoulaim2 = this.soulD;
                    aimN2 = 4;
                } else {
                    tempsoulaim1 = this.soulD;
                    tempsoulaim2 = this.soulC;
                }
                break;
            case 4:
            default:
                if (soulD.isAlive()) {
                    tempsoulaim1 = this.soulD;
                    tempsoulaim2 = this.soulC;
                    aimN2 = 3;
                } else {
                    tempsoulaim1 = this.soulC;
                    tempsoulaim2 = this.soulD;
                }
                break;
        }
        atbCost(whichAtb, tempSk);
        if ((tempSk.getType() == Skill.Type.HEAL || tempSk.getType() == Skill.Type.HEALBUFF
                || tempSk.getType() == Skill.Type.BUFF) && (!isPlayer)) {
            if (soulC.isAlive()) {
                tempsoulaim1 = soulC;
                tempsoulaim2 = soulD;
            } else {
                tempsoulaim1 = soulD;
                tempsoulaim2 = soulC;
            }
        }
        who = tempsoul.getName();
        with = " usa " + tempSk.getName();
        poison(tempsoul);
        regen(tempsoul);
        canAct = disableOrBlind(tempsoul);
        isSkillBeingUsed = true;
        if (canAct) {
            skillType(tempSk, tempsoul, tempsoulaim1, tempsoulaim2, aimN1, aimN2);
        }
    }

    private void skillType(Skill sk, Soul user, Soul aim1, Soul aim2, int aimN1, int aimN2) {
        int damage1 = 0;
        int damage2 = 0; // if AOE this is second
        int heal = 0;

        switch (sk.getType()) { //Formula and inflict statuses
            case DMG:
                damage1 = dmgFormula(sk, user, aim1);
                damage2 = dmgFormula(sk, user, aim2);
                break;
            case DMGBUFF:
                damage1 = dmgFormula(sk, user, aim1);
                damage2 = dmgFormula(sk, user, aim2);
                inflictBuff(sk, user);
                break;
            case DMGDEBUFF:
                damage1 = dmgFormula(sk, user, aim1);
                damage2 = dmgFormula(sk, user, aim2);
                inflictDebuff(sk, aim1);
                if (sk.getTarget() == Skill.Target.FOES) {
                    inflictDebuff(sk, aim2);
                }
                break;
            case DMGHEAL:
                damage1 = dmgFormula(sk, user, aim1);
                damage2 = dmgFormula(sk, user, aim2);
                break;
            case DRAIN:
                damage1 = dmgFormula(sk, user, aim1);
                damage2 = dmgFormula(sk, user, aim2);
                break;
            case HEAL:
                heal = dmgFormula(sk, user, aim1) * -1;
                break;
            case HEALBUFF:
                heal = dmgFormula(sk, user, user) * -1;
                switch (sk.getTarget()) {
                    case USER:
                        inflictBuff(sk, user);
                        break;
                    case ALLY:
                        inflictBuff(sk, aim1);
                        break;
                    case ALLIES:
                        inflictBuff(sk, aim1);
                        inflictBuff(sk, aim2);
                        break;
                }
                break;
            case BUFF:
                switch (sk.getTarget()) {
                    case USER:
                        inflictBuff(sk, user);
                        break;
                    case ALLY:
                        inflictBuff(sk, aim1);
                        break;
                    case ALLIES:
                        inflictBuff(sk, aim1);
                        inflictBuff(sk, aim2);
                        break;
                }
                break;
            case DEBUFF:
                inflictDebuff(sk, aim1);
                if (sk.getTarget() == Skill.Target.FOES) {
                    System.out.println(aim2.toInfo() + "\n" + toString());
                    inflictDebuff(sk, aim2);
                }
                break;
        }

        switch (sk.getType()) { //damage or healing
            case DMG:
            case DMGBUFF:
            case DMGDEBUFF:
            case DMGHEAL:
                aim1.damage(damage1);
                if (sk.getTarget() == Skill.Target.FOES) {
                    aim2.damage(damage2);
                }
                break;
            case DRAIN:
                user.damage(aim1.damage(damage1) * -1);
                if (sk.getTarget() == Skill.Target.FOES) {
                    user.damage(aim2.damage(damage2) * -1);
                }
                break;
            case HEAL:
            case HEALBUFF:
                if (sk.getTarget() == Skill.Target.USER) {
                    user.damage(heal);
                } else {
                    aim1.damage(heal);
                }
                if (sk.getTarget() == Skill.Target.ALLIES) {
                    aim2.damage(heal);
                }
        }
        switch (sk.getType()) {
            case DMG:
            case DMGBUFF:
            case DMGDEBUFF:
            case DMGHEAL:
            case DRAIN:
                if (sk.getTarget() == Skill.Target.FOES) {
                    totalDmg = "[ " + damage1 + " " + damage2 + " ]";
                    damageText(aimN1, damage1, false);
                    damageText(aimN2, damage2, false);
                } else {
                    totalDmg = "[ " + damage1 + " ]";
                    damageText(aimN1, damage1, false);
                }
                break;
            case HEAL:
            case HEALBUFF:
                if (sk.getTarget() == Skill.Target.ALLIES) {
                    totalDmg = "[ " + heal + " ]";
                    damageText(aimN1, heal, true);
                    damageText(aimN2, heal, true);
                } else {
                    totalDmg = "[ " + heal + " ]";
                    damageText(aimN1, heal, true);
                }
                break;
        }

    }

    private int statUsed(Skill skill, Soul soul) {
        switch (skill.getStat()) {
            case ATK:
                return soul.getStr();
            case AGI:
                return soul.getAgi();
            case ESP:
                return soul.getEsp();
            case MAG:
                return soul.getMag();
            case VIT:
                return soul.getVit();
        }
        return 1;
    }

    private void inflictBuff(Skill skill, Soul aim) {
        Array<BuffDebuff> buffs = new Array<BuffDebuff>();
        BuffDebuff temp = new BuffDebuff(0, skill.getBuff1(), 99, false);
        BuffDebuff temp2 = new BuffDebuff(0, skill.getBuff2(), 99, false);
        switch (aim.isBuffActive(skill.getBuff1(), false, false)) {
            case 0: //No tiene buff
                aim.getBuffs().add(temp);
                break;
            case 1: //Tiene el buff
                buffs = aim.getBuffs();
                for (int i = 0; i < buffs.size; i++) {
                    if (buffs.get(i).getBuff() == temp.getBuff()) {
                        buffs.get(i).setEnhanced(true);
                        i = buffs.size;
                    }
                }
                aim.setBuffs(buffs);
                break;
            case 2: // Tiene el buff mejorado, no pasa nada
                break;
        }
        switch (aim.isBuffActive(skill.getBuff2(), false, false)) {
            case 0: //No tiene buff
                aim.getBuffs().add(temp2);
                break;
            case 1: //Tiene el buff
                buffs = aim.getBuffs();
                for (int i = 0; i < buffs.size; i++) {
                    if (buffs.get(i).getBuff() == temp2.getBuff()) {
                        buffs.get(i).setEnhanced(true);
                        i = buffs.size;
                    }
                }
                aim.setBuffs(buffs);
                break;
            case 2: // Tiene el buff mejorado, no pasa nada
                break;
        }
    }

    private void inflictDebuff(Skill skill, Soul aim) {

        Array<BuffDebuff> debuffs = new Array<BuffDebuff>();
        BuffDebuff temp = new BuffDebuff(0, skill.getDeb1(), 99, false);
        BuffDebuff temp2 = new BuffDebuff(0, skill.getDeb2(), 99, false);
        if (aim.isBuffActive(Skill.Buff.ASTRA, false, true) == 0) {//Si tiene Astra evita todos los debuff
            switch (aim.isDebuffActive(skill.getDeb1(), false, false)) {
                case 0: //No tiene debuff
                    debuffs = aim.getDebuffs();
                    debuffs.add(temp);
                    aim.setDebuffs(debuffs);
                    break;
                case 1: //Tiene el debuff
                    debuffs = aim.getDebuffs();
                    for (int i = 0; i < debuffs.size; i++) {
                        if (debuffs.get(i).getDebuff() == temp.getDebuff()) {
                            debuffs.get(i).setEnhanced(true);
                            i = debuffs.size;
                        }
                    }
                    aim.setDebuffs(debuffs);
                    break;
                case 2: // Tiene el debuff mejorado, no pasa nada
                    break;
            }
            switch (aim.isDebuffActive(skill.getDeb2(), false, false)) {
                case 0: //No tiene debuff
                    aim.getDebuffs().add(temp2);
                    break;
                case 1: //Tiene el debuff
                    debuffs = aim.getDebuffs();
                    for (int i = 0; i < debuffs.size; i++) {
                        if (debuffs.get(i).getDebuff() == temp2.getDebuff()) {
                            debuffs.get(i).setEnhanced(true);
                            i = debuffs.size;
                        }
                    }
                    aim.setDebuffs(debuffs);
                    break;
                case 2: // Tiene el debuff mejorado, no pasa nada
                    break;
            }
        }
    }

    private int dmgFormula(Skill skill, Soul soul, Soul aim) { //dmg formula: STAT * MOVEPWR *0.02 * (fireRes*0.01)
        int finaldamage;
        finaldamage = (int) (statUsed(skill, soul) * skill.getPower() * 0.03);

        switch (skill.getElement()) {
            case NO:
                break;
            case FIRE:
                finaldamage = (int) (finaldamage * (aim.getFireRes() * 0.01));
                break;
            case LIGHTNING:
                finaldamage = (int) (finaldamage * (aim.getLitRes() * 0.01));
                break;
            case WATER:
                finaldamage = (int) (finaldamage * (aim.getWaterRes() * 0.01));
                break;
        }

        return finaldamage;
    }

    public Skill getSkillset(int spriteNum, int index) {
        index = index - 1;
        switch (spriteNum) {
            case 1:
            default:
                return skillA.get(index);
            case 2:
                return skillB.get(index);
            case 3:
                return skillC.get(index);
            case 4:
                return skillD.get(index);
        }
    }

    public boolean enemyActions(int battleSpd, boolean isLogVisible) {
        for (int i = 0; i < skillC.size && i < skillD.size; i++) {

            if (isSkillBeingUsed || isLogVisible) {
                battleSpd = 0;
            }

            if (soulA.isAlive() && soulA.isDebuffActive(Skill.Debuff.SLEEP, false, false) == 0) {
                atbA = charge(soulA.getAgi(), battleSpd, atbA);
            } else {
                atbA = 0;
            }
            if (soulB.isAlive() && soulB.isDebuffActive(Skill.Debuff.SLEEP, false, false) == 0) {
                atbB = charge(soulB.getAgi(), battleSpd, atbB);
            } else {
                atbB = 0;
            }
            if (soulC.isAlive() && soulC.isDebuffActive(Skill.Debuff.SLEEP, false, false) == 0) {
                atbC = charge(soulC.getAgi(), battleSpd, atbC);
            } else {
                atbC = 0;
            }
            if (soulD.isAlive() && soulD.isDebuffActive(Skill.Debuff.SLEEP, false, false) == 0) {
                atbD = charge(soulD.getAgi(), battleSpd, atbD);
            } else {
                atbD = 0;
            }

            if (atbC >= maxAtb && !isLogVisible) {
                skillInput(true, MathUtils.random(1, 3), false, MathUtils.random(1, 2));
                if (atbD >= maxAtb) {
                    atbD = maxAtb - 100;
                }
            } else if (atbD >= maxAtb && !isLogVisible) {
                skillInput(false, MathUtils.random(1, 3), false, MathUtils.random(1, 2));
            }

        }
        return false;
    }

    public int charge(int speedModifier, int battleSpd, int thisAtb) {
        if (speedModifier == 0) {
            battleSpd = 3;
        }
        speedModifier = speedModifier / 5;
        switch (battleSpd) {
            case 0:
            default:
                break;
            case 3:
                if (thisAtb > maxAtb) {
                    thisAtb = 0;
                }
                break;
            case 1:
                thisAtb = thisAtb + (speedModifier + 60) / 2;
                break;
            case 2:
                thisAtb = thisAtb + (speedModifier + 60);
                break;
        }

        if (thisAtb > maxAtb) {
            thisAtb = maxAtb;
        }
        return thisAtb;
    }

    public int getAtbSoul(int spriteNum) {
        switch (spriteNum) {
            case 1:
                if (soulA.isAlive()) {
                    return atbA;
                } else {
                    atbA = 0;
                }
                break;
            case 2:
                if (soulB.isAlive()) {
                    return atbB;
                } else {
                    atbB = 0;
                }
                break;
            case 3:
                return atbC;
            case 4:
                return atbD;
        }
        return 0;
    }

    public boolean isBattleFinished() {
        if (!soulA.isAlive() && !soulB.isAlive()) {
            return true;
        } else if (!soulC.isAlive() && !soulD.isAlive()) {
            return true;
        }
        return false;
    }

    private int codeForSkill(int skillSlot, Soul tempsoul) {
        switch (skillSlot) {
            case 1:
            default:
                return tempsoul.getCmdCode1();
            case 2:
                return tempsoul.getCmdCode2();
            case 3:
                return tempsoul.getCmdCode3();
        }
    }

    private void atbCost(int whichAtb, Skill tempSk) {
        switch (whichAtb) {
            case 1:
                atbA = atbA - tempSk.getCdwTime(maxAtb);
                break;
            case 2:
                atbB = atbB - tempSk.getCdwTime(maxAtb);
                break;
            case 3:
                atbC = atbC - tempSk.getCdwTime(maxAtb);
                break;
            case 4:
                atbD = atbD - tempSk.getCdwTime(maxAtb);
                break;
        }
    }

    private void poison(Soul tempsoul) {
        switch (tempsoul.isDebuffActive(Skill.Debuff.POISON, false, false)) {
            case 0:
                break;
            case 1:
                tempsoul.damage(tempsoul.getMaxvit() / 20);
                break;
            case 2:
                tempsoul.damage(tempsoul.getMaxvit() / 10);
                break;
        }
    }

    private void regen(Soul tempsoul) {
        switch (tempsoul.isBuffActive(Skill.Buff.REGEN, false, false)) {
            case 0:
                break;
            case 1:
                tempsoul.damage(tempsoul.getMaxvit() / 20 * -1);
                break;
            case 2:
                tempsoul.damage(tempsoul.getMaxvit() / 10 * -1);
                break;
        }
    }

    private boolean disableOrBlind(Soul tempsoul) {
        switch (tempsoul.isDebuffActive(Skill.Debuff.DISABLE, false, true)) {
            case 1:
                with = " está deshabilitado";
                return false;
            case 2:
                with = " está deshabilitado";
                return false;
            default:
            case 0:
                switch (tempsoul.isDebuffActive(Skill.Debuff.BLIND, false, false)) {
                    case 1:
                        if (MathUtils.random(0, 7) != 0) { //12.5% de fallar
                        } else {
                            with = " ha fallado por la ceguera";
                            return false;
                        }
                        break;
                    case 2:
                        if (MathUtils.random(0, 3) != 0) { //25% de fallar
                        } else {
                            with = " ha fallado por la ceguera";
                            return false;
                        }
                        break;
                    default:
                    case 0:
                        break;
                }
        }
        return true;
    }

    private void damageText(int aimN, int damage, boolean isHeal) {
        String heal = " ";
        if (isHeal) {
            heal = "+";
        }
        switch (aimN) {
            case 1:
                dmgA = heal + damage;
                break;
            case 2:
                dmgB = heal + damage;
                break;
            case 3:
                dmgC = heal + damage;
                break;
            case 4:
                dmgD = heal + damage;
                break;
        }
    }

}
