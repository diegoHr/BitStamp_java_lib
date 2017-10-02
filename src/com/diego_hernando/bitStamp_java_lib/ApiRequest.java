package com.diego_hernando.bitStamp_java_lib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.diego_hernando.bitStamp_java_lib.exceptions.BadResponseException;

public class ApiRequest {
	
	
	private final String baseUrl="https://www.bitstamp.net/api/";
	
	private static PublicApiOperations publicOperations;
	private static PrivateApiOperations privateOperations;
	
	public ApiRequest() {
		if(publicOperations==null) {
			publicOperations=new PublicApiOperations();
		}
		if(privateOperations==null) {
			privateOperations=new PrivateApiOperations();
		}
		
	}
	
	
	public Date getDateServer() throws IOException {
		URL url = new URL(baseUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		return new Date(con.getHeaderFieldDate("date", new Date().getTime()));
		
	}
	private String publicOperation(String operation) throws IOException, BadResponseException {
		
		StringBuilder result = new StringBuilder();
		URL url = new URL(baseUrl+operation);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("GET");
		
		int responseCode=con.getResponseCode();
		
		if(responseCode!=HttpURLConnection.HTTP_OK){
			throw new BadResponseException(responseCode);
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
		
	}
	
	
	public String publicTicker(BitStampCurrencyPairs currencyPair) throws IOException, BadResponseException {
		return publicOperation(String.format(publicOperations.TICKER,currencyPair.toString()));
	}
	
	
	public String publicOrderBook(BitStampCurrencyPairs currencyPair) throws IOException, BadResponseException {
		return publicOperation(String.format(publicOperations.ORDER_BOOK, currencyPair.toString()));
	}
	
	public String publicTransactionsLastHour(BitStampCurrencyPairs currencyPair) throws IOException, BadResponseException {
		String operation=String.format(publicOperations.TRANSACTIONS_DEFAULT, currencyPair.toString());
		return publicOperation(operation);
	}
	
	public String publicTransactionsLastDay(BitStampCurrencyPairs currencyPair) throws IOException, BadResponseException {
		return publicTransactions(currencyPair, "day");
	}
	public String publicTransactionsLastMinute(BitStampCurrencyPairs currencyPair) throws IOException, BadResponseException {
		return publicTransactions(currencyPair, "minute");
	}
	
	public String publicConvRateEurUsd() throws IOException, BadResponseException {
		return publicOperation(publicOperations.CONV_RATE_EUR_USD);
	}
	
	private String publicTransactions(BitStampCurrencyPairs currencyPair, String time) throws IOException, BadResponseException {
		String operation=String.format(publicOperations.TRANSACTIONS, currencyPair.toString(),time);
		return publicOperation(operation);
	}
	
	
	private String privateOperation(String operation,String key, String signature,long nonce) throws IOException, BadResponseException {
		
		String postParameters  = "key="+key+"&signature="+signature+"&nonce="+nonce;
		return privateOperationCustomPostParameters(operation, postParameters);
		
		
	}
	private String privateOperationCustomPostParameters(String operation,String postParameters)throws IOException, BadResponseException {
		byte[] postData = postParameters.getBytes( StandardCharsets.UTF_8 );
		
		StringBuilder result = new StringBuilder();
		URL url = new URL(baseUrl+operation);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setDoOutput( true );
		con.setInstanceFollowRedirects( false );
		con.setRequestMethod( "POST" );
		con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
		con.setRequestProperty( "charset", "utf-8");
		con.setRequestProperty( "Content-Length", Integer.toString( postData.length ));
		con.setUseCaches( false );
		
		
		try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
			   wr.write( postData );
		}
		
		int responseCode=con.getResponseCode();
		if(responseCode!=HttpURLConnection.HTTP_OK){
			throw new BadResponseException(responseCode);
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}
	
	
	public String privateGetBalance(String key, String signature,long nonce) throws IOException, BadResponseException {
		
		return privateOperation(privateOperations.BALANCE_ALL, key, signature, nonce);
	}
	
	public String privateGetBalance(String key,String signature, long nonce, BitStampCurrencyPairs currency) throws IOException, BadResponseException {
		return privateOperation(String.format(privateOperations.BALANCE,currency.toString()), key, signature, nonce);
	}
	
	public String privateGetTransactions(String key,String signature, long nonce) throws IOException, BadResponseException {
		return privateOperation(privateOperations.USER_TRANSACTIONS_ALL, key, signature, nonce);
	}
	
	public String privateGetTransactions(String key,String signature, long nonce, BitStampCurrencyPairs currency) throws IOException, BadResponseException {
		return privateOperation(String.format(privateOperations.USER_TRANSACTIONS,currency.toString()), key, signature, nonce);
	}
	
	public String privateGetOpenOrders(String key,String signature, long nonce) throws IOException, BadResponseException {
		return privateOperation(privateOperations.LIST_OPEN_ORDERS_ALL, key, signature, nonce);
	}
	
	public String privateGetOpenOrders(String key,String signature, long nonce,BitStampCurrencyPairs currency) throws IOException, BadResponseException {
		return privateOperation(String.format(privateOperations.LIST_OPEN_ORDERS,currency.toString()), key, signature, nonce);
	}
	
	public String privateGetOrderStatus(String key,String signature, long nonce,int idOrder) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&id="+idOrder;
		return privateOperationCustomPostParameters(privateOperations.ORDER_STATUS, postParameters);
	}
	
	
	public String privateCancelOrder(String key, String signature, long nonce, int idOrder) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&id="+idOrder;
		return privateOperationCustomPostParameters(privateOperations.CANCEL_ORDER, postParameters);
	}
	
	public String privateCancelAllOrders(String key, String signature, long nonce) throws IOException, BadResponseException {
		return privateOperation(privateOperations.CANCEL_ALL_ORDERS, key, signature, nonce);
	}
	
	public String privateOpenBuyLimitOrder(String key,String signature, long nonce, BitStampCurrencyPairs currency, double amount,double price, boolean isDailyOrder ) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&amount="+amount+"&price="+price;
		if(isDailyOrder) {
			postParameters=postParameters+"&daily_order=True";
		}
		return privateOperationCustomPostParameters(String.format(privateOperations.BUY_LIMIT_ORDER, currency.toString()), postParameters);
	}
	
	public String privateOpenBuyLimitOrder(String key,String signature, long nonce, BitStampCurrencyPairs currency, double amount,double price, double limitPrice ) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&amount="+amount+"&price="+price+"&limit_price="+limitPrice;
		
		return privateOperationCustomPostParameters(String.format(privateOperations.BUY_LIMIT_ORDER, currency.toString()), postParameters);
	}
	
	public String privateOpenBuyMarketOrder(String key, String signature, long nonce, BitStampCurrencyPairs currency, double amount) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&amount="+amount;
		
		return privateOperationCustomPostParameters(String.format(privateOperations.BUY_MARKET_ORDER, currency.toString()), postParameters);
	}
	
	public String privateOpenSellLimitOrder(String key,String signature, long nonce, BitStampCurrencyPairs currency, double amount,double price, double limitPrice ) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&amount="+amount+"&price="+price+"&limit_price="+limitPrice;
		
		return privateOperationCustomPostParameters(String.format(privateOperations.SELL_LIMIT_ORDER, currency.toString()), postParameters);
	}
	
	public String privateOpenSellLimitOrder(String key,String signature, long nonce, BitStampCurrencyPairs currency, double amount,double price, boolean isDailyOrder ) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&amount="+amount+"&price="+price;
		if(isDailyOrder) {
			postParameters=postParameters+"&daily_order=True";
		}
		return privateOperationCustomPostParameters(String.format(privateOperations.SELL_LIMIT_ORDER, currency.toString()), postParameters);
	}
	
	public String privateOpenSellMarketOrder(String key, String signature, long nonce, BitStampCurrencyPairs currency, double amount) throws IOException, BadResponseException {
		String postParameters="key="+key+"&signature="+signature+"&nonce="+nonce+"&amount="+amount;
		
		return privateOperationCustomPostParameters(String.format(privateOperations.SELL_MARKET_ORDER, currency.toString()), postParameters);
	}
	
	
	
	
	
	
	
	
	
	private class PublicApiOperations{
		
		//ticker/currencypair
		private final String TICKER="v2/ticker/%s/";
		
		//order_book/{currency_pair}/
		private final String ORDER_BOOK="v2/order_book/%s/";
		
		//transactions/{currency_pair}/ - default time 1 hour
		private final String TRANSACTIONS_DEFAULT="v2/transactions/%s/";
		private final String TRANSACTIONS="v2/transactions/%s/?time=%s";
		
		
		private final String CONV_RATE_EUR_USD="eur_usd/";
		
	}
	
	private class PrivateApiOperations{
		
		
		private final String BALANCE_ALL="v2/balance/";
		
		//balance/{currency_pair}/
		private final String BALANCE="v2/balance/%s/";
		
		
		private final String USER_TRANSACTIONS_ALL="v2/user_transactions/";
		
		//user_transactions/{currency_pair}/
		private final String USER_TRANSACTIONS="v2/user_transactions/%s/";
		
		
		private final String LIST_OPEN_ORDERS_ALL="v2/open_orders/all/";
		
		//open_orders/{currency_pair}
		private final String LIST_OPEN_ORDERS="v2/open_orders/%s/";
		
		private final String ORDER_STATUS="order_status/";
		
		private final String CANCEL_ORDER="v2/cancel_order/";
		
		private final String CANCEL_ALL_ORDERS="cancel_all_orders/";
		
		
		//buy/{currency_pair}/
		private final String BUY_LIMIT_ORDER="v2/buy/%s/";
		
		//buy/market/{currency_pair}/
		private final String BUY_MARKET_ORDER="v2/buy/market/%s/";
		
		//sell/{currency_pair}/
		private final String SELL_LIMIT_ORDER="v2/sell/%s/";
		
		//sell/market/{currency_pair}/
		private final String SELL_MARKET_ORDER="v2/sell/market/%s/";
		
		
		
		
	}

}
