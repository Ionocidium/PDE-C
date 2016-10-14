package model;

import java.awt.Component;

public interface APIInterface
{
  public void addComponent(int id, Component comp);
  public void removeComponent(int id);
  public void setConsoleText(String message);
}
