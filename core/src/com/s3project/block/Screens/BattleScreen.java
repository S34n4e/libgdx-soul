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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.s3project.block.Main;
import com.s3project.block.Objects.BattleData;
import com.s3project.block.Objects.ParticleEffectActor;
import com.s3project.block.Objects.Skill;
import com.s3project.block.Objects.Soul;
import com.s3project.block.Preloads.BattleLogic;

/**
 *
 * @author S34n4e
 */
public final class BattleScreen implements ApplicationListener, Screen {

    private Skin skin;
    private Stage stage;
    private Main m;
    private BattleLogic bl;
    private boolean isSoul1;
    private boolean isLogVisible;
    private int objective = 3; // being 1 and 2 you, 3 and 4 enemies.
    private int battleSpeed = 2; // 2 fast, 1 slow, 0 stop
    private boolean isInfo;

    /*
    public BattleScreen(Main m, int enm1, int enm2, short lv1, short lv2) {
        super();
        this.m = m;
        bl = new BattleLogic(m.g.mySouls.get(0).copy(), m.g.mySouls.get(1).copy(), 
                m.g.allSouls.get(enm1).copy(), m.g.allSouls.get(enm2), m.g.allSkills);
            bl.soulC.calc((short) lv1);
            bl.soulD.calc((short) lv2);
        create();
    }
    */
    
