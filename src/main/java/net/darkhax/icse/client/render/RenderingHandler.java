package net.darkhax.icse.client.render;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.icse.ICSE;
import net.darkhax.icse.common.packet.PacketRequestInfo;
import net.darkhax.icse.lib.DataAccess;
import net.darkhax.icse.lib.Utilities;
import net.darkhax.icse.plugins.InfoPlugin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {
    
    public static NBTTagCompound dataTag = new NBTTagCompound();
    private static UUID lastEntity;
    private static int lineCount;
    
    @SubscribeEvent
    public void onOverlayRendered (RenderGameOverlayEvent.Text event) {
        
        final Minecraft mc = Minecraft.getMinecraft();
        
        if (mc.gameSettings.showDebugInfo)
            return;
            
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            
            float distance = mc.playerController.getBlockReachDistance();
            Entity entity = getMouseOver(mc, event.getPartialTicks());
            RayTraceResult results = rayTrace(mc.getRenderViewEntity(), distance, event.getPartialTicks());
            
            if (results != null && mc.theWorld != null) {
                
                IBlockState state = mc.theWorld.getBlockState(results.getBlockPos());
                if (entity != null) {
                    
                    if (lastEntity != null && !lastEntity.equals(entity.getUniqueID()))
                        dataTag = new NBTTagCompound();
                        
                    lastEntity = entity.getUniqueID();
                    DataAccess info = new DataAccess(mc.theWorld, mc.thePlayer, entity, dataTag);
                    
                    boolean requireSync = false;
                    for (InfoPlugin provider : ICSE.plugins)
                        if (provider.requireEntitySync(mc.theWorld, entity))
                            requireSync = true;
                            
                    if (requireSync && mc.thePlayer.ticksExisted % 20 == 0)
                        ICSE.network.sendToServer(new PacketRequestInfo(entity.getUniqueID()));
                        
                    if (info.isValidEntity()) {
                        
                        for (InfoPlugin provider : ICSE.plugins)
                            if (provider.requireEntityOverride(info))
                                info = provider.overrideEntity(info);
                                
                        if (info.isValidEntity()) {
                            
                            lineCount = 0;
                            
                            List<String> lines = new ArrayList<String>();
                            lines.add(info.entity.getDisplayName().getFormattedText());
                            
                            for (InfoPlugin provider : ICSE.plugins)
                                provider.addEntityInfo(lines, info);
                                
                            lines.add(ChatFormatting.BLUE + "" + ChatFormatting.ITALIC + Utilities.getModName(info.entity));
                            
                            for (String line : lines)
                                mc.fontRendererObj.drawStringWithShadow(line, 10, getLineOffset(), 0xFFFFFF);
                        }
                    }
                }
                
                else if (state != null && state.getBlock() != null) {
                    
                    DataAccess info = new DataAccess(results, mc.theWorld, mc.thePlayer, state, results.getBlockPos(), results.sideHit, dataTag);
                    
                    boolean requireSync = false;
                    for (InfoPlugin provider : ICSE.plugins)
                        if (provider.requireTileSync(mc.theWorld, mc.theWorld.getTileEntity(info.pos)))
                            requireSync = true;
                            
                    if (requireSync && mc.thePlayer.ticksExisted % 20 == 0)
                        ICSE.network.sendToServer(new PacketRequestInfo(results.getBlockPos()));
                        
                    if (info.isValidBlock()) {
                        
                        for (InfoPlugin provider : ICSE.plugins)
                            if (provider.requireTileOverride(info))
                                info = provider.overrideTile(info);
                                
                        if (info.isValidBlock()) {
                            
                            lineCount = 0;
                            
                            List<String> lines = new ArrayList<String>();
                            lines.add(info.stack.getDisplayName());
                            
                            for (InfoPlugin provider : ICSE.plugins)
                                provider.addTileInfo(lines, info);
                                
                            lines.add(ChatFormatting.BLUE + "" + ChatFormatting.ITALIC + Utilities.getModName(info.stack));
                            
                            for (String line : lines)
                                mc.fontRendererObj.drawStringWithShadow(line, 10, getLineOffset(), 0xFFFFFF);
                        }
                    }
                }
            }
        }
    }
    
    public int getLineOffset () {
        
        lineCount++;
        return lineCount * 10;
    }
    
    public RayTraceResult rayTrace (Entity entity, double distance, float partialTicks) {
        
        Vec3d vec3 = entity.getPositionEyes(partialTicks);
        Vec3d vec31 = entity.getLook(partialTicks);
        Vec3d vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
        return entity.worldObj.rayTraceBlocks(vec3, vec32, false);
    }
    
    // Modified from Vanilla to detect entities that are collidable
    public Entity getMouseOver (Minecraft mc, float partialTicks) {
        
        Entity pointedEntity = null;
        Entity entity = mc.getRenderViewEntity();
        
        if (entity != null && mc.theWorld != null) {
            
            mc.mcProfiler.startSection("pick");
            mc.pointedEntity = null;
            double d0 = (double) mc.playerController.getBlockReachDistance();
            mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            Vec3d vec3d = entity.getPositionEyes(partialTicks);
            boolean flag = false;
            
            if (mc.playerController.extendedReach()) {
                
                d0 = 6.0D;
                d1 = 6.0D;
            }
            
            else if (d0 > 3.0D)
                flag = true;
                
            if (mc.objectMouseOver != null)
                d1 = mc.objectMouseOver.hitVec.distanceTo(vec3d);
                
            Vec3d vec3d1 = entity.getLook(partialTicks);
            Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0);
            Vec3d vec3d3 = null;
            
            float f = 1.0F;
            
            List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0).expand((double) f, (double) f, (double) f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
                
                public boolean apply (Entity entity) {
                    
                    return entity != null;
                }
            }));
            
            double d2 = d1;
            
            for (int j = 0; j < list.size(); ++j) {
                
                Entity entity1 = (Entity) list.get(j);
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz((double) entity1.getCollisionBorderSize());
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                
                if (axisalignedbb.isVecInside(vec3d)) {
                    
                    if (d2 >= 0.0D) {
                        
                        pointedEntity = entity1;
                        vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
                        d2 = 0.0D;
                    }
                }
                
                else if (raytraceresult != null) {
                    
                    double d3 = vec3d.distanceTo(raytraceresult.hitVec);
                    
                    if (d3 < d2 || d2 == 0.0D) {
                        
                        if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity.canRiderInteract()) {
                            
                            if (d2 == 0.0D) {
                                
                                pointedEntity = entity1;
                                vec3d3 = raytraceresult.hitVec;
                            }
                        }
                        
                        else {
                            
                            pointedEntity = entity1;
                            vec3d3 = raytraceresult.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }
            
            if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > 3.0D) {
                
                pointedEntity = null;
                mc.objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing) null, new BlockPos(vec3d3));
            }
            
            if (pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null)) {
                
                mc.objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);
                
                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    mc.pointedEntity = pointedEntity;
            }
            
            mc.mcProfiler.endSection();
        }
        
        return pointedEntity;
    }
}
