package com.springdemo.prototypescope;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyCustomBeanProcessor implements BeanPostProcessor, BeanFactoryAware, DisposableBean {

	private BeanFactory beanFactory;

	private final List<Object> prototypeBeans = new LinkedList<>();

	
    // simply return the instantiated bean as-is
    @Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		System.out.println("MyCustomBeanProcessor::postProcessBeforeInitialization bean: " +  bean + ", beanName: " + beanName);
        return bean; // we could potentially return any object reference here...
    }
    
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		// after start up, keep track of the prototype scoped beans. 
		// we will need to know who they are for later destruction
		if (beanFactory.isPrototype(beanName)) {
			synchronized (prototypeBeans) {
				System.out.println("MyCustomBeanProcessor::postProcessAfterInitialization bean: " +  bean + ", beanName: " + beanName);
				prototypeBeans.add(bean);
			}
		}
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactory: " + beanFactory);
		this.beanFactory = beanFactory;
	}


	@Override
	public void destroy() throws Exception {

		// loop through the prototype beans and call the destroy() method on each one
        synchronized (prototypeBeans) {
        	for (Object bean : prototypeBeans) {
        		if (bean instanceof DisposableBean) {
                    DisposableBean disposable = (DisposableBean)bean;
                    try {
                        disposable.destroy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        	prototypeBeans.clear();
        }        
	}
}
