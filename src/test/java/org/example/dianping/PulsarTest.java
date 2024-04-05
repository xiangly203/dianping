package org.example.dianping;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.pulsar.core.PulsarTemplate;


@Slf4j
@SpringBootTest(classes = DianpingApplication.class)
public class PulsarTest {
    @Resource
    private PulsarTemplate<String> pulsarTemplate;

    @Test
    public void TestSend() throws PulsarClientException {
        pulsarTemplate.send("my-topic", "message");
    }

}
