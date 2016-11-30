package cn.kellygod.schoolclient.connection;

import java.security.KeyStore;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@SuppressWarnings({ "deprecation" })
public class SSLClient extends DefaultHttpClient {
    private static DefaultHttpClient mDefaultHttpClient=null;

    /**
     *
     *  非线程安全
     *
     * */
	public static HttpClient getNewHttpClient() {
        if(mDefaultHttpClient==null) {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                // HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                //HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                mDefaultHttpClient=new DefaultHttpClient(ccm, params);
                //重定向
                mDefaultHttpClient.setRedirectHandler(new DefaultRedirectHandler(){
                    @Override
                    public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
                        boolean isRedirect = super.isRedirectRequested(response, context);
                        if (!isRedirect) {
                            int responseCode = response.getStatusLine().getStatusCode();
                            if (responseCode == 301 || responseCode == 302) {
                                return true;
                            }
                        }
                        return isRedirect;
                    }
                });
            } catch (Exception e) {
                mDefaultHttpClient= new DefaultHttpClient();
            }
        }
        return mDefaultHttpClient;
    }  
}
