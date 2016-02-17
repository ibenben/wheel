package xyz.letus.framework.ioc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import xyz.letus.framework.ioc.annotation.Component;

/**
 * 类操作助手
 * @ClassName: ClassHelper
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2015年9月19日
 *
 */
public class ClassFactory {

	/**
	 * 获取交由容器管理的类
	 * @Title: getBeanClasses
	 * @Description: TODO
	 * @param @return    
	 * @return Map<String, Class<?>>    
	 * @throws
	 */
	public static  Map<String, Class<?>> getBeanClasses(String basePackage){
		Map<String, Class<?>> annotationClasses = new HashMap<String, Class<?>>();
		Set<Class<?>> classes = ClassLoader.getClassSet(basePackage);
		for(Class<?> clazz : classes){
			if(clazz.isAnnotationPresent(Component.class)){
				Component component = clazz.getAnnotation(Component.class);
				String name = clazz.getSimpleName();
				String value = component.value();
				if(value.length() > 0){
					name = value;
				}
				classes.add(clazz);
				annotationClasses.put(name, clazz);
			}
		}
		return annotationClasses;
	}
	
	

}
