package com.diego_hernando.bitStamp_java_lib;

public interface WebsocketTradeDoneListener {
	
	public void onTradeDone(String jsonTrade,BitStampCurrencyPairs tradingPair);

}
