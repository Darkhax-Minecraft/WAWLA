package net.darkhax.wawla.lib;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class InfoAccess {

    // Mutual
    /**
     * Access to the client side world.
     */
    public World world = null;

    /**
     * Access to the synced tag compound.
     */
    public NBTTagCompound tag = null;

    /**
     * Access to the client side player.
     */
    public EntityPlayer player = null;

    // Entity
    /**
     * The entity being looked at. Might be null if no entity is looked at.
     */
    public Entity entity = null;

    // Block
    /**
     * Access to the raytrace.
     */
    public RayTraceResult result = null;

    /**
     * The block being looked at.
     */
    public Block block = null;

    /**
     * The state of the block being looked at.
     */
    public IBlockState state = null;

    /**
     * An ItemStack version of the block being looked at.
     */
    public ItemStack stack = null;

    /**
     * The side of the block being looked at.
     */
    public EnumFacing side = null;

    /**
     * The position of the block being looked at.
     */
    public BlockPos pos = null;

    /**
     * Constructs a new InfoAccess object for an entity.
     *
     * @param world The client side world.
     * @param player The client side player.
     * @param entity The entity being looked at.
     */
    public InfoAccess (World world, EntityPlayer player, Entity entity, NBTTagCompound tag) {

        this.world = world;
        this.player = player;
        this.entity = entity;
        this.tag = tag;
    }

    /**
     * Contructs a new InfoAccess object for a block.
     *
     * @param world The client side world.
     * @param player The client side player.
     * @param state The state of the block.
     * @param pos The position of the block.
     * @param side The side being looked at.
     */
    public InfoAccess (RayTraceResult result, World world, EntityPlayer player, ItemStack stack, IBlockState state, BlockPos pos, EnumFacing side, NBTTagCompound tag) {

        this.world = world;
        this.player = player;
        this.state = state;
        this.block = state.getBlock();
        this.pos = pos;
        this.side = side;
        this.stack = stack;
        this.tag = tag;
        this.result = result;
    }

    /**
     * Checks if a block has all the valid variables to be shown.
     *
     * @return boolean Whether or not the block should be shown.
     */
    public boolean isValidBlock () {

        return this.block != null && this.state != null && this.stack != null && this.stack.getItem() != null && this.side != null && this.pos != null;
    }
}
