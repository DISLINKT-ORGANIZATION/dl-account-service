package dislinkt.accountservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import dislinkt.accountservice.dtos.KafkaNotification;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	public Map<String, Object> consumerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return props;
	}

	@Bean
	public ConsumerFactory<String, KafkaNotification> consumerFactory() {
		ObjectMapper om = new ObjectMapper();
		return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
				new JsonDeserializer<KafkaNotification>(KafkaNotification.class, om, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaNotification> kafkaListenerContainerFactory(
			ConsumerFactory<String, KafkaNotification> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, KafkaNotification> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
}
