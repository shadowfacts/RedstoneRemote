package net.shadowfacts.redstoneremote

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
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
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID)
		network!!.registerMessage(HandlerUpdateRemote::class.java, PacketUpdateRemote::class.java, 0, Side.SERVER)
	}

	@Mod.EventBusSubscriber
	object EventHandler {

		@JvmStatic
		@SubscribeEvent
		fun registerBlocks(event: RegistryEvent.Register<Block>) {
			event.registry.register(signalProvider)
			GameRegistry.registerTileEntity(TileEntitySignalProvider::class.java, "$MOD_ID:signal_provider")
		}

		@JvmStatic
		@SubscribeEvent
		fun registerItems(event: RegistryEvent.Register<Item>) {
			event.registry.register(remote)
		}

		@JvmStatic
		@SubscribeEvent
		fun registerModels(event: ModelRegistryEvent) {
			remote.initItemModel()
		}

	}

}