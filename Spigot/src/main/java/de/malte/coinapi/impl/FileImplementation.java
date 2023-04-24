package de.malte.coinapi.impl;

import de.malte.coinapi.CoinSystem;
import de.malte.coinapi.utils.FileConfig;
import de.malte.coinapi.utils.Util;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileImplementation implements CoinSystem {
    private final FileConfig coinsConfig;

    public FileImplementation(FileConfig coinsConfig) {
        this.coinsConfig = coinsConfig;
    }

    @Override
    public Optional<Double> getCoins(UUID uuid) {
        Optional<Map<UUID, Double>> optional = getAllCoins();
        return optional.map(uuidDoubleMap -> uuidDoubleMap.get(uuid));
    }

    @Override
    public boolean setCoins(UUID uuid, double amount) {
        if (amount < 0.0)
            return false;
        coinsConfig.set(Util.convertUUID(uuid), amount);
        coinsConfig.saveConfig();
        return true;
    }

    @Override
    public boolean addCoins(UUID uuid, double amount) {
        if (amount < 0.0)
            return false;
        Optional<Double> coins = getCoins(uuid);
        return setCoins(uuid, coins.orElse(0.0) + amount);
    }

    @Override
    public boolean removeCoins(UUID uuid, double amount) {
        Optional<Double> coins = getCoins(uuid);
        return setCoins(uuid, coins.orElse(0.0) - amount);
    }

    @Override
    public Optional<Map<UUID, Double>> getAllCoins() {
        ConfigurationSection section = coinsConfig;
        Map<UUID, Double> map = new HashMap<>();
        for (String key : section.getKeys(false)) {
            map.put(Util.convertUUID(key), section.getDouble(key));
        }
        return Optional.of(map);
    }
}
