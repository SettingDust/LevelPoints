package com.settingdust.levelpoints.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class Config {
    private String fileName;
    private final JavaPlugin plugin;

    private File configFile;
    private YamlConfig fileConfiguration;

    /**
     * Init config
     *
     * @param fileName
     * @param plugin
     */
    public Config(String fileName, JavaPlugin plugin) {
        this.plugin = plugin;
        this.load(fileName);
    }

    /**
     *
     * @param plugin
     */
    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Load config with name
     *
     * @param fileName
     */
    public void load(String fileName) {
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.load(configFile);
    }

    /**
     * Load config with File
     *
     * @param configFile
     */
    public void load(File configFile) {
        try {
            if (!configFile.exists()) {
                configFile.mkdirs();
                configFile.createNewFile();
                this.plugin.saveResource(fileName, true);
                this.load(plugin.getResource(fileName));
            } else
                this.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load file with InputStream
     *
     * @param inputStream
     */
    @Deprecated
    public void load(InputStream inputStream) {
        try {
            this.fileConfiguration.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return file name
     *
     * @return
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Return current config
     *
     * @return
     */
    public YamlConfig getConfig() {
        return fileConfiguration;
    }

    /**
     * Reload config
     */
    public void reloadConfig() {
        this.load(fileName);
    }

    public void saveConfig(){
        try {
            this.fileConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
