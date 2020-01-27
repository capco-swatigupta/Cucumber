package com.capco.steps;

import org.openqa.selenium.WebElement;
import com.capco.library.CommonActions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class F2_DepositSteps {

	CommonActions commonActions;

	String initailBalance = "";
	String updatedBalance = "";

	public F2_DepositSteps(CommonActions commonActions) {
		super();
		this.commonActions = commonActions;
	}

	@When("user click on {string} as deposit")
	public void user_click_on_as_deposit(String string) throws Exception {
		
		initailBalance = getBalance();
		commonActions.takesScreenshot("User click on deposit");
		commonActions.click(string);
		

	}

	@When("user enter {string} in {string}")
	public void user_enter_in(String string, String string2) {
		commonActions.enterText(string2, string);
		commonActions.takesScreenshot("when user enter amount in textfield");
	}

	@When("user click on {string}")
	public void user_click_on(String string) throws Exception {
		commonActions.takesScreenshot("when user click on deposit button");
		commonActions.click(string);
	
		updatedBalance = getBalance();

	}

	private String getBalance() {
		
		WebElement accType = commonActions.getElement("PrimaryBalance");
		if (accType != null) {
			commonActions.logPassStatus("Account Value : "+accType.getText());
			commonActions.takesScreenshot("Value for Account Type");
		} else {
			commonActions.logFailStatus("Account Value not matched");
		}
		return accType.getText();
	}
	
	@Then("verify the balance in account")
	public void verify_the_balance_in_account() {
		if (!updatedBalance.equals(initailBalance))
			commonActions.logPassStatus("Passed | Account Value matched");
		else
			commonActions.logFailStatus("Account Value failed");
	}


	@When("user select value {string} in  {string}")
	public void user_select_value_in(String string, String string2) {
		try {
			commonActions.click(string2);
			Thread.sleep(2000);
			commonActions.click(string);
			commonActions.takesScreenshot("when user select value for account type");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
