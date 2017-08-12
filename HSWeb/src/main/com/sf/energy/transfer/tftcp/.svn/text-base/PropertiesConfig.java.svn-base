package com.sf.energy.transfer.tftcp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
/**
 * 将界面中配置的信息写入到配置文件中
 * @author ky
 * @version 1.0
 */
public class PropertiesConfig {  
	 ///配置文件的位置
	private static String filePath="msg.properties";
	/** 
     * 根据KEY，读取文件对应的值 
     * @param key 键 
     * @return key对应的值 
     */  
    public  String readData(String key) {  
        
        Properties props = new Properties();  
        try {  
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));  
            props.load(in);  
            in.close();  
            String value = props.getProperty(key);  
            return value;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    /** 
     * 修改或添加键值对 如果key存在，修改, 反之，添加。 
     * @param key 键 
     * @param value 键对应的值 
     */  
    public void addOrAlter(String key, String value) {  
   
        Properties prop = new Properties();  
        try {  
            File file = new File(filePath);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            prop.load(fis);  
            fis.close();  
            OutputStream fos = new FileOutputStream(filePath);  
            prop.setProperty(key, value);  
            //保存，并加入注释  
            prop.store(fos, "Update '" + key + "' value");  
            fos.close();  
        } catch (IOException e) {  
            System.err.println("Visit " + filePath + " for updating " + value + " value error");  
        }  
    }  
    /**
     * 根据键值删除相应值
     * @param key
     */
    public void delete(String key){
    	 Properties props = new Properties();  
         try {  
        	 File file = new File(filePath);  
        	 if (!file.exists()) {
        		 return;
        	 }
        	 else {
        		 InputStream fis = new FileInputStream(file);  
                 props.load(fis);            
                 fis.close();  
                 OutputStream fos = new FileOutputStream(filePath); 
                 props.remove(key);
                 fos.close();
			}
                         
         } catch (Exception e) {  
             e.printStackTrace();  
             return;  
         }  
    }
    public static void main(String[] args){
    	PropertiesConfig p=new PropertiesConfig();
    	p.addOrAlter("transerverport","8001");
    	p.addOrAlter("tranclientip","115.156.249.2");
    	p.addOrAlter("tranclientport","8002");
    	p.addOrAlter("serverport","8002");
    }
       
}  

