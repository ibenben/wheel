package xyz.letus.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xyz.letus.framework.aop.annotation.Aspect;
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
	private Map<String, Class<?>> componentClasses;
	private Map<String, Class<?>> aspectClasses;
	private List<String> packages;
	
	public ClassFactory(List<String> packages){
		this.packages = packages;
		componentClasses = new HashMap<String, Class<?>>();
		aspectClasses = new HashMap<String, Class<?>>();
		this.parse();
	}

	/**
	 * 解析路径，并获取所要的类
	 * @Title: parse
	 * @Description: TODO
	 * @return void    
	 * @throws
	 */
	public void parse(){
		for (String packagePath : packages) {
			Set<Class<?>> classes = ClassLoader.getClassSet(packagePath);
			for(Class<?> clazz : classes){
				if(clazz.isAnnotationPresent(Component.class)){
					//普通类托管
					Component component = clazz.getAnnotation(Component.class);
					String name = clazz.getSimpleName();
					String value = component.value();
					if(value.length() > 0){
						name = value;
					}
					
					componentClasses.put(name, clazz);
				}else if(clazz.isAnnotationPresent(Aspect.class)){
					//切面类托管
					aspectClasses.put(clazz.getSimpleName(), clazz);
				}
			}
		}
	}

	public Map<String, Class<?>> getComponentClasses() {
		return componentClasses;
	}

	public Map<String, Class<?>> getAspectClasses() {
		return aspectClasses;
	}
	
	
	
}
