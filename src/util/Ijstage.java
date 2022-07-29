package util;

import javafx.stage.WindowEvent;

public interface Ijstage {
	
	void OnShow(WindowEvent e);
    void OnClose(WindowEvent e);
    void OnHidden(WindowEvent e);
    void OnHiding(WindowEvent e);
    void OnShowing(WindowEvent e);
}
