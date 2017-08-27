package com.diego_hernando.bitStamp_java_lib;

public enum CurrencyPairsBitStamp {
	
	
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
	
	
	CurrencyPairsBitStamp(String currencyPair){
		this.currencyPair=currencyPair;
	}
	
	@Override
	public String toString() {
		return this.currencyPair;
	}

}

