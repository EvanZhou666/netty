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

import io.netty.util.concurrent.OrderedEventExecutor;

/**
 *
 * 注册后将处理 Channel 的所有 IO 操作。
 * 一个 {@link EventLoop} 实例通常会处理多个 {@link Channel}，但这可能取决于实现细节和内部结构。
 *
 */
public interface EventLoop extends OrderedEventExecutor, EventLoopGroup {
    @Override
    EventLoopGroup parent();
}
