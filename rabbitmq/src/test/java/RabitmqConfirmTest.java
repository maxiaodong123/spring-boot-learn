import com.mxd.rabbitmq.SpringBootRabbitmqApplication;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: mxd
 * @Description: 消息确认测试
 * @Date: 2021/11/23 9:47
 */
@SpringBootTest(classes = SpringBootRabbitmqApplication.class)
@WebAppConfiguration
public class RabitmqConfirmTest {

    @Autowired
    RabbitTemplate template;

    @Test
    void testConfirmTrue() {
        // 设置confirm回调函数
        template.setConfirmCallback(new ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, java.lang.String s) {
                if (b) System.out.println("消息发送成功");
                else System.out.println("消息发送失败");
            }

        });
        // 模拟生产者发送信息--正常情况
        template.convertAndSend("","confirm_test_queue","日志级别：info；日志模块：user；日志信息：*****");
    }

    @Test
    void testConfirmFalse() {
        // 设置confirm回调函数
        template.setConfirmCallback(new ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, java.lang.String s) {
                if (b) System.out.println("消息发送成功");
                else System.out.println("消息发送失败");
            }

        });
        // 模拟生产者发送信息
        // 不存在的交换机--异常情况
        template.convertAndSend("confirm_test_queue","confirm_test_queue","日志级别：info；日志模块：user；日志信息：*****");
    }

    @Test
    void testReturnFalse() {
        // 设置return回调函数
        template.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println(returnedMessage.toString());
//                System.out.println(s+"*********");

            }
        });
        template.setMandatory(true);
        // 模拟生产者发送信息
        // 正确的交换机 错误的routekey -- 异常情况
        template.convertAndSend("","noexist.user.info","日志级别：info；日志模块：user；日志信息：*****");
    }
}




