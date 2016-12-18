package net.shadowfacts.redstoneremote.network

import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

/**
 * @author shadowfacts
 */
class HandlerUpdateRemote: IMessageHandler<PacketUpdateRemote, IMessage> {

	override fun onMessage(message: PacketUpdateRemote, ctx: MessageContext): IMessage? {
		val player = FMLCommonHandler.instance().minecraftServerInstance.playerList.getPlayerByUUID(message.player)
		val stack = player.getHeldItem(message.hand)
		stack!!.tagCompound = message.tag
		return null
	}

}