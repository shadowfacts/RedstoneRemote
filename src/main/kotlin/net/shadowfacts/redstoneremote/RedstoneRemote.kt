package net.shadowfacts.redstoneremote

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.shadowfacts.redstoneremote.block.BlockSignalProvider
import net.shadowfacts.redstoneremote.block.TileEntitySignalProvider
import net.shadowfacts.redstoneremote.item.ItemRemote
import net.shadowfacts.redstoneremote.network.HandlerUpdateRemote
import net.shadowfacts.redstoneremote.network.PacketUpdateRemote

/**
 * @author shadowfacts
 */
@Mod(modid = MOD_ID, name = NAME, version = VERSION, dependencies = "required-after:shadowmc;", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object RedstoneRemote {

	var network: SimpleNetworkWrapper? = null

//	Content
	val remote = ItemRemote()
	val signalProvider = BlockSignalProvider()

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		GameRegistry.register(remote)
		GameRegistry.register(signalProvider)
		GameRegistry.registerTileEntity(TileEntitySignalProvider::class.java, "$MOD_ID:signal_provider")

		network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID)
		network!!.registerMessage(HandlerUpdateRemote::class.java, PacketUpdateRemote::class.java, 0, Side.SERVER)
	}

	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	fun preInitClient(event: FMLPreInitializationEvent) {
		remote.initItemModel()
	}

}