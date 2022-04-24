package mao.rabbitmq_implementing_delay_queues_based_on_plugins.controller;

import mao.rabbitmq_implementing_delay_queues_based_on_plugins.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project name(项目名称)：rabbitMQ_implementing_delay_queues_based_on_plugins
 * Package(包名): mao.rabbitmq_implementing_delay_queues_based_on_plugins.controller
 * Class(类名): MyController
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/24
 * Time(创建时间)： 18:06
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@RestController
public class MyController
{
    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test1/{message}")
    public String test1(@PathVariable String message)
    {
        log.info("开始发送消息：" + "延迟10秒：" + message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.DELAYED_EXCHANGE,
                RabbitMQConfig.DELAYED_ROUTING_KEY,
                "延迟10秒：" + message,
                new MessagePostProcessor()
                {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException
                    {
                        message.getMessageProperties().setDelay(10000);
                        return message;
                    }
                });
        return message;
    }

    @GetMapping("/test2/{message}")
    public String test2(@PathVariable String message)
    {
        log.info("开始发送消息：" + "延迟30秒：" + message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.DELAYED_EXCHANGE,
                RabbitMQConfig.DELAYED_ROUTING_KEY,
                "延迟30秒：" + message,
                new MessagePostProcessor()
                {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException
                    {
                        message.getMessageProperties().setDelay(30000);
                        return message;
                    }
                });
        return message;
    }
}
