package com.simplydiffrient.ClassroomQuestions.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Handles getting properties from the properties file and providing
 * them to the other classes as needed.
 */
public class PropertyGetter
{

  private static String PROPERTY_FILE = "config.properties";

  private static PropertyGetter mPropertyGetter = null;
  private Properties mProps = null;

  protected PropertyGetter(String pPropertyFile)
  {
    mProps = new Properties();
    readPropertiesFile(pPropertyFile);
  }

  public synchronized static PropertyGetter getInstance()
  {
    if (mPropertyGetter == null)
    {
      mPropertyGetter = new PropertyGetter(PROPERTY_FILE);
    }
    return mPropertyGetter;
  }

  private void readPropertiesFile(String pPropertyFile)
  {
    try
    {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pPropertyFile);
      if (inputStream != null)
      {
        mProps.load(inputStream);
      }
      else
      {
        throw new FileNotFoundException("Property file '" + pPropertyFile + "' not found.");
      }
    }
    catch (IOException exception)
    {
      // TODO: Implement better error handling here.
    }

  }


  public String getProperty(String pPropertyName)
  {
    return mProps.getProperty(pPropertyName);
  }
}