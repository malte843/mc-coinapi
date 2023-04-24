package de.malte.coinapi;

import de.malte.coinapi.impl.FileImplementation;
import de.malte.coinapi.impl.MySQLImplementation;
import de.malte.coinapi.listener.JoinListener;
import de.malte.coinapi.utils.FileConfig;
import de.malte.coinapi.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinAPI extends JavaPlugin {
    private static CoinSystem coinSystem;
    public static String dbType;

    private FileConfig coinsConfig;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("coins.yml", false);
        dbType = getConfig().getString("databaseType");
        switch (dbType) {
            case "mysql":
                mySQL = new MySQL();
                mySQL.setupDataSource(
                        getConfig().getString("address"),
                        getConfig().getInt("port"),
                        getConfig().getString("database"),
                        getConfig().getString("username"),
                        getConfig().getString("password")
                );
                coinSystem = new MySQLImplementation(mySQL);
                break;
            case "file":
                coinsConfig = new FileConfig(getDataFolder().getName(), "coins.yml");
                coinSystem = new FileImplementation(coinsConfig);
                break;
            default:
                throw new IllegalArgumentException("Invalid config argument '" + dbType + "' \n'databaseType' can only be 'mysql' or 'file'");
        }
        double initialCoins = getConfig().getDouble("initialCoins");
        if (initialCoins > 0.0)
            Bukkit.getPluginManager().registerEvents(new JoinListener(initialCoins), this);
    }

    public static CoinSystem getCoinSystem() {
        return coinSystem;
    }
}
