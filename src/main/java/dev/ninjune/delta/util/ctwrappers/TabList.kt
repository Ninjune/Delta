package dev.ninjune.delta.util.ctwrappers

import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import dev.ninjune.delta.Delta.mc
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.world.WorldSettings.GameType

object TabList {
    private val playerComparator = Ordering.from(PlayerComparator())

    @JvmStatic
    fun getNames(): List<String> {
        if (Client.getTabGui() == null) return listOf()

        val playerList = playerComparator.sortedCopy(mc.thePlayer!!.sendQueue.playerInfoMap)

        return playerList.map(Client.getTabGui()!!::getPlayerName)
    }

    internal class PlayerComparator internal constructor() : Comparator<NetworkPlayerInfo> {
        override fun compare(playerOne: NetworkPlayerInfo, playerTwo: NetworkPlayerInfo): Int {
            val teamOne = playerOne.playerTeam
            val teamTwo = playerTwo.playerTeam

            return ComparisonChain
                .start()
                .compareTrueFirst(
                    playerOne.gameType != GameType.SPECTATOR,
                    playerTwo.gameType != GameType.SPECTATOR
                ).compare(
                    teamOne?.registeredName ?: "",
                    teamTwo?.registeredName ?: ""
                ).compare(
                    playerOne.gameProfile.name,
                    playerTwo.gameProfile.name
                ).result()
        }
    }
}