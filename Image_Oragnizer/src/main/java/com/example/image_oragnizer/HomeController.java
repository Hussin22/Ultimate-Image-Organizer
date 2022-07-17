package com.example.image_oragnizer;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.bmp.BmpMetadataReader;
import com.drew.imaging.gif.GifMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.png.PngProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.drew.metadata.Metadata;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.awt.font.ShapeGraphicAttribute;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.Desktop;


public class HomeController implements Initializable {
    @FXML
    private Pane F_Pane;
    @FXML
    private CheckBox All_Type;

    @FXML
    private TextField Des_Image;

    @FXML
    private Label Img_Count;

    @FXML
    private TextField Img_Path;

    @FXML
    private CheckBox Only_Jpg;
    @FXML
    private CheckBox Copy;
    @FXML
    private CheckBox Cut;

    @FXML
    private ComboBox<String> Or_By;
    @FXML
    private Label Lbl_1;

    @FXML
    private Label Lbl_2;
    String TypeFile;
    int Mounth_N;
    File[] listOfFiles;
    Metadata metadata;
    Boolean  First_Start;
int Count;
int N_Images;
    @FXML
    protected void FindImgs()  {


        if(Or_By.getValue() == "Date") {
            Count = 0;
            File folder = new File(Img_Path.getText());
            listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    // System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    //   System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
            for (int i = 0; i < listOfFiles.length; i++) {
                File ImagePath = new File(listOfFiles[i].toString());

              //  System.out.println(listOfFiles[i].getName());
                if (listOfFiles[i].getName().toLowerCase().contains("bmp")) {
                    try {
                        metadata = BmpMetadataReader.readMetadata(ImagePath);
                    } catch (IOException e) {
                        Alerty(e.toString());
                    }
                } else if (listOfFiles[i].getName().toLowerCase().contains("jpg") ) {
                    try {
                        metadata = JpegMetadataReader.readMetadata(ImagePath);
                    } catch (JpegProcessingException e) {
                        Alerty(e.toString());
                    } catch (IOException e) {
                        Alerty(e.toString());
                    }

                } else if (listOfFiles[i].getName().toLowerCase().contains("png") ) {
                    try {
                        metadata = PngMetadataReader.readMetadata(ImagePath);
                    } catch (PngProcessingException e) {
                        Alerty(e.toString());
                    } catch (IOException e) {
                        Alerty(e.toString());
                    }

                } else if (listOfFiles[i].getName().toLowerCase().contains("gif") ) {
                    try {
                        metadata = GifMetadataReader.readMetadata(ImagePath);
                    } catch (IOException e) {
                        Alerty(e.toString());
                    }

                } else {
                    continue;
                }

                String F_Name = listOfFiles[i].getName();

                try {
                    SetData(metadata, listOfFiles[i].toString(), F_Name);
                } catch (ParseException e) {
                    Alerty(e.toString());
                }

            }
        }else{
            Count = 0;
            File folder = new File(Img_Path.getText());
            listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    // System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    //   System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
            for (int i = 0; i < listOfFiles.length; i++) {
                File ImagePath = new File(listOfFiles[i].toString());
                String F_Name = listOfFiles[i].getName();
//System.out.println(listOfFiles[i].toString());
                SortbyType(listOfFiles[i].toString(),F_Name);

            }

        }
    }

    private void SortbyType(String imagePath, String f_Name) {

TypeFile="Uknown";
        if(imagePath.toString().toLowerCase().contains("jpg")){
            TypeFile = "Jpg";
        }
        else if(imagePath.toString().toLowerCase().contains("png") ){
            TypeFile = "Png";
        }
        else if(imagePath.toString().toLowerCase().contains("bmp") ){
            TypeFile = "Bmp";
        }
        else if(imagePath.toString().toLowerCase().contains("gif") ){
            TypeFile = "Gif";
        }
        File TypeDer = new File(Des_Image.getText()+"/"+TypeFile);
        if (!TypeDer.exists()){
            TypeDer.mkdirs();
        }
        File N_TypeDer = new File(Des_Image.getText()+"/"+TypeFile+"/"+f_Name);
        if(imagePath.toLowerCase().contains("jpg")||imagePath.toLowerCase().contains("png")
        ||imagePath.toLowerCase().contains("bmp")||imagePath.toLowerCase().contains("gif")){
            copyImage(imagePath, String.valueOf(N_TypeDer));
        }

    }

    @FXML
    public void CheckBox3(){
        if(Cut.isSelected()){
            Copy.setSelected(false);

        }
    } @FXML
    public void CheckBox4(){
        if(Copy.isSelected()){
            Copy.setSelected(true);
            Cut.setSelected(false);

        }}
    @FXML
    public void SelectImgs(){
        N_Images=0;
        final Stage primaryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\Users\\Public\\Documents"));
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
         Img_Path.setText(selectedDirectory.getAbsolutePath());
         Lbl_1.setVisible(false);

        File folder = new File(Img_Path.getText());
        listOfFiles = folder.listFiles();
       int i=0;
        while (i<listOfFiles.length){
            String File = listOfFiles[i].toString();
            if(File.toLowerCase().contains("jpg")||File.toLowerCase().contains("png")
                    ||File.toLowerCase().contains("bmp")||File.toLowerCase().contains("gif")){
                N_Images++;
            }
            i++;
        }
        Img_Count.setText(String.valueOf(N_Images));
    }
    @FXML
    public void SelectDes(){
        final Stage primaryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\Users\\Public\\Documents"));
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        Des_Image.setText(selectedDirectory.getAbsolutePath());
        Lbl_2.setVisible(false);
    }
    public  void SetData(Metadata metadata,String ImageDer,String Fname) throws ParseException {
        // A Metadata object contains multiple Directory objects
        for (Directory directory : metadata.getDirectories()) {
            //System.out.println(directory);
            // Each Directory stores values in Tag objects
            for (Tag tag : directory.getTags()) {
if(tag.toString().contains("Exif IFD0") && tag.toString().contains("Date") ){

    String YearImage = tag.toString().substring(23,28);
    String MounthImage = tag.toString().substring(29,31);
    String DayImage = tag.toString().substring(29,31);
    String HourImage = tag.toString().substring(35,37);
    //System.out.println(YearImage);
    //System.out.println(HourImage);
    createNFolder(YearImage,MounthImage,DayImage,HourImage,ImageDer,Fname);
}
              else if(tag.toString().contains("Date") && tag.toString().contains("+")){
                    String YearImage = tag.toString().substring(55);
                    String MounthImage = tag.toString().substring(32,35);
                    CounvertMO(MounthImage);
                    String DayImage = tag.toString().substring(36,38);
                    String HourImage = tag.toString().substring(39,41);
                    createNFolder(YearImage, String.valueOf(Mounth_N),DayImage,HourImage,ImageDer,Fname);

                }

            }

            //
            // Each Directory may also contain error messages
            //
            for (String error : directory.getErrors()) {
                Alerty(error);
            }
        }
    }

    private void CounvertMO(String mounthImage) {

        switch (mounthImage){
            case "Jan":
                Mounth_N = 1;
                break;
            case "Feb":
                Mounth_N = 2;
                break;
            case "Mar":
                Mounth_N = 3;
                break;
            case "Apr":
                Mounth_N = 4;
                break;
            case "May":
                Mounth_N = 5;
                break;
            case "Jun":
                Mounth_N = 6;
                break;
            case "Jul":
                Mounth_N = 7;
                break;
            case "Aug":
                Mounth_N = 8;
                break;
            case "Sep":
                Mounth_N =9;
                break;
            case "Oct":
                Mounth_N = 10;
                break;
            case "Nov":
                Mounth_N =11;
                break;
            case "Dec":
                Mounth_N =12;
                break;
            default:
                Mounth_N =1;


        }
    }

    public  void createNFolder(String Year, String Mounth, String Day, String Hour, String ImgDer, String fname) {

        File YearDer = new File(Des_Image.getText()+"/"+Year);
        if (!YearDer.exists()){
            YearDer.mkdirs();
        }
        File MounthDer = new File(Des_Image.getText()+"/"+Year+"/"+Mounth);
        if (!MounthDer.exists()){
            MounthDer.mkdirs();
        }
        File DayDer = new File(Des_Image.getText()+"/"+Year+"/"+Mounth+"/"+Day);
        if (!DayDer.exists()){
            DayDer.mkdirs();
        }
        File HourDer = new File(Des_Image.getText()+"/"+Year+"/"+Mounth+"/"+Day+"/"+Hour);
        if (!HourDer.exists()){
            HourDer.mkdirs();
        }
        String NewDer = Des_Image.getText()+"/"+Year+"/"+Mounth+"/"+Day+"/"+Hour+"/"+fname;
        copyImage(ImgDer, NewDer);






    }

    public void copyImage(String ImageDer, String newDer) {
        Path source = Paths.get(ImageDer);
        Path target = Paths.get(newDer);
        try{
            Count++;
            if(Copy.isSelected()){
            FileUtils.copyFile(source.toFile(), target.toFile(), true);
            }
            else{
                FileUtils.moveFile(source.toFile(), target.toFile());

            }


            if(N_Images == Count ) {
                Alert Al = new Alert(Alert.AlertType.INFORMATION);
                Al.setTitle("Completed");
                Al.setContentText("All Images is Processed!!");
                Al.showAndWait();
                Desktop.getDesktop().open(new File(Des_Image.getText()));

            }
        } catch (IOException e) {
            Alerty(e.toString());
        }
    }
@FXML
public void Hide_Pane() throws IOException {
    F_Pane.setVisible(false);
    FileOutputStream out = new FileOutputStream("C:\\Users\\Public\\Documents\\config.properties");
    FileReader in = new FileReader("C:\\Users\\Public\\Documents\\config.properties");
    Properties prop = new Properties();
    prop.load(in);
    in.close();
    prop.setProperty("IsFirst","true");

    prop.store(out, null);
    out.close();


}
@FXML
public void Close(){
        System.exit(0);
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> data = FXCollections.observableArrayList("Type", "Date");

        Or_By.setItems(data);
        Or_By.getSelectionModel().select("Date");
        Copy.setSelected(true);

        try {
            ShowWarning();
        } catch (IOException e) {
            Alerty(e.toString());
        }
    }

    private void ShowWarning() throws IOException {

        checkfirst();
        if (First_Start == false){

            F_Pane.setVisible(true);
        }
        else{
            F_Pane.setVisible(false);
        }
    }

    @FXML
    public void github() throws IOException, URISyntaxException { Desktop.getDesktop().browse(new URL("https://github.com/Hussin22").toURI());}
    @FXML
    public void Linkdin() throws IOException, URISyntaxException { Desktop.getDesktop().browse(new URL("https://www.linkedin.com/in/hussin-tsouli-1322361a3/").toURI());}
    @FXML
    public void cv() throws IOException, URISyntaxException { Desktop.getDesktop().browse(new URL("https://hussin22.github.io/My-Portoflio/").toURI());}

    private void checkfirst() throws IOException {
        FileReader pread = new FileReader("C:\\Users\\Public\\Documents\\config.properties");
        Properties prop = new Properties();
        prop.load(pread);
        First_Start = Boolean.valueOf((prop.getProperty("IsFirst")));


    }
    public void Alerty(String Error){
        Alert Al = new Alert(Alert.AlertType.ERROR);
        Al.setTitle("Error");
        Al.setHeaderText("There is an Error !!");
        Al.setContentText(Error);
    }
}