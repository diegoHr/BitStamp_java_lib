package com.diego_hernando.bitStamp_java_lib;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import com.diego_hernando.bitStamp_java_lib.exceptions.BadResponseException;

public class BitStampAPI {
	
	private String key;
	private String secret;
	private String id;

	
	
	
	public void setKey(String key) {
		this.key=key;
	}
	
	public void setSecret(String secret) {
		this.secret=secret;
	}
	public void setId(String id) {
		this.id=id;
	}
		
	
	public String getTicker(BitStampCurrencyPairs currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTicker(currencyPair);
	}
	public String getOrderBook(BitStampCurrencyPairs currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicOrderBook(currencyPair);
	}
	public String getTransactionsLastHour(BitStampCurrencyPairs currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTransactionsLastHour(currencyPair);
	}
	public String getTransactionsLastDay(BitStampCurrencyPairs currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTransactionsLastDay(currencyPair);
	}
	public String getTransactionsLastMinute(BitStampCurrencyPairs currencyPair) throws IOException,BadResponseException {
		return new ApiRequest().publicTransactionsLastMinute(currencyPair);
	}
	public String getConvRateEurUsd() throws IOException,BadResponseException {
		return new ApiRequest().publicConvRateEurUsd();
	}
	
	public String getBalance() throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetBalance(key, signature, nonce);
	}
	
	public String getBalance(BitStampCurrencyPairs currency) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetBalance(key, signature, nonce,currency);
	}
	
	public String getMyTransactions() throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetTransactions(key, signature, nonce);
	}
	
	public String getMyTransactions(BitStampCurrencyPairs currency) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetTransactions(key, signature, nonce,currency);
	}
	
	public String getOpenOrders() throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetOpenOrders(key, signature, nonce);
	}
	
	public String getOpenOrders(BitStampCurrencyPairs currency) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetOpenOrders(key, signature, nonce, currency);
	}
	
	public String getOrderStatus(int idOrder) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetOrderStatus(key, signature, nonce, idOrder);
	}
	
	public String cancelOrder(int idOrder) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateCancelOrder(key, signature, nonce, idOrder);
	}
	
	public String cancelAllOrders() throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateCancelAllOrders(key, signature, nonce);
	}
	
	public String openBuyLimitOrder(BitStampCurrencyPairs currency, double amount,double price, boolean isDailyOrder ) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateOpenBuyLimitOrder(key, signature, nonce, currency, amount, price, isDailyOrder);
	}
	
	public String openBuyLimitOrder(BitStampCurrencyPairs currency, double amount,double price,double limitPrice ) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateOpenBuyLimitOrder(key, signature, nonce, currency, amount, price, limitPrice);
	}
	
	public String openBuyMarketOrder(BitStampCurrencyPairs currency, double amount) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateOpenBuyMarketOrder(key, signature, nonce, currency, amount);
		
	}
	
	public String openSellLimitOrder(BitStampCurrencyPairs currency, double amount,double price, boolean isDailyOrder ) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateOpenSellLimitOrder(key, signature, nonce, currency, amount, price, isDailyOrder);
	}
	
	public String openSellLimitOrder(BitStampCurrencyPairs currency, double amount,double price,double limitPrice ) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateOpenSellLimitOrder(key, signature, nonce, currency, amount, price, limitPrice);
	}
	
	public String openSellMarketOrder(BitStampCurrencyPairs currency, double amount) throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateOpenSellMarketOrder(key, signature, nonce, currency, amount);
		
	}
	
	public Date getDateServer() throws IOException {
		return new ApiRequest().getDateServer();
	}
	
	
	
	
	
	
	public String getSignature(long nonce) {
		String message=nonce+id+key;
		
		
		String signature="";
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		    SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		    sha256_HMAC.init(secret_key);
		    byte[] hash = sha256_HMAC.doFinal(message.getBytes());
		    signature = byteArrayToHex(hash).toUpperCase();
		    
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return signature;
		
	}
	private String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b));
		   return sb.toString();
		}
	
	
	
	
	
	
	
	
	

	
	

}
