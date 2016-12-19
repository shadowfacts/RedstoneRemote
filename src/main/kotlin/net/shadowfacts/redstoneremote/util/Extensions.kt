package net.shadowfacts.redstoneremote.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * @author shadowfacts
 */
fun ItemStack.initDefaults() {
	tagCompound = NBTTagCompound()
	tagCompound!!.setInteger("strength", 15)
	tagCompound!!.setInteger("duration", 3)
	tagCompound!!.setBoolean("specificSide", false)
}

fun ItemStack.getStrength(): Int {
	if (!hasTagCompound()) initDefaults()
	return tagCompound!!.getInteger("strength")
}

fun ItemStack.setStrength(strength: Int) {
	if (!hasTagCompound()) initDefaults()
	tagCompound!!.setInteger("strength", strength)
}

fun ItemStack.getDuration(): Int {
	if (!hasTagCompound()) initDefaults()
	return tagCompound!!.getInteger("duration")
}

fun ItemStack.setDuration(duration: Int) {
	if (!hasTagCompound()) initDefaults()
	tagCompound!!.setInteger("duration", duration)
}

fun ItemStack.useSpecificSide(): Boolean {
	if (!hasTagCompound()) initDefaults()
	return tagCompound!!.getBoolean("specificSide")
}

fun ItemStack.setSpecificSide(specificSide: Boolean) {
	if (!hasTagCompound()) initDefaults()
	tagCompound!!.setBoolean("specificSide", specificSide)
}