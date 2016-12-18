package net.shadowfacts.redstoneremote.network

import io.netty.buffer.ByteBuf
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import java.util.*

/**
 * @author shadowfacts
 */
class PacketUpdateRemote(var player: UUID?, var hand: EnumHand?, var tag: NBTTagCompound?): IMessage {

	constructor(player: EntityPlayer, hand: EnumHand): this(player.uniqueID, hand, player.getHeldItem(hand)!!.tagCompound)

	constructor(): this(null, null, null)

	override fun toBytes(buf: ByteBuf) {
		buf.writeLong(player!!.mostSignificantBits)
		buf.writeLong(player!!.leastSignificantBits)
		buf.writeInt(hand!!.ordinal)
		ByteBufUtils.writeTag(buf, tag)
	}

	override fun fromBytes(buf: ByteBuf) {
		player = UUID(buf.readLong(), buf.readLong())
		hand = EnumHand.values()[buf.readInt()]
		tag = ByteBufUtils.readTag(buf)
	}

}