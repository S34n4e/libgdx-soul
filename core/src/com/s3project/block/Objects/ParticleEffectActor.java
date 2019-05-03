/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s3project.block.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
   private ParticleEffect effect;
   public int x;
   public int y;
   public Batch batch;

   public ParticleEffectActor(ParticleEffect effect) {
      this.effect = effect;
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);
            effect.draw(batch); //define behavior when stage calls Actor.draw()
   }
   
   @Override
   public void act(float delta) {
      super.act(delta);
            effect.setPosition(x, y); //set to whatever x/y you prefer
            effect.update(delta); //update it
            effect.start(); //need to start the particle spawning
   }

   public ParticleEffect getEffect() {
      return effect;
   }
}