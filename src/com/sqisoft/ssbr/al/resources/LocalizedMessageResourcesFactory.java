package com.sqisoft.ssbr.al.resources;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * Copyright (c) 2007 EPIONTECH CORPERATION. 
 * All Rights Reserved
 * 
 * @comment : 
 *
 * 
 * @program id : LocalizedMessageResourcesFactory.java
 * @regdate 2007. 1. 10
 * @author lee612
 *
 */
public class LocalizedMessageResourcesFactory extends MessageResourcesFactory
{
    public MessageResources createResources(String config)
    {
        return new LocalizedMessageResources( this
        		                              , config
        		                              , this.returnNull );
    }
}
