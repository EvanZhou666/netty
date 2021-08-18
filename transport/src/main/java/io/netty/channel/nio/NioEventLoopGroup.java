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
package io.netty.channel.nio;

import io.netty.channel.Channel;
import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopTaskQueueFactory;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;

import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 *
 * 实现了{@link MultithreadEventLoopGroup}，用于基于 NIO {@link Selector} 的 {@link Channel}s。
 */
public class NioEventLoopGroup extends MultithreadEventLoopGroup {

    /**
     *
     * 使用默认线程数、默认 {@link ThreadFactory} 和 {@link SelectorProvider#provider()} 返回的 {@link SelectorProvider} 创建一个新实例。
     */
    public NioEventLoopGroup() {
        this(0);
    }

    /**
     * 使用指定数量的线程 {@link ThreadFactory} 和 {@link SelectorProvider#provider()} 返回的 {@link SelectorProvider} 创建一个新实例
     *
     */
    public NioEventLoopGroup(int nThreads) {
        this(nThreads, (Executor) null);
    }

    /**
     *
     * 使用默认线程数、给定的 {@link ThreadFactory} 和 {@link SelectorProvider#provider()} 返回的 {@link SelectorProvider} 创建一个新实例。
     *
     */
    public NioEventLoopGroup(ThreadFactory threadFactory) {
        this(0, threadFactory, SelectorProvider.provider());
    }

    /**
     * 使用指定数量的线程、给定的 {@link ThreadFactory} 和 {@link SelectorProvider#provider()} 返回的 {@link SelectorProvider} 创建一个新实例。
     *
     */
    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, Executor executor) {
        this(nThreads, executor, SelectorProvider.provider());
    }

    /**
     * 使用指定数量的线程、给定的 {@link ThreadFactory} 和给定的 {@link SelectorProvider} 创建一个新实例。
     *
     */
    public NioEventLoopGroup(
            int nThreads, ThreadFactory threadFactory, final SelectorProvider selectorProvider) {
        this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory,
        final SelectorProvider selectorProvider, final SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(
            int nThreads, Executor executor, final SelectorProvider selectorProvider) {
        this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, Executor executor, final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                             final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory,
                RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                             final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory,
                             final RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                             final SelectorProvider selectorProvider,
                             final SelectStrategyFactory selectStrategyFactory,
                             final RejectedExecutionHandler rejectedExecutionHandler,
                             final EventLoopTaskQueueFactory taskQueueFactory) {
        super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory,
                rejectedExecutionHandler, taskQueueFactory);
    }

    /**
     * @param nThreads 此实例将使用的线程数。
     * @param executor 要使用的 Executor，或者 {@code null} 如果应该使用默认值。
     * @param chooserFactory 要使用的 {@link EventExecutorChooserFactory}。
     * @param selectorProvider 要使用的 {@link SelectorProvider}。
     * @param selectStrategyFactory 要使用的 {@link SelectStrategyFactory}。
     * @param rejectedExecutionHandler 要使用的 {@link RejectedExecutionHandler}。
     * @param taskQueueFactory 要使用的 the {@link EventLoopTaskQueueFactory} for {@link SingleThreadEventLoop#execute(Runnable)}，为null意味着使用默认值
     * @param tailTaskQueueFactory the {@link EventLoopTaskQueueFactory} to use for
     *                             {@link SingleThreadEventLoop#executeAfterEventLoopIteration(Runnable)},
     *                             or {@code null} if default one should be used.
     */
    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory,
                             SelectorProvider selectorProvider,
                             SelectStrategyFactory selectStrategyFactory,
                             RejectedExecutionHandler rejectedExecutionHandler,
                             EventLoopTaskQueueFactory taskQueueFactory,
                             EventLoopTaskQueueFactory tailTaskQueueFactory) {
        super(nThreads, executor, chooserFactory, selectorProvider, selectStrategyFactory,
                rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory);
    }

    /**
     * 设置子事件循环中用于 I/O 的所需时间百分比，默认是50%。
     * 这意味着事件循环将尝试为 IO 花费与非 IO 任务相同的时间。
     */
    public void setIoRatio(int ioRatio) {
        for (EventExecutor e: this) {
            ((NioEventLoop) e).setIoRatio(ioRatio);
        }
    }

    /**
     * 用新创建的 {@link Selector} 替换子事件循环的当前 {@link Selector}，以解决臭名昭著的 epoll 100% CPU 错误。
     */
    public void rebuildSelectors() {
        for (EventExecutor e: this) {
            ((NioEventLoop) e).rebuildSelector();
        }
    }

    @Override
    protected EventLoop newChild(Executor executor, Object... args) throws Exception {
        SelectorProvider selectorProvider = (SelectorProvider) args[0];
        SelectStrategyFactory selectStrategyFactory = (SelectStrategyFactory) args[1];
        RejectedExecutionHandler rejectedExecutionHandler = (RejectedExecutionHandler) args[2];
        EventLoopTaskQueueFactory taskQueueFactory = null;
        EventLoopTaskQueueFactory tailTaskQueueFactory = null;

        int argsLength = args.length;
        if (argsLength > 3) {
            taskQueueFactory = (EventLoopTaskQueueFactory) args[3];
        }
        if (argsLength > 4) {
            tailTaskQueueFactory = (EventLoopTaskQueueFactory) args[4];
        }
        return new NioEventLoop(this, executor, selectorProvider,
                selectStrategyFactory.newSelectStrategy(),
                rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory);
    }
}
