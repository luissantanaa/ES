/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kafka;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author alexandre
 */

@Entity
public class KafkaMessage implements Comparable<KafkaMessage>, Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String Message;

    public KafkaMessage() {
    }

    
    
    
    public KafkaMessage(String Message) {
        this.Message = Message;
    }

    
    
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public int compareTo(KafkaMessage t) {
       return this.getMessage().compareTo(t.getMessage());
    }
    
    
    
}
