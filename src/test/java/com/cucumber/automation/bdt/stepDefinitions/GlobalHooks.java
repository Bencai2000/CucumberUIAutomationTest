package com.cucumber.automation.bdt.stepDefinitions;

import org.apache.log4j.Logger;

import com.cucumber.automation.utils.GithubTestBase;

import cucumber.api.java.Before;

public class GlobalHooks extends GithubTestBase{
	private static Logger logger = Logger.getLogger(GlobalHooks.class);
	private static boolean dunit = false;
	
	@Before
    public void beforeAll() {
		
		if(!dunit) {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	dunit = true;
            		logger.info("Global Hooks After ALL");
//            		createScreenshotAlways();
            		
            		if(teardown()){
            			logger.info("Shut Down - Successfully");
            		}
            		logger.info("After Tear Down - " + dunit);
                }
            });
            
            logger.info("Before Initialize Github Test");
            initialGithubTest();
            logger.info("After Initialize Github Test");
            dunit = true;
            
        }
    }
	
}
