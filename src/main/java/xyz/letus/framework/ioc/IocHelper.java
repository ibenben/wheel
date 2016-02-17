package xyz.letus.framework.ioc;

import java.lang.reflect.Field;
import java.util.Map;

import xyz.letus.framework.ioc.annotation.Inject;

/**
 * 依赖注入助手类
 * @ClassName: IocHelper
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2015年9月19日
 *
 */
public class IocHelper {
	/**
	 * 注入属性
	 * @Title: inject
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @throws
	 */
	public static void inject(Map<String, Object> beanMap){
		/**
		 * 为类中的属性注入值
		 */
		for(Map.Entry<String, Object> entry : beanMap.entrySet()){
			String name = entry.getKey();
			Object beanInstance = entry.getValue();
			
			Field[] beanFields = beanInstance.getClass().getDeclaredFields();
			
			for(Field field : beanFields){
				if(field.isAnnotationPresent(Inject.class)){
					Inject inject = field.getAnnotation(Inject.class);
					if(inject.value().length() > 0){
						name = inject.value();
					}
					Class<?> fieldClazz = field.getType();
					Object fieldInstance = beanMap.get(name);
					if(fieldInstance != null){
						ReflectionFactory.setField(beanInstance, field, fieldInstance);
					}
				}
			}
		}
	}
}
