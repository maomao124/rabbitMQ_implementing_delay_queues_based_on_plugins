package mao.rabbitmq_implementing_delay_queues_based_on_plugins.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Project name(项目名称)：rabbitMQ_implementing_delay_queues_based_on_plugins
 * Package(包名): mao.rabbitmq_implementing_delay_queues_based_on_plugins.config
 * Class(类名): RabbitMQConfig
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/24
 * Time(创建时间)： 18:05
 * Version(版本): 1.0
 * Description(描述)： 无
 */
@Configuration
public class RabbitMQConfig
{
    //延迟交换机
    public static final String DELAYED_EXCHANGE = "delay_exchange";
    //队列
    public static final String DELAYED_QUEUE = "delay_queue";
    //routing_key
    public static final String DELAYED_ROUTING_KEY = "delay";


    /**
     * Gets message converter.
     *
     * @return the message converter
     */
    @Bean
    public MessageConverter getMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * Custom exchange custom exchange.
     *
     * @return the custom exchange
     */
    @Bean
    public CustomExchange customExchange()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, map);
    }

    /**
     * Queue queue.
     *
     * @return the queue
     */
    @Bean
    public Queue queue()
    {
        return new Queue(DELAYED_QUEUE, false, false, false, null);
    }

    /**
     * Delay exchange bind delay queue binding.
     *
     * @param customExchange the custom exchange
     * @param queue          the queue
     * @return the binding
     */
    @Bean
    public Binding delay_exchange_bind_delay_queue(@Qualifier("customExchange") CustomExchange customExchange,
                                                   @Qualifier("queue") Queue queue)
    {
        return BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
