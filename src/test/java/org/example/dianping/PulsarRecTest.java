package org.example.dianping;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.shade.org.jvnet.hk2.annotations.Service;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.core.PulsarTemplate;

@Slf4j
@SpringBootTest(classes = DianpingApplication.class)
@Service
public class PulsarRecTest {
    @Test
    @PulsarListener(subscriptionName = "my-subscription", topics = "my-topic")
    public void handleMessage(Message<String> message) {
        String content = message.getValue();
        System.out.println("Received message: " + content);
    }
}
