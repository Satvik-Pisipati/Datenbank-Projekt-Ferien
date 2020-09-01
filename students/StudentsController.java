package students;

import dbUtil.DBConnection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    private DBConnection dc;

    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new DBConnection();
    }

}
