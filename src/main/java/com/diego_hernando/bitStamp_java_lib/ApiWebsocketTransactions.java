package com.diego_hernando.bitStamp_java_lib;

import java.util.HashMap;


import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;


public class ApiWebsocketTransactions {
	
	private final static String KEY="de504dc5763aeef9ff52";
	private final static String NAME_CHANNEL="live_trades_";
	private final static String EVENT="trade";
	private Pusher wbSocket;
	private HashMap<BitStampCurrencyPairs,Channel> mapChannels;
	private boolean connected=false;
	private WebsocketErrorListener wbSocketErrorListener;
	private WebsocketTradeDoneListener wbTradeDoneListener;
	
	
	protected ApiWebsocketTransactions(WebsocketErrorListener websocketErrorListener, WebsocketTradeDoneListener websocketTradeListener) {
		this.wbSocketErrorListener=websocketErrorListener;
		this.wbTradeDoneListener=websocketTradeListener;
		PusherOptions options=new PusherOptions().setEncrypted(true);
		wbSocket=new Pusher(KEY,options);
		mapChannels=new HashMap<BitStampCurrencyPairs,Channel>();	
	}
	
	public void disconnect() {
		connected=false;
		wbSocket.disconnect();
	}
	
	public void connect() {
		connected=true;
		wbSocket.connect(new ConnectionEventListener() {
		    @Override
		    public void onConnectionStateChange(ConnectionStateChange change) {
		        if(change.getCurrentState().equals(ConnectionState.DISCONNECTED) && connected){
		        	wbSocket.connect();	
		        }
		    }
		    @Override
		    public void onError(String message, String code, Exception e) {
		        wbSocketErrorListener.onError(message, code, ApiWebsocketTransactions.this);
		    }
		});	
	}
	
	public void openAllChannels() {
		for (BitStampCurrencyPairs pair:BitStampCurrencyPairs.values()) {
			openChannel(pair);
		}
	}
	
	public void openChannel(final BitStampCurrencyPairs pair) {
		Channel channel=wbSocket.subscribe(NAME_CHANNEL+pair);
		
		channel.bind(EVENT, new SubscriptionEventListener() {
		    @Override
		    public void onEvent(String channel, String event, String data) {
		        wbTradeDoneListener.onTradeDone(data, pair);
		    }
		});
		mapChannels.put(pair,channel );
	}
	

}
