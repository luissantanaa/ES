/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open_s the template in the editor.
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
public class StockHistoryEntry implements Comparable<StockHistoryEntry>, Serializable {
    private double low =0.0;
    private int volume =0;
    private double open_s =0.0;
    private double high =0.0;
    private double close_s =0.0;
    
    @Id
    private String date_s = "";
    
    
    @Id
    private String id="";
    
    public StockHistoryEntry(){
    }
    
    
    public StockHistoryEntry(String id,String date_s,double low, int volume, double open_s, double high, double close_s){
        this.id = id;
        this.date_s = date_s;
        this.low = low;
        this.volume = volume;
        this.open_s = open_s;
        this.high = high;
        this.close_s = close_s;      
    }


    public String getId() {
        return id;
    }
    

    public double getLow() {
        return low;
    }

    public int getVolume() {
        return volume;
    }

    public double getOpen() {
        return open_s;
    }

    public double getHigh() {
        return high;
    }

    public double getClose() {
        return close_s;
    }
    
    public String getDate() {
        return date_s;
    }
    

    public void setLow(double low) {
        this.low = low;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setOpen(double open_s) {
        this.open_s = open_s;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setClose(double close_s) {
        this.close_s = close_s;
    }

    public void setDate(String date_s) {
        this.date_s = date_s;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    
     
    @Override
    public int compareTo( StockHistoryEntry stock) {
        return this.getDate().compareTo(stock.getDate());
    }
    
}
