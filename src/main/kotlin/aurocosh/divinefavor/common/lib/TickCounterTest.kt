package aurocosh.divinefavor.common.lib

import net.minecraft.util.math.MathHelper

class TickCounterTest {
    private var tickRate: Int = 0
    private var currentTicks: Int = 0

    constructor() {
        tickRate = 1
        currentTicks = 0
    }

    constructor(tickRate: Int) {
        this.tickRate = tickRate
        currentTicks = 0
    }

    fun setTickRate(tickRate: Int) {
        this.tickRate = Math.max(1, Math.abs(tickRate))
    }

    fun setCurrentTicks(ticks: Int) {
        currentTicks = MathHelper.clamp(ticks, 0, tickRate)
    }

    fun tick(): Boolean {
        if (currentTicks < tickRate) {
            currentTicks++
            return false
        } else {
            currentTicks = 0
            return true
        }
    }
}
