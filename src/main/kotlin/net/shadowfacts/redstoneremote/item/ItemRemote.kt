package net.shadowfacts.redstoneremote.item

import net.minecraft.block.BlockAir
import net.minecraft.client.Minecraft
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.shadowfacts.forgelin.extensions.rayTrace
import net.shadowfacts.redstoneremote.RedstoneRemote
import net.shadowfacts.redstoneremote.block.BlockSignalProvider
import net.shadowfacts.redstoneremote.gui.GUIRemote
import net.shadowfacts.redstoneremote.network.PacketUpdateRemote
import net.shadowfacts.redstoneremote.util.getDuration
import net.shadowfacts.redstoneremote.util.getStrength
import net.shadowfacts.redstoneremote.util.initDefaults
import net.shadowfacts.redstoneremote.util.useSpecificSide
import net.shadowfacts.shadowmc.item.ItemBase

/**
 * @author shadowfacts
 */
class ItemRemote: ItemBase("remote") {

	init {
		creativeTab = CreativeTabs.REDSTONE
	}

	override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack?, slotChanged: Boolean): Boolean {
		return !oldStack.isItemEqual(newStack) || slotChanged
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		val result = player.rayTrace(16.0)
		if (result != null) {
			if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
				val pos = result.blockPos.offset(result.sideHit)
				val state = world.getBlockState(pos)
				if (state.block is BlockAir) {
					val stack = player.getHeldItem(hand)
					BlockSignalProvider.create(world, pos, stack.getStrength(), stack.getDuration() * 20, stack.useSpecificSide(), result.sideHit)
				}
			} else {
				if (world.isRemote) {
					openGUI(player, hand)
				}
			}
			player.swingArm(hand)
			return ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand))
		}
		return ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand))
	}

	@SideOnly(Side.CLIENT)
	private fun openGUI(player: EntityPlayer, hand: EnumHand) {
		val synchronizer = {
			RedstoneRemote.network!!.sendToServer(PacketUpdateRemote(player, hand))
		}
		Minecraft.getMinecraft().displayGuiScreen(GUIRemote.create(player.getHeldItem(hand), synchronizer))
	}

	override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
		if (tab == CreativeTabs.REDSTONE) {
			val stack = ItemStack(this)
			stack.initDefaults()
			items.add(stack)
		}
	}

}