package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ModuleLightLevel extends Module {

    public ModuleLightLevel(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        int x = access.getPosition().blockX;
        int y = access.getPosition().blockY;
        int z = access.getPosition().blockZ;

        if (access.getBlock() != null && access.getWorld() != null && access.getWorld().isAirBlock(x, y + 1, z)) {

            int level = getBlockLightLevel(access.getWorld(), x, y, z);

            String color = "";

            if (level > 7)
                color = "" + EnumChatFormatting.GREEN;

            else
                color = "" + EnumChatFormatting.DARK_RED;

            tooltip.add("Light Level: " + color + level);
        }
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
     * @param z: The z position of the bloc.
     * @return int: An integer between 0 and 15, depending on the light level. 15 represents the highest
     *         possible strength of light, while 0 repressions a complete absence of light.
     */
    public int getBlockLightLevel(World world, int x, int y, int z) {

        return world.getChunkFromChunkCoords(x >> 4, z >> 4).getBlockLightValue(x & 0xF, y + 1, z & 0xF, 16);
    }
}
