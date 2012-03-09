package org.sandwich.network
import org.jboss.netty.channel.group.DefaultChannelGroup
import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.ExceptionEvent
import org.jboss.netty.channel.ChannelStateEvent
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.handler.codec.http.HttpRequest

class ChannelHandler(allChannels: DefaultChannelGroup) extends SimpleChannelUpstreamHandler {

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
    e.getChannel.close()
  }

  override def channelOpen(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    allChannels.add(e.getChannel)
  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
    e.getMessage match {

      case nettyHttpRequest: HttpRequest =>
      	println(nettyHttpRequest)
    }
  }

}