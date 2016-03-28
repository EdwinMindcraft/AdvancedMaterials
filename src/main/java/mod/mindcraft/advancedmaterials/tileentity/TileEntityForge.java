package mod.mindcraft.advancedmaterials.tileentity;

import mod.mindcraft.advancedmaterials.inventory.AlloyForgeRecipe;
import mod.mindcraft.advancedmaterials.inventory.AlloyForgeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;

public class TileEntityForge extends TileEntity implements ITickable,
		ISidedInventory {
	
	public ItemStack[] stack = new ItemStack[9];
	private int progress = 0;
	
	public TileEntityForge() {
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stack[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (stack[index] == null)
			return null;
		ItemStack is = stack[index].copy();
		if (stack[index].stackSize <= count)
			stack[index] = null;
		else
			stack[index].stackSize -= count;
		
		if (stack[index] != null)
			is.stackSize -= stack[index].stackSize;
		return is;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (stack[index] == null)
			return null;
		ItemStack is = stack[index].copy();
		stack[index] = null;
		return is;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stack[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index != 8;
	}

	@Override
	public int getField(int id) {
		if (id == 0)
			return progress;
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == 0)
			progress = value;
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
	}

	@Override
	public String getName() {
		return "AlloyForge";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText("Alloy Forge");
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index != 8;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 8;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(pos, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.getNbtCompound());
		worldObj.markBlockForUpdate(pos);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] == null)
				continue;
			NBTTagCompound tmp = new NBTTagCompound();
			stack[i].writeToNBT(tmp);
			tmp.setInteger("Slot", i);
			list.appendTag(tmp);
		}
		compound.setInteger("Progress", progress);
		compound.setTag("Inventory", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList list = compound.getTagList("Inventory", 10);
		if (list == null)
			return;
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt = list.getCompoundTagAt(i);
			stack[nbt.getInteger("Slot")] = ItemStack.loadItemStackFromNBT(nbt);
		}
		progress = compound.getInteger("Progress");
	}
	

	@Override
	public void update() {

		InventoryBasic subInv = new InventoryBasic("AlloyForgeCraft", false, 8);
		subInv.setInventorySlotContents(0, stack[0]);
		subInv.setInventorySlotContents(1, stack[1]);
		subInv.setInventorySlotContents(2, stack[2]);
		subInv.setInventorySlotContents(3, stack[3]);
		subInv.setInventorySlotContents(4, stack[4]);
		subInv.setInventorySlotContents(5, stack[5]);
		subInv.setInventorySlotContents(6, stack[6]);
		subInv.setInventorySlotContents(7, stack[7]);
		//System.out.println(progress);
		AlloyForgeRecipe recipe = AlloyForgeRegistry.getMatchingRecipe(subInv);
		if (recipe == null || !(stack[8] == null || (ItemStack.areItemsEqual(recipe.getOutput(), getStackInSlot(8)) && getStackInSlot(8).stackSize + recipe.getOutput().stackSize <= 64))) {
			progress = 0;
			return;
		} else {
			if (progress >= 200) {
				if (!worldObj.isRemote) {
					decrStackSize(0, 1);
					decrStackSize(1, 1);
					decrStackSize(2, 1);
					decrStackSize(3, 1);
					decrStackSize(4, 1);
					decrStackSize(5, 1);
					decrStackSize(6, 1);
					decrStackSize(7, 1);
					if (stack[8] == null)
						setInventorySlotContents(8, recipe.getOutput());
					else {
						stack[8].stackSize += recipe.getOutput().stackSize;
					}
				}
				progress = 0;
			} else {
				progress++;
			}
		}
	}

}
