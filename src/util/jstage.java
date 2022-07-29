package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class jstage extends Stage implements Ijstage{

	private jstage stage_principal;

    public jstage getStage_principal() {
        return stage_principal;
    }
    public void setStage_principal(jstage stage_principal) {
        this.initOwner(stage_principal);
        this.stage_principal = stage_principal;
    }

    public void OnCreate(String fxml) throws IOException {
        FXMLLoader loader = null;
        Parent root = null;
        String style = null;
        Scene scene = null;

        loader = new FXMLLoader(this.getClass().getResource(fxml));
        loader.setController(this);
        root = loader.load();

        style = this.getClass().getResource("/style/standard.css").toExternalForm();

        scene = new Scene(root);

        scene.getStylesheets().add(style);

        this.setTitle("jstage");
        this.setScene(scene);
        this.setOnShown(this::OnShow);
        this.setOnCloseRequest(this::OnClose);
        this.setOnHidden(this::OnHidden);
        this.setOnHiding(this::OnHiding);
        this.setOnShowing(this::OnShowing);
    }
    
	@Override
	public void OnShow(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnClose(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnHidden(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnHiding(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnShowing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
