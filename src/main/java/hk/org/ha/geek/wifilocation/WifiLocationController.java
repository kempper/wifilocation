package hk.org.ha.geek.wifilocation;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class WifiLocationController {	
    
    @RequestMapping(value="/api/v1/poi",method = RequestMethod.GET, headers="Accept=application/json")
    public String getTransactions(@RequestParam(value="id") String id) {        
        
    	String json = "";
    	    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {    
    		
    		POIBean bean = new POIBean();
    		bean.setId(id);    		
    		
    		if(id.equals("HAB-0001")) {
    			bean.setPoiName("Leature Theatre");
    			bean.setActions("MSG","Auto Check-in completed.");
    		}
    		else if(id.equals("HAB-0002")) {     		
    			bean.setPoiName("Main Lobby");
    			bean.setActions("URL","http://www.ha.org.hk/vistor/ha_index.asp");
    		}
    		else if(id.equals("HAB-0003")) {
    			bean.setPoiName("M/F Room 1");
    			bean.setActions("URL","http://www.ha.org.hk/vistor/ha_index/asp");
    			bean.setActions("EVENT", "Blood Donation Campaign||2018-02-24 13:00||2018-02-24 17:30");
    		}
    		
    		
    		StringBuilder contentBuilder = new StringBuilder();
    		try {

    		    BufferedReader in = new BufferedReader(new FileReader("/home/pbrcadm/projects/poi_list.csv"));
    		    String str;
    		    while ((str = in.readLine()) != null) {
    		        String[] poi = str.split(",");
    		        System.out.print(str+"="+poi[0]+"; "+poi[1]+"; "+poi[2]);
    		    }
    		    in.close();
    		} catch (IOException e) {
    		}

    		json = mapper.writeValueAsString(bean);
    		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return json;
    }
    
    
}