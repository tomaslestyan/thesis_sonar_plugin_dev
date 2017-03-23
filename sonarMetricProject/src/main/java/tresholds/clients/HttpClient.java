/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds.clients;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Tomas Lestyan
 */
@Deprecated
public class HttpClient {

	private static final String URL = "http://tom.v2n.cz/diplomka/public/index.php/api/record/create";
	/** The logger object */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param json
 	 * @return the value
	 */
	public int getValue(String json) {
		return 0;
	}

	/**
	 * @param json
	 * @return boolean
	 */
	public boolean postValue(String json) {
		return postValue(json, 3, 1000);
	}

	/**
	 * @param json
	 * @return boolean
	 */
	public boolean postValueNoRetry(String json) {
		return postValue(json, 1, 0);
	}

	/**
	 * @param json
	 * @param retry
	 * @param timeout
	 * @return
	 */
	public boolean postValue(String json, int retry, int timeout) {
		if (retry == 0) return false;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(URL);
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				log.warn("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			httpClient.getConnectionManager().shutdown();
			httpClient.close();
		} catch (MalformedURLException e) {
			log.warn("Http error", e);
		} catch (IOException e) {
			log.warn("Http error", e);
		}
		// stop immediatelly if goole not responds
		return canConnect() && postValue(json, retry - 1, timeout);//TODO get error from response
	}

	/**
	 * @return boolean
	 */
	private boolean canConnect(){
		 HttpURLConnection connection = null;
		    try {
		        URL u = new URL("http://www.google.com/");
		        connection = (HttpURLConnection) u.openConnection();
		        connection.setRequestMethod("HEAD");
		        int code = connection.getResponseCode();
		        log.warn("Server not reachable: " + code);
		    } catch (MalformedURLException e) {
		    	log.warn("Http error", e);
		    } catch (IOException e) {
		    	log.warn("Http error", e);
		    }
			return false;
	}
}
