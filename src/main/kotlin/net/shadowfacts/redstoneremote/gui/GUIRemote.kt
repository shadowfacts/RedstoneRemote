package net.shadowfacts.redstoneremote.gui

import net.minecraft.client.gui.GuiScreen
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.shadowfacts.redstoneremote.MOD_ID
import net.shadowfacts.redstoneremote.util.*
import net.shadowfacts.shadowmc.ui.dsl.*
import net.shadowfacts.shadowmc.ui.element.UILabel
import net.shadowfacts.shadowmc.util.KeyboardHelper

/**
 * @author shadowfacts
 */
object GUIRemote {

	private val BG = ResourceLocation(MOD_ID, "textures/gui/remote.png")

	fun create(remote: ItemStack, synchronize: () -> Unit): GuiScreen {
		return screen {
			fixed {
				id = "root"
				width = 146
				height = 180

				image {
					id = "bg"
					width = 146
					height = 180
					texture = BG
				}

				fixed {
					id = "top"
					width = 146
					height = 60

					stack {
						id = "topStack"
						addClass("verticalStack")

						label {
							id = "strengthLabel"
							text = "Signal Strength"
						}

						stack {
							id = "strengthStack"
							addClass("innerStack")

							var strength: UILabel? = null

							buttonText {
								id = "decrStrength"
								text = "-"
								handler = { btn, mouseBtn ->
									if (remote.getStrength() >= 2) {
										remote.setStrength(if (KeyboardHelper.isShiftPressed()) 1 else remote.getStrength() - 1)
										strength!!.setText(remote.getStrength().toString())
										true
									} else {
										false
									}
								}
							}

							strength = label {
								id = "strength"
								text = remote.getStrength().toString()
								width = 20
							}

							buttonText {
								id = "incrStrength"
								text = "+"
								handler = { btn, mouseBtn ->
									if (remote.getStrength() <= 14) {
										remote.setStrength(if (KeyboardHelper.isShiftPressed()) 15 else remote.getStrength() + 1)
										strength!!.setText(remote.getStrength().toString())
										true
									} else {
										false
									}
								}
							}
						}
					}
				}

				fixed {
					id = "center"
					width = 146
					height = 60

					stack {
						id = "centerStack"
						addClass("verticalStack")

						label {
							id = "durationLabel"
							text = "Signal Duration"
						}

						stack {
							id = "durationStack"
							addClass("innerStack")

							var duration: UILabel? = null

							buttonText {
								id = "decrDuration"
								text = "-"
								handler = { btn, mouseBtn ->
									if (remote.getDuration() >= 2) {
										remote.setDuration(if (KeyboardHelper.isShiftPressed()) 1 else remote.getDuration() - 1)
										duration!!.setText(remote.getDuration().toString() + "s")
										true
									} else {
										false
									}
								}
							}

							duration = label {
								id = "duration"
								text = remote.getDuration().toString() + "s"
								width = 20
							}

							buttonText {
								id = "incrDuration"
								text = "+"
								handler = { btn, mouseBtn ->
									if (remote.getDuration() <= 14) {
										remote.setDuration(if (KeyboardHelper.isShiftPressed()) 15 else remote.getDuration() + 1)
										duration!!.setText(remote.getDuration().toString() + "s")
										true
									} else {
										false
									}
								}
							}
						}
					}
				}

				fixed {
					id = "bottom"
					width = 146
					height = 60

					stack {
						id = "bottomStack"
						addClass("verticalStack")

						label {
							id = "specificSideLabel"
							text = "Use Specific Side"
						}

						buttonToggle {
							id = "specificSide"
							state = remote.useSpecificSide()
							handler = {
								remote.setSpecificSide(it.state)
							}
						}
					}
				}
			}

			style("$MOD_ID:remote")
			closeHandler(synchronize)
			pausesGame(false)
		}
	}

}