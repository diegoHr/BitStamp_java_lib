package com.diego_hernando.bitStamp_java_lib;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

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
	
	public String getBalance() throws IOException, BadResponseException {
		long nonce=new Date().getTime();
		String signature=getSignature(nonce);
		return new ApiRequest().privateGetBalance(key, signature, nonce);
	}
	
	
	public String getSignature(long nonce) {
		String message=nonce+id+key;
		
		
		String signature="";
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		    SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		    sha256_HMAC.init(secret_key);
		    byte[] hash = sha256_HMAC.doFinal(message.getBytes());
		    signature = DatatypeConverter.printHexBinary(hash).toUpperCase();
		    
			
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
	
	
	
	
	
	
	
	
	

	
	

}
