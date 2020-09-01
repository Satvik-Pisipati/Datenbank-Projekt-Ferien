package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import dbUtil.DBConnection;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;


    @FXML
    private TableView<StudentData> studenttable;

    @FXML
    private TableColumn<StudentData, String> idcolumn;
    @FXML
    private TableColumn<StudentData, String> fnamecolumn;
    @FXML
    private TableColumn<StudentData, String> lnamecolumn;
    @FXML
    private TableColumn<StudentData, String> emailcolumn;
    @FXML
    private TableColumn<StudentData, String> dobcolumn;
    @FXML
    private TableColumn<StudentData,String> classcolumn;

    private DBConnection dc;
    private ObservableList<StudentData> data;

    private String sql = "SELECT * FROM students";


    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new DBConnection();
    }
    

    public void clearFields(javafx.event.ActionEvent event) {
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);
    }

    public void loadStudentData(javafx.event.ActionEvent event) {
        try {

            Connection conn = DBConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs  = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                this.data.add(new StudentData(rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6)));
            }

        }catch (SQLException ex){
            System.err.println("Error: "+ ex);
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("ID"));
        this.fnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("firstName"));
        this.lnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("DOB"));
        this.classcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("class"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);
    }

    public void addStudent(javafx.event.ActionEvent event) {
        String sqlInsert = "INSERT INTO students(id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            stmt.setString(1, this.id.getText());
            stmt.setString(2, this.firstname.getText());
            stmt.setString(3, this.lastname.getText());
            stmt.setString(4, this.email.getText());
            stmt.setString(5, this.dob.getEditor().getText());

            stmt.execute();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
