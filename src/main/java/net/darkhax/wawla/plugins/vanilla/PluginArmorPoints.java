package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class PluginArmorPoints extends Feature implements IEntityComponentProvider, IServerDataProvider<Entity> {

    private static final ResourceLocation ENABLED = new ResourceLocation("wawla", "armor_points");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(ENABLED, true);
        hwyla.registerEntityDataProvider(this, LivingEntity.class);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, LivingEntity.class);
    }
        
    @Override
    public void appendBody (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {
        
        if (config.get(ENABLED)) {
            
            final int armorPoints = accessor.getServerData().getInt("WawlaArmor");
            
            if (armorPoints > 0) {
                
                this.addInfo(info, "armor", armorPoints);
            }
        }
    }

    @Override
    public void appendServerData (CompoundNBT nbt, ServerPlayerEntity player, World world, Entity target) {

        if (target instanceof LivingEntity) {
            
            nbt.putInt("WawlaArmor", ((LivingEntity) target).getTotalArmorValue());
        }
    }
}