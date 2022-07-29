package util;


import javafx.scene.control.TextField;

/**
 * Created by Amanda on 27/11/2016.
 */
public class Mascaras {

    public static void setMaxLength(TextField txt, int length ) {
        txt.textProperty().addListener( ( observable, oldValue, newValue ) -> {
            if ( newValue.length() > length ) {
                txt.setText( oldValue );
            }
        } );
    }
}
