package com.sqisoft.ssbr.al.util;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

import java.util.Locale;

public class MessageHelper
{
    private MessageResources resources = null;
    private boolean          loaded    = false;

    private static MessageHelper instance = new MessageHelper();

    public static void load(MessageResources resources)
    {
        instance.setResources(resources);
        instance.setLoaded(true);
    }

    public static MessageHelper getInstance()
    {
        return instance;
    }

    private MessageHelper()
    {

    }

    public String getConfig()
    {
        return resources.getConfig();
    }

    public MessageResourcesFactory getFactory()
    {
        return resources.getFactory();
    }

    public boolean getReturnNull()
    {
        return resources.getReturnNull();
    }

    public void setReturnNull(boolean b)
    {
        resources.setReturnNull(b);
    }

    public String getMessage(String s)
    {
        return resources.getMessage(s);
    }

    public String getMessage(String s, Object[] objects)
    {
        return resources.getMessage(s, objects);
    }

    public String getMessage(String s, Object o)
    {
        return resources.getMessage(s, o);
    }

    public String getMessage(String s, Object o, Object o1)
    {
        return resources.getMessage(s, o, o1);
    }

    public String getMessage(String s, Object o, Object o1, Object o2)
    {
        return resources.getMessage(s, o, o1, o2);
    }

    public String getMessage(String s, Object o, Object o1, Object o2, Object o3)
    {
        return resources.getMessage(s, o, o1, o2, o3);
    }

    public String getMessage(Locale locale, String s)
    {
        return resources.getMessage(locale, s);
    }

    public String getMessage(Locale locale, String s, Object[] objects)
    {
        return resources.getMessage(locale, s, objects);
    }

    public String getMessage(Locale locale, String s, Object o)
    {
        return resources.getMessage(locale, s, o);
    }

    public String getMessage(Locale locale, String s, Object o, Object o1)
    {
        return resources.getMessage(locale, s, o, o1);
    }

    public String getMessage(Locale locale, String s, Object o, Object o1, Object o2)
    {
        return resources.getMessage(locale, s, o, o1, o2);
    }

    public String getMessage(Locale locale, String s, Object o, Object o1, Object o2, Object o3)
    {
        return resources.getMessage(locale, s, o, o1, o2, o3);
    }

    public boolean isPresent(String s)
    {
        return resources.isPresent(s);
    }

    public boolean isPresent(Locale locale, String s)
    {
        return resources.isPresent(locale, s);
    }

    public MessageResources getMessageResources(String s)
    {
        return MessageResources.getMessageResources(s);
    }

    public MessageResources getResources()
    {
        return resources;
    }

    public void setResources(MessageResources resources)
    {
        this.resources = resources;
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public void setLoaded(boolean loaded)
    {
        this.loaded = loaded;
    }
}
