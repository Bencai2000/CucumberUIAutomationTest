package com.tab.qa.framework.core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.cucumber.automation.utils.GithubTestBase;
import com.tab.qa.framework.utils.Constants;


/**
 * All Page Object classes must be extended from this base class
 */

public class PageBase extends SeleniumBase{

	public boolean waitForClickableElementById(String elementId) {
//		logger.info(String.format("waitForClickableElementById(%s)", elementId));
		return waitForClickableElementById(elementId, Constants.TIME_OUT);
	}
	
	public boolean waitForClickableElementById(String elementId, int timeoutSeconds) {
		//logger.info(String.format("waitForClickableElementById(%s)", elementId));
		return waitForClickableElement(By.id(elementId), timeoutSeconds);
	}
	
	public boolean waitForClickableElement(By locator) {
		//logger.info(String.format("waitForClickableElement(%s)", locator));		
		return waitForClickableElement(locator, Constants.TIME_OUT);
	}
	
	public boolean waitForClickableElement(By locator, int timeoutSeconds) {
		//logger.info(String.format("waitForClickableElement(%s)", locator));
		
		boolean clickable = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds, 200);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			clickable = true;
//			logger.info("The element is clickable >> " + locator);
		} catch (Exception e) {
//			logger.info(e.getMessage());
			e.printStackTrace();
		}	
		return clickable;
	}
	
	public boolean waitForClickableElement(WebElement element) {
		//logger.info(String.format("waitForClickableElement(%s)", element));		
		return waitForClickableElement(element, Constants.TIME_OUT);
	}
	
	public boolean waitForClickableElement(WebElement element, int timeoutSeconds) {
		//logger.info(String.format("waitForClickableElement(%s)", element));
		
		boolean clickable = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds, 200);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			clickable = true;
//			logger.info("The element is clickable >> "+ element);
		} catch (Exception e) {
//			logger.info(e.getMessage());
			e.printStackTrace();
		}	
		return clickable;
	}
	
	public WebElement waitForClickable(WebElement element) {
		//logger.info(String.format("waitForClickable(%s)", element));
		
		WebElement elementToClick = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  Constants.TIME_OUT, 200);
			elementToClick = wait.until(ExpectedConditions.elementToBeClickable(element));
