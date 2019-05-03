/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Preloads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/**
 *
 * @author S34n4e
 */
public class Textures {

    private final TextureAtlas t1; //GUI
    private final TextureAtlas t2; //Monsters
    private final TextureAtlas t3; //Monsters portrait
    private final TextureAtlas t1f; //GUI flip
    private final TextureAtlas t2f; //Monsters flip
    private final TextureAtlas tbg; //Backgrounds
    private ParticleEffectPool.PooledEffect effect;
    private final Array<ParticleEffectPool.PooledEffect> animationList;

    public Textures() {
        t1 = new TextureAtlas("atlas1.atlas");
        t2 = new TextureAtlas("atlas2.atlas");
        t3 = new TextureAtlas("atlas4.atlas");
        t1f = new TextureAtlas("atlas1.atlas");
        t2f = new TextureAtlas("atlas2.atlas");
        tbg = new TextureAtlas("atlas3.atlas");
        Iterator it = t1f.getRegions().iterator();
        while (it.hasNext()) {
            AtlasRegion a = (AtlasRegion) it.next();
            a.flip(true, false);
        }
        it = t2f.getRegions().iterator();
        while (it.hasNext()) {
            AtlasRegion a = (AtlasRegion) it.next();
            a.flip(true, false);
        }
        animationList = new Array<ParticleEffectPool.PooledEffect>();
        addParticleEffect("objetivo"); //#0
        addParticleEffect("usuario");  //#1
    }

    private void addParticleEffect(String effectName) {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Gdx.files.internal("animation/" + effectName + ".party"), t1);
        //pe.load(Gdx.files.internal("animation/" + effectName + ".party"), Gdx.files.internal("animation/"));
        animationList.add(new ParticleEffectPool(pe, 1, 4).obtain());
        animationList.get(0).setPosition(0, 0);
    }

    /**
     * @return AtlasRegion de HUB o GUI
    *
     */
    public AtlasRegion get(String s) {
        try {
            return t1.findRegion(s);
        } catch (Exception e) {
            System.out.println(" ! ! ! Error: ! ! ! " + e);
            return t1.findRegion("hubvit");
        }
    }

    /**
     * @return AtlasRegion de HUB o GUI con flip
    *
     */
    public AtlasRegion get(String s, boolean flip) {
        try {
            if (flip) {
                return t1f.findRegion(s);
            } else {
                return t1.findRegion(s);
            }
        } catch (Exception e) {
            System.out.println(" ! ! ! Error: ! ! ! " + e);
            return t1f.findRegion("hubvit");
        }
    }

    /**
     * @param s monster name
     * @return AtlasRegion de monster
     *
     */
    public AtlasRegion get2(String s) {
        try {
            return t2.findRegion(s);
        } catch (Exception e) {
            System.out.println(" ! ! ! Error: ! ! ! " + e);
            return t1.findRegion("hubvit");
        }
    }

    /**
     * @param s monster name
     * @return AtlasRegion de monster con flip
     *
     */
    public AtlasRegion get2(String s, boolean flip) {
        try {
            return t2f.findRegion(s);
        } catch (Exception e) {
            System.out.println(" ! ! ! Error: ! ! ! " + e);
            return t2f.findRegion("hubvit");
        }
    }

    public void drawAnimation(int idAnimation, SpriteBatch batch, int x, int y) {
        if (effect != animationList.get(idAnimation)) {
            effect = animationList.get(idAnimation);
        }
        effect.setPosition(x, y);
        effect.draw(batch, Gdx.graphics.getDeltaTime());
        refreshOneAnimation(idAnimation);
        if (effect.isComplete()) {
            effect.free();
        }
    }

    public void refreshOneAnimation(int id) {
        if (animationList.get(id).isComplete()) {
            animationList.get(id).free();
            animationList.get(id).reset();
        }
    }

    public void kill() {
        t1.dispose();
        t2.dispose();
        t1f.dispose();
        t2f.dispose();
        tbg.dispose();
    }

    public ParticleEffect getAnimation(int index) {
        return animationList.get(index);
    }

    /**
     * @return AtlasRegion de backgrounds
    *
     */
    public AtlasRegion getBG(String bg) {
        try {
            return tbg.findRegion(bg);
        } catch (Exception e) {
            System.out.println(" ! ! ! Error: ! ! ! " + e);
            return t1.findRegion("hubvit");
        }
    }

    /**
     * @param s monster name
     * @return AtlasRegion de portrait de monster
    *
     */
    public AtlasRegion get3(String s) {
        try {
            return t3.findRegion(s + "2");
        } catch (Exception e) {
            System.out.println(" ! ! ! Error: ! ! ! " + e);
            return t1.findRegion("hubvit");
        }
    }

}
