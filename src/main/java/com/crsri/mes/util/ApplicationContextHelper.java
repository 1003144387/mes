package com.crsri.mes.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈获取springBean的工具类〉
 *
 * @author zcj
 * @date 2018/12/3 22:19
 * @since 1.0.0
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
    public static Object getBean(String beanName){return context.getBean(beanName);}

    public static <T> T getBean(Class<T> tClass){return context.getBean(tClass);}
}
