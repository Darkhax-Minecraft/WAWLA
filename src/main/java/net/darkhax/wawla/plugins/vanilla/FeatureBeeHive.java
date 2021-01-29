package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class FeatureBeeHive extends Feature implements IComponentProvider, IServerDataProvider<TileEntity> {
    
    private static final ResourceLocation SHOW_COUNT = new ResourceLocation("wawla", "beehive_count");
    private static final ResourceLocation SHOW_HONEY = new ResourceLocation("wawla", "beehive_honey");
    private static final ResourceLocation SHOW_SMOKED = new ResourceLocation("wawla", "beehive_smoked");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(SHOW_COUNT, true);
        hwyla.addConfig(SHOW_HONEY, true);
        hwyla.addConfig(SHOW_SMOKED, true);
        
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, BeehiveBlock.class);
        hwyla.registerBlockDataProvider(this, BeehiveBlock.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IDataAccessor accessor, IPluginConfig config) {
        
        if (accessor.getTileEntity() instanceof BeehiveTileEntity) {
            
            final BeehiveTileEntity hive = (BeehiveTileEntity) accessor.getTileEntity();
            
            if (config.get(SHOW_COUNT) && accessor.getServerData().contains("WAWLABeeCount")) {
                
                final int count = accessor.getServerData().getInt("WAWLABeeCount");
                
                if (count > 0) {
                    
                    this.addInfo(info, "beehive.count", count);
                }
                
                else {
                    
                    this.addInfo(info, "beehive.empty");
                }
            }
            
            final int honeyLevel = BeehiveTileEntity.getHoneyLevel(accessor.getBlockState());
            
            if (config.get(SHOW_HONEY) && honeyLevel > 0) {
                
                this.addInfo(info, "beehive.honey", honeyLevel);
            }
            
            if (config.get(SHOW_SMOKED) && hive.isSmoked()) {
                
                this.addInfo(info, "beehive.smoked");
            }
        }
    }
    
    @Override
    public void appendServerData (CompoundNBT nbt, ServerPlayerEntity player, World world, TileEntity tile) {
        
        if (tile instanceof BeehiveTileEntity) {
            
            final BeehiveTileEntity hive = (BeehiveTileEntity) tile;
            nbt.putInt("WAWLABeeCount", hive.getBeeCount());
        }
    }
}