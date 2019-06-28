package com.crsri.mes.common.log.annontation;

import java.lang.annotation.*;

/**
 * @program: ck-mes
 * @Date: 2018/9/21 9:43
 * @Author: zcj
 * @Description: 自定义注解拦截controller
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description()  default "";
}
