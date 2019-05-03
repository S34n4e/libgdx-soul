package com.s3project.block;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.s3project.block.Preloads.GameData;
import com.s3project.block.Preloads.LocateCommons;
import com.s3project.block.Preloads.RenderCommons;
import com.s3project.block.Screens.MenuScreen;
import com.s3project.block.Preloads.Textures;
import com.s3project.block.Screens.BattleChoiceScreen;
import com.s3project.block.Screens.BattleScreen;
import com.s3project.block.Screens.ChangeSoulScreen;
import com.s3project.block.Screens.EquipScreen;
import com.s3project.block.Screens.TitleScreen;
import java.io.UnsupportedEncodingException;

public final class Main extends Game {

    public Camera cam;
    public Viewport view = new ScalingViewport(Scaling.fit, 1280, 720, cam);
    public SpriteBatch sb;
    public Skin skin;
    public Skin skin2;
    public Stage stage;
    public Textures tex;
    public RenderCommons rc;
    public LocateCommons lc;
    public GameData g;
    public Music bms;
    public int bmsNum;
    public int battleCode;
    
    public void sound(int id) {
        if(bmsNum != id){
        bmsNum = id;
        if(bms == null) {
        bms = Gdx.audio.newMusic(Gdx.files.internal("bms/ff15arrange.ogg"));
        }
        bms.stop();
        switch(id){
            case 1:
                bms = Gdx.audio.newMusic(Gdx.files.internal("bms/ff15arrange.ogg"));
        break;
            case 2:
            default:
                bms = Gdx.audio.newMusic(Gdx.files.internal("bms/ff13-2battlethemearrange.ogg"));
                break;
        }
        bms.play();
        bms.setLooping(true);
        }
    }
    

    @Override
    public void create() {

        try {
            FileHandle file = Gdx.files.local("gamedata.txt");//load file --done
            Json json = new Json(JsonWriter.OutputType.json);
            json.addClassTag("gamedata", Array.class);
            Array<GameData> templist = new Json().fromJson(Array.class, file);
            g = templist.get(0);
            g.reloadAllSkillSoul();
            g.fillBattles();
        } catch (Exception e) {
            g = new GameData();
            addRandomSoulandLevel(5, 5, 70, false);
        }
        bmsNum = 0;
        rc = new RenderCommons();
        lc = new LocateCommons();
        tex = new Textures();
        skin = new Skin(); // Common size
        skin2 = new Skin(); // Big size, titles, etc...
        stage = new Stage();
        cam = new OrthographicCamera(1280, 720);
        view = new ScalingViewport(Scaling.fit, 1280, 720, cam);
        sb = new SpriteBatch();
        view.apply();
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);

        stage.setViewport(view);
        skin.addRegions(new TextureAtlas(Gdx.files.internal("data/uiskin.atlas")));
        skin.add("default-font",
                fontGenerator(Color.BLACK, 2, Color.LIGHT_GRAY, 0.8f, 24),
                BitmapFont.class);
        skin.load(Gdx.files.internal("data/uiskin.json"));

        skin2.addRegions(new TextureAtlas(Gdx.files.internal("data/uiskin.atlas")));
        skin2.add("default-font",
                fontGenerator(Color.BLACK, 1, Color.LIGHT_GRAY, 0.8f, 36),
                BitmapFont.class);
        skin2.load(Gdx.files.internal("data/uiskin.json"));

        Gdx.input.setInputProcessor(stage);
        changeScreen(4);
    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    private void addRandomSoulandLevel(int IniLV, int maxLV, int quantity, boolean isRandom) {
        while (quantity > 0) {
            if (isRandom) {
                g.mySouls.add(g.allSouls.get(MathUtils.random(1, 70)).copy());
            } else {
                if (quantity > g.allSouls.size) {
                    quantity = (g.allSouls.size - 1);
                }
                g.mySouls.add(g.allSouls.get(quantity).copy());
            }
            g.mySouls.get(g.mySouls.size - 1).calc((short) MathUtils.random(IniLV, maxLV));
            quantity--;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    public BitmapFont fontGenerator(Color shadowC, int offset, Color borderC, float border, int size) {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.shadowColor = shadowC;
        parameter.shadowOffsetX = offset;
        parameter.shadowOffsetY = offset;
        parameter.borderColor = borderC;
        parameter.borderWidth = border;
        parameter.size = size;
        BitmapFont bfont = gen.generateFont(parameter);
        gen.dispose();
        return bfont;
    }

    /**
     *
     * @param i 1(Menu) 2(Battle) 3(Equip) 4(Title) 5(Change Soul)
     */
    public void changeScreen(int i) {
        stage.clear();
        save();
        //stage.addAction(Actions.moveBy(0, 100, 5.0f));
        switch (i) {
            case 1:
                setScreen(new MenuScreen(Main.this));
                break;
            case 2:
                setScreen(new BattleScreen(this, battleCode));
                break;
            case 3:
                setScreen(new EquipScreen(Main.this));
                break;
            case 4:
                setScreen(new TitleScreen(Main.this));
                break;
            case 5:
                setScreen(new ChangeSoulScreen(Main.this));
                break;
            case 6:
                setScreen(new BattleChoiceScreen(this));
                break;
        }

    }
    
    private void save(){
        Array<GameData> templist = new Array<GameData>();
        templist.add(g);
        FileHandle file = Gdx.files.local("gamedata.txt");//load file --done
        Json json = new Json(JsonWriter.OutputType.json);
        json.addClassTag("gamedata", templist.getClass());
        try {
            //write
            file.writeBytes(json.toJson(templist, templist.getClass()).getBytes("UTF-8"), false);
        } catch (UnsupportedEncodingException ex) {
            System.err.println("ERR: " + ex.getLocalizedMessage());
        }
    }

}
