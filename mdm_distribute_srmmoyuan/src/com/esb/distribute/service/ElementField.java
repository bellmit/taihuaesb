package com.esb.distribute.service;

public @interface ElementField {
	/**
     * Ŀ��ʵ���key
     */
    String key() default "";

    /**
     * Ĭ��ֵ
     */
    String defaultvalue() default "";

    /**
     * ����ʵ���ȡֵ����key
     */
    String reference() default "";
    
    /**
     * �����Ƿ�ȡentity���ֶ�, ������Ҫʱ, ���ֶ���ֵ
     */
    String isEntity() default "";
}
