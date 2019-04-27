package com.example.springbootredislock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisLockApplicationTests {

	@Autowired
	DistributedLockHandler distributedLockHandler;

	Lock lock = new Lock("lock1", "012345");

	/**
	 * 先运行，占用锁
	 */
	@Test
	public void contextLoads() {
		if (distributedLockHandler.tryLock(lock)) {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean delete = distributedLockHandler.releaseLock(lock);
			System.out.println("删除：" + delete);
		}
	}

	/**
	 * 后运行，尝试获得锁
	 */
	@Test
	public void contextLoads2() {
		if (distributedLockHandler.tryLock(lock)) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean delete = distributedLockHandler.releaseLock(lock);
			System.out.println("删除：" + delete);
		}
	}

}
