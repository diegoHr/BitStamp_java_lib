package com.diego_hernando.bitStamp_java_lib.tests;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.diego_hernando.bitStamp_java_lib.ApiWebsocketTransactions;
import com.diego_hernando.bitStamp_java_lib.BitStampAPI;
import com.diego_hernando.bitStamp_java_lib.BitStampCurrencyPairs;
import com.diego_hernando.bitStamp_java_lib.WebsocketErrorListener;
import com.diego_hernando.bitStamp_java_lib.WebsocketTradeDoneListener;

public class ApiWebsocketTransactionsTest {
	
	private boolean success;
	@Test
	public void test() throws Exception{
		success=false;
		WebsocketErrorListener wbErrListener=new WebsocketErrorListener() {
			
			@Override
			public void onError(String error, String errorCode, ApiWebsocketTransactions webSocket) {
				System.out.println(error+"|"+errorCode);
				
			}
		};
		WebsocketTradeDoneListener wbTradeDoneListener=new WebsocketTradeDoneListener() {
			
			@Override
			public void onTradeDone(String jsonTrade, BitStampCurrencyPairs tradingPair) {
				Assert.assertTrue(jsonTrade.length()>0);	
				System.out.println(tradingPair+": "+jsonTrade);
				success=true;
			}
		};
		
		ApiWebsocketTransactions wbs=new BitStampAPI().getWebSocketOfTransactions(wbErrListener,wbTradeDoneListener);
		
		wbs.openAllChannels();
		wbs.connect();
		
		Thread.sleep(20000);
		Assert.assertTrue(success);
		
		wbs.disconnect();
	}

}
