package mod.mindcraft.advancedmaterials.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mod.mindcraft.advancedmaterials.integration.component.ItemNuclearReactorComponent;
import mod.mindcraft.advancedmaterials.integration.component.ItemNuclearReactorFluidComponent;
import mod.mindcraft.advancedmaterials.integration.recipes.NuclearFusionRecipe;
import mod.mindcraft.advancedmaterials.integration.registry.NuclearFusionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import cofh.api.energy.IEnergyProvider;

public class TileEntityNuclearReactor extends TileEntity implements ISidedInventory, ITickable, IEnergyProvider, IFluidHandler, IFluidTank {
	
	public ItemStack[] stack = new ItemStack[54];
	public int[] stackHeat = new int[54];
	public int hullHeat = 200;
	public int prevTemp = 200;
	public int energy = 0;
	public int prevEnergy = 0;
	public FluidStack storedFluid;
	
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
		return 10000000;
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
		if (worldObj.isRemote)
			return;
		ItemStack[][] map = new ItemStack[6][9];
		this.prevTemp = this.hullHeat;
		this.prevEnergy = this.energy;
		if (worldObj.getBiomeGenForCoords(pos).temperature * (worldObj.isRaining() ? 200 : 250) > hullHeat)
			hullHeat += Math.min(worldObj.getBiomeGenForCoords(pos).temperature * 10 * ((hullHeat - worldObj.getBiomeGenForCoords(pos).temperature * (worldObj.isRaining() ? 200 : 250)) / 10000), 5);
		else if (worldObj.getBiomeGenForCoords(pos).temperature * (worldObj.isRaining() ? 200 : 250) < hullHeat)
			hullHeat -= Math.min(((2F - worldObj.getBiomeGenForCoords(pos).temperature) * 10) * ((hullHeat - worldObj.getBiomeGenForCoords(pos).temperature * (worldObj.isRaining() ? 200 : 250)) / 10000), 5);
		int[][] heatMap = new int[6][9];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				map[i][j] = this.stack[i * 9 + j];
				heatMap[i][j] = this.stackHeat[i * 9 + j];
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					if (item.component.maxAbsorbedHeat != -1) {
						heatMap[i][j] = map[i][j].getItemDamage();
						stackHeat[i*9+j] = map[i][j].getItemDamage();
					}
				}
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					if ((item.component.maxTemperature == -1 ? Integer.MAX_VALUE : item.component.maxTemperature) < hullHeat || (item.component.minTemperature == -1 ? Integer.MIN_VALUE : item.component.minTemperature) > hullHeat)
						continue;
					heatMap[i][j] += calcHeat(item, map, i, j);
					heatMap[i][j] = transferHeat(item, map, heatMap, i, j);
					//System.out.println(heatMap[i][j]);
					//System.out.println(heatMap[i][j] + " " + this.stackHeat[i * 9 + j]);
					energy += calcPower(item, map, i, j);
					if (energy > getMaxEnergyStored(EnumFacing.UP))
						energy = getMaxEnergyStored(EnumFacing.UP);
				}
			}
		}
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					if ((item.component.maxTemperature == -1 ? Integer.MAX_VALUE : item.component.maxTemperature) < hullHeat || (item.component.minTemperature == -1 ? Integer.MIN_VALUE : item.component.minTemperature) > hullHeat)
						continue;
					dissipateHeat(item, map, heatMap, i, j);
					hullHeat += Math.max(heatMap[i][j] - this.stackHeat[i * 9 + j], 0);
				}
			}
		}
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					if ((item.component.maxTemperature == -1 ? Integer.MAX_VALUE : item.component.maxTemperature) < hullHeat || (item.component.minTemperature == -1 ? Integer.MIN_VALUE : item.component.minTemperature) > hullHeat)
						continue;
					if (item.component.duration != -1) {
						map[i][j].setItemDamage(map[i][j].getItemDamage() + 1);
						if (map[i][j].getItemDamage() >= item.component.duration)
							map[i][j] = null;
					}
					if (item.component.maxAbsorbedHeat != -1) {
						map[i][j].setItemDamage(heatMap[i][j]);
						if (map[i][j].getItemDamage() >= item.component.maxAbsorbedHeat)
							map[i][j] = null;
					}
				}
			}
		}
				
		if (hullHeat > 100000)
			worldObj.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 40F, true);
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				this.stack[i * 9 + j] = map[i][j];
				this.stackHeat[i * 9 + j] = heatMap[i][j];
			}
		}
		
		if (hullHeat < 0)
			hullHeat = 0;

		
		ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
		HashMap<Integer, FluidStack> fluidMap = new HashMap<Integer, FluidStack>();
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] != null && map[i][j].getItem() instanceof ItemNuclearReactorFluidComponent) {
					ItemNuclearReactorComponent item = (ItemNuclearReactorComponent) map[i][j].getItem();
					//System.out.println((item.component.maxTemperature == -1 ? Integer.MIN_VALUE : item.component.maxTemperature) < hullHeat);
					if ((item.component.maxTemperature == -1 ? Integer.MAX_VALUE : item.component.maxTemperature) < hullHeat || (item.component.minTemperature == -1 ? Integer.MIN_VALUE : item.component.minTemperature) > hullHeat)
						continue;
					//System.out.println("Working");
					FluidStack stack = ((ItemNuclearReactorFluidComponent)map[i][j].getItem()).getFluid(map[i][j]);
					boolean inputed = false;
					for (int k = 0; k < fluids.size(); k++) {
						if (fluids.get(k).getFluid().equals(stack.getFluid())) {
							inputed = true;
							fluids.set(k, new FluidStack(fluids.get(k).getFluid(), fluids.get(k).amount + stack.amount));
						}
					}
					if (!inputed)
						fluids.add(stack);
					//System.out.println(stack.getFluid().getName());
					fluidMap.put(i * 9 + j, stack);
				}
			}
		}
		
		FluidStack[] array = new FluidStack[fluids.size()];
		for (int i = 0; i < fluids.size(); i++) {
			array[i] = fluids.get(i);
		}
		
		NuclearFusionRecipe recipe = NuclearFusionRegistry.getRecipe(array);
		//System.out.println(recipe == null);
		if (recipe != null && (storedFluid == null || (recipe.getOutput().getFluid().equals(storedFluid.getFluid()) && recipe.getOutput().amount + storedFluid.amount <= getCapacity()))) {
			for (FluidStack stack : recipe.getInputs()) {
				for(Entry<Integer, FluidStack> entry : fluidMap.entrySet()) {
					if (entry.getValue().containsFluid(stack)) {
						this.stack[entry.getKey()].setItemDamage(this.stack[entry.getKey()].getItemDamage() + stack.amount);
						if (((ItemNuclearReactorFluidComponent)this.stack[entry.getKey()].getItem()).getFluid(this.stack[entry.getKey()]).amount <= 0)
							this.stack[entry.getKey()] = null;
						break;
					}
				}
			}
			if (storedFluid == null)
				storedFluid = recipe.getOutput().copy();
			else 
				storedFluid.amount += recipe.getOutput().amount;
		}
		worldObj.markBlockForUpdate(pos);
	}
	
	private int transferHeat(ItemNuclearReactorComponent item, ItemStack[][] map, int[][] heatMap, int x, int y) {
		int num = 0;
		int heat = heatMap[x][y] * 1;
		int newHeat = heatMap[x][y] * 1;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == j || i == -j)
					continue;
				int posX = x+i;
				if (posX < 0 || posX > 5 || y+j < 0 || y+j > 8 || map[posX][y+j] == null || !(map[posX][y+j].getItem() instanceof ItemNuclearReactorComponent) || ((ItemNuclearReactorComponent)map[posX][y+j].getItem()).component.absorption == 0)
					continue;
				num++;
			}
		}
		if (num == 0)
			return newHeat;
		
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
				toTransfer = Math.min(toTransfer, Math.min(component.component.absorption == -1 ? Integer.MAX_VALUE : component.component.absorption, item.component.distrib == -1 ? Integer.MAX_VALUE : item.component.distrib));
