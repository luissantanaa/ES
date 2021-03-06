/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stocks;



import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author alexandre
 */

@Entity
public class Stock implements Serializable {
    
    @Id
    private String ID;
    
    
    private String name;
    private String region;
    private String currency;
    private String type;
    private String timezone;
    

    
    public Stock(){}
    
    public Stock(String ID, String name, String region, String currency, String type,String timezone) {
        this.ID = ID;
        this.name = name;
        this.region = region;
        this.currency = currency;
        this.type=type;
        this.timezone = timezone;
    }
    
    
    
    public Stock(String ID, String name){
        this.ID = ID;
        this.name = name;
    
    }
    
    public String getID(){
        return ID;
    }
    
    public void setID(String ID){
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Stock{" + "ID=" + ID + ", name=" + name + ", region=" + region + ", currency=" + currency + ", type=" + type + ", timezone=" + timezone + '}';
    }
    
    
    
}
