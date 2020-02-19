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

@Controller
public class StockController {
    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private String[] stock_comp = {"QCOM","PYPL","ORCL","NVDA","TSLA", "MSFT","FB", "GM", "NFLX", "AMZN", "IRBT", "CAT", "CSCO", "NKE", "SNE", "AAPL", "MCD", "DIS", "BAC", "INTC", "AMD", "ADBE", "HPE", "IBM", "DELL", "SPOT"};
    
    @RequestMapping("/stocks")
    public String stock_list(Model model){
        model.addAttribute("list_of_stocks", this.stock_comp);
        return "stocks_list";
    }
    
    @RequestMapping("/stock_id/{id}")
    public String stock_info(@PathVariable String id, Model model) throws Exception{
       
        //HttpGet JSONRequest = new HttpGet("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+id+"&interval=60min&outputsize=compact&apikey=JMLWD2TDC4G2OKTX");
        HttpGet JSONRequest = new HttpGet("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+id+"&apikey=JMLWD2TDC4G2OKTX");
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
                   if(symbol.equals(id)){
                       break;
                   }
                   
                }
                
                if(json_tmp != null){
                    System.out.println(json_tmp);
                    model.addAttribute("name", (String) json_tmp.get("2. name"));
                    model.addAttribute("region", json_tmp.get("4. region"));
                    model.addAttribute("currency", json_tmp.get("8. currency"));
                    model.addAttribute("type", json_tmp.get("3. type"));
                    model.addAttribute("timezone", json_tmp.get("7. timezone"));
                    model.addAttribute("symbol", json_tmp.get("1. symbol"));
                    
                }
                
                
            }

        }
        return "stock";
    }
    
}
