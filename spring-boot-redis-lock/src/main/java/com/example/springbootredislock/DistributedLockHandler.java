package com.example.springbootredislock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class DistributedLockHandler {

	private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);
	private final static long LOCK_EXPIRE = 30 * 1000L;//单个业务持有锁的时间30s，防止死锁
	private final static long LOCK_TRY_INTERVAL = 30L;//默认30ms尝试一次
	private final static long LOCK_TRY_TIMEOUT = 40 * 1000L;//默认尝试20s

	@Autowired
	private StringRedisTemplate template;

	/**
	 * 尝试获取全局锁
	 *
	 * @param lock 锁的名称
	 * @return true 获取成功，false获取失败
	 */
	public boolean tryLock(Lock lock) {
		return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
	}

	/**
	 * 尝试获取全局锁
	 *
	 * @param lock    锁的名称
	 * @param timeout 获取超时时间 单位ms
	 * @return true 获取成功，false获取失败
	 */
	public boolean tryLock(Lock lock, long timeout) {
		return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
	}

	/**
	 * 尝试获取全局锁
	 *
	 * @param lock        锁的名称
	 * @param timeout     获取锁的超时时间
	 * @param tryInterval 多少毫秒尝试获取一次
	 * @return true 获取成功，false获取失败
	 */
	public boolean tryLock(Lock lock, long timeout, long tryInterval) {
		return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);
	}

	/**
	 * 尝试获取全局锁
	 *
	 * @param lock           锁的名称
	 * @param timeout        获取锁的超时时间
	 * @param tryInterval    多少毫秒尝试获取一次
	 * @param lockExpireTime 锁的过期
	 * @return true 获取成功，false获取失败
	 */
	public boolean tryLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
		return getLock(lock, timeout, tryInterval, lockExpireTime);
	}


	/**
	 * 操作redis获取全局锁
	 *
	 * @param lock           锁的名称
	 * @param timeout        获取的超时时间
	 * @param tryInterval    多少ms尝试一次
	 * @param lockExpireTime 获取成功后锁的过期时间
	 * @return true 获取成功，false获取失败
	 */
	public boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
		long startTime = System.currentTimeMillis();
		try {
			if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {
				return false;
			}
			do{
				if (!template.hasKey(lock.getName())) {
					ValueOperations<String, String> ops = template.opsForValue();
					ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
					System.out.println("获取锁成功.");
					return true;
				} else {//存在锁
					logger.debug("lock is exist!！！");
					System.out.println("获取锁失败，正在重试...");
				}
				if (System.currentTimeMillis() - startTime > timeout) {
					//尝试超过了设定值之后直接跳出循环
					return false;
				}
				Thread.sleep(tryInterval);
			}
			while (template.hasKey(lock.getName())) ;
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			return false;
		}
		if(!template.hasKey(lock.getName())) {
			System.out.println("锁已被释放");
			ValueOperations<String, String> ops = template.opsForValue();
			ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
			System.out.println("获取锁成功.");
			return true;
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
		return false;
	}

	/**
	 * 释放锁
	 */
	public boolean releaseLock(Lock lock) {
		if (!StringUtils.isEmpty(lock.getName())) {
			return template.delete(lock.getName());
		}
		return false;
	}

}