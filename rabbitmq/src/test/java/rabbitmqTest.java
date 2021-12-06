import com.mxd.rabbitmq.SpringBootRabbitmqApplication;
import com.mxd.rabbitmq.constant.RabbitConstant;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: mxd
 * @Description: rabbitmq测试类
 * @Date: 2021/12/3 9:07
 */
@SpringBootTest(classes = SpringBootRabbitmqApplication.class)
@WebAppConfiguration
public class rabbitmqTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void batchSendMessage(){
        for (int i = 0; i < 1000000; i++) {
            System.out.println("发送第" + i + "个消息");
            rabbitTemplate.convertAndSend(RabbitConstant.SIMPLE_QUEUE_NAME, "hello world! 执行次数为：" + i);
        }
    }
}
