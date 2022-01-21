package com.esb.distribute.service;

public @interface ElementField {
	/**
     * 目标实体的key
     */
    String key() default "";

    /**
     * 默认值
     */
    String defaultvalue() default "";

    /**
     * 参照实体的取值对象key
     */
    String reference() default "";
    
    /**
     * 参照是否取entity中字段, 当不需要时, 此字段有值
     */
    String isEntity() default "";
}
