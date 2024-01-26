package com.sqisoft.ssbr.al.resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import jp.epiontech.frame.conf.Configuration;
import jp.epiontech.frame.conf.ConfigurationException;

import com.sqisoft.ssbr.al.util.MessageHelper;



import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;



/**
 * Copyright (c) 2007 EPIONTECH CORPERATION. 
 * All Rights Reserved
 * @project : SBB LIBRA PROJECT
 * @programid : LocalizedMessageResources.java
 * @regdate 2007/01/26
 * @author epion.lee612
 * @comment : 
 *
 * 
 * @modify_history :
 *  version :         writer  :        date  :        comment :
 */
public class LocalizedMessageResources extends MessageResources
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4336219501298215061L;
	private Configuration conf = null;
	
	/**
	 * Constructors 
	 * @date : 2007/01/26
	 * @param factory
	 * @param config
	 */
	public LocalizedMessageResources( MessageResourcesFactory factory
			                          , String config )
    {
        super( factory, config );
        MessageHelper.load(this);
        
        try {
			conf = Configuration.getInstance();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Constructors 
     * @date : 2007/01/26
     * @param factory
     * @param config
     * @param returnNull
     */
    public LocalizedMessageResources( MessageResourcesFactory factory
    		                          , String config
    		                          , boolean returnNull )
    {
        super( factory, config, returnNull);
        MessageHelper.load(this);
        
        try {
			conf = Configuration.getInstance();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    protected HashMap locales = new HashMap();

    protected HashMap messages = new HashMap();

    public String getMessage(Locale locale, String key)
    {

        String localeKey = localeKey(locale);
        String originalKey = messageKey(localeKey, key);
        String messageKey = null;
        String message = null;
        int underscore = 0;
        boolean addIt = false;

        while(true)
        {
            loadLocale(localeKey);

            messageKey = messageKey(localeKey, key);
            synchronized (messages)
            {
                message = (String) messages.get(messageKey);
                if(message != null)
                {
                    if(addIt)
                    {
                        messages.put(originalKey, message);
                    }
                    return (message);
                }
            }

            addIt = true;
            underscore = localeKey.lastIndexOf("_");
            if (underscore < 0)
                break;
            localeKey = localeKey.substring(0, underscore);

        }

        if (!defaultLocale.equals(locale))
        {
            localeKey = localeKey(defaultLocale);
            messageKey = messageKey(localeKey, key);
            loadLocale(localeKey);
            synchronized(messages)
            {
                message = (String)messages.get(messageKey);
                if (message != null)
                {
                    messages.put(originalKey, message);
                    return (message);
                }
            }
        }

        localeKey = "";
        messageKey = messageKey(localeKey, key);
        loadLocale(localeKey);
        synchronized (messages)
        {
            message = (String) messages.get(messageKey);
            if (message != null)
            {
                messages.put(originalKey, message);
                return (message);
            }
        }

        if(returnNull)
        {
            return (null);
        }
        else
        {
            return ("???" + messageKey(locale, key) + "???");
        }
    }

    protected void loadLocale(String localeKey)
    {

        synchronized(locales)
        {
            if(locales.get(localeKey) != null)
            {
                return;
            }
            locales.put(localeKey, localeKey);
        }

        String name = config.replace('.', '/');
        if(localeKey.length() > 0)
        {
            name += "_" + localeKey;
        }
        name += ".properties";
        InputStream is = null;
        Properties props = new Properties();

        try
        {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if(classLoader == null)
            {
                classLoader = this.getClass().getClassLoader();
            }
            is = classLoader.getResourceAsStream(name);
            if(is != null)
            {
                BufferedReader reader = new BufferedReader( 
                		                   new InputStreamReader( is
                		                		                   , conf.get("frame.message.encoding")));
                while(true)
                {
                    String line = reader.readLine();
                    if(line == null)
                    {
                        break;
                    }

                    line = line.trim();
                    if(line.length() == 0 || line.charAt(0) == '#' || line.indexOf("=") < 0)
                    {
                        continue;
                    }

                    String key = line.substring(0, line.indexOf("="));
                    String value = line.substring(line.indexOf("=") + 1);
                    props.setProperty(key, value);
                }
                is.close();
            }

        }
        catch(Throwable t) {
            if(is != null)  {
                try {
                    is.close();
                }
                catch(Throwable u) {

                }
            }
        }

        if(props.size() < 1)
        {
            return;
        }
        synchronized(messages)
        {
            Enumeration names = props.keys();
            while(names.hasMoreElements())
            {
                String key = (String) names.nextElement();
                messages.put(messageKey(localeKey, key), props.getProperty(key));
            }
        }
    }
}
