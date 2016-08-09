package com.settingdust.levelpoints.config;

import org.bukkit.configuration.file.YamlConfiguration;
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
    private YamlConfig fileConfiguration = new YamlConfig();

    /**
     * Init config
     *
     * @param fileName
     * @param plugin
     */
    public Config(String fileName, JavaPlugin plugin) {
        this.plugin = plugin;
        this.load(fileName, true);
    }

    /**
     * @param fileName
     * @param plugin
     * @param def
     */
    public Config(String fileName, JavaPlugin plugin, boolean def) {
        this.plugin = plugin;
        this.load(fileName, def);
    }

    /**
     * @param plugin
     */
    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @param fileName
     * @param def
     */
    public void load(String fileName, boolean def) {
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.load(configFile, def);
    }

    /**
     * @param configFile
     * @param def
     */
    public void load(File configFile, boolean def) {
        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                if (def)
                    this.load(plugin.getResource(configFile.getName()));
                else
                    this.load(new FileInputStream(configFile));
                this.saveConfig();
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
        this.fileConfiguration = YamlConfig.loadConfiguration(inputStream);
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
        this.load(configFile, true);
    }

    public void saveConfig() {
        try {
            this.fileConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
