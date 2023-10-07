package dev.ninjune.delta.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import dev.ninjune.delta.Delta;

public class DeltaConfig extends Config
{
    @Switch(
            name = "Beam Highlight",
            size = OptionSize.SINGLE,
            category = "Slayers",
            subcategory = "Blaze"
    )
    public static boolean beamHighlight = false;

    public DeltaConfig()
    {
        super(new Mod(Delta.MODNAME, ModType.SKYBLOCK), "delta.json");
        initialize();
    }
}
