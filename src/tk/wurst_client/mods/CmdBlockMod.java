/*
 * Copyright � 2014 - 2016 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import tk.wurst_client.gui.mods.GuiCmdBlock;
import tk.wurst_client.mods.Mod.Bypasses;

@Mod.Info(category = Mod.Category.EXPLOITS,
	description = "Allows you to make a Command Block without having OP.\n"
		+ "Appears to be patched on Spigot.",
	name = "CMD-Block",
	tags = "CmdBlock, CommandBlock, cmd block, command block",
	help = "Mods/CMD-Block")
@Bypasses
public class CmdBlockMod extends Mod
{
	@Override
	public void onEnable()
	{
		if(mc.thePlayer.inventory.getStackInSlot(0) != null)
		{
			wurst.chat.error("Please clear the first slot in your hotbar.");
			setEnabled(false);
			return;
		}else if(!mc.thePlayer.capabilities.isCreativeMode)
		{
			wurst.chat.error("Creative mode only.");
			setEnabled(false);
			return;
		}
		mc.displayGuiScreen(new GuiCmdBlock(this, mc.currentScreen));
		setEnabled(false);
	}
	
	public void createCmdBlock(String cmd)
	{
		ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setTag("Command", new NBTTagString(cmd));
		stack.writeToNBT(nbtTagCompound);
		stack.setTagInfo("BlockEntityTag", nbtTagCompound);
		mc.thePlayer.connection
			.sendPacket(new CPacketCreativeInventoryAction(36, stack));
		wurst.chat.message("Command Block created.");
	}
}
