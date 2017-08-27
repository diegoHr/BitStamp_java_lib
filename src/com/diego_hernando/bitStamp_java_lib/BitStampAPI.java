package com.diego_hernando.bitStamp_java_lib;

import java.io.IOException;

import com.diego_hernando.bitStamp_java_lib.exceptions.BadResponseException;

public class BitStampAPI {
	
	private String key;
	private String secret;
	
	
	
	public void setKey(String key) {
		this.key=key;
	}
	
	public void setSecret(String secret) {
		this.secret=secret;
	}
	
	
	
	public String getTicker(CurrencyPairsBitStamp currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTicker(currencyPair);
	}
	public String getOrderBook(CurrencyPairsBitStamp currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicOrderBook(currencyPair);
	}
	public String getTransactionsLastHour(CurrencyPairsBitStamp currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTransactionsLastHour(currencyPair);
	}
	public String getTransactionsLastDay(CurrencyPairsBitStamp currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTransactionsLastDay(currencyPair);
	}
	public String getTransactionsLastMinute(CurrencyPairsBitStamp currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTransactionsLastMinute(currencyPair);
	}
	public String getConvRateEurUsd() throws IOException,BadResponseException {
		return new ApiRequest().publicConvRateEurUsd();
	}
	
	
	
	
	
	
	

	
	

}
