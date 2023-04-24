package de.malte.coinapi;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CoinSystem {
    /**
     *
     * @param uuid The UUID of the Player you want the Coins from
     * @return The amount of Coins the Player has in form of an Optional in case the Player is not in the database
     */
    Optional<Double> getCoins(UUID uuid);
    /**
     *
     * @param uuid The UUID of the Player you want to set Coins for
     * @param amount The amount of Coins you want to set them to
     * @return true if success, false if Coins would go into negative
     */
    boolean setCoins(UUID uuid, double amount);
    /**
     *
     * @param uuid The UUID of the Player you want to add Coins to
     * @param amount The amount of Coins you want to add
     * @return true if success, false if amount is a negative value
     */
    boolean addCoins(UUID uuid, double amount);
    /**
     *
     * @param uuid The UUID of the Player you want to remove Coins from
     * @param amount The amount of Coins you want to remove
     * @return true if success, false if Coins would go into negative
     */
    boolean removeCoins(UUID uuid, double amount);
    /**
     *
     * @return An HashMap of all PlayerUUIDs and those Coins that are registered in database
     */
    Optional<Map<UUID, Double>> getAllCoins();
}
