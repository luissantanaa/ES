/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stocks;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 *
 * @author alexandre
 */
@Entity
public class StockHistory implements Comparable<StockHistory> {
    private double low =0.0;
    private int volume =0;
    private double open =0.0;
    private double high =0.0;
    private double close =0.0;
    private String date = "";
    
    
    @Id
    private long id;
    
    public StockHistory(){
    }
    
    
    public StockHistory(String date,double low, int volume, double open, double high, double close){
        this.date = date;
        this.low = low;
        this.volume = volume;
        this.open = open;
        this.high = high;
        this.close = close;
        
    }

    public double getLow() {
        return low;
    }

    public int getVolume() {
        return volume;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getClose() {
        return close;
    }
    
    public String getDate() {
        return date;
    }
    
     
    @Override
    public int compareTo( StockHistory stock) {
        return this.getDate().compareTo(stock.getDate());
    }
    
}
