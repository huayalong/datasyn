package com.ruoyi.datanetty;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cjh:
 * @version 创建时间：2022年8月19日 上午11:04:01 类说明 用来保存当前链接
 */
public class ChannelMap {

	public static int channelNum = 0;

	private static ConcurrentHashMap<String, Channel> channelHashMap = null;// concurrentHashmap以解决多线程冲突

	public static ConcurrentHashMap<String, Channel> getChannelHashMap() {
		return channelHashMap;
	}

	/**
	 * 根据ip来获取集合中的连接
	 * @param name
	 * @return
	 */
	public static Channel getChannelByName(String name) {
		if (channelHashMap == null || channelHashMap.isEmpty()) {
			return null;
		}
		return channelHashMap.get(name);
	}

	/**
	 * 添加客户端新接入的连接
	 * @param name
	 * @param channel
	 */
	public static void addChannel(String name, Channel channel) {
		if (channelHashMap == null) {
			channelHashMap = new ConcurrentHashMap<String, Channel>(10);
		}
		channelHashMap.put(name, channel);
		channelNum++;
	}

	/**
	 * 根据名称删除已经离线的连接
	 * @param name
	 * @return
	 */
	public static int removeChannelByName(String name) {
		if (channelHashMap.containsKey(name)) {
			channelHashMap.remove(name);
			return 0;
		} else {
			return 1;
		}
	}
}
