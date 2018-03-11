package com.diego_hernando.bitStamp_java_lib;

public interface WebsocketErrorListener{

	
	public void onError(String error,String errorCode,ApiWebsocketTransactions webSocket);
	
	
}
