package xyz.letus.framework.ioc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类加载器
 * @ClassName: ClassLoader
 * @Description: TODO
 * @author 潘广伟(笨笨)
 * @date 2015年9月14日
 *
 */
public class ClassLoader {
	public static final Logger LOGGER = LoggerFactory.getLogger(ClassLoader.class);
	
	/**
	 * 获取线程上的类加载器
	 * @Title: getClassLoader
	 * @Description: TODO
	 * @param @return    
	 * @return java.lang.ClassLoader    
	 * @throws
	 */
	public static java.lang.ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
	/**
	 * 加载类
	 * @Title: loadClass
	 * @Description: TODO
	 * @param @param className
	 * @param @param isInitialized
	 * @param @return    
	 * @return Class<?>    
	 * @throws
	 */
	public static Class<?> loadClass(String className,boolean isInitialized){
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			LOGGER.error("load class failure", e);
			throw new RuntimeException(e);
		}
		
		return clazz;
	}
	/**
	 * 获取包下所有的类
	 * @Title: getClassSet
	 * @Description: TODO
	 * @param @param packageName
	 * @param @return    
	 * @return Set<Class<?>>    
	 * @throws
	 */
	public static Set<Class<?>> getClassSet(String packageName){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
			
			while(urls.hasMoreElements()){
				URL url = urls.nextElement();
				if(url != null){
					String protocol = url.getProtocol();
					if(protocol.equals("file")){
						String packagePath = url.getPath().replace("%20", " ");
						addCommonClass(classes, packagePath, packageName);
					}else if(protocol.equals("jar")){
						addJarClasses(classes, url);
					}
				}
			}
			
		} catch (IOException e) {
			LOGGER.error("get class set failure", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return classes;
	}
	/**
	 * 把jar包中所有的类加入集合
	 * @Title: addJarClasses
	 * @Description: TODO
	 * @param @param classes
	 * @param @param url
	 * @param @throws IOException    
	 * @return void    
	 * @throws
	 */
	private static void addJarClasses(Set<Class<?>> classes, URL url)
			throws IOException {
		JarURLConnection connetion = (JarURLConnection) url.openConnection();
		if(connetion != null){
			JarFile jar = connetion.getJarFile();
			if(jar != null){
				Enumeration<JarEntry> enties = jar.entries();
				while(enties.hasMoreElements()){
					JarEntry entry = enties.nextElement();
					String entryName = entry.getName();
					if(entryName.endsWith(".class")){
						String className = entryName.substring(0, entryName.lastIndexOf('.')).replaceAll("/", ".");
						doAddClass(classes, className);
					}
				}
			}
		}
	}
	
	/**
	 * 把文件夹中的所有类加入集合
	 * @Title: addCommonClass
	 * @Description: TODO
	 * @param @param classes
	 * @param @param packagePath
	 * @param @param packageName    
	 * @return void    
	 * @throws
	 */
	private static void addCommonClass(Set<Class<?>> classes,String packagePath,String packageName){
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
			}
		});
		
		for(File file : files){
			String fileName = file.getName();
			if(file.isFile()){
				String className = packageName + '.' +  fileName.substring(0, fileName.lastIndexOf('.'));
				doAddClass(classes, className);
			}else{
				String subPackagetPath = packagePath + "/" + fileName;
				String subPackagetName = packageName + "/" + fileName;
				addCommonClass(classes, subPackagetPath, subPackagetName);
			}
		}
	}
	/**
	 * 加入类到集合
	 * @Title: doAddClass
	 * @Description: TODO
	 * @param @param classes
	 * @param @param className    
	 * @return void    
	 * @throws
	 */
	private static void doAddClass(Set<Class<?>> classes,String className){
		classes.add(loadClass(className, false));
	}
}
