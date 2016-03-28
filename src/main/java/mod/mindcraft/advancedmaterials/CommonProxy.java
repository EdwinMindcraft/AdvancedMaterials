package mod.mindcraft.advancedmaterials;

import mod.mindcraft.advancedmaterials.inventory.container.ContainerAlloyForge;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerCentrifuge;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerCrystallizer;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerGrinder;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerNuclearReactor;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCentrifuge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCrystallizer;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityForge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityGrinder;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0) return new ContainerAlloyForge(player.inventory, (TileEntityForge)world.getTileEntity(new BlockPos(x, y, z)));
		if (ID == 1) return new ContainerGrinder(player.inventory, (TileEntityGrinder)world.getTileEntity(new BlockPos(x, y, z)));
		if (ID == 2) return new ContainerCentrifuge(player.inventory, (TileEntityCentrifuge)world.getTileEntity(new BlockPos(x, y, z)));
		if (ID == 3) return new ContainerCrystallizer(player.inventory, (TileEntityCrystallizer)world.getTileEntity(new BlockPos(x, y, z)));
		if (ID == 4) return new ContainerNuclearReactor(player.inventory, (TileEntityNuclearReactor)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

}
