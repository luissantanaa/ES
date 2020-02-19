/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stocks;

/**
 *
 * @author alexandre
 */
public class Stock {
    private String nome;
    private String ID;
    
    
    public Stock(String ID, String nome){
        this.ID = ID;
        this.nome = nome;
    
    }
    
    public int getValue(){
        return 1;
    }
    
}
