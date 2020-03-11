/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rep;

import kafka.KafkaMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alexandre
 */
public interface LoggerRep extends JpaRepository<KafkaMessage, String>{
    
}
