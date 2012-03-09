package org.sandwich.network

import org.jboss.netty.channel._
import org.jboss.netty.channel.group._
import org.jboss.netty.bootstrap._
import java.util.concurrent.Executors
import org.jboss.netty.handler.codec.http.HttpRequestDecoder
import org.jboss.netty.handler.codec.http.HttpResponseEncoder


class Server(port: Int = 9999, address: String = "0.0.0.0") {

  val bootstrap = new ServerBootstrap(
    new org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool()))

  val allChannels = new DefaultChannelGroup

  val defaultUpStreamHandler = new ChannelHandler(allChannels)

  class DefaultPipelineFactory extends ChannelPipelineFactory {
    def getPipeline = {
      val newPipeline = Channels.pipeline()
      newPipeline.addLast("decoder", new HttpRequestDecoder(4096, 8192, 8192))
      newPipeline.addLast("encoder", new HttpResponseEncoder())
      newPipeline.addLast("handler", defaultUpStreamHandler)
      newPipeline
    }
  }

  def start() {
    bootstrap.setPipelineFactory(new DefaultPipelineFactory)
    allChannels.add(bootstrap.bind(new java.net.InetSocketAddress(address, port)))
  }

  def stop() {
    allChannels.close().awaitUninterruptibly()
    bootstrap.releaseExternalResources()
  }
}