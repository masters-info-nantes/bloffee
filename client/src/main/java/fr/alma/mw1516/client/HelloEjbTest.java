package fr.alma.mw1516.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import fr.alma.mw1516.services.sample.Country;
import fr.alma.mw1516.services.sample.HelloRemote;

/**
 * @author Arnaud Thimel (Code Lutin)
 */
public class HelloEjbTest {

    public static void main(String[] args) throws Exception {
        Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        Context context = new InitialContext(jndiProperties);

        HelloRemote helloService = (HelloRemote) context.lookup("ejb:/ame-services-ejb-0.1-SNAPSHOT/HelloBean!fr.alma.mw1415.services.sample.HelloRemote");
        Country result = helloService.sayHello("France");
        System.out.println(result);
    }

}
