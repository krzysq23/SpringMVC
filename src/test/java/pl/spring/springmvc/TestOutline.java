package pl.spring.springmvc;

import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate/testoutline.feature")
public class TestOutline {
}
