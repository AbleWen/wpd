package com.wlh.wpd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * property文件读者
 */
public class PropertyFileReader
{
    private static String PROPERTY_FILE = "system.properties";

    private static final Logger logger = Logger
            .getLogger(PropertyFileReader.class);

    /**
     * 根据Key 读取Value
     * @param key
     * @return
     */
    public static String readData(String key)
    {

        Resource re = new ClassPathResource("system.properties");
        InputStream in = null;
        try
        {
            in = re.getInputStream();
        }
        catch (IOException e)
        {
            logger
                    .error("read file [system.properties] occured IOException .");
            return null;
        }
        Properties props = new Properties();
        try
        {
            props.load(in);
            in.close();
            String value = props.getProperty(key);
            return value;
        }
        catch (Exception e)
        {
            logger
                    .error("get message from file [system.properties] occured error."
                            + e.getMessage());
        }
        return null;
    }

    public static List<String> getValueList(String key)
    {
        List<String> list = null;
        String value = readData(key);
        if (StringUtils.isNotEmpty(value))
        {
            String[] array = value.split(",");
            if (array.length != 0)
            {
                list = Arrays.asList(array);
            }
        }
        return list;
    }

    public static boolean getValueBoolean(String key)
    {
        boolean value = false;
        String strValue = readData(key);
        if (StringUtils.isNotEmpty(strValue))
        {
            value = Boolean.parseBoolean(strValue);
        }
        return value;
    }

    public static int getValueInt(String key)
    {
        int value = 0;
        String strValue = readData(key);
        if (StringUtils.isNotEmpty(strValue))
        {
            value = Integer.parseInt(strValue);
        }
        return value;
    }

    /**
     * 修改或添加键值对 如果key存在，修改 反之，添加。
     * @param key
     * @param value
     */
    public static void writeData(String key, String value)
    {
        Properties prop = new Properties();
        try
        {
            File file = new File(PROPERTY_FILE);
            if (!file.exists())
            {
                boolean flag = file.createNewFile();
                if (!flag)
                {
                    logger
                            .error("visit ["
                                    + PROPERTY_FILE
                                    + "] for updating ["
                                    + value
                                    + "] value error, file [PROPERTY_FILE] no found and create file failed.");
                    return;
                }
            }
            InputStream fis = new FileInputStream(file);
            prop.load(fis);
            fis.close();// 一定要在修改值之前关闭fis
            OutputStream fos = new FileOutputStream(PROPERTY_FILE);
            prop.setProperty(key, value);
            prop.store(fos, "Update '" + key + "' value");
            fos.close();
        }
        catch (IOException e)
        {
            logger.error("visit [" + PROPERTY_FILE + "] for updating [" + value
                    + "] value error");
        }

    }

    /**
     * <根据Key 读取Value> <功能详细描述>
     * @param file
     * @param key
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public static String getValue(String filePath, String key)
    {

        Resource re = new ClassPathResource(filePath);
        InputStream in = null;
        try
        {
            in = re.getInputStream();
        }
        catch (IOException e)
        {
            logger.error("read file occured IOException .");
            return null;
        }
        Properties props = new Properties();
        try
        {
            props.load(in);
            in.close();
            String value = props.getProperty(key);
            return value;
        }
        catch (Exception e)
        {
            logger.error("get message from file  occured error."
                    + e.getMessage());
        }
        return null;
    }

    /**
     * <根据Key 读取Value> <功能详细描述>
     * @param file
     * @param key
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     */
    public static List<String> getValueList(String filePath, String key)
    {
        List<String> list = null;
        String value = getValue(filePath, key);

        if (StringUtils.isNotEmpty(value))
        {
            String[] array = value.split(",");
            if (array.length != 0)
            {
                list = Arrays.asList(array);
            }
        }

        return list;
    }

}
