/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Special {@link EventExecutorGroup} which allows registering {@link Channel}s that get
 * processed for later selection during the event loop.
 *
 */
public interface EventLoopGroup extends EventExecutorGroup {
    /**
     * Return the next {@link EventLoop} to use
     */
    @Override
    EventLoop next();

    /**
     *使用此 {@link EventLoop} 注册一个 {@link Channel}. 注册完成后，返回的 {@link ChannelFuture} 将收到通知。
     */
    ChannelFuture register(Channel channel);

    /**
     * 使用 {@link ChannelFuture} 向此 {@link EventLoop} 注册 {@link Channel}.
     * 一旦注册完成，被传递的ChannelFuture将得到通知，同时也将被返回。
     * .
     */
    ChannelFuture register(ChannelPromise promise);

    /**
     * 使用此 {@link EventLoop} 注册一个 {@link Channel}。
     * 一旦注册完成，被传递的ChannelFuture将得到通知，同时也将被返回。
     *
     * @deprecated Use {@link #register(ChannelPromise)} instead.
     */
    @Deprecated
    ChannelFuture register(Channel channel, ChannelPromise promise);
}
