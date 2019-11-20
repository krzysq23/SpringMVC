package pl.spring.springmvc;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate/test.feature")
public class SpringMvcApplicationTest {

    private static WireMockServer wireMockServer = new WireMockServer();

    @BeforeClass
    public static void setUp() throws Exception {
/*        wireMockServer.start();
        configureFor("localhost", 8082);
        stubFor(
                get(urlEqualTo("/api/hello"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("Hello World!")));

        stubFor(

                get(urlEqualTo("/api/findByIsbn-ISBN84723423"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"title\": \"Pan Tadeusz\", author: \"Adam Mickiewicz\" }")));*/

    }

    @AfterClass
    public static void tearDown() throws Exception {
        /*wireMockServer.stop();*/
    }

}