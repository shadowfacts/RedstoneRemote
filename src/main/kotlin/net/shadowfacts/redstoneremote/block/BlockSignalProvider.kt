package net.shadowfacts.redstoneremote.block

import net.minecraft.block.BlockAir
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.shadowfacts.redstoneremote.RedstoneRemote
import java.util.*

/**
 * @author shadowfacts
 */
class BlockSignalProvider: BlockAir() {

	companion object {
		val POWER: PropertyInteger = PropertyInteger.create("power", 0, 15)

		fun create(world: World, pos: BlockPos, power: Int, duration: Int) {
			world.setBlockState(pos, RedstoneRemote.signalProvider.defaultState.withProperty(POWER, power))
			world.notifyNeighborsOfStateChange(pos, RedstoneRemote.signalProvider, false)
			(world.getTileEntity(pos) as TileEntitySignalProvider).duration = duration
		}
	}

	init {
		setRegistryName("signal_provider")
		unlocalizedName = registryName.toString()
	}

	override fun createBlockState(): BlockStateContainer {
		return BlockStateContainer(this, POWER)
	}

	override fun getMetaFromState(state: IBlockState): Int {
		return state.getValue(POWER)
	}

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState {
		return defaultState.withProperty(POWER, meta)
	}

	override fun canConnectRedstone(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing?): Boolean {
		return true
	}

	@Deprecated("")
	override fun canProvidePower(state: IBlockState): Boolean {
		return true
	}

	@Deprecated("")
	override fun getWeakPower(state: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Int {
		return state.getValue(POWER)
	}

	override fun hasTileEntity(state: IBlockState): Boolean {
		return true
	}

	override fun createTileEntity(world: World, state: IBlockState): TileEntity? {
		return TileEntitySignalProvider()
	}

	override fun randomDisplayTick(state: IBlockState, world: World, pos: BlockPos, rand: Random) {
		world.spawnParticle(EnumParticleTypes.REDSTONE, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 0.0, 0.0, 0.0)
	}

}