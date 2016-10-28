package model;

import java.awt.Component;

public interface APIInterface
{
  public void addComponent(Component comp);
  public void removeComponent(Component comp);
  public void setConsoleText(String message);
  public void setVisible(boolean isVisible);
}
