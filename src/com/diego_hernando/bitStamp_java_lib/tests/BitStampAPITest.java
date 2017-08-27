package com.diego_hernando.bitStamp_java_lib.tests;


import static org.junit.Assert.assertNotNull;

import java.io.PrintStream;

import org.junit.Test;

import com.diego_hernando.bitStamp_java_lib.BitStampAPI;
import com.diego_hernando.bitStamp_java_lib.CurrencyPairsBitStamp;

public class BitStampAPITest   {
	private BitStampAPI api;
	
	
	@Test
	public void getTickerTest() throws Exception {
		setUp();
		for(CurrencyPairsBitStamp curPair: CurrencyPairsBitStamp.values()) {
			getTickerOnePairTest(curPair);
		}
	}
	
	@Test
	public void getOrderBookTest() throws Exception{
		setUp();
		for(CurrencyPairsBitStamp curPair: CurrencyPairsBitStamp.values()) {
			getOrderBookOnePairTest(curPair);
		}
	}
	
	@Test
	public void getTransactionsTest() throws Exception{
		setUp();
		for(CurrencyPairsBitStamp currencyPair: CurrencyPairsBitStamp.values()){
			getTransactionLastDayOnePairTest(currencyPair);
			getTransactionLastHourOnePairTest(currencyPair);
			getTransactionLastMinuteOnePairTest(currencyPair);
		}
	}
	
	@Test
	public void getConvRateEurUsdTest() throws Exception{
		setUp();
		String response=api.getConvRateEurUsd();
		System.out.println("Conversion rate between Eur and Usd : "+response);
		assertNotNull(response);
	}
	
	
    protected void setUp() throws Exception{
		api=new BitStampAPI();
	}

	
	private void getTickerOnePairTest(CurrencyPairsBitStamp currencyPair) throws Exception {
				
		String response=api.getTicker(currencyPair);
		
		printTickerResponse(currencyPair, response);
		
		assertNotNull(response);
		
	}
	
	private void getOrderBookOnePairTest (CurrencyPairsBitStamp currencyPair) throws Exception{
		String response=api.getTicker(currencyPair);
		System.out.println("Order Book "+currencyPair+": "+response);
		assertNotNull(response);
	}
	
	private void getTransactionLastHourOnePairTest (CurrencyPairsBitStamp currencyPair) throws Exception{
		String response=api.getTransactionsLastHour(currencyPair);
		new PrintStream(System.out, true, "UTF-8").println("Last hour transactions "+currencyPair+": "+response);
		assertNotNull(response);
	}
	
	private void getTransactionLastDayOnePairTest (CurrencyPairsBitStamp currencyPair) throws Exception{
		String response=api.getTransactionsLastDay(currencyPair);
		new PrintStream(System.out, true, "UTF-8").println("Last day transactions "+currencyPair+": "+response);
		assertNotNull(response);
	}
	
	private void getTransactionLastMinuteOnePairTest (CurrencyPairsBitStamp currencyPair) throws Exception{
		String response=api.getTransactionsLastMinute(currencyPair);
		new PrintStream(System.out, true, "UTF-8").println("Last minute transactions "+currencyPair+": "+response);
		assertNotNull(response);
	}
	
	
	
	
	
	
	
	
	
	
	private void printTickerResponse(CurrencyPairsBitStamp currencyPair,String response) {
		System.out.println("Ticker "+currencyPair+": "+response);
	}

}
