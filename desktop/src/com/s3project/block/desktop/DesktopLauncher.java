package com.s3project.block.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.s3project.block.Main;
import com.s3project.block.Tools.MainWdw;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = (int) (720*0.8);
        config.width = (int)(1280*0.8);
        config.resizable = true;
        //config.fullscreen = true;
        new MainWdw().setVisible(true);
        new LwjglApplication(new Main(), config);
//        new LwjglApplication(new TextureDownloadTest(), config);
    }
}
