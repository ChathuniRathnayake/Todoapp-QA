/*package com.finalproject.todoapp;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")  // "features" folder under src/test/resources
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.finalproject.todoapp.steps")
public class CucumberTest {
}*/

package com.finalproject.todoapp;

import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber
public class CucumberTest {
}

