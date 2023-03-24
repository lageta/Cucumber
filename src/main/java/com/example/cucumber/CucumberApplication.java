package com.example.cucumber;

import io.cucumber.gherkin.GherkinParser;
import io.cucumber.messages.types.*;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SpringBootApplication
public class CucumberApplication {

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SpringApplication.run(CucumberApplication.class, args);


        // Analyser un fichier Gherkin et ressortir la méthode associée
        Path path = Paths.get("./src/main/java/com/example/cucumber/example.feature");
        GherkinParser parser = GherkinParser.builder().build();
        Envelope envelope = parser.parse(path).filter(e -> e.getPickle().isPresent()).findFirst().get();
        Pickle pickle = envelope.getPickle().get();
        List<PickleStep> steps = pickle.getSteps();

            PickleStep pickleStep = pickle.getSteps().get(0);
            String methodName = pickleStep.getText().replaceAll(" ", "");
            Pattern pattern = Pattern.compile("<([^>]*)>");
            Matcher matcher = pattern.matcher(methodName);
            String arg = "";
            while (matcher.find()) {
                arg = matcher.group(1);
                methodName = methodName.replace("<" + arg + ">", "");
            }
            Method method = ExampleStepDefinitions.class.getMethod(methodName, String.class);
            method.invoke(new ExampleStepDefinitions(), arg);

        // Run un fichier Gherkin avec Cucumber + Junit
        Result result = new JUnitCore().run(RunWithJunit.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

    }



}
