package net.shadowfacts.redstoneremote.block

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.shadowfacts.redstoneremote.RedstoneRemote
import net.shadowfacts.shadowmc.ShadowMC
import net.shadowfacts.shadowmc.network.PacketRequestTEUpdate
import net.shadowfacts.shadowmc.tileentity.BaseTileEntity

/**
 * @author shadowfacts
 */
class TileEntitySignalProvider: BaseTileEntity(), ITickable {

	var duration: Int = 0
	var power: Int = 0

	override fun update() {
		duration--
		if (duration == 0) {
			BlockSignalProvider.create(world, pos, 0, 0, false, EnumFacing.NORTH)
			world.notifyNeighborsOfStateChange(pos, RedstoneRemote.signalProvider)
			world.setBlockToAir(pos)
		}
	}

	override fun onLoad() {
		if (world.isRemote) {
			ShadowMC.network.sendToServer(PacketRequestTEUpdate(this))
		}
	}

	override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
		tag.setInteger("duration", duration)
		tag.setInteger("power", power)
		return super.writeToNBT(tag)
	}

	override fun readFromNBT(tag: NBTTagCompound) {
		super.readFromNBT(tag)
		duration = tag.getInteger("duration")
		power = tag.getInteger("power")
	}

}