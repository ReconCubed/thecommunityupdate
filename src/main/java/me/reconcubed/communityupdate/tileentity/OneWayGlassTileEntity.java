package me.reconcubed.communityupdate.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OneWayGlassTileEntity extends TileEntity {
    private String copyBlock;


    public OneWayGlassTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public String getCopyBlock() {
        return copyBlock;
    }

    public void setCopyBlock(String copyBlock) {
        this.copyBlock = copyBlock;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
        compound.putString("block", );
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("block")) {
            Block block = Block
            copyBlock = compound.getString("block");
        }
    }
}
