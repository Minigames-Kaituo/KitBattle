package fun.kaituo;


import fun.kaituo.event.PlayerChangeGameEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static fun.kaituo.GameUtils.*;

public class KitBattle extends JavaPlugin {
    List<Player> players = new ArrayList<>();

    public static KitBattleGame getGameInstance() {
        return KitBattleGame.getInstance();
    }

    public void onEnable() {
        saveDefaultConfig();
        registerGame(getGameInstance());
        KitBattleGame.getInstance().startGame();
        getCommand("kbspawn").setExecutor(new KitBattleCommandExecutor(getGameInstance().spawnLocations, players));
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
        if (players.size() > 0) {
            for (Player p : players) {
                p.teleport(new Location(world, 0.5, 89.0, 0.5));
                Bukkit.getPluginManager().callEvent(new PlayerChangeGameEvent(p, getGameInstance(), null));
            }
        }
        unregisterGame(getGameInstance());
    }
}