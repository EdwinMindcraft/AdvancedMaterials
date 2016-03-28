package mod.mindcraft.advancedmaterials.tileentity;

import mod.mindcraft.advancedmaterials.integration.component.ItemNuclearReactorComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import cofh.api.energy.IEnergyProvider;

public class TileEntityNuclearReactor extends TileEntity implements ISidedInventory, ITickable, IEnergyProvider {
	
	public ItemStack[] stack = new ItemStack[54];
	public int[] stackHeat = new int[54];
	public int hullHeat = 200;
	public int energy = 0;
	
	public TileEntityNuclearReactor() {
		
	}

	@Override
	public int getSizeInventory() {
		return 54;
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
		return stack.getItem() instanceof ItemNuclearReactorComponent;
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
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return 1000000;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		int extract = Math.min(energy, maxExtract);
		if (!simulate)
			energy -= extract;
		return extract;
	}

	@Override
	public void update() {
		ItemStack[][] map = new ItemStack[6][9];
		if (worldObj.getBiomeGenForCoords(pos).temperature * 250 > hullHeat)
			hullHeat += Math.min(worldObj.getBiomeGenForCoords(pos).temperature * 10 * ((hullHeat - worldObj.getBiomeGenForCoords(pos).temperature * 250) / 10000), 5);
		else if (worldObj.getBiomeGenForCoords(pos).temperature * 250 < hullHeat)
			hullHeat -= Math.min(((2F - worldObj.getBiomeGenForCoords(pos).temperature) * 10) * ((hullHeat - worldObj.getBiomeGenForCoords(pos).temperature * 250) / 10000), 5);
		int[][] heatMap = new int[6][9];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				map[i][j] = this.stack[i * 9 + j];
				heatMap[i][j] = this.stackHeat[i * 9 + j];
			}
		}
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					int heat = calcHeat(item, map, i, j);
					int rest = transferHeat(item, map, heatMap, i, j, heat);
					hullHeat += rest;
				}
			}
		}
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					if (item.component.cool != 0)
						dissipateHeat(item, map, heatMap, i, j);
					if (item.component.duration != -1) {
						map[i][j].setItemDamage(map[i][j].getItemDamage() + 1);
					}
				}
			}
		}
		
		if (hullHeat < 0)
			hullHeat = 0;
		worldObj.markBlockForUpdate(pos);
	}
	
	private int transferHeat(ItemNuclearReactorComponent item, ItemStack[][] map, int[][] heatMap, int x, int y, int heat) {
		int num = 0;
		int newHeat = heat;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == j || i == -j)
					continue;
				//System.out.println((x+i) + " " + (y+j));
				int posX = x+i;
				if (posX < 0 || posX > 5 || y+j < 0 || y+j > 8 || map[posX][y+j] == null || !(map[posX][y+j].getItem() instanceof ItemNuclearReactorComponent) || ((ItemNuclearReactorComponent)map[posX][y+j].getItem()).component.absorption == 0)
					continue;
				num++;
			}
		}
		if (num == 0)
			return heat;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == j || i == -j)
					continue;
				if (x+i < 0 || x+i > 5 || y+j < 0 || y+j > 8 || map[x+i][y+j] == null || !(map[x+i][y+j].getItem() instanceof ItemNuclearReactorComponent) || ((ItemNuclearReactorComponent)map[x+i][y+j].getItem()).component.absorption == 0)
					continue;
				int toTransfer = (int) Math.ceil((float)heat / (float)num);
				if (newHeat - toTransfer < 0)
					toTransfer = newHeat;
				ItemNuclearReactorComponent component = (ItemNuclearReactorComponent) map[x+i][y+j].getItem();
				toTransfer = Math.min(toTransfer, Math.min(component.component.absorption, item.component.distrib == -1 ? Integer.MAX_VALUE : item.component.distrib));
				newHeat -= toTransfer;
				heatMap[x+i][y+j] += toTransfer;
				heatMap[x][y] -= toTransfer;
			}
		}
		
		return newHeat;
	}
	
	private void dissipateHeat(ItemNuclearReactorComponent item, ItemStack[][] map, int[][] heatMap, int x, int y) {
		int coolGain = item.component.cool;
		float coolMul = 1;
		if (x > 0 && map[x - 1][y] != null && map[x - 1][y].getItem() instanceof ItemNuclearReactorComponent)
			coolMul *= ((ItemNuclearReactorComponent)map[x - 1][y].getItem()).component.coolMul;
		if (y > 0 && map[x][y - 1] != null && map[x][y - 1].getItem() instanceof ItemNuclearReactorComponent)
			coolMul *= ((ItemNuclearReactorComponent)map[x][y - 1].getItem()).component.coolMul;
		if (x < 5 && map[x + 1][y] != null && map[x + 1][y].getItem() instanceof ItemNuclearReactorComponent)
			coolMul *= ((ItemNuclearReactorComponent)map[x + 1][y].getItem()).component.coolMul;
		if (y < 8 && map[x][y + 1] != null && map[x][y + 1].getItem() instanceof ItemNuclearReactorComponent)
			coolMul *= ((ItemNuclearReactorComponent)map[x][y + 1].getItem()).component.coolMul;
		coolGain *= Math.max(1F, coolMul);
		//System.out.println(heatMap[x][y]);
		heatMap[x][y] -= coolGain;
		map[x][y].setItemDamage(map[x][y].getItemDamage() + heatMap[x][y]);
		if (heatMap[x][y] < 0) {
			hullHeat += heatMap[x][y];
			heatMap[x][y] = 0;
		}
	}
	
	private int calcHeat(ItemNuclearReactorComponent item, ItemStack[][] map, int x, int y) {
		int heatGain = item.component.heat;
		float heatMul = 1;
		if (x > 0 && map[x - 1][y] != null && map[x - 1][y].getItem() instanceof ItemNuclearReactorComponent)
			heatMul *= ((ItemNuclearReactorComponent)map[x - 1][y].getItem()).component.heatMul;
		if (y > 0 && map[x][y - 1] != null && map[x][y - 1].getItem() instanceof ItemNuclearReactorComponent)
			heatMul *= ((ItemNuclearReactorComponent)map[x][y - 1].getItem()).component.heatMul;
		if (x < 5 && map[x + 1][y] != null && map[x + 1][y].getItem() instanceof ItemNuclearReactorComponent)
			heatMul *= ((ItemNuclearReactorComponent)map[x + 1][y].getItem()).component.heatMul;
		if (y < 8 && map[x][y + 1] != null && map[x][y + 1].getItem() instanceof ItemNuclearReactorComponent)
			heatMul *= ((ItemNuclearReactorComponent)map[x][y + 1].getItem()).component.heatMul;
		heatGain *= Math.max(1F, heatMul);					
		return heatGain;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		worldObj.markBlockForUpdate(pos);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
}