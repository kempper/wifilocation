package hk.org.ha.geek.wifilocation;

import java.util.List;
import java.util.ArrayList;


public class POIBean {

	private String regionId;
	private String poiName;
	private List<Pair> actions;
		
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getPoiName() {
		return poiName;
	}
	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	public List<Pair> getActions() {
		return actions;	
	}

	public void setActions(String returnType, String returnValue, String isSameDayEvent) {
		Pair pair = new Pair();
		pair.setReturnType(returnType);
		pair.setReturnValue(returnValue);
		pair.setIsSameDayEvent(isSameDayEvent);
		List<Pair> newActions = this.actions;
		if(newActions==null) {
			newActions = new ArrayList<Pair>();
		}
		newActions.add(pair);

		this.actions = newActions;
	}

	private class Pair {
		private String returnType;
		private String returnValue;
		private String isSameDayEvent;
		
		public String getReturnType() {
			return returnType;
		}
		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}
		public String getReturnValue() {
			return returnValue;
		}
		public void setReturnValue(String returnValue) {
			this.returnValue = returnValue;
		}
		
		public String getIsSameDayEvent() {
			return isSameDayEvent;
		}
		
		public void setIsSameDayEvent(String isSameDayEvent) {
			this.isSameDayEvent = isSameDayEvent;
		}
	
	}
	
}
