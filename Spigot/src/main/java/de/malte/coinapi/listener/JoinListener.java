package de.malte.coinapi.listener;

import de.malte.coinapi.CoinAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final double initialCoins;

    public JoinListener(double initialCoins) {
        this.initialCoins = initialCoins;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (CoinAPI.getCoinSystem().getCoins(event.getPlayer().getUniqueId()).isPresent()) return;
        CoinAPI.getCoinSystem().addCoins(event.getPlayer().getUniqueId(), initialCoins);
    }
}
