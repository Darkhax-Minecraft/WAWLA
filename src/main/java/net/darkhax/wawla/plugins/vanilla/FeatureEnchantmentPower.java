package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class FeatureEnchantmentPower extends Feature implements IComponentProvider {
    
    private static final ResourceLocation TABLE_POWER = new ResourceLocation("wawla", "table_ench_power");
    private static final ResourceLocation BLOCK_POWER = new ResourceLocation("wawla", "block_ench_power");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(TABLE_POWER, true);
        hwyla.addConfig(BLOCK_POWER, true);
        
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, Block.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IDataAccessor accessor, IPluginConfig config) {
        
        if (config.get(BLOCK_POWER)) {
            
            final float power = accessor.getBlockState().getEnchantPowerBonus(accessor.getWorld(), accessor.getPosition());
            
            if (power > 0f) {
                
                info.add(this.getInfoComponent("enchpower", power));
            }
        }
        
        if (config.get(TABLE_POWER) && accessor.getBlock() instanceof EnchantingTableBlock) {
            
            final float power = this.getTotalPower(accessor.getWorld(), accessor.getPosition());
            
            if (power > 0f) {
                
                info.add(this.getInfoComponent("enchpower", power));
            }
        }
    }
    
    private float getTotalPower (World world, BlockPos pos) {
        
        float power = 0;
        
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if ((k != 0 || l != 0) && world.isAirBlock(pos.add(l, 0, k)) && world.isAirBlock(pos.add(l, 1, k))) {
                    power += this.getPower(world, pos.add(l * 2, 0, k * 2));
                    power += this.getPower(world, pos.add(l * 2, 1, k * 2));
                    
                    if (l != 0 && k != 0) {
                        power += this.getPower(world, pos.add(l * 2, 0, k));
                        power += this.getPower(world, pos.add(l * 2, 1, k));
                        power += this.getPower(world, pos.add(l, 0, k * 2));
                        power += this.getPower(world, pos.add(l, 1, k * 2));
                    }
                }
            }
        }
        
        return power;
    }
    
    private float getPower (World world, BlockPos pos) {
        
        return world.getBlockState(pos).getEnchantPowerBonus(world, pos);
    }
}