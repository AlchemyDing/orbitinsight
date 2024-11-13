package com.orbitinsight.core.bean;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author dingjiefei
 */
public interface ManualBean extends DisposableBean {

    String beanName();

    Class<?> beanClass();

}
