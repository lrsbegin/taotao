package com.taotao.rest.jedis;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedisSingle() {
		//创建一个Jedis对象
		Jedis jedis = new Jedis("192.168.137.131", 6379);
		//调用Jedis对象的方法
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭Jedis
		jedis.close();
	}
	
	/**
	 * 使用连接池
	 */
	@Test
	public void tetsJedisPool() {
		//创建连接池对象
		JedisPool pool = new JedisPool("192.168.137.131", 6379);
		//从连接池中获得jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jedis
		jedis.close();
		//关闭连接池
		pool.close();
	}
	
	/**
	 * redis集群版测试
	 * @throws IOException 
	 */
	@Test
	public void tetsJedisCluster() throws IOException {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.137.131", 7000));
		nodes.add(new HostAndPort("192.168.137.131", 7001));
		nodes.add(new HostAndPort("192.168.137.131", 7002));
		nodes.add(new HostAndPort("192.168.137.132", 7003));
		nodes.add(new HostAndPort("192.168.137.132", 7004));
		nodes.add(new HostAndPort("192.168.137.132", 7005));
		
		JedisCluster cluster = new JedisCluster(nodes);
		String string = cluster.get("hello");
		System.out.println(string);
		
		cluster.close();
	}
	
	/**
	 * 测试单机版spring和jedis的整合
	 * @throws IOException 
	 */
	@Test
	public void testSreingJedisSingle() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		JedisPool pool = (JedisPool) context.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	/**
	 * 测试集群版spring和jedis的整合
	 * @throws IOException 
	 */
	@Test
	public void testSreingJedisCluster() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		JedisCluster jedisCluster = (JedisCluster) context.getBean("redisClient");
		String string = jedisCluster.get("hello");
		System.out.println(string);
		jedisCluster.close();
	}
}
