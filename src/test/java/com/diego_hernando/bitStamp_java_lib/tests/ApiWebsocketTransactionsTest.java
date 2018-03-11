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
	
	@Test
	public void test() throws Exception{
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
			}
		};
		
		ApiWebsocketTransactions wbs=new BitStampAPI().getWebSocketOfTransactions(wbErrListener,wbTradeDoneListener);
		
		wbs.openAllChannels();
		wbs.connect();
		System.out.println("Press enter key when you want finish this test.");
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
		
		wbs.disconnect();
		
	}

}
