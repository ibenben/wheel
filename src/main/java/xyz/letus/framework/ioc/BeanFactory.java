package xyz.letus.framework.ioc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Bean助手类
 * @ClassName: BeanHelper
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2015年9月19日
 *
 */
public class BeanFactory {
	private static final Map<String, Object> BEAN_MAP = new HashMap<String, Object>();
	
	/**
	 * 创建所有托管的实例
	 * @Title: createInstance
	 * @Description: TODO
	 * @param @param packages    
	 * @return void    
	 * @throws
	 */
	public static void createInstance(List<String> packages){
		for (String packagePath : packages) {
			Map<String, Class<?>> beanClasses = ClassFactory
					.getBeanClasses(packagePath);
			for (Entry<String, Class<?>> entry : beanClasses.entrySet()) {
				Object obj = ReflectionFactory.newInstance(entry.getValue());
				
				BEAN_MAP.put(entry.getKey(), obj);
			}
		}
		
		IocHelper.inject(BEAN_MAP);
	}
	
	
	/**
	 * 获取Bean实例
	 * @Title: getBean
	 * @Description: TODO
	 * @param @param name
	 * @param @return    
	 * @return T    
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		if(!BEAN_MAP.containsKey(name)){
			throw new RuntimeException("can not get bean by className:"+name);
		}
		
		return (T) BEAN_MAP.get(name);
	}
}
