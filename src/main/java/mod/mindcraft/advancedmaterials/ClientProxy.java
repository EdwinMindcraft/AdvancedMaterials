package mod.mindcraft.advancedmaterials;

import mod.mindcraft.advancedmaterials.inventory.gui.GuiAlloyForge;
import mod.mindcraft.advancedmaterials.inventory.gui.GuiCentrifuge;
import mod.mindcraft.advancedmaterials.inventory.gui.GuiCrystallizer;
import mod.mindcraft.advancedmaterials.inventory.gui.GuiGrinder;
import mod.mindcraft.advancedmaterials.inventory.gui.GuiNuclearReactor;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCentrifuge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCrystallizer;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityForge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityGrinder;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0) return new GuiAlloyForge(player.inventory, (TileEntityForge)world.getTileEntity(new BlockPos(x, y, z)));
		if (ID == 1) return new GuiGrinder(player.inventory, (TileEntityGrinder)world.getTileEntity(new BlockPos(x, y, z)));		
		if (ID == 2) return new GuiCentrifuge(player.inventory, (TileEntityCentrifuge)world.getTileEntity(new BlockPos(x, y, z)));		
		if (ID == 3) return new GuiCrystallizer(player.inventory, (TileEntityCrystallizer)world.getTileEntity(new BlockPos(x, y, z)));
		if (ID == 4) return new GuiNuclearReactor(player.inventory, (TileEntityNuclearReactor)world.getTileEntity(new BlockPos(x, y, z)));
		return super.getClientGuiElement(ID, player, world, x, y, z);
	}
	
}
