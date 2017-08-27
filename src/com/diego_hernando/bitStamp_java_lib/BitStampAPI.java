package com.diego_hernando.bitStamp_java_lib;

public class BitStampAPI {
	
	private String key;
	private String secret;
	
	private CurrencyPairs currencyPairs;
	
	public void setKey(String key) {
		this.key=key;
	}
	
	public void setSecret(String secret) {
		this.secret=secret;
	}
	
	public CurrencyPairs getCurrencyPairs() {
		return currencyPairs;
	}
	
	
	
	
	
	
	private final class CurrencyPairs {
		
		
		public static final String BTCUSD="btcusd";
		public static final String BTCEUR="btceur";
		public static final String EURUSD="eurusd";
		public static final String XRPUSD="xrpusd";
		public static final String XRPEUR="xrpeur";
		public static final String XRPBTC="xrpbtc";
		public static final String LTCUSD="ltcusd";
		public static final String LTCEUR="ltceur";
		public static final String LTCBTC="ltcbtc";
		public static final String ETHUSD="ethusd";
		public static final String ETHEUR="etheur";
		public static final String ETHBTC="ethbtc";
		
		private  String[] currencyPairs=new String[] {BTCUSD,BTCEUR,EURUSD,XRPUSD,XRPEUR,XRPBTC,LTCUSD,LTCEUR,LTCBTC,ETHUSD,ETHEUR,ETHBTC};
		
		
		public String [] getAllCurrencyPairs() {
			return currencyPairs;
		}
		
		

	}

	
	

}
