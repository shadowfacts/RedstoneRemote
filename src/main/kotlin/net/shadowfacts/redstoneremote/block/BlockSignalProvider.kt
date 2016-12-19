package net.shadowfacts.redstoneremote.block

import net.minecraft.block.BlockAir
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyDirection
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
		val SPECIFIC_SIDE: PropertyBool = PropertyBool.create("specific_side")
		val SIDE: PropertyDirection = PropertyDirection.create("side")

		fun create(world: World, pos: BlockPos, power: Int, duration: Int, specificSide: Boolean, side: EnumFacing) {
			world.setBlockState(pos, RedstoneRemote.signalProvider.defaultState.withProperty(SPECIFIC_SIDE, specificSide).withProperty(SIDE, side))
			val tile = (world.getTileEntity(pos) as TileEntitySignalProvider)
			tile.duration = duration
			tile.power = power
			world.notifyNeighborsOfStateChange(pos, RedstoneRemote.signalProvider)
		}
	}

	init {
		setRegistryName("signal_provider")
		unlocalizedName = registryName.toString()
	}

	override fun createBlockState(): BlockStateContainer {
		return BlockStateContainer(this, SPECIFIC_SIDE, SIDE)
	}

	override fun getMetaFromState(state: IBlockState): Int {
		var meta = state.getValue(SIDE).ordinal
		if (state.getValue(SPECIFIC_SIDE)) {
			meta = meta.or(0b1000)
		}
		return meta
	}

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState {
		val specificSide = meta.shr(3)
		val side = meta.and(0b0111)
		return defaultState.withProperty(SPECIFIC_SIDE, specificSide == 1).withProperty(SIDE, EnumFacing.VALUES[side])
	}

	override fun canConnectRedstone(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing?): Boolean {
		return true
	}

	@Deprecated("")
	override fun canProvidePower(state: IBlockState): Boolean {
		return true
	}

	@Deprecated("")
	override fun getWeakPower(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing): Int {
		if (!state.getValue(SPECIFIC_SIDE) || (state.getValue(SPECIFIC_SIDE) && state.getValue(SIDE) == side)) {
			return (world.getTileEntity(pos) as TileEntitySignalProvider).power
		}
		return 0
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