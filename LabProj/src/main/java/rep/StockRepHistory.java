/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rep;

/**
 *
 * @author alexandre
 */

import stocks.StockHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepHistory extends JpaRepository<StockHistoryEntry, String>{
    
}

