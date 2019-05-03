/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.s3project.block.Main;
import com.s3project.block.Objects.Skill;

/**
 *
 * @author S34n4e
 */
public final class EquipScreen implements ApplicationListener, Screen {

    private Main m;
    private int index;
    private boolean showSkillInfo;

    public EquipScreen(Main m) {
        super();
        this.m = m;
        create();
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
    public void dispose() {
    }

    public void setMain(Main m) {
        this.m = m;
    }

    @Override
    public void create() {
        if (m == null) {
            m = new Main();
        }
        index = 0;
        showSkillInfo = false;
        m.stage.addActor(createHub(m.tex.getBG("bg2"), 1280, 720, 0, 0, Color.WHITE));
        m.stage.addActor(createBox(m.skin2, "default", "Equipo", 1280, 240, 0, 480));
        m.stage.addActor(createBox(m.skin2, "default", "Datos", 1280, 480, 0, 0));
        m.stage.addActor(createPortrait(m.tex.get3(m.g.mySouls.get(0).getSprite()), 200, 200, 256, 480, Color.WHITE, 0));
        m.stage.addActor(createPortrait(m.tex.get3(m.g.mySouls.get(1).getSprite()), 200, 200, 768, 480, Color.WHITE, 1));
        m.stage.addActor(createLayout(m.skin, "default", 880, 400, 200, 50));
        m.stage.addActor(createBackButton(m.skin, "default", 150, 50, 0, 0));
        m.stage.addActor(createChangePartyButton(m.skin, "default", 150, 50, 150, 0));
        m.stage.addActor(createSkInfoButton(m.skin, "default", 150, 50, 300, 0));
        m.stage.addActor(createSkillInfo(m.skin, "default", 1280, 720, -400, -100, 1));
        m.stage.addActor(createSkillInfo(m.skin, "default", 1280, 720, 0, -100, 2));
        m.stage.addActor(createSkillInfo(m.skin, "default", 1280, 720, 400, -100, 3));
        //create ventana info
        //create button cambiar
        //create button descr habilidades
        //create button descr pasiva
    }

    @Override
    public void render() {
        m.rc.refresh(m);
        m.sb.begin();
        m.stage.draw();
        m.stage.act(Gdx.graphics.getDeltaTime());
        m.sb.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        render();
    }

    @Override
    public void hide() {
    }

    /*Metodo repetido en BattleScreen*/
    public Image createHub(TextureAtlas.AtlasRegion a, int w, int h, int x, int y, Color color) {
        Image temp = new Image(a);
        temp.setPosition((x), (y));
        temp.setSize(w, h);
        temp.setColor(color);
        temp.setTouchable(Touchable.disabled);
        return temp;
    }

    private Actor createBox(Skin skin, String adefault, String text, int w, int h, int x, int y) {
        final Actor temp = new Window(text, skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.setColor(Color.WHITE);
        temp.setTouchable(Touchable.disabled);

        return temp;
    }

    private Actor createText(Skin skin, String adefault, int w, int h, int x, int y, int choice) {
        Label temp = new Label(" - ", skin, adefault);
        temp.setBounds(0, 0, 0, 0);
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                int skCode = y;
                switch (y) {
                    case 1:
                    default:
                        skCode = m.g.mySouls.get(index).getCmdCode1();
                        break;
                    case 2:
                        skCode = m.g.mySouls.get(index).getCmdCode2();
                        break;
                    case 3:
                        skCode = m.g.mySouls.get(index).getCmdCode3();
                        break;
                }
                switch (choice) {
                    case 1://nombre
                        temp.setText(m.g.mySouls.get(index).getName());
                        break;
                    case 2://nivel
                        temp.setText("" + m.g.mySouls.get(index).getLv());
                        break;
                    case 3://tnl
                        temp.setText("" + m.g.mySouls.get(index).getTNL());
                        break;
                    case 4://siguiente
                        temp.setText("" + m.g.mySouls.get(index).getMaxvit());
                        break;
                    case 5://ataque
                        temp.setText("" + m.g.mySouls.get(index).getStr());
                        break;
                    case 6:
                        temp.setText("" + m.g.mySouls.get(index).getMag());
                        break;
                    case 7:
                        temp.setText("" + m.g.mySouls.get(index).getEsp());
                        break;
                    case 8:
                        temp.setText("" + m.g.mySouls.get(index).getAgi());
                        break;
                    case 9:
                        temp.setText(m.g.mySouls.get(index).getFireRes() + "%");
                        break;
                    case 10:
                        temp.setText(m.g.mySouls.get(index).getWaterRes() + "%");
                        break;
                    case 11:
                        temp.setText(m.g.mySouls.get(index).getLitRes() + "%");
                        break;
                    case 12:
                        temp.setText(" " + m.g.allSkills.get(m.g.mySouls.get(index).getCmdCode1()).getName());
                        break;
                    case 13:
                        temp.setText(" " + m.g.allSkills.get(m.g.mySouls.get(index).getCmdCode2()).getName());
                        break;
                    case 14:
                        temp.setText(" " + m.g.allSkills.get(m.g.mySouls.get(index).getCmdCode3()).getName());
                        break;
                    case 15:
                        temp.setText(" " + m.g.mySouls.get(index).getPassiveCode());
                        break;
                    //De aqui en adelante es para skills!!!

                    case 16:
                        temp.setText(" " + m.g.allSkills.get(skCode).getName());
                        break;
                    case 17:
                        switch (m.g.allSkills.get(skCode).getTarget()) {
                            case ALL:
                                temp.setText(" Todos");
                                break;
                            case ALLIES:
                                temp.setText(" Aliados");
                                break;
                            case ALLY:
                                temp.setText(" Aliado/Usuario");
                                break;
                            case FOE:
                                temp.setText(" Rival");
                                break;
                            case FOES:
                                temp.setText(" Rivales");
                                break;
                            case USER:
                                temp.setText(" Usuario");
                                break;
                            case NO:
                                temp.setText(" - ");
                                break;
                        }
                        break;
                    case 18:
                        temp.setText(" " + m.g.allSkills.get(skCode).getPower());
                        break;
                    case 19:
                        switch (m.g.allSkills.get(skCode).getStat()) {
                            case AGI:
                                temp.setText(" Agilidad");
                                break;
                            case ATK:
                                temp.setText(" Ataque");
                                break;
                            case ESP:
                                temp.setText(" Espíritu");
                                break;
                            case MAG:
                                temp.setText(" Magia");
                                break;
                            case VIT:
                                temp.setText(" Vitalidad");
                                break;
                            default:
                                temp.setText(" - ");
                                break;

                        }
                        break;
                    case 20:
                        switch (m.g.allSkills.get(skCode).getElement()) {
                            case FIRE:
                                temp.setText(" Fuego");
                                break;
                            case LIGHTNING:
                                temp.setText(" Rayo");
                                break;
                            case WATER:
                                temp.setText(" Agua");
                                break;
                            case NO:
                                temp.setText(" - ");
                                break;
                        }
                        break;
                    case 21:
                        temp.setText(" " + m.g.allSkills.get(skCode).getCooldownText());
                        break;
                    case 22:
                        switch(m.g.allSkills.get(skCode).getType()) {
                            case BUFF:
                                temp.setText(" " + m.g.allSkills.get(skCode).getBuff1().name() + "/" + m.g.allSkills.get(skCode).getBuff2().name());
                                break;
                            case DMGBUFF:
                            case HEALBUFF:
                                temp.setText(" " + m.g.allSkills.get(skCode).getBuff1().name());
                                break;
                            default:
                                temp.setText(" - ");
                                break;
                        }
                        break;
                    case 23:
                        switch(m.g.allSkills.get(skCode).getType()) {
                            case DEBUFF:
                                temp.setText(" " + m.g.allSkills.get(skCode).getDeb1().name() + "/" + m.g.allSkills.get(skCode).getDeb2().name());
                                break;
                            case DMGDEBUFF:
                                temp.setText(" " + m.g.allSkills.get(skCode).getDeb1().name());
                                break;
                                default:
                                temp.setText(" - ");
                                break;
                        }
                        break;
                }
                return false;
            }
        });
        return temp;
    }

    private Actor createPortrait(TextureAtlas.AtlasRegion img, int w, int h, int x, int y, Color color, final int choice) {
        final Image temp = new Image(img);
        temp.setBounds(x, y, w, h);
        temp.setColor(color);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                index = choice;
            }
        });
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                if (choice == index) {
                    temp.setColor(Color.WHITE);
                } else {
                    temp.setColor(Color.BLACK);
                }
                return false;
            }
        });
        return temp;
    }

    private Actor createBackButton(Skin skin, String adefault, int w, int h, int x, int y) {
        TextButton temp = new TextButton("Volver", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                m.changeScreen(4);
            }
        });
        return temp;
    }

    private Actor createLayout(Skin skin, String adefault, int w, int h, int x, int y) {
        Table temp = new Table(skin);
        temp.setRound(true);
        temp.setBounds(x, y, w, h);
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 1)).expand().left().uniform();
        temp.add();
        temp.row();
        temp.add();
        temp.row();
        temp.add(" Nivel", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 2)).expand().right().uniform();
        temp.row();
        temp.add(" Sig. nivel", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 3)).expand().right().uniform();
        temp.add().expand().uniform();
        temp.add("Debilidad a ", "default-font", Color.YELLOW);
        temp.add().expand().uniform();
        temp.add().expand().uniform();
        temp.add("Habilidades ", "default-font", Color.YELLOW);
        temp.row();
        temp.add(" Vitalidad", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 4)).expand().right().uniform();
        temp.add().expand().uniform();
        temp.add(" Fuego", "default-font", Color.RED).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 9)).expand().uniform();
        temp.add().expand().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 12)).expand().left().uniform();
        temp.row();
        temp.add(" Ataque", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 5)).expand().right().uniform();
        temp.add().expand().uniform();
        temp.add(" Agua", "default-font", Color.BLUE).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 10));
        temp.add().expand().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 13)).expand().left().uniform();
        temp.row();
        temp.add(" Magia", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 6)).expand().right().uniform();
        temp.add().expand().uniform();
        temp.add(" Rayo", "default-font", Color.ORANGE).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 11));
        temp.add().expand().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 14)).expand().left().uniform();
        temp.row();
        temp.add(" Espíritu", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 7)).expand().right().uniform();
        temp.row();
        temp.add(" Agilidad", "default-font", Color.GOLD).expand().left().uniform();
        temp.add(createText(skin, adefault, 100, 100, 0, 0, 8)).expand().right().uniform();
        //temp.getCells().shuffle();
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                temp.setVisible(!showSkillInfo);
                return false;
            }
        });
        return temp;
    }

    private Actor createChangePartyButton(Skin skin, String adefault, int w, int h, int x, int y) {
        TextButton temp = new TextButton("Cambiar", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                m.changeScreen(5);
            }
        });
        return temp;
    }

    private Actor createSkInfoButton(Skin skin, String adefault, int w, int h, int x, int y) {
        TextButton temp = new TextButton("Habilidades", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                showSkillInfo = !showSkillInfo;
            }
        });
        return temp;
    }

    private Actor createSkillInfo(Skin skin, String adefault, int w, int h, int x, int y, int skillNum) {
        Actor temp = new Container(skillInfoInside(skillNum));
        temp.setBounds(x, y, w, h);
        temp.setVisible(false);
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                temp.setVisible(showSkillInfo);
                return false;
            }
        });
        return temp;
    }

    private Actor skillInfoInside(int skillNum) {
        Table temp = new Table(m.skin);
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                temp.clear();
                int i = skillNum;
                temp.add("Habilidad", "default-font", Color.GOLD).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 16)).right();
                temp.row();
                temp.add("Objetivo", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 17)).right();
                temp.row();
                temp.add("Poder", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 18)).right();
                temp.row();
                temp.add("Estadística", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 19)).right();
                temp.row();
                temp.add("Elemento", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 20)).right();
                temp.row();
                temp.add("Carga", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 21)).right();
                temp.row();
                temp.add("Mejoras", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 22)).right();
                temp.row();
                temp.add("Efectos", "default-font", Color.YELLOW).left();
                temp.add(createText(m.skin, "default", 0, 0, 0, i, 23)).right();

                return false;
            }
        });
        return temp;
    }

}
