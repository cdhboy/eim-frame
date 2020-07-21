package com.eim.dao.aspect;

import com.eim.dao.DynamicDataSourceContextHolder;
import com.eim.dao.annotation.TargetDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)//加载顺序越小越高
@Component
public class DynamicDataSourceAspect {
    // private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    //@within在类上设置
    //@annotation在方法上进行设置
//    @Pointcut("@within(com.eim.dao.annotation.TargetDataSource)||@annotation(com.eim.dao.annotation.TargetDataSource)")
//    public void pointcut() {
//    }


//    @Before("@annotation(targetDataSource)")
//    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
//
//        Method method = ((MethodSignature) point.getSignature()).getMethod();
//        TargetDataSource annotationClass = method.getAnnotation(TargetDataSource.class);//获取方法上的注解
//        if (annotationClass == null) {
//            annotationClass = point.getTarget().getClass().getAnnotation(TargetDataSource.class);//获取类上面的注解
//            if (annotationClass == null) return;
//        }
//        //获取注解上的数据源的值的信息
//        String dataSourceKey = annotationClass.value();
//        if (dataSourceKey != null) {
//            //给当前的执行SQL的操作设置特殊的数据源的信息
//            DynamicDataSourceContextHolder.setDataSourceRouterKey(dataSourceKey);
//        }
//
//
//        String dsId = targetDataSource.value();
//
//        DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
//
//    }
//
//    @After("@annotation(targetDataSource)")
//    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
//        //logger.info("恢复数据源-" + point.getSignature());
//
//        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
//    }

    @Around("@within(com.eim.dao.annotation.TargetDataSource)||@annotation(com.eim.dao.annotation.TargetDataSource)")
    public Object crossCuttingCode(ProceedingJoinPoint point)
            throws Throwable {
        Object obj = null;

        Method method = ((MethodSignature) point.getSignature()).getMethod();
        TargetDataSource annotationClass = method.getAnnotation(TargetDataSource.class);//获取方法上的注解
        if (annotationClass == null) {
            annotationClass = point.getTarget().getClass().getAnnotation(TargetDataSource.class);//获取类上面的注解
            if (annotationClass == null) return null;
        }
        //获取注解上的数据源的值的信息
        String dataSourceKey = annotationClass.value();
        if (dataSourceKey != null) {
            //给当前的执行SQL的操作设置特殊的数据源的信息
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dataSourceKey);
        }

        try {
            obj = point.proceed();
        } finally {
            DynamicDataSourceContextHolder.removeDataSourceRouterKey();
        }

        return obj;
    }
}