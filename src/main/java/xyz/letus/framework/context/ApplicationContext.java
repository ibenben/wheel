package xyz.letus.framework.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import xyz.letus.framework.ioc.BeanFactory;

/**
 * 类容器
 * @ClassName: IocContext
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2016年2月17日
 *
 */
public class ApplicationContext {
	private String path;
	
	private ApplicationContext(String path){
		this.path = path;
		init();
	}
	
	public static ApplicationContext getContext(String path){
		return new ApplicationContext(path);
	}
	/**
	 * 初始化
	 * @Title: init
	 * @Description: TODO
	 * @param     
	 * @return void    
	 * @throws
	 */
	public void init(){
		try {
			ResourceFactory.parse(path);
			List<String> packages = ResourceFactory.getPackages();
			BeanFactory.createInstance(packages);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据别名获取一个对象
	 * @Title: getBean
	 * @Description: TODO
	 * @param @param name
	 * @param @return    
	 * @return T    
	 * @throws
	 */
	public <T> T getBean(String name)  {
		return BeanFactory.getBean(name);
	}
}