    public BattleScreen(Main m, int battleCode) {
        super();
        this.m = m;
        if(battleCode >= 0 && battleCode < m.g.allBattles.size){
        bl = new BattleLogic(m.g.mySouls.get(0).copy(), m.g.mySouls.get(1).copy(), 
                m.g.allSouls.get(m.g.allBattles.get(battleCode).getEnm1()).copy(), 
                m.g.allSouls.get(m.g.allBattles.get(battleCode).getEnm2()).copy(), 
                m.g.allSkills);
        bl.soulC.calc((short) m.g.allBattles.get(battleCode).getLv1());
        bl.soulD.calc((short) m.g.allBattles.get(battleCode).getLv2());
        } else {
        bl = new BattleLogic(m.g.mySouls.get(0).copy(), m.g.mySouls.get(1).copy(), 
                m.g.allSouls.get(MathUtils.random(1, 70)).copy(), m.g.allSouls.get(MathUtils.random(1, 70)), m.g.allSkills);
            bl.soulC.calc((short) (m.g.mySouls.get(0).getLv() + MathUtils.random(1,5)));
            bl.soulD.calc((short) (m.g.mySouls.get(0).getLv() + MathUtils.random(1,5)));
        }
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
        try {
            if (m == null) {
                m = new Main();
            }
            isSoul1 = true; //If false then is Soul2
            isInfo = false;
            isLogVisible = false;
            stage = m.stage;
            skin = m.skin;
            stage.addActor(createHub( m.tex.getBG("bg") , 1280, 720, 0, 0, Color.WHITE));
            stage.addActor(createSprite(m.tex.get2(bl.soulA.getSprite(), true), -100, 100, 1));
            stage.addActor(createSprite(m.tex.get2(bl.soulB.getSprite(), true), -100, -200, 2));
            stage.addActor(createSprite(m.tex.get2(bl.soulC.getSprite()), 450, 100, 3));
            stage.addActor(createSprite(m.tex.get2(bl.soulD.getSprite()), 450, -200, 4));
            stage.addActor(createIsP1(m.tex.get("hubdiamond"), 300, 50, 400, 300));// cual es el personaje actual
            stage.addActor(createTargetAnim());
            stage.addActor(createHub(m.tex.get("sombra"), 240, 60, 0, 562, Color.WHITE));
            stage.addActor(createHub(m.tex.get("sombra"), 240, 60, 0, 662, Color.WHITE));
            stage.addActor(createHub(m.tex.get("sombra", true), 240, 60, 1040, 562, Color.WHITE));
            stage.addActor(createHub(m.tex.get("sombra", true), 240, 60, 1040, 662, Color.WHITE));
            stage.addActor(createHub(m.tex.get("sombra"), 350, 480, 0, -50, Color.WHITE));
            stage.addActor(createHpGauge(m.tex.get("gaugeverde"), 160, 20, 20, 690, 1));//Barras de vida
            stage.addActor(createHpGauge(m.tex.get("gaugeverde"), 160, 20, 20, 590, 2));
            stage.addActor(createHpGauge(m.tex.get("gaugeverde"), 160, 20, 1100, 690, 3));
            stage.addActor(createHpGauge(m.tex.get("gaugeverde"), 160, 20, 1100, 590, 4));
            stage.addActor(createHub(m.tex.get("hubvit"), 200, 40, 0, 680, Color.YELLOW));//Hub barras de vida
            stage.addActor(createHub(m.tex.get("hubvit"), 200, 40, 0, 580, Color.YELLOW));
            stage.addActor(createHub(m.tex.get("hubvit"), 200, 40, 1080, 680, Color.ORANGE));
            stage.addActor(createHub(m.tex.get("hubvit"), 200, 40, 1080, 580, Color.ORANGE));
            stage.addActor(createBtnHab(1, 1, skin, "default", 300, 80, 0, 160));//Botones de habilidad
            stage.addActor(createBtnHab(1, 2, skin, "default", 300, 80, 20, 80));
            stage.addActor(createBtnHab(1, 3, skin, "default", 300, 80, 40, 0));
            stage.addActor(createBtnHab(2, 1, skin, "default", 300, 80, 0, 160));
            stage.addActor(createBtnHab(2, 2, skin, "default", 300, 80, 20, 80));
            stage.addActor(createBtnHab(2, 3, skin, "default", 300, 80, 40, 0));
            stage.addActor(createHabGauge2(m.tex.get("gaugeamarillo"), 220, 15, 80, 306, 1));
            stage.addActor(createHabGauge2(m.tex.get("gaugeamarillo"), 220, 15, 80, 306, 2));
            stage.addActor(createHub(m.tex.get("hubdiamond"), 4, 30, 134, 300, Color.BLACK));// carga 1
            stage.addActor(createHub(m.tex.get("hubdiamond"), 4, 30, 188, 300, Color.BLACK));// carga 2
            stage.addActor(createHub(m.tex.get("hubdiamond"), 4, 30, 242, 300, Color.BLACK));// carga 3
            stage.addActor(createHub(m.tex.get("hubvit"), 280, 30, 50, 300, Color.GOLD));
            stage.addActor(createBtnSwap(skin, "default", 340, 90, 0, 350));
            stage.addActor(createBtnSpd(skin, "default", 80, 60, 1180, 0));
            stage.addActor(createBtnHelp(skin, "default", 80, 60, 1100, 0));
            stage.addActor(createUsesLeft(m.skin2, "default", 60, 50, 10, 250));// Conteo de usos 
            stage.addActor(createSoulName(skin, "default", 60, 50, 20, 646, 1));// nombre de Soul
            stage.addActor(createSoulName(skin, "default", 60, 50, 20, 546, 2));
            stage.addActor(createSoulName(skin, "default", 60, 50, 1060, 646, 3));
            stage.addActor(createSoulName(skin, "default", 60, 50, 1060, 546, 4));
            stage.addActor(createSoulDamage(skin, "default", 60, 50, 300, 300, 1));
            stage.addActor(createWdwInfo(skin, "default", 1180, 620, 50, 50));
            stage.addActor(createLogInfo(skin, "default", 560, 50, 380, 660));
            stage.addActor(createFinishButton(skin, "default", 560, 100, 380, 450));
            stage.addAction(createActions());
            m.sound(2);
        } catch (Exception e) {
            Gdx.app.log("LOL", "ERR " + e.toString() + " AND " + e.getLocalizedMessage());
            for (int z = 0; z < e.getStackTrace().length; z++) {
                Gdx.app.log(" LOL " + z, "ERR " + e.getStackTrace()[z].toString());
            }
        }

    }

    @Override
    public void render() {
        m.rc.refresh(m);
        m.sb.begin();
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
        m.sb.end();
        /*
        move++;
        
        if (move >= 100) {
            move = 0;
        }
*/
    }

