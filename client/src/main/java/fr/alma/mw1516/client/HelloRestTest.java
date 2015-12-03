package fr.alma.mw1516.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
public class HelloRestTest {

    public static void main(String[] args) throws Exception {

        // Sans proxy
        CloseableHttpClient httpClient = HttpClients.createDefault();




//        // Avec proxy
//        NTCredentials ntCreds = new NTCredentials("", null,"", "" );
//
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials( new AuthScope("proxy.ensinfo.sciences.univ-nantes.prive",3128), ntCreds );
//        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
//
//        clientBuilder.useSystemProperties();
//        clientBuilder.setProxy(new HttpHost("proxy.ensinfo.sciences.univ-nantes.prive", 3128));
//        clientBuilder.setDefaultCredentialsProvider(credsProvider);
//        clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
//
//        CloseableHttpClient httpClient = clientBuilder.build();


        
        // Définition de l'URL d'appel
        HttpPost request = new HttpPost("http://localhost:8080/ame-services-rest/service/sample/sayHello");

        request.setHeader("accept", "application/json");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", "Whatever"));
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
        request.setEntity(encodedFormEntity);

        // Execution de la requête
        CloseableHttpResponse response = httpClient.execute(request);

        // Affichage des données de la réponse (statusCode + headers)
        System.out.println(response);

        // Récupération d'une valeur dans les headers
        Header header = response.getFirstHeader("Content-Type");
        System.out.println(header.getValue());

        // Affichage du contenu de la réponse
        System.out.println(getContextAsString(response));
    }
//
//    public static void main(String[] args) throws Exception {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        HttpPost request = new HttpPost("http://localhost:8080/ame-services-rest-0.1-SNAPSHOT/service/sample/sayHello");
//
//        request.setHeader("accept", "application/json");
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("name", "France"));
//        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
//        request.setEntity(encodedFormEntity);
//        CloseableHttpResponse response = httpClient.execute(request);
//
//        System.out.println(response);
//        System.out.println(getContextAsString(response));
//    }

    protected static String getContextAsString(HttpResponse response) throws IOException {

        StringWriter writer = new StringWriter();
        InputStream inputStream = response.getEntity().getContent();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } finally {
            inputStream.close();
        }
        return writer.toString();
    }

}
