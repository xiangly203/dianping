package org.example.dianping;

import jakarta.annotation.Resource;
import org.example.dianping.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class DianpingApplicationTests {
	@Resource
	private UserMapper userMapper;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void test() {
	}

}
