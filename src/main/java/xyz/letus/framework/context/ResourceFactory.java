package xyz.letus.framework.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 资源管理器
 * @ClassName: ResourceFactory
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2016年2月17日
 *
 */
public class ResourceFactory {
	private static final List<String> SCAN_PACKAGES = new ArrayList<String>();

	/**
	 * 解析资源文件（配置文件）
	 * @Title: parse
	 * @Description: TODO
	 * @param @param fileName
	 * @param @throws FileNotFoundException
	 * @param @throws IOException    
	 * @return void    
	 * @throws
	 */
	public static void parse(String fileName) throws FileNotFoundException, IOException {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);  
		Properties properties = new Properties();
		properties.load(stream);
		Enumeration<?> e = properties.propertyNames();// 得到配置文件的名字
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			
			if("scanPackage".equals(key)){
				SCAN_PACKAGES.add(value);
			}
		}
	}
	/**
	 * 获取自动描述的包路径
	 * @Title: getPackages
	 * @Description: TODO
	 * @param @return    
	 * @return List<String>    
	 * @throws
	 */
	public static List<String> getPackages(){
		return SCAN_PACKAGES;
	}
	
}
