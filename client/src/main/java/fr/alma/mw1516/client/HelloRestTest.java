package fr.alma.mw1516.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adrien Garandel, Nicolas Brondin
 */
public class HelloRestTest {

    public static void main(String[] args) throws Exception {
        authTest();

        tokenTest();

    }

    private static void authTest() throws IOException {
        // Sans proxy
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Définition de l'URL d'appel
        HttpPost request = new HttpPost("http://localhost:8080/ame-services-rest/service/auth");

        request.setHeader("accept", "application/json");
        request.setHeader("Api-Key","reQSFGgFSbgc54uyhjg35hgf23vJhg432JNkjH");

        { // Valid IMEI
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", "444786932124868"));
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
            request.setEntity(encodedFormEntity);

            System.out.println("== Valid IMEI =================");
            execute(httpClient, request);
            System.out.println("===============================");
        }

        { // Not Found IMEI
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", "444786932124865"));
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
            request.setEntity(encodedFormEntity);

            System.out.println("== Not Found IMEI =============");
            execute(httpClient, request);
            System.out.println("===============================");
        }

        { // Malformed IMEI
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", "44478693212486"));
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
            request.setEntity(encodedFormEntity);

            System.out.println("== Malformed IMEI =============");
            execute(httpClient, request);
            System.out.println("===============================");
        }

        { // Api-Key invalid
            request.setHeader("Api-Key", "reQSFGgFSbgc54uyhjg35hgf23vNkjH");
            System.out.println("== Api-Key invalid ============");
            execute(httpClient, request);
            System.out.println("===============================");
        }
    }

    private static void tokenTest() {
        // Sans proxy
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Définition de l'URL d'appel
        HttpGet request = new HttpGet("http://localhost:8080/ame-services-rest/service/token");

        request.setHeader("accept", "application/json");
        request.setHeader("Api-Key","reQSFGgFSbgc54uyhjg35hgf23vJhg432JNkjH");

        { // Token valid
            request.setHeader("Authorization", "ca8b354a-2993-487c-9efc-dce0704c3ab4");
            System.out.println("== Token valid ================");
            execute(httpClient, request);
            System.out.println("===============================");
        }

        { // Token Invalid
            request.setHeader("Authorization", "ca8b354a-2993-487c-9efc-dce0704c3ab0");
            System.out.println("== Token invalid ==============");
            execute(httpClient, request);
            System.out.println("===============================");
        }

        { // Token Malformed
            request.setHeader("Authorization", "ca8b354a-2993-487c-9efc-dce0704c3");
            System.out.println("== Token malformed ============");
            execute(httpClient, request);
            System.out.println("===============================");
        }

        { // Api-Key invalid
            request.setHeader("Api-Key", "reQSFGgFSbgc54uyhjg35hgf23vNkjH");
            System.out.println("== Api-Key invalid ============");
            execute(httpClient, request);
            System.out.println("===============================");
        }
    }

    private static void execute(CloseableHttpClient httpClient, HttpRequestBase request) {
        // Execution de la requête
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);

            // Affichage des données de la réponse (statusCode + headers)
            System.out.println(response);

            // Récupération d'une valeur dans les headers
            Header header = response.getFirstHeader("Content-Type");
            System.out.println(header.getValue());

            // Affichage du contenu de la réponse

            System.out.println(getContextAsString(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