    public Image createSprite(TextureAtlas.AtlasRegion a, final int x, final int y, final int spriteNum) {
        final Image temp = new Image(a);
        temp.setPosition(stage.getWidth() / 2 + (x) - (temp.getWidth() / 2), stage.getHeight() / 2 + (y) - (temp.getHeight() / 2));
        final Soul soul;
        switch (spriteNum) {
            case 1:
            default:
                soul = bl.soulA;
                break;
            case 2:
                soul = bl.soulB;
                break;
            case 3:
                soul = bl.soulC;
                break;
            case 4:
                soul = bl.soulD;
                break;
        }
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                objective = spriteNum;
            }
        });
        temp.addAction(Actions.forever(Actions.sequence(Actions.moveBy(-50f, 0,1.0f), Actions.moveBy(50f, 0, 1.0f))));
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (soul.isAlive()) {
                    if (objective == spriteNum) {
                        temp.setY(10 + stage.getHeight() / 2 + (y) - (temp.getHeight() / 2));
                    } else {
                        temp.setY(stage.getHeight() / 2 + (y) - (temp.getHeight() / 2));
                    }
                } else {
                    temp.addAction(Actions.color(Color.CLEAR, 1.0f));
                }
                return false;
            }
        });
        return temp;
    }

    public Image createHub(TextureAtlas.AtlasRegion a, int w, int h, int x, int y, Color color) {
        Image temp = new Image(a);
        temp.setPosition((x), (y));
        temp.setSize(w, h);
        temp.setColor(color);
        temp.setTouchable(Touchable.disabled);
        return temp;
    }

    public Image createHpGauge(TextureAtlas.AtlasRegion a, final int w, final int h, int x, int y, int spriteNum) {
        final Image temp = new Image(a);
        temp.setPosition((x), (y));
        temp.setSize(w, h);
        final Soul soul;
        switch (spriteNum) {
            case 1:
            default:
                soul = bl.soulA;
                break;
            case 2:
                soul = bl.soulB;
                break;
            case 3:
                soul = bl.soulC;
                break;
            case 4:
                soul = bl.soulD;
                break;
        }
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                temp.setSize((int) (soul.getVit() * w / soul.getMaxvit()), h);
                return false;
            }
        });
        return temp;
    }

    public Image createHabGauge2(TextureAtlas.AtlasRegion a, final int w, final int h, int x, int y, final int spriteNum) {
        final Image temp = new Image(a);
        temp.setPosition((x), (y));
        temp.setSize(w, h);
        switch (spriteNum) {
            case 1:
            default:
                break;
            case 2:
                temp.setVisible(false);
                break;
        }

        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                switch (spriteNum) {
                    case 1:
                    default:
                        temp.setVisible(isSoul1);
                        break;
                    case 2:
                        temp.setVisible(!isSoul1);
                        break;
                }
                temp.setSize((int) bl.getAtbSoul(spriteNum) * w / bl.maxAtb, h);
                return false;
            }
        });
        return temp;
    }

    private Actor createBtnHab(final int spriteNum, final int skillNum, Skin skin, String adefault, int w, int h, int x, int y) {
        final Skill skill = bl.getSkillset(spriteNum, skillNum);
        String cooldown = "1";
        switch (skill.getCdw()) {
            case VFAST:
                cooldown = "1";
                break;
            case FAST:
                cooldown = "2";
                break;
            case SLOW:
                cooldown = "3";
                break;
            case VSLOW:
                cooldown = "4";
                break;
        }
        final TextButton temp = new TextButton(skill.getName() + " [ " + cooldown + " ] ", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                if (bl.getAtbSoul(spriteNum) >= skill.getCdwTime(bl.maxAtb) && !isLogVisible) {
                    bl.skillInput(isSoul1, skillNum, true, objective);
                    isLogVisible = true;
                }
            }
        });
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                switch (spriteNum) {
                    case 1:
                    default:
                        temp.setVisible(isSoul1);
                        break;
                    case 2:
                        temp.setVisible(!isSoul1);
                        break;
                }
                temp.setDisabled(bl.getAtbSoul(spriteNum) < skill.getCdwTime(bl.maxAtb));
                return false;
            }
        });
        return temp;
    }

    private Actor createBtnSwap(Skin skin, String adefault, int w, int h, int x, int y) {
        final TextButton temp = new TextButton(bl.soulA.getName(), skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                System.out.println(bl.soulA.toString());
                System.out.println(bl.soulB.toString());
                System.out.println(bl.soulC.toString());
                System.out.println(bl.soulD.toString());
                isSoul1 = !isSoul1;
                if (isSoul1) {
                    temp.setText(bl.soulA.getName());
                } else {
                    temp.setText(bl.soulB.getName());
                }
            }
        });
        return temp;
    }

    private Actor createBtnSpd(Skin skin, String adefault, int w, int h, int x, int y) {
        final TextButton temp = new TextButton("> >", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                battleSpeed--;
                if (battleSpeed < 0) {
                    battleSpeed = 2;
                }
                switch (battleSpeed) {
                    case 1:
                    default:
                        temp.setText(">");
                        break;
                    case 2:
                        temp.setText("> >");
                        break;
                    case 0:
                        temp.setText("| |");
                        break;
                }
            }
        });
        return temp;
    }

    private Action createActions() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                bl.enemyActions(battleSpeed, isLogVisible);
                return false;
            }
        };
    }

    private synchronized Actor createLogInfo(final Skin skin, final String adefault, final int w, final int h, final int x, final int y) {
        final TextButton temp = new TextButton("Info de habilidad usada", skin, adefault);

        temp.setBounds(x, y, w, h);
        temp.setTouchable(Touchable.disabled);
        temp.setColor(0.7f, 0.7f, 0.7f, 1.0f);
        temp.setVisible(isLogVisible);
        temp.addAction(new Action() {
            @Override
            public synchronized boolean act(float delta) {
                if (bl.isSkillBeingUsed) {
                    bl.isSkillBeingUsed = false;
                    isLogVisible = true;
                    if (bl.isPlayerBox) {
                        temp.setColor(1.0f, 1.0f, 0.9f, 1.0f);
                    } else {
                        temp.setColor(1.0f, 0.5f, 0.2f, 1.0f);
                    }
                    temp.setVisible(isLogVisible);
                    temp.setText(bl.who + bl.with + "! " + bl.totalDmg);
                    com.badlogic.gdx.utils.Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isLogVisible = false;
                            temp.setVisible(isLogVisible);
                        }
                    }, 2.0f);
                }
                return false;
            }
        });
        return temp;
    }

    private Actor createBtnHelp(final Skin skin, final String adefault, int w, int h, int x, int y) {
        final TextButton temp = new TextButton(" ! ", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                isInfo = !isInfo;
            }
        });
        return temp;
    }

    private Actor createWdwInfo(Skin skin, String adefault, int w, int h, int x, int y) {
        String s
                = bl.soulA.toString()
                + "\n" + bl.skillA.get(0).getName() + " " + bl.skillA.get(0).getDescription()
                + "\n" + bl.skillA.get(1).getName() + " " + bl.skillA.get(1).getDescription()
                + "\n" + bl.skillA.get(2).getName() + " " + bl.skillA.get(2).getDescription()
                + "\n\n" + bl.soulB.toString()
                + "\n" + bl.skillB.get(0).getName() + " " + bl.skillB.get(0).getDescription()
                + "\n" + bl.skillB.get(1).getName() + " " + bl.skillB.get(1).getDescription()
                + "\n" + bl.skillB.get(2).getName() + " " + bl.skillB.get(2).getDescription();
        final TextArea ta = new TextArea(s, m.skin2, adefault);
        ta.setBounds(x, y, w, h);
        ta.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                ta.setVisible(isInfo);
                return false;
            }
        });
        return ta;
    }

    private Actor createIsP1(TextureAtlas.AtlasRegion get, int w, int h, int x, int y) {
        final ParticleEffectActor temp = new ParticleEffectActor(m.tex.getAnimation(1));
        temp.x = x;
        temp.y = y;
        temp.batch = m.sb;
        temp.setSize(w/2, h/2);
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (isSoul1) {
                    temp.x = 550;
                    temp.y = 500;
                } else {
                    temp.x = 550;
                    temp.y = 200;
                }
                return false;
            }
        });
        return temp;
    }

    private Actor createUsesLeft(Skin skin, String adefault, int w, int h, int x, int y) {
        final Label temp = new Label("Carga: 0", skin, adefault);
        temp.setSize(w, h);
        temp.setPosition(x, y);
        temp.setTouchable(Touchable.disabled);
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {

                temp.setText("Carga: " + bl.usesLeft(isSoul1) + " ");

                return false;
            }
        });
        return temp;
    }

    private Actor textBox(Skin skin, String adefault, int w, int h, int x, int y, final int id) {
        final Label temp = new Label("500", skin, adefault);
        temp.setSize(w, h);
        temp.setPosition(x, y);
        temp.setTouchable(Touchable.disabled);
        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                switch (id) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                    case 0:
                        break;
                }
                return false;
            }
        });
        return temp;
    }

    private Actor createSoulName(Skin skin, String adefault, int w, int h, int x, int y, int soulNum) {
        final Label temp = new Label(adefault, skin);
        Soul soul;
        switch (soulNum) {
            default:
            case 1:
                soul = bl.soulA;
                break;
            case 2:
                soul = bl.soulB;
                break;
            case 3:
                soul = bl.soulC;
                break;
            case 4:
                soul = bl.soulD;
                break;
        }
        temp.setText(soul.getName());
        temp.setPosition(x, y);
        temp.setSize(w, h);
        return temp;
    }

    private Actor createFinishButton(Skin skin, String adefault, int w, int h, int x, int y) {
        final TextButton temp = new TextButton(" ", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.addListener(new ClickListener() {
            @Override
            public synchronized void clicked(InputEvent event, float x, float y) {
                if (bl.isBattleFinished()) {
                    if (bl.soulA.isAlive() || bl.soulB.isAlive()) { //EXP of the battle
                        m.g.mySouls.get(0).moreExp(bl.soulC.getLv(), bl.soulD.getLv());
                        m.g.mySouls.get(1).moreExp(bl.soulC.getLv(), bl.soulD.getLv());
                    }

                    stage.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                            m.changeScreen(4);
                            return false;
                        }
                    });
                }
            }
        });

        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                temp.setVisible(bl.isBattleFinished());
                if (bl.soulA.isAlive() || bl.soulB.isAlive()) {
                    temp.setText("Has ganado la batalla\n Experiencia: " + (bl.soulC.getLv()+ bl.soulD.getLv())*3 );//Dice que esta al triple!!!
                } else {
                    temp.setText("Has perdido la batalla ");
                }
                return false;
            }
        });
        return temp;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        render();
    }

    @Override
    public void hide() {
    }

    private Actor createTargetAnim() {
        final ParticleEffectActor temp = new ParticleEffectActor(m.tex.getAnimation(0));
        temp.x = 100;
        temp.y = 100;
        temp.batch = m.sb;

        temp.addAction(new Action() {
            @Override
            public boolean act(float delta) {

                switch (objective) {
                    case 1:
                    default:
                        temp.x = 550;
                        temp.y = 500;
                        if (!bl.soulA.isAlive() && bl.soulB.isAlive()) {
                            objective = 2;
                        }
                        break;
                    case 2:
                        temp.x = 550;
                        temp.y = 200;
                        if (!bl.soulB.isAlive() && bl.soulA.isAlive()) {
                            objective = 1;
                        }
                        break;
                    case 3:
                        temp.x = 1050;
                        temp.y = 500;
                        if (!bl.soulC.isAlive() && bl.soulD.isAlive()) {
                            objective = 4;
                        }
                        break;
                    case 4:
                        temp.x = 1050;
                        temp.y = 200;
                        if (!bl.soulD.isAlive() && bl.soulC.isAlive()) {
                            objective = 3;
                        }
                        break;
                }
                return false;
            }
        });

        return temp;
    }

    private Actor createSoulDamage(Skin skin, String adefault, int w, int h, int x, int y, final int soulNum) {
        Label temp = new Label("000", skin, adefault);
        temp.setBounds(x, y, w, h);
        temp.setVisible(false);
        temp.addAction(new Action() {
            @Override
            public boolean act(float f) {
                temp.setVisible(isLogVisible);
                if (bl.isSkillBeingUsed) {
                    switch (soulNum) {
                        default:
                        case 1:
                            temp.setText(bl.dmgA);
                            break;
                        case 2:
                            temp.setText(bl.dmgB);
                            break;
                        case 3:
                            temp.setText(bl.dmgC);
                            break;
                        case 4:
                            temp.setText(bl.dmgD);
                            break;
                    }
                }
                return false;
            }

        });
        return temp;
    }

}
