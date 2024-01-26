package com.sqisoft.ssbr;

import java.io.*;

public class SXSException extends Exception
{
  
    private Throwable rootCause;
    private boolean isFirst;
    
    private int errCode = 0;
    
    public int getErrCode() {
		return errCode;
	}
 
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	/**
     * 2006. 12. 27 
     * lee612
     * Constuctor
     */
    public SXSException()
    {
        isFirst = true;
    }

    /**
     * 2006. 12. 27 
     * lee612
     * Constuctor
     * @param s
     */
    public SXSException(String s)
    {
        super(s);
        isFirst = true;
    }

    /**
     * 2006. 12. 27 
     * lee612
     * Constuctor
     * @param s
     */
    public SXSException(int eCode , String s)
    {
        super(s);
        setErrCode(eCode);
        
        isFirst = true;
    }

    
    /**
     * 2006. 12. 27 
     * lee612
     * Constuctor
     * @param message
     * @param rootCause
     */
    public SXSException(String message, Throwable rootCause)
    {
        super( message );
        isFirst = true;
        this.rootCause = rootCause;
        isFirst = false;
    }

    /**
     * 2006. 12. 27 
     * lee612
     * Constuctor
     * @param rootCause
     */
    public SXSException(Throwable rootCause)
    {
    	super( rootCause.getMessage() );
        this.rootCause = rootCause;
        isFirst = false;
    }

    /**
     * @return
     */
    public Throwable getRootCause()
    {
        return rootCause;
    }

    /**
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable)
    {

        StringWriter s = new StringWriter();
        PrintWriter pw = new PrintWriter( s );
        pw.println(" : getStackTrace() : =====PrintStackTrace=====");
        throwable.printStackTrace(pw);
        return s.toString();
    }

    /**
     * @return
     */
    public String getStackTraceString()
    {
        StringWriter s = new StringWriter();
        printStackTrace(new PrintWriter(s));
        return s.toString();
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }


    public void printStackTrace(PrintStream s)
    {
        synchronized(s) {
            super.printStackTrace(s);
            if(rootCause != null)
                rootCause.printStackTrace(s);
            if(isFirst || !(rootCause instanceof SXSException))
                s.println("-----------------------------");
        }
    }

    public void printStackTrace(PrintWriter s)
    {
        synchronized(s)
        {
            super.printStackTrace(s);
            if(rootCause != null)
                rootCause.printStackTrace(s);
            if(isFirst || !(rootCause instanceof SXSException))
                s.println("-----------------------------");
        }
    }
}