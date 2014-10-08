package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ModuleLightLevel extends Module {

    private String showDay = "wawla.light.showDay";
    private String showMonsterSpawn = "wawla.light.monsterSpawn";
    private String showLightLevel = "wawla.light.lightLevel";

    public ModuleLightLevel(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getBlock() != null && access.getWorld() != null) {

            if (config.getConfig(showLightLevel) && (!access.getWorld().isBlockNormalCubeDefault(access.getPosition().blockX, access.getPosition().blockY + 1, access.getPosition().blockZ, false) || access.getWorld().isAirBlock(access.getPosition().blockX, access.getPosition().blockY + 1, access.getPosition().blockZ))) {

                int dayLevel = getBlockLightLevel(access.getWorld(), access.getPosition().blockX, access.getPosition().blockY, access.getPosition().blockZ, true);
                int nightLevel = getBlockLightLevel(access.getWorld(), access.getPosition().blockX, access.getPosition().blockY, access.getPosition().blockZ, false);

                String display = StatCollector.translateToLocal("tooltip.wawla.lightLevel") + ": ";

                if (config.getConfig(showMonsterSpawn)) {

                    if (nightLevel <= 7)
                        display = display + EnumChatFormatting.DARK_RED + "" + nightLevel + " ";

                    else if (nightLevel > 7)
                        display = display + EnumChatFormatting.GREEN + "" + nightLevel + " ";
                }

                if (config.getConfig(showDay))
                    display = display + EnumChatFormatting.YELLOW + "(" + dayLevel + ")";

                tooltip.add(display);
            }
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla-General", showLightLevel);
        register.addConfig("Wawla-General", showMonsterSpawn);
        register.addConfig("Wawla-General", showDay);
    }

    /**
     * Retrieves the light value (0-15) of a block. Keep in mind that the light level of a block is
     * usually determined by what is above it. As such of you were to specify a block that does not have
     * an empty space above it (like a block in a wall) you will be reading the light level in the block
     * above, this will normally return 0, unless the block is a source of light. This method is set to
     * ignore all light coming from non-block sources such as the sun and moon.
     * 
     * @param world: An instance of the world.
     * @param x: The x position of the block.
     * @param y: The y position of the block. (the +1 to get above is done by the method)
     * @param z: The z position of the block.
     * @param day: Would you like to take daylight into account?
     * @return int: An integer between 0 and 15, depending on the light level. 15 represents the highest
     *         possible strength of light, while 0 repressions a complete absence of light.
     */
    public int getBlockLightLevel(World world, int x, int y, int z, boolean day) {

        return (day) ? world.getChunkFromChunkCoords(x >> 4, z >> 4).getBlockLightValue(x & 0xF, y + 1, z & 0xF, 0) : world.getChunkFromChunkCoords(x >> 4, z >> 4).getBlockLightValue(x & 0xF, y + 1, z & 0xF, 16);
    }
}
