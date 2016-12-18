package net.shadowfacts.redstoneremote.block

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import net.shadowfacts.redstoneremote.RedstoneRemote
import net.shadowfacts.shadowmc.tileentity.BaseTileEntity

/**
 * @author shadowfacts
 */
class TileEntitySignalProvider: BaseTileEntity(), ITickable {

	var duration: Int = 0

	override fun update() {
		duration--
		if (duration == 0) {
			BlockSignalProvider.create(world, pos, 0, 0)
			world.notifyNeighborsOfStateChange(pos, RedstoneRemote.signalProvider, false)
			world.setBlockToAir(pos)
		}
	}

	override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
		tag.setInteger("duration", duration)
		return super.writeToNBT(tag)
	}

	override fun readFromNBT(tag: NBTTagCompound) {
		super.readFromNBT(tag)
		duration = tag.getInteger("duration")
	}

}