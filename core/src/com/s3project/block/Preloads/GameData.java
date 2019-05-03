/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Preloads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.s3project.block.Objects.BattleData;
import com.s3project.block.Objects.Skill;
import com.s3project.block.Objects.Soul;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author S34n4e
 */
public class GameData {

    public Array<Soul> soulTeam;
    public Array<Soul> mySouls;
    public Array<Soul> allSouls;
    public Array<Skill> allSkills;
    public Array<BattleData> allBattles;

    public GameData() {
        allSouls = new Array<Soul>();
        mySouls = new Array<Soul>();
        allSkills = new Array<Skill>();
        allBattles = new Array<BattleData>();
        reloadAllSkillSoul();
        fillBattles();
    }

    public GameData(Array<Soul> soulTeam, Array<Soul> mySouls, Array<Soul> allSouls) {
        this.soulTeam = soulTeam;
        this.mySouls = mySouls;
        this.allSouls = allSouls;
        this.allBattles = new Array<BattleData>();
        reloadAllSkillSoul();
        fillBattles();
    }

    public void saveGame() {
    }

    public void loadGame() {
    }
    
    public void reloadAllSkillSoul(){
        allSkills();
        allSouls();
    }
    
    public void fillBattles(){ // 7 en total, del 0 al 6
        allBattles.clear();
        allBattles.add(new BattleData(34, 35, 5, 5, "Batalla sencilla", 1));
        allBattles.add(new BattleData(55, 10, 5, 5, "Batalla sencilla", 1));
        allBattles.add(new BattleData(57, 52, 10, 10, "Batalla sencilla", 1));
        allBattles.add(new BattleData(58, 68, 15, 15, "Batalla sencilla", 1));
        allBattles.add(new BattleData(59, 7, 20, 20, "Batalla sencilla", 1));
        allBattles.add(new BattleData(62, 2, 25, 25, "Batalla sencilla", 1));
        allBattles.add(new BattleData(61, 25, 30, 30, "Batalla sencilla", 1));
        allBattles.add(new BattleData(56, 4, 35, 35, "Batalla sencilla", 1));
        allBattles.add(new BattleData(60, 33, 40, 40, "Batalla sencilla", 1));
        allBattles.add(new BattleData(45, 32, 45, 45, "Batalla sencilla", 1));
        allBattles.add(new BattleData(15, 54, 50, 50, "Batalla sencilla", 1));
        allBattles.add(new BattleData(14, 13, 55, 55, "Batalla sencilla", 1));
        allBattles.add(new BattleData(12, 16, 60, 60, "Batalla sencilla", 1));
        allBattles.add(new BattleData(11, 48, 70, 70, "Batalla sencilla", 1));
        allBattles.add(new BattleData(67, 66, 80, 80, "Batalla sencilla", 1));
        allBattles.add(new BattleData(61, 62, 90, 90, "Batalla sencilla", 1));
        allBattles.add(new BattleData(58, 59, 90, 90, "Batalla sencilla", 1));
        allBattles.add(new BattleData(55, 57, 90, 90, "Batalla sencilla", 1));
        allBattles.add(new BattleData(56, 60, 90, 90, "Batalla sencilla", 1));
        allBattles.add(new BattleData(56, 60, 95, 95, "Batalla sencilla", 1));
        allBattles.add(new BattleData(70, 70, 99, 99, "Batalla sencilla", 1));
    }
    

    private void allSkills() {
        FileHandle file2 = Gdx.files.internal("skills.txt");
        Json json2 = new Json(JsonWriter.OutputType.json);
        json2.addClassTag("skills", allSkills.getClass());
        allSkills = new Json().fromJson(allSkills.getClass(), file2);
    }

    private void allSouls() {
        FileHandle file = Gdx.files.internal("souls.txt");
        Json json = new Json(JsonWriter.OutputType.json);
        json.addClassTag("souls", allSouls.getClass());
        allSouls = new Json().fromJson(allSouls.getClass(), file);

    }

}
