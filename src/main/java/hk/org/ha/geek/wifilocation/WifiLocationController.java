package hk.org.ha.geek.wifilocation;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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
    
    @RequestMapping(value="/api/v1/poilist",method = RequestMethod.GET, headers="Accept=application/json")
    public String getFullList(@RequestParam(value="isSameDayEvent") String isSameDayEvent) {        
    String json = "";
    	    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {    
    		
    		HashMap eventMap = new HashMap();
    		HashMap poiMap = new HashMap();

    		StringBuilder contentBuilder = new StringBuilder();
    		try {

    		    BufferedReader in = new BufferedReader(new FileReader("/home/pbrcadm/projects/poi_list.csv"));
    		    BufferedReader in2 = new BufferedReader(new FileReader("/home/pbrcadm/projects/event_list.csv"));

                POIBean bean;

    		    String str;
    		    while ((str = in.readLine()) != null) {
    		        String[] poi = str.split(",");
    		        poiMap.put(poi[0], poi);
    		    }
    		    
    		    while ((str = in2.readLine()) != null) {
    		        String[] event = str.split(",");
                    String[] poi = (String[])poiMap.get(event[0]);

                    if(isSameDayEvent!=null&&isSameDayEvent.equals("T")) {
                        if(isSameDayEvent.equals(event[3])) {
                            bean = (POIBean)eventMap.get(event[0]);
                            if(bean == null) {
                                bean = new POIBean();
                                bean.setRegionId(event[0]);
                                bean.setPoiName(poi[1]);
                            }
                            
                            bean.setActions(event[1],event[2],event[3]);
                            eventMap.put(event[0], bean);
                        }
                    }
                    else {
                        bean = (POIBean)eventMap.get(event[0]);
                        if(bean == null) {
                            bean = new POIBean();
                            bean.setRegionId(event[0]);
                            bean.setPoiName(poi[1]);
                        }
                        
                        bean.setActions(event[1],event[2],event[3]);
                        eventMap.put(event[0], bean);
                    }
    		    }
    		    
    		    in.close();
    		} catch (IOException e) {
    		}
    		
    		json = mapper.writeValueAsString(eventMap);
    		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return json;
    
    }
    
    @RequestMapping(value="/api/v1/poi",method = RequestMethod.GET, headers="Accept=application/json")
    public String getPoi(@RequestParam(value="id") String id) {        
        
    	String json = "";
    	    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {    
    		
    		/*
    		POIBean bean = new POIBean();
    		bean.setRegionId(id);    		
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
    		*/
    		HashMap poiMap = new HashMap();
    		HashMap eventMap = new HashMap();

        	POIBean bean = new POIBean();


    		StringBuilder contentBuilder = new StringBuilder();
    		try {

    		    BufferedReader in = new BufferedReader(new FileReader("/home/pbrcadm/projects/poi_list.csv"));
    		    BufferedReader in2 = new BufferedReader(new FileReader("/home/pbrcadm/projects/event_list.csv"));

    		    String str;
    		    while ((str = in.readLine()) != null) {
    		        String[] poi = str.split(",");
    		        System.out.println(poi[0]+"; "+poi[1]+"; "+poi[2]);
    		        poiMap.put(poi[0], poi);
    		    }
    		    
    		    String[] poi = (String[])poiMap.get(id);
                
                bean.setRegionId(id);
                bean.setPoiName(poi[1]);

    		    while ((str = in2.readLine()) != null) {
    		        String[] event = str.split(",");
    		        if(event[0].equals(bean.getRegionId())) {
                        bean.setActions(event[1],event[2],event[3]);
    		        }
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
