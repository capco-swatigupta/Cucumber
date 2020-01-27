package com.capco.steps;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.capco.library.CommonActions;

import cucumber.api.java.en.Then;

public class F1_LoginSteps {
	
	CommonActions commonActions;
	
	
	public F1_LoginSteps(CommonActions commonActions) {
		super();
		this.commonActions = commonActions;
	}
	
	@Then("verify user logs in successfully")
	public void verify_user_logs_in_successfully() {
		WebElement check=commonActions.getElement("Home");
		Assert.assertTrue(check.isDisplayed());
	}
   
	@Then("verify the invalid login")
	public void verify_the_invalid_login() {
		String capturePass="";
		if(capturePass.equals(commonActions.getValueFromJson("pwd")))
		commonActions.logFailStatus("Please enter a password");
		else
			commonActions.logPassStatus("Login Successfully");
	}



}
