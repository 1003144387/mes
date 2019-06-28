package com.crsri.mes.common.log.annontation;

import java.lang.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈记录service日志的注解〉
 *
 * @author zcj
 * @date 2018/9/21 9:45
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {

    String description()  default "";
}
