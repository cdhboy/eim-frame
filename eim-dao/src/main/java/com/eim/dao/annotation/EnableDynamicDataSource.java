package com.eim.dao.annotation;

import com.eim.dao.DynamicDataSourceRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加在springboot启动类上，启动动态数据源注册
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DynamicDataSourceRegister.class})
public @interface EnableDynamicDataSource {
}
