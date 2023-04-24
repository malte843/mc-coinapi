package de.malte.coinapi.listener;

import de.malte.coinapi.CoinAPI;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {
    private final double initialCoins;

    public JoinListener(double initialCoins) {
        this.initialCoins = initialCoins;
    }

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        if (CoinAPI.getCoinSystem().getCoins(event.getPlayer().getUniqueId()).isPresent()) return;
        CoinAPI.getCoinSystem().addCoins(event.getPlayer().getUniqueId(), initialCoins);
    }
}
