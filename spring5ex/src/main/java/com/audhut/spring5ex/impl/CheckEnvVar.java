package com.audhut.spring5ex.impl;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CheckEnvVar implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //here you can check if any env variable exist
        Environment env = conditionContext.getEnvironment();
        boolean envvar = env.containsProperty("JAVA_HOME");
        System.out.println("env var is " + envvar);
        //if false is returned bean will not be created
        return true;
    }
}
