package xyz.letus.framework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 代理管理器
 * @ClassName: ProxyManager
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2016年3月7日
 *
 */
public class ProxyManager {
	/**
	 * 创建一个前置增强的代理对象
	 * @Title: createBeforeProxy
	 * @Description: TODO
	 * @param @param aspect 切面对象
	 * @param @param target 目标对象
	 * @param @param matchMethods 匹配的方法名
	 * @param @param before 前置增强方法
	 * @param @return    
	 * @return T    
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createBeforeProxy(final Object aspect,final Object target,final List<String> matchMethods,final Method before){
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
			
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				/**
				 * 增加前置增强
				 */
				if(matchMethods.contains(method.getName())){
					before.invoke(aspect, args);
				}
				
				Object result = method.invoke(target, args);
				return result;
			}
		});
	}
}
