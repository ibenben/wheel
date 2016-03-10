package xyz.letus.framework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面
 * @ClassName: Aspect
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2016年3月7日
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
	String value() default "";
}