//			logger.info("The element is clickable >> "+ element);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.info(e.getMessage());
		}	
		return elementToClick;
	}
	
	public boolean waitForPageElement(By locator) {
		//logger.info(String.format("waitForPageElement(%s)", locator));	
		return waitForVisibilityOfElement(locator, Constants.TIME_OUT) != null ? true : false;
	}
	
	public void waitForElementPresent(final By by, int timeoutSeconds){ 
		WebDriverWait wait = (WebDriverWait)new WebDriverWait(getDriver(), timeoutSeconds)
		                  .ignoring(StaleElementReferenceException.class); 
		wait.until(new ExpectedCondition<Boolean>(){ 
//	 		@Override 
	 		public Boolean apply(WebDriver webDriver) { 
	 		WebElement element = webDriver.findElement(by); 
	 		return element != null && element.isDisplayed(); 
	 		} 
	 	}); 
	}
	
	public void refresh() {
		getDriver().navigate().refresh();
	}
	
	public void waitAndClick(By by) {
		//logger.info(String.format("waitAndClick(%s)", by));
		
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  Constants.TIME_OUT);
			WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(by));
			clickable.click();
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void waitAndClick(WebElement element) {
		//logger.info(String.format("waitAndClick(%s)", element));
		
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  Constants.TIME_OUT);
			WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(element));
			clickable.click();
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void clearAndInput(By locator, String text) {
		//logger.info(String.format("clearAndInput(%s)", text));

		clearAndInput(getDriver().findElement(locator), text);
	}
	
	public void clearAndInput(WebElement element, String text) {
		//logger.info(String.format("clearAndInput(%s, %s)", element, text));

		try {
			element.clear();
			element.sendKeys(text + "\t");
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
		
	protected Select getSelectOptions(WebElement element) {
		//logger.info(String.format("getSelectOptions(%s)", element));
		
		Select options = null;
	    try {
	    	options = new Select(element);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
	    return options;
	}
	
	public void clearAndPaste(WebElement element, String text) {
		//logger.info(String.format("clearAndPaste(%s, %s)", element, text));
		
		try {
			element.clear();
			
			StringSelection stringSelection = new StringSelection (text);
			Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
			clpbrd.setContents (stringSelection, null);		
			
			System.out.println(clpbrd.getContents(text));
			
			element.sendKeys(Keys.chord(Keys.CONTROL, "v"));			
			
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void hover(WebElement element) throws InterruptedException
    {
		sleepms(200);
        Actions action = new Actions(getDriver());
        //move to the element to hover
        action.moveToElement(element).build().perform();
        sleepms(500);
    }
	
	public void doubleClick(WebElement element) throws InterruptedException
    {
		Actions action = new Actions(getDriver());
        action.doubleClick(element).build().perform();
        sleepms(200);
    }
	
	public void contextMenuClick(WebElement performRightClickOn) {
		Actions action = new Actions(getDriver());
//		action.moveToElement(performRightClickOn).build();
//		action.contextClick(performRightClickOn).build();  // this will perform right click 
		action.moveToElement(performRightClickOn).contextClick(performRightClickOn).build().perform();
	}
	
	public WebElement waitForPresenceOfElement(By locator) {
		//logger.info(String.format("waitForPresenceOfElement(%s)", locator));
		
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  Constants.TIME_OUT);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			//element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public WebElement waitForPresenceOfElement(By locator, int seconds) {
		//logger.info(String.format("waitForPresenceOfElement(%s, %s)", locator, seconds));
		
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  seconds);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			//element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	
	public Boolean waitTillDisplay(final By locator){
	    WebDriverWait wait = new WebDriverWait(getDriver(), Constants.TIME_OUT); 
	    Boolean displayed = wait.until(new ExpectedCondition<Boolean>() {
	    	public Boolean apply(WebDriver driver) {
	    		return driver.findElement(locator).isDisplayed();      						}
        	});
        return displayed;
	}
	
	public Boolean waitTillEnabled(final By locator){
	    WebDriverWait wait = new WebDriverWait(getDriver(), Constants.TIME_OUT); 
	    Boolean enabled = wait.until(new ExpectedCondition<Boolean>() {
	    	public Boolean apply(WebDriver driver) {
	    		return driver.findElement(locator).isEnabled();      				}
        	});
        return enabled;
	}	
	
	public WebElement waitForVisibilityOfElement(By locator, int timeoutSeconds) {
		//logger.info(String.format("waitForVisibilityOfElement(%s)", locator));
		
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return element;
	}
	
	public WebElement waitForVisibilityOf(WebElement element) {
		//logger.info(String.format("waitForVisibilityOf(%s)", element));
		
		WebElement visibleElement = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  Constants.TIME_OUT);
			visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return visibleElement;
	}
	
	public boolean waitForInvisibilityOf(By locator, int timeoutSeconds) {
		//logger.info(String.format("waitForInvisibilityOf(%s)", locator));
		
		boolean invisibility = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds);
			invisibility = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		return invisibility;
	}
	
	
	public boolean waitUntilModalScreenDisappear() {
		return waitForInvisibilityOf(By.className("ModalScreen"), Constants.TIME_OUT);	
	}
	
	public boolean waitForStalenessOf(WebElement element, int timeoutSeconds) {
		//logger.info(String.format("waitForStalenessOf(%s)", element));
		
		boolean staleness = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds);
			staleness = wait.until(ExpectedConditions.stalenessOf(element));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return staleness;
	}
	
	public boolean waitForDialog(String fragmentOfTitle) {
		//logger.info(String.format("waitForDialog(%s)", fragmentOfTitle));	
		boolean is_displayed = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  Constants.TIME_OUT);
			is_displayed = wait.until(ExpectedConditions.titleContains(fragmentOfTitle));
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return is_displayed;
	}
	
	public String getText(WebElement element) {
		//Chen
		//logger.info(String.format("getText(%s)", element));
		
		String text = element.getText();
		if (text.isEmpty()) {
            text = element.getAttribute("outerHTML");
        }
		return text;	
	}
	
	@SuppressWarnings("unused")
	private void setValueUsingJS(WebElement element, String value) {
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].value = arguments[1]", element, value);
    }
	
	protected void scrollIntoViewUsingJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	protected WebElement getParent(WebElement child) {
		WebElement parent = child.findElement(By.xpath(".."));
		return parent;
	}
	
	/**
	 * Switches to the element that currently has focus within the page.
	 * @return Currently focused element or the body element if this cannot be detected
	 */
	protected WebElement getFocusedElement() {
		WebElement element = getDriver().switchTo().activeElement();
		return element;
	}

    /**
     * @param e --> Current Web Element
     * @param ancestor --> Ancestor element tagname e.g. div will select all ancestor divs and return first ancestor
     * @param predicate --> used to find a specific node or a node that contains a specific value e.g. @class='builder_input'
     * @return first ancestor element found
     */
    public WebElement getAncestor(WebElement e, String ancestor, String predicate)
    {
        //predicate = (!predicate.isEmpty()) ? predicate : "[" + predicate + "]");
        try
        {
            return e.findElement(By.xpath("ancestor::" + ancestor + predicate));
        }
        catch (NoSuchElementException ex)
        {
            return null;
        }
    }
	
	
	public WebElement getMatchingElement(List<WebElement> elements, String nameToMatch) {
		WebElement matchingElement = null;
		for(WebElement element : elements) {
			if(getText(element).equals(nameToMatch)) {
				matchingElement = element;
				break;
			}
		}
		return matchingElement;
	}
	
    public WebDriver switchToWindowDriver() {	
		WebDriver popup = null;
		String windowsHandle = getDriver().getWindowHandle();		
		popup = getDriver().switchTo().window(windowsHandle);			
		return popup;
	}
    
	public void switchToMainFrameOnPage() {
		switchToWindowDriver().switchTo().frame(getDriver().findElement(By.cssSelector("#iFMain")));
	}
	
}
