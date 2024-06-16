package com.orbitinsight.core;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author dingjiefei
 */
public interface ManualBean extends DisposableBean {

    String beanName();

    Class<?> beanClass();

}
