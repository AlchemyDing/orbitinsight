package com.orbitinsight.core;


/**
 * @author dingjiefei
 */
public abstract class AbstractManualBean implements ManualBean {

    protected String beanName;

    protected AbstractManualBean(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String beanName() {
        return beanName;
    }

}
