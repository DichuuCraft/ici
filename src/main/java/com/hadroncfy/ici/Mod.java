package com.hadroncfy.ici;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.hadroncfy.ici.config.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class Mod implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Config config;

    @Override
    public void onInitialize() {
        try {
            loadConfig();
            LOGGER.info("ici: Loaded config");
        }
        catch(Throwable e){
            LOGGER.error("ici: Failed to load config", e);
            e.printStackTrace();
        }
    }

    public static void loadConfig() throws IOException {
        File f = new File("config", "ici.json");
        if (f.exists()){
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)){
                config = Config.GSON.fromJson(reader, Config.class);
            }
        }
        else {
            config = new Config();
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)){
            writer.write(Config.GSON.toJson(config));
        }
    }

    public static Config getConfig(){
        return config;
    }
}