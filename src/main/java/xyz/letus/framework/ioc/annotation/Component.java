package xyz.letus.framework.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件描述注解
 * @ClassName: Component
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2015年9月19日
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
	String value() default "";
}