//				System.out.println(newHeat);
				newHeat -= toTransfer;
//				System.out.println(newHeat);
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
		coolGain *= coolMul;
		heatMap[x][y] -= coolGain;
		if (heatMap[x][y] < 0) {
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
		heatGain *= heatMul;					
		if (item.component.fromHull) {
			heatGain += Math.min(hullHeat, item.component.absorption);
			hullHeat -= heatGain;
		}
		return heatGain;
	}
	
	private int calcPower(ItemNuclearReactorComponent item, ItemStack[][] map, int x, int y) {
		int powerGain = item.component.power;
		float powerMul = 1;
		if (x > 0 && map[x - 1][y] != null && map[x - 1][y].getItem() instanceof ItemNuclearReactorComponent)
			powerMul *= ((ItemNuclearReactorComponent)map[x - 1][y].getItem()).component.powerMul;
		if (y > 0 && map[x][y - 1] != null && map[x][y - 1].getItem() instanceof ItemNuclearReactorComponent)
			powerMul *= ((ItemNuclearReactorComponent)map[x][y - 1].getItem()).component.powerMul;
		if (x < 5 && map[x + 1][y] != null && map[x + 1][y].getItem() instanceof ItemNuclearReactorComponent)
			powerMul *= ((ItemNuclearReactorComponent)map[x + 1][y].getItem()).component.powerMul;
		if (y < 8 && map[x][y + 1] != null && map[x][y + 1].getItem() instanceof ItemNuclearReactorComponent)
			powerMul *= ((ItemNuclearReactorComponent)map[x][y + 1].getItem()).component.powerMul;
		powerGain *= powerMul;				
		return powerGain;
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
		NBTTagList list = new NBTTagList();
		compound.setInteger("HullHeat", hullHeat);
		compound.setInteger("PrevHullHeat", prevTemp);
		compound.setInteger("Energy", energy);
		compound.setInteger("PrevEnergy", prevEnergy);
		NBTTagCompound nbt = new NBTTagCompound();
		if (storedFluid != null)
			storedFluid.writeToNBT(nbt);
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] == null)
				continue;
			NBTTagCompound tmp = new NBTTagCompound();
			stack[i].writeToNBT(tmp);
			tmp.setShort("Slot", (short)i);
			tmp.setInteger("Heat", stackHeat[i]);
			list.appendTag(tmp);
		}
		compound.setTag("Fluid", nbt);
		compound.setTag("Inventory", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		hullHeat = compound.getInteger("HullHeat");
		prevTemp = compound.getInteger("PrevHullHeat");
		energy = compound.getInteger("Energy");
		prevEnergy = compound.getInteger("PrevEnergy");
		storedFluid = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid"));
		NBTTagList list = compound.getTagList("Inventory", 10);
		if (list == null)
			return;
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt = list.getCompoundTagAt(i);
			stack[nbt.getShort("Slot")] = ItemStack.loadItemStackFromNBT(nbt);
			stackHeat[nbt.getShort("Slot")] = nbt.getInteger("Heat");
		}
	}
	
	//TODO Fluid
	
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] {getInfo()};
	}

	@Override
	public FluidStack getFluid() {
		return null;
	}

	@Override
	public int getFluidAmount() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return 16000;
	}

	@Override
	public FluidTankInfo getInfo() {
		return new FluidTankInfo(this);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}
}