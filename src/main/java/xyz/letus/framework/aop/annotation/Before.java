package xyz.letus.framework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 前置增强注解
 * @ClassName: Before
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2016年3月7日
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
	/**
	 * 切入点表达式
	 * 
	 * @Title: value
	 * @Description: TODO
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	String value();
}
