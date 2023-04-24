package de.malte.coinapi;

import de.malte.coinapi.impl.MySQLImplementation;
import de.malte.coinapi.listener.JoinListener;
import de.malte.coinapi.utils.FileConfig;
import de.malte.coinapi.utils.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CoinAPI extends Plugin {
    private static CoinSystem coinSystem;
    private FileConfig config;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        File file = new File(getDataFolder(), "config.yml");
        saveResource("config.yml", file.toPath(), false);
        config = new FileConfig(file);
        mySQL = new MySQL();
        mySQL.setupDataSource(
                config.getConfig().getString("address"),
                config.getConfig().getInt("port"),
                config.getConfig().getString("database"),
                config.getConfig().getString("username"),
                config.getConfig().getString("password")
        );
        coinSystem = new MySQLImplementation(mySQL);
        double initialCoins = config.getConfig().getDouble("initialCoins");
        if (initialCoins > 0.0)
            ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinListener(initialCoins));
    }

    private void saveResource(String path, Path savePath, boolean replace) {
        InputStream stream = getClass().getResourceAsStream(path);
        try {
            if (replace)
                Files.copy(stream, savePath, StandardCopyOption.REPLACE_EXISTING);
            else
                Files.copy(stream, savePath);
        } catch (FileAlreadyExistsException ignore) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CoinSystem getCoinSystem() {
        return coinSystem;
    }
}
