package dev.ninjune.delta.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import dev.ninjune.delta.Delta;

public class DeltaConfig extends Config
{
    @Switch(
            name = "Beam Highlight",
            description = "Highlights the beams on Blaze and Enderman boss.",
            category = "Slayers",
            subcategory = "Blaze"
    )
    public static boolean beamHighlight = false;

    @Switch(
            name = "Hide Original Beam",
            description = "Hides the original guardian beams.",
            category = "Slayers",
            subcategory = "Blaze"
    )
    public static boolean hideOriginalBeam = true;

    @Switch(
            name = "Beam 2d",
            description = "Make beam 2d",
            category = "Slayers",
            subcategory = "Blaze"
    )
    public static boolean beam2d = false;

    @Switch(
            name = "Slime NoRender",
            description = "No render for slimes/sludges in chollows.",
            category = "General"
    )
    public static boolean slimeNoRender = false;

    public DeltaConfig()
    {
        super(new Mod(Delta.MODNAME, ModType.SKYBLOCK), "delta.json");
        initialize();
        addDependency("Hide Original Beam", "Beam Highlight");
        addDependency("Beam 2d", "Beam Highlight");
    }
}
