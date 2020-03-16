/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kafka;

//import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

/**
 *
 * @author alexandre
 */

@Service
public class KafkaProducer {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "stocks";
 
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public void sendMessage(String message) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        logger.info(String.format(formatter.format(date) + " Accessing-> %s", message));
        this.kafkaTemplate.send(TOPIC,formatter.format(date) +"  -> " +  message);
    }
}
