package dev.ninjune.delta.mixin;

import dev.ninjune.delta.network.RecievePacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ninjune.delta.network.NetworkEventManager.getEvents;

@Mixin(value = NetworkManager.class, priority = Integer.MAX_VALUE)
public class NetworkManagerMixin
{
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onReceivePacket(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        getEvents().forEach((event) -> {
            if(event instanceof RecievePacketEvent)
                event.fire(packet);
        });
    }
}
