package com.gkatzioura.spring.aop.aspect;

import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * Created by gkatzioura on 5/28/16.
 */
@Aspect
@Component("SampleServiceAspect")
@ConditionalOnProperty(name="audit.kafka.service.enable",
havingValue="true")
public class SampleServiceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleServiceAspect.class);
//
//    @Before("execution(* com.gkatzioura.spring.aop.service.SampleService.createSample (java.lang.String)) && args(sampleName)")
//    public void beforeSampleCreation(String sampleName) {
//
//        System.out.println("A request was issued for a sample name: "+sampleName);
//    }

    @Around("execution(* com.gkatzioura.spring.aop.controller.SampleController.* (..))")
    public Object aroundSampleCreation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("A request was issued for a sample name:= ");
//        enterMethod(proceedingJoinPoint);
        String paramters = getAuditParameters(proceedingJoinPoint);
        System.out.println("-------------finish-------"+paramters+"===");
//        sampleName = sampleName+"!";

//        Sample sample = (Sample) proceedingJoinPoint.proceed(new Object[] {sampleName});
//        sample.setName(sample.getName().toUpperCase());

        return null;
    }
    private void enterMethod(ProceedingJoinPoint joinPoint) {
    	System.out.println("-=-=-=-=-=--");
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        Class<?> clz = signature.getDeclaringType();
        String methodName = signature.getName();
        String[] paramNames = signature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();

        StringBuilder builder = new StringBuilder("\n------------------------@DebugLog Enter---------------------------------\n");
       
        builder.append(methodName).append(":\n");
        for (int i = 0; i < paramValues.length; i++) {
        	System.out.println("param "+paramNames[i]+ "value "+paramValues[i]);
            builder.append(paramNames[i])
                    .append("=")
                    .append(paramValues[i])
                    .append("\n");
        }
        builder.append("\n------------------------@DebugLog Enter end---------------------------------\n");

        System.out.println("value of log "+builder.toString());
        //        Log.d(clz.getSimpleName(), builder.toString());
    }

    
    
    
	private String getAuditParameters(ProceedingJoinPoint pjp) {
		String var = pjp.getSignature().toLongString();
		String sub = var.substring((var.indexOf('(') + 1), var.indexOf(')'));
		int paramterCount = 0;
		String[] s = null;
		if (sub != null && !sub.trim().isEmpty()) {
			s = sub.split(",");
		}
		StringBuilder parameters = new StringBuilder();

		try {
			int count = 0;
			// Start New Code old updated
			for (Object object : pjp.getArgs()) {
				System.out.println("start parameter iterate");
				Boolean auditFlag = false;
				Class cs = null;
				try {
					cs = Class.forName(s[paramterCount].trim());
					paramterCount++;
				} catch (ClassNotFoundException e1) {
					System.out.println("Exception in getting class name "+ e1.getMessage());
				}

				java.lang.reflect.Method[] m = cs.getMethods();
				MethodSignature sig = (MethodSignature) pjp.getSignature();
				String[] name = sig.getParameterNames();
				if (cs.getName().equalsIgnoreCase("java.lang.String") || cs.getName().equalsIgnoreCase("java.lang.Integer")
						|| cs.getName().equalsIgnoreCase("java.lang.Double")
						|| cs.getName().equalsIgnoreCase("java.lang.Long")
						|| cs.getName().equalsIgnoreCase("java.lang.Float")
						|| cs.getName().equalsIgnoreCase("java.lang.Boolean")) {
					if (name != null) {
						parameters.append(name[count] + " " + ":"
								+ " " + (object != null ? object.toString() : object)
								+ " " + " ");
						auditFlag = true;
					}
				} else {
					System.out.println("start wrapper class and objects auditing");
					if (object != null) {
						for (java.lang.reflect.Method method : m) {
							String methodName = method.getName();
							if (!methodName.endsWith("Password") && !methodName.endsWith("Identifier")
									&& methodName.startsWith("get") && !methodName.endsWith("CreatedTime")
									&& !methodName.endsWith("ModifiedTime") && !methodName.endsWith("Time")) {
								try {
									if (method.invoke(object) != null) {
										parameters.append(method.getName().substring(3, method.getName().length())
												+ ":" + method.invoke(object) + ","
												+ " ");
										auditFlag = true;
									}
								} catch (IllegalArgumentException | IllegalAccessException
										| InvocationTargetException e) {
									System.out.println("Exception in method invoke "+ e.getMessage());
								}
							}
						}
					}

				}
				if (auditFlag.equals(false)) {
					System.out.println("start list and other paramter auditing");
					try {
						parameters.append(name[count] + " " + ":"
								+ " " + (object != null ? new Gson().toJson(object) : object)
								+ " " + " ");
					} catch (Exception e) {
						System.out.println("Exception in convert json "+ e.getMessage());
					}
				}
				count++;
			}

		} catch (Exception e) {
			System.out.println("Exception in auditing "+e.getStackTrace());
			System.out.println("Exception in getting audit parameters "+ e.getMessage());
		}
		// End New Code

		System.out.println("parameter value "+parameters.toString());
		return parameters.toString();
	}

}

