package net.shadowfacts.redstoneremote.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * @author shadowfacts
 */
fun ItemStack.getStrength(): Int {
	if (!hasTagCompound()) return 15
	return tagCompound!!.getInteger("strength")
}

fun ItemStack.setStrength(strength: Int) {
	if (!hasTagCompound()) tagCompound = NBTTagCompound()
	tagCompound!!.setInteger("strength", strength)
}

fun ItemStack.getDuration(): Int {
	if (!hasTagCompound()) return 3
	return tagCompound!!.getInteger("duration")
}

fun ItemStack.setDuration(duration: Int) {
	if (!hasTagCompound()) tagCompound = NBTTagCompound()
	tagCompound!!.setInteger("duration", duration)
}