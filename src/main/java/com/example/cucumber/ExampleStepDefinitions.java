package com.example.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class ExampleStepDefinitions {


    public void todayIs(String day) {
        System.out.println("Today is " + day);
    }
    @Given("today is {string}")
    public void today_is(String day) {
        System.out.println("Today is " + day + " Junit");
    }

    @When("I do something")
    public void doSomething() {
        System.out.println("Doing something");
    }

    @Then("Something should happen")
    public void somethingShouldHappen() {
        System.out.println("Something happened");
    }
}
