package mod.mindcraft.advancedmaterials.integration;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import com.google.common.collect.Lists;

public abstract class TileEntityMachine<T extends IMachineRecipe, V extends IRecipeRegistry<T>> extends TileEntity implements IMachine<T, V>, ISidedInventory, ITickable {
	
	protected ItemStack[] stack;
	protected int progress = 0;
	
	public TileEntityMachine(int size) {
		stack = new ItemStack[size];
	}

	@Override
	public int getSizeInventory() {
		return stack.length;
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
		return index == 0;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < stack.length; i++)
			stack[i] = null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		for (int i : getInputSlots())
			if (i == index)
				return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		for (int i : getOutputSlots())
			if (i == index)
				return true;
		return false;
	}
	
	protected boolean slotMatch(ItemStack stack, ItemStack test) {
		return test == null || stack == null || (ItemStack.areItemsEqual(test, stack) && stack.stackSize + test.stackSize <= 64);
	}
	
	public int getProgress() {
		return progress;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update() {
		if (worldObj.isRemote)
			return;
		IInventory subInv = new InventoryBasic("Craft", false, getInputSlots().length);
		for (int i = 0; i < getInputSlots().length; i++) {
			subInv.setInventorySlotContents(i, getStackInSlot(getInputSlots()[i]));
		}
		T recipe = getRegistry().getRecipe(subInv);
		if (recipe == null) {
			worldObj.markBlockForUpdate(pos);
			progress = 0;
			return;
		}
		
		for (int i = 0; i < Math.min(recipe.getInputs().size(), getInputSlots().length); i++) {
			Object obj = recipe.getInputs().get(i);
			if (obj instanceof ItemStack) {
				if (((ItemStack)obj).stackSize > getStackInSlot(getInputSlots()[i]).stackSize) {
					worldObj.markBlockForUpdate(pos);
					progress = 0;
					return;
				}
			} else if (obj instanceof ArrayList) {
				if (((ArrayList<ItemStack>)obj).get(0).stackSize > getStackInSlot(getInputSlots()[i]).stackSize) {
					worldObj.markBlockForUpdate(pos);
					progress = 0;
					return;
				}
			}
		}
		
		boolean matches = true;
		
		for (int i = 0; i < Math.min(getOutputSlots().length, recipe.getOutputs().size()); i++) {
			Object obj = recipe.getOutputs().get(i);
			if (obj instanceof ItemStack) {
				ItemStack sub = (ItemStack) obj;
				sub.stackSize = (int) Math.ceil((float)sub.stackSize / 100F);
				if (!slotMatch(getStackInSlot(getOutputSlots()[i]), sub))
					matches = false;
			} else if (obj instanceof ArrayList) {
				boolean subMatch = false;
				for (ItemStack is : (ArrayList<ItemStack>)obj) {
					ItemStack sub = is.copy();
					sub.stackSize = (int) Math.ceil((float)sub.stackSize / 100F);
					if (slotMatch(sub, getStackInSlot(getOutputSlots()[i])))
						subMatch = true;
				}
				if (!subMatch)
					matches = false;
			}
		}
		//System.out.println(matches);
		if (!matches) {
			worldObj.markBlockForUpdate(pos);
			progress = 0;
			return;
		}
		if (progress >= getMaxProgress()) {
			if (!worldObj.isRemote) {
				for (int i = 0; i < Math.min(getOutputSlots().length, recipe.getOutputs().size()); i++) {
					Random rand = new Random();
					ItemStack is = getStackInSlot(getOutputSlots()[i]);
					Object obj = recipe.getOutputs().get(i);
					if (is == null) {
						if (obj instanceof ItemStack) {
							ItemStack stack = ((ItemStack) obj).copy();
							int effectiveSize = 0;
							for (float j = (float)stack.stackSize / 100F; j > 0 ; j--) {
								if (rand.nextFloat() <= j)
									effectiveSize++;
							}
							stack.stackSize = effectiveSize;
							if (effectiveSize > 0)
								setInventorySlotContents(getOutputSlots()[i], stack);
						} else if (obj instanceof ArrayList && ((ArrayList<ItemStack>)obj).size() > 0) {
							ItemStack stack = (((ArrayList<ItemStack>)obj).get(0)).copy();
							int effectiveSize = 0;
							for (float j = (float)stack.stackSize / 100F; j > 0 ; j--) {
								if (rand.nextFloat() <= j)
									effectiveSize++;
							}
							stack.stackSize = effectiveSize;
							if (effectiveSize > 0)
								setInventorySlotContents(getOutputSlots()[i], stack);
						}
					} else {
						if (obj instanceof ItemStack) {
							ItemStack newIs = is.copy();
							int effectiveSize = 0;
							for (float j = (float)((ItemStack)obj).stackSize / 100F; j > 0 ; j--) {
								if (rand.nextFloat() <= j)
									effectiveSize++;
							}
							newIs.stackSize += effectiveSize;
							setInventorySlotContents(getOutputSlots()[i], newIs);
						} else if (obj instanceof ArrayList && ((ArrayList<ItemStack>)obj).size() > 0) {
							ItemStack newIs = is.copy();
							int effectiveSize = 0;
							for (float j = (float)((ArrayList<ItemStack>)obj).get(0).stackSize / 100F; j > 0 ; j--) {
								if (rand.nextFloat() <= j)
									effectiveSize++;
							}
							newIs.stackSize += effectiveSize;
							setInventorySlotContents(getOutputSlots()[i], newIs);
						}
					}
				}
				for (int i = 0; i < Math.min(getInputSlots().length, recipe.getInputs().size()); i++) {
					Object obj = recipe.getInputs().get(i);
					if (obj instanceof ItemStack) {
						decrStackSize(getInputSlots()[i], ((ItemStack)obj).stackSize);
					} else if (obj instanceof ArrayList && ((ArrayList<ItemStack>)obj).size() > 0) {
						decrStackSize(getInputSlots()[i], ((ArrayList<ItemStack>)obj).get(0).stackSize);
					}
				}
			}
			progress = 0;
			recipe = null;
		} else {
			progress++;
		}
		worldObj.markBlockForUpdate(pos);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
		worldObj.markBlockForUpdate(pkt.getPos());
		this.markDirty();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList list = new NBTTagList();
		compound.setInteger("Progress", progress);
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] == null)
				continue;
			NBTTagCompound tmp = new NBTTagCompound();
			stack[i].writeToNBT(tmp);
			tmp.setShort("Slot", (short)i);
			list.appendTag(tmp);
		}
		compound.setTag("Inventory", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		progress = compound.getInteger("Progress");
		NBTTagList list = compound.getTagList("Inventory", 10);
		if (list == null)
			return;
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt = list.getCompoundTagAt(i);
			stack[nbt.getShort("Slot")] = ItemStack.loadItemStackFromNBT(nbt);
		}
	}
	


}
