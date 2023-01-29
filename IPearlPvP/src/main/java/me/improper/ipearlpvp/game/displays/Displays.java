package me.improper.ipearlpvp.game.displays;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LightningStrike;

public class Displays {

    public static void sprinkle(Location location) {
        double radius = 3;
        for (double y = 0; y < 80; y += 0.05) {
            double x = Math.cos(y) * radius;
            double z = Math.sin(y) * radius;
            Location newLoc = location.clone().add(x,y/6,z);
            newLoc.getWorld().spawnParticle(Particle.END_ROD,newLoc,1,0,0,0,0);
            //radius = radius < 0.4 ? radius : radius - 0.005;
            radius -= 0.005;
        }
        location.getWorld().spawn(location,LightningStrike.class);
    }
}
