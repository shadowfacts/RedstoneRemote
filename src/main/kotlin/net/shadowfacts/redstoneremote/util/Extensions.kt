package net.shadowfacts.redstoneremote.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * @author shadowfacts
 */
fun ItemStack.getStrength(): Int {
	if (!hasTagCompound()) {
		tagCompound = NBTTagCompound()
		tagCompound!!.setInteger("strength", 15)
		tagCompound!!.setInteger("duration", 3)
		return 15
	}
	return tagCompound!!.getInteger("strength")
}

fun ItemStack.setStrength(strength: Int) {
	if (!hasTagCompound()) {
		tagCompound = NBTTagCompound()
		tagCompound!!.setInteger("duration", 3)
	}
	tagCompound!!.setInteger("strength", strength)
}

fun ItemStack.getDuration(): Int {
	if (!hasTagCompound()) {
		tagCompound = NBTTagCompound()
		tagCompound!!.setInteger("strength", 15)
		tagCompound!!.setInteger("duration", 3)
		return 3
	}
	return tagCompound!!.getInteger("duration")
}

fun ItemStack.setDuration(duration: Int) {
	if (!hasTagCompound()) {
		tagCompound = NBTTagCompound()
		tagCompound!!.setInteger("strength", 15)
	}
	tagCompound!!.setInteger("duration", duration)
}