package com.orbitinsight.core;


import org.springframework.beans.factory.support.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author dingjiefei
 */
@Component
public class BeanCreator {

    private static ConfigurableApplicationContext applicationContext;

    public BeanCreator(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static Object createBean(Object object) {
        if (!(object instanceof ManualBean)) {
            throw new IllegalArgumentException("Object must be of type ManualBean");
        }
        ManualBean bean = (ManualBean) object;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        if (applicationContext.containsBean(bean.beanName())) {
            beanFactory.removeBeanDefinition(bean.beanName());
        }
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(bean.beanClass());
        beanDefinition.setInstanceSupplier(new InstanceSupplier<Object>() {
            @Override
            public Object get(RegisteredBean registeredBean) throws Exception {
                return object;
            }
        });
        beanFactory.registerBeanDefinition(bean.beanName(), beanDefinition);
        return beanFactory.getBean(bean.beanName());
    }
}