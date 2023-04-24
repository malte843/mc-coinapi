package de.malte.coinapi.impl;

import de.malte.coinapi.CoinSystem;
import de.malte.coinapi.utils.MySQL;
import de.malte.coinapi.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MySQLImplementation implements CoinSystem {
    private final MySQL mySQL;

    public MySQLImplementation(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    @Override
    public Optional<Double> getCoins(UUID uuid) {
        try (ResultSet rs = mySQL.query("SELECT amount FROM coins WHERE uuid=?", Util.convertUUID(uuid))) {
            if (rs.next()) {
                return Optional.of(rs.getDouble("coins"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean setCoins(UUID uuid, double amount) {
        if (amount < 0.0)
            return false;
        mySQL.update("UPDATE coins SET amount=? WHERE uuid=?", amount, Util.convertUUID(uuid));
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

    private Optional<Map<String, Double>> getAllCoinsString() {
        Map<String, Double> map = new HashMap<>();
        try (ResultSet rs = mySQL.query("SELECT * FROM coins")) {
            while (rs.next())
                map.put(rs.getString("uuid"), rs.getDouble("amount"));
            return Optional.of(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Map<UUID, Double>> getAllCoins() {
        Optional<Map<String, Double>> optional = getAllCoinsString();
        return optional.map(stringDoubleMap -> stringDoubleMap.entrySet().stream()
                .collect(Collectors.toMap(k -> Util.convertUUID(k.getKey()), Map.Entry::getValue)));
    }
}
