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

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import rep.StockRep;
import rep.StockRepHistory;


@Controller
public class StockController {
    
    @Autowired
    StockRep StockRepository;
    
    @Autowired
    StockRepHistory StockHistoryRepository;
    
    
    
    private int counter =0;
    private Instant time_start = Instant.MIN;
    private Instant time_end;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final String[] stock_comp = {"QCOM","PYPL","ORCL","NVDA","TSLA", "MSFT","FB", "GM", "NFLX", "AMZN", "IRBT", "CAT", "CSCO", "NKE", "SNE", "AAPL", "MCD", "DIS", "BAC", "INTC", "AMD", "ADBE", "HPE", "IBM", "DELL", "SPOT"};
    
    @RequestMapping("/stocks")
    public String stock_list(Model model){
        model.addAttribute("list_of_stocks", this.stock_comp);
        return "stocks_list";
    }
    
    
    //Need to decide what happens with this function, information never changes, info statically added to DB?
    @RequestMapping("/stock_id/{id}")
    public String stock_info(@PathVariable String id, Model model) throws Exception{
        List<Stock> stock_information =StockRepository.findAll();
        
        Stock s_tmp = null;
        for(Stock s : stock_information){
            if(s.getID().equals(id)){
                s_tmp = s;
                break;
            }
        }
        
        System.out.print(s_tmp);
        
        model.addAttribute("stock",s_tmp);
        
        

        return "stock";
    }
    @RequestMapping("/history/{id}")
    public String stock_history(@PathVariable String id, Model model) throws Exception{
        this.time_end = Instant.now();
        
        if((double)Duration.between(this.time_start, this.time_end).toMinutes() > 1  ){
            this.time_start = Instant.now();
            this.counter =1;
        }
        else{
            if(this.counter < 5){
                this.counter+=1;
            }else{
                
                double time = (double)Duration.between(this.time_start, this.time_end).toMillis();
                time = time/1000;
                time = 60 -time;
                model.addAttribute("time", time);
                return "api_error";
            }
        }
        
        List<StockHistoryEntry> historico =StockHistoryRepository.findAll();
        
        List<StockHistoryEntry> historico_filtered = new LinkedList<>();
        
        for(StockHistoryEntry e : historico){
            if(e.getId().equals(id)){
                historico_filtered.add(e);
                
            }
        }
        
        
        Collections.sort(historico_filtered, Collections.reverseOrder());
                
        
        model.addAttribute("historico",historico_filtered);
        
           
        
        
        return "s_history";
    }
    
    
    @Scheduled(fixedRate = 60000)
    public void getStockHistory() throws Exception{
        //make requests to api and save it to rep using mysql db
        String[] stock_comp = {"TSLA", "NFLX"};
        
        for(String x : stock_comp){
            HttpGet JSONRequest = new HttpGet("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + x +"&interval=1min&outputsize=compact&apikey=JMLWD2TDC4G2OKTX");
            try (CloseableHttpResponse response = httpClient.execute(JSONRequest)) {
                HttpEntity entity = response.getEntity();
                Header headers = entity.getContentType();
                

                if (entity != null) {
                    JSONObject json_tmp = null;
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(result);

                    System.out.println(json);
                    System.out.print("\n");
                    System.out.print(json.get("Time Series (1min)"));
                    
                    System.out.print("\n");
                    JSONObject tmp_2 = (JSONObject) json.get("Time Series (1min)"); 
                    Iterator<String> keys = tmp_2.keys();
                    System.out.print("OK\n");
                    ArrayList<StockHistoryEntry> history_list = new ArrayList<>();
                    ArrayList<StockHistoryEntry> tmp = new ArrayList<>();
                    StockHistoryEntry saveToDB = null;
                   
                    while(keys.hasNext()) {
                        String key = keys.next();
                        
                        JSONObject tmp_3 =  (JSONObject) tmp_2.get(key);
                        double low = Double.parseDouble((String) tmp_3.get("3. low"));
                        int volume = Integer.parseInt((String) tmp_3.get("5. volume"));
                        double open = Double.parseDouble((String) tmp_3.get("1. open"));
                        double high = Double.parseDouble((String) tmp_3.get("2. high"));
                        double close = Double.parseDouble((String) tmp_3.get("4. close"));

                        saveToDB = new StockHistoryEntry((String) x,(String)key, low,volume,open,high,close);
                        tmp.add(saveToDB);
                    }
                    Collections.sort(tmp, Collections.reverseOrder());
                    
                    saveToDB= tmp.remove(0);
                    
                    
                    StockHistoryRepository.save(saveToDB);
                }
            }
        }
        
    }
    
    
    @RequestMapping("/add")
    public String addStockInfo() throws Exception{
        this.time_end = Instant.now();
        
        if((double)Duration.between(this.time_start, this.time_end).toMinutes() > 1  ){
            this.time_start = Instant.now();
            this.counter =1;
        }
        else{
            if(this.counter < 5){
                this.counter+=1;
            }else{
                double time = (double)Duration.between(this.time_start, this.time_end).toMillis();
                time = time/100;
                time = 60-time;
            }
        }
        
        String[] stocks = {"TSLA", "NFLX"};
        
        for(String x : stocks){

            HttpGet JSONRequest = new HttpGet("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+x+"&apikey=JMLWD2TDC4G2OKTX");
            try (CloseableHttpResponse response = httpClient.execute(JSONRequest)) {
                HttpEntity entity = response.getEntity();
                Header headers = entity.getContentType();


                if (entity != null) {
                    JSONObject json_tmp = null;
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(result);

                    System.out.println(json);
                    JSONArray tmp = (JSONArray) json.get("bestMatches");

                    for(int i =0;i<tmp.length();i++){
                       json_tmp = tmp.getJSONObject(i);
                       String symbol = (String) json_tmp.get("1. symbol");
                       if(symbol.equals(x)){
                           break;
                       }

                    }

                    if(json_tmp != null){
                        System.out.println(json_tmp);
                        
                        Stock s = new Stock((String) x, (String) json_tmp.get("2. name"),(String) json_tmp.get("4. region"),(String) json_tmp.get("8. currency"),(String) json_tmp.get("3. type"),(String) json_tmp.get("7. timezone") );
                        
                        StockRepository.save(s);
                    }


                }

            }
        }
        return "redirect:/";

    }
    
}
