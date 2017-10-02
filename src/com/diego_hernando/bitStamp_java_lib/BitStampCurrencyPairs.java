package com.diego_hernando.bitStamp_java_lib;

import java.util.HashMap;
import java.util.Map;

public enum BitStampCurrencyPairs {
	
	
	BTCUSD ("btcusd"),
	BTCEUR ("btceur"),
	EURUSD ("eurusd"),
	XRPUSD ("xrpusd"),
	XRPEUR ("xrpeur"),
	
	XRPBTC ("xrpbtc"),
	
	LTCUSD("ltcusd"),
	LTCEUR("ltceur"),
	
	LTCBTC ("ltcbtc"),
	ETHUSD ("ethusd"),
	ETHEUR ("etheur"),
	ETHBTC ("ethbtc");
	
	private String currencyPair;
	private static Map<String,BitStampCurrencyPairs> mapForFindValues;
	
	private static Map<String,BitStampCurrencyPairs> getMapForFindValues(){
		if(mapForFindValues==null) {
			mapForFindValues=new HashMap<>();
		}
		return mapForFindValues;
	}
	
	BitStampCurrencyPairs(String currencyPair){
		Map<String,BitStampCurrencyPairs> mapValues=getMapForFindValues();
				
		this.currencyPair=currencyPair;
		mapValues.put(currencyPair, this);
	}
	
	@Override
	public String toString() {
		return this.currencyPair;
		
	}
	
	
	public BitStampCurrencyPairs findValue(String value) {
		BitStampCurrencyPairs currency=getMapForFindValues().get(value);
		return currency;
		
	}

}

