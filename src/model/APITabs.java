package model;

import java.awt.Component;

public interface APITabs {
	  public void addComponent(String tabName, Component comp);
	  public void removeComponent(Component comp);
	  public void setVisible(boolean isVisible);
}
