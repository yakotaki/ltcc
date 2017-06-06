package com.bihang.api;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {

	public static void main(String[] args) throws IOException, JsonEOFException {

		String sURL0 = "https://openexchangerates.org/api/latest.json?app_id=3a920ead9f0241c2bfbbd0c87677181d&symbols=XAU";
		String sURL1 = "https://data.btcchina.com/data/ticker?market=ltccny";
		String sURL2 = "https://data.btcchina.com/data/ticker?market=btccny";
		String sURL3 = "http://api.huobi.com/staticmarket/ticker_ltc_json.js";
		String sURL4 = "http://api.huobi.com/usdmarket/ticker_btc_json.js";
		String sURL5 = "https://openexchangerates.org/api/latest.json?app_id=3a920ead9f0241c2bfbbd0c87677181d&base=USD&only_alternative=1";

		// Connect to the URL using java's native library
		URL url0 = new URL(sURL0);
		URL url1 = new URL(sURL1);
		URL url2 = new URL(sURL2);
		URL url3 = new URL(sURL3);
		URL url4 = new URL(sURL4);
		URL url5 = new URL(sURL5);

		ArrayList<ArrayList<Double>> values = new ArrayList<>();

		// loop
		
		long l = System.currentTimeMillis();
//		String csvFile = "/beta0_3" + l + ".csv";
		String csvFile = "/beta0_3.csv";
		FileWriter writer = new FileWriter(csvFile);

		int n = 0;
		
		while (n < 100000) {

			try {
				
				HttpURLConnection request0 = (HttpURLConnection) url0.openConnection();
				HttpURLConnection request1 = (HttpURLConnection) url1.openConnection();
				HttpURLConnection request2 = (HttpURLConnection) url2.openConnection();
				HttpURLConnection request3 = (HttpURLConnection) url3.openConnection();
				HttpURLConnection request4 = (HttpURLConnection) url4.openConnection();
				HttpURLConnection request5 = (HttpURLConnection) url5.openConnection();

				request0.connect();

				JsonParser jp = new JsonParser(); // from gson
				JsonElement root = jp.parse(new InputStreamReader((InputStream) request0.getContent()));
				JsonObject rootobj = root.getAsJsonObject();
				Double v0 = rootobj.get("rates").getAsJsonObject().get("XAU").getAsDouble();

				v0 = v0 * 200000;
				
				request0.disconnect();
				request1.connect();

				jp = new JsonParser();
				root = jp.parse(new InputStreamReader((InputStream) request1.getContent()));
				rootobj = root.getAsJsonObject();
				Double v1 = rootobj.get("ticker").getAsJsonObject().get("last").getAsDouble();
				Double v2 = rootobj.get("ticker").getAsJsonObject().get("vol").getAsDouble();

				v2 = v2/1000;

				request1.disconnect();
				request2.connect();

				jp = new JsonParser();
				root = jp.parse(new InputStreamReader((InputStream) request2.getContent()));
				rootobj = root.getAsJsonObject();
				Double v3 = rootobj.get("ticker").getAsJsonObject().get("last").getAsDouble();
				Double v4 = rootobj.get("ticker").getAsJsonObject().get("vol").getAsDouble();

				v3 = v3/100;
				v4 = v4/100;
				
				request2.disconnect();
				request3.connect();

				jp = new JsonParser();
				root = jp.parse(new InputStreamReader((InputStream) request3.getContent()));
				rootobj = root.getAsJsonObject();
				Double v5 = rootobj.get("ticker").getAsJsonObject().get("last").getAsDouble();
				Double v6 = rootobj.get("ticker").getAsJsonObject().get("vol").getAsDouble();

				v6 = v6/10000;
				
				request3.disconnect();
				request4.connect();

				jp = new JsonParser();
				root = jp.parse(new InputStreamReader((InputStream) request4.getContent()));
				rootobj = root.getAsJsonObject();
				Double v7 = rootobj.get("ticker").getAsJsonObject().get("last").getAsDouble();
				Double v8 = rootobj.get("ticker").getAsJsonObject().get("vol").getAsDouble();

				v7 = v7/10;
				v8 = v8*100;
				
				request4.disconnect();
				request5.connect();

				jp = new JsonParser(); // from gson
				root = jp.parse(new InputStreamReader((InputStream) request5.getContent()));
				rootobj = root.getAsJsonObject();

				Double v9 = rootobj.get("rates").getAsJsonObject().get("ETH").getAsDouble();
				Double v10 = rootobj.get("rates").getAsJsonObject().get("BTS").getAsDouble();
				Double v11 = rootobj.get("rates").getAsJsonObject().get("DASH").getAsDouble();
				Double v12 = rootobj.get("rates").getAsJsonObject().get("DOGE").getAsDouble();

				v9 = v9 *10000;
				v10 = v10*10;
				v11 = v11 *10000;
				
				
				request5.disconnect();

				CSVUtils.writeLine(writer, Arrays.asList(
						
						v0.toString() + "," + 
						v1.toString() + "," + 
						v2.toString() + "," + 
						v3.toString() + "," + 
						v4.toString() + "," + 
						v5.toString() + "," + 
						v6.toString() + "," + 
						v7.toString() + "," + 
						v8.toString() + "," + 
						v9.toString() + "," + 
						v10.toString() + "," + 
						v11.toString() + "," + 
						v12.toString() + "," ));

				writer.flush();

				try {
					Thread.currentThread().sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				n++;
				
				System.out.println(v0.toString() + "," + 
						v1.toString() + "," + 
						v2.toString() + "," + 
						v3.toString() + "," + 
						v4.toString() + "," + 
						v5.toString() + "," + 
						v6.toString() + "," + 
						v7.toString() + "," + 
						v8.toString() + "," + 
						v9.toString() + "," + 
						v10.toString() + "," + 
						v11.toString() + "," + 
						v12.toString() + "," );
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
		}
		writer.close();
	}
}