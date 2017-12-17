package SearchEngine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GUI extends Application {

    Stage window;
    //Scene dictionaryScene, cacheScene;
    ListView<String> dictionary;
    TableView <CacheTermGui>cache;
    private boolean doStemming=true;
    TextField postingInput;
    TextField loadInput;
    TextField saveInput;
    TextField corpusInput;
    String s="";
    String pathToPosting="C:\\Users\\yaels\\Desktop\\11";
    String pathToCorpus="";
    ReadFile r;
    Parser P;
    Indexer indexer;
    long totalTime;
    boolean finish=false;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Welcome to our search engine! ");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //table for dictionary
        /**TableColumn<DictionaryTermGui,String> termCol=new TableColumn<>("IRproject.Term");
        termCol.setMinWidth(200);
        termCol.setCellValueFactory(new PropertyValueFactory<>("term"));

        TableColumn<DictionaryTermGui,String>amountCol=new TableColumn<>("Quantity");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
*/
        dictionary = new ListView<>();


        //table for cache
        TableColumn<CacheTermGui,String>termColC=new TableColumn<>("IRproject.Term");
        termColC.setMinWidth(200);
        termColC.setCellValueFactory(new PropertyValueFactory<>("term"));

        TableColumn<CacheTermGui,String>amountColc=new TableColumn<>("Quantity");
        amountColc.setMinWidth(100);
        amountColc.setCellValueFactory(new PropertyValueFactory<>("amount"));

        cache = new TableView<>();
        //cache.setItems(getCacheTermGui());
        cache.getColumns().addAll(termColC,amountColc);

        //corpus Label - constrains use (child, column, row)
        Label corpusLabel = new Label("Enter path to corpus:");
        GridPane.setConstraints(corpusLabel, 0, 0);

        //corpos path Input
        corpusInput = new TextField();
        corpusInput.setPromptText("corpus path here");
        GridPane.setConstraints(corpusInput, 1, 0);
        //browse button
        Button browseButton2 = new Button("browse");
        GridPane.setConstraints(browseButton2, 2, 0);
        browseButton2.setOnAction(e-> browserPosting());

        //posting Label
        Label postingLabel = new Label("Enter path to posting files:");
        GridPane.setConstraints(postingLabel, 0, 1);

        //posting path Input
        postingInput = new TextField();
        postingInput.setPromptText("posting path here");
        GridPane.setConstraints(postingInput, 1, 1);

        //browse button
        Button browseButton = new Button("browse");
        GridPane.setConstraints(browseButton, 2, 1);
        browseButton.setOnAction(e-> browser());

        //Stemming
        Label stemmLabel = new Label("Do you want to preform Stemming?");
        GridPane.setConstraints(stemmLabel, 1, 2);
        //ToggleGroup stemming = new ToggleGroup();
        CheckBox stemmerCheck=new CheckBox("Stemming?");
        GridPane.setConstraints(stemmerCheck, 2, 2);

        //Start
        Button startButton = new Button("START");
        GridPane.setConstraints(startButton, 1, 3);
        startButton.setOnAction(e -> {
            try {
                StartButton(postingInput.getText(), corpusInput.getText(), stemmerCheck.isSelected());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        startButton.disableProperty().bind(Bindings.createBooleanBinding( () -> !((postingInput.getText()!=null && corpusInput.getText()!=null)),
                postingInput.textProperty(), corpusInput.textProperty()));

        //option 2 for disable with listner
        /**startButton.setDisable(true); // Initally text box was empty so button was disable

         postingInput.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> ov, String t, String t1) {
        //System.out.println(t+"====="+t1);
        if(postingInput.equals("")&&corpusInput.equals(("")))
        {
        startButton.setDisable(true);
        System.out.println("what the fuck");
        }

        else
        startButton.setDisable(false);
        }
        });
         //end of listner
         */

        //RESET
        Button resetButton = new Button("RESET");
        GridPane.setConstraints(resetButton, 2, 4);
        //reset Label
        Label resetLabel = new Label("To reset the posting and dictionary:");
        GridPane.setConstraints(resetLabel, 1, 4);

        //Display cache
        Button cacheDisplayButton = new Button("Cache");
        GridPane.setConstraints(cacheDisplayButton, 2, 5);
        Label displayCacheLabel = new Label("To display the Cache:");
        GridPane.setConstraints(displayCacheLabel, 1, 5);
        cacheDisplayButton.setOnAction(e->displayCacheTable());

        //Display dictionary
        Button dictionaryDisplayButton = new Button("Dictionary");
        GridPane.setConstraints(dictionaryDisplayButton, 2, 6);
        Label displayDictionaryLabel = new Label("To display the Dictionary:");
        GridPane.setConstraints(displayDictionaryLabel, 1, 6);
        dictionaryDisplayButton.setOnAction(e->displayDictTable());

        //save the created files
        Button saveButton = new Button("SAVE");
        GridPane.setConstraints(saveButton, 4, 7);
        Label saveLabel = new Label("To save the files:");
        GridPane.setConstraints(saveLabel, 0, 7);
        saveButton.setOnAction(e -> {
            try {
                saveFiles();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        Button browseButton3 = new Button("browse");
        GridPane.setConstraints(browseButton3, 2, 7);
        browseButton3.setOnAction(e-> browserSave());

        Button browseButton4 = new Button("browse");
        GridPane.setConstraints(browseButton4, 2, 8);
        browseButton4.setOnAction(e-> browserLoad());


        //load the created files
        Button loadButton = new Button("LOAD");
        GridPane.setConstraints(loadButton, 4, 8);
        Label loadLabel = new Label("To load the files:");
        GridPane.setConstraints(loadLabel, 0, 8);
        loadButton.setOnAction(e -> {
            try {
                loadFiles();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        loadInput = new TextField();
        loadInput.setPromptText("load path here");
        GridPane.setConstraints(loadInput, 1, 8);

        saveInput = new TextField();
        saveInput.setPromptText("save path here");
        GridPane.setConstraints(saveInput, 1, 7);

        //Add everything to grid
        grid.getChildren().addAll(corpusLabel, corpusInput, postingLabel, postingInput,browseButton, startButton
                ,stemmerCheck,stemmLabel,resetButton,resetLabel,cacheDisplayButton,displayCacheLabel,saveButton,saveLabel,
                loadButton,loadLabel,browseButton2,dictionaryDisplayButton,displayDictionaryLabel,browseButton3,browseButton4,
                saveInput,loadInput);

        Scene scene = new Scene(grid, 500, 300);
        window.setScene(scene);
        window.show();
    }
    //When button is clicked, handle() gets called
    //Button click is an ActionEvent (also MouseEvents, TouchEvents, etc...)

    public void StartButton (String s1, String s2, boolean box1) throws IOException
    {
        Platform .runLater(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                if(s1.length()>0&&s2.length()>0)
                {//the fields are filled

                    pathToCorpus=s1;
                    pathToPosting=s2;
                    //change secene to alert and back to the main window to let write again
                    //RadioButton chk = (RadioButton)box1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                    if(box1) {
                        System.out.println("i checked yes");
                        doStemming=true;
                    }
                    else {
                        doStemming=false;
                        System.out.println("i checked no");
                    }

                }
                else{// the fields are missing
                    //change scene to alert and back to the main window to let write again
                    AlertBox.display("Missing Input", "Error: no paths had been written!");
                }
                //r = new ReadFile(pathToCorpus);
                r= new ReadFile("C:\\");
                int i = 0;
                while (r.nextFile<r.filesPaths.size())
                {
                    System.out.println(i);
                    Runtime instance=Runtime.getRuntime();
                    System.out.println((instance.totalMemory())/(1024*1024)+"fd");
                    r.breakToFiles();
                    System.out.println((instance.totalMemory())/(1024*1024)+"fdd");
                    P = new Parser(r.stopword,r.documents,doStemming);
                    P.ParseAll();
                    //indexer=new Indexer(P.m_terms,0,pathToPosting);
                    try {
                        indexer =new Indexer(P.m_terms,i,pathToPosting);//changed to i
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("here dosnt");
                    }
                    i++;
                    //indexer=new Indexer();//add the m_terms and the path for posting files
                }
                try {
                    indexer.mergAllFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dictionary.setItems(getDictionaryTermGui());
                cache.setItems(getCacheTermGui());
                long endTime   = System.currentTimeMillis();
                totalTime = endTime - startTime;
                System.out.println(totalTime/1000/60);
                finish=true;
            }
        });
    }
    public ObservableList<String> getDictionaryTermGui()
    {//get the items for the dictionary
        dictionary =new ListView<>();
        ObservableList<String> termsDictionary= FXCollections.observableArrayList();
        Map<String,TermDic>dict= indexer.m_Dictionary;//change to public for dictionary in indexer

        for(String str: dict.keySet())
        {
            String i=dict.get(str).getApperances()+"";
            termsDictionary.add("Term: "+dict.get(str).getName()+" , Frequency in our corpus: "+i);

        }
        //**********need to add the words from the dictionary**************
        return termsDictionary;
    }

    public ObservableList<CacheTermGui>getCacheTermGui()
    {//get the items for the dictionary
        ObservableList<CacheTermGui> termsCache= FXCollections.observableArrayList();
        cache =new TableView<>();
       // ObservableList<CacheTermGui> termsDictionary= FXCollections.observableArrayList();
        Map<String,TermCache>cachesss= indexer.m_Cache;//change to public for dictionary in indexer
        //**********need to add the words from the dictionary**************
        for(String str: cachesss.keySet())
        {
            //String i="";
            termsCache.add(new CacheTermGui(str,cachesss.get(str).getFavDocs()));
        }
        return termsCache;
    }


    public void displayDictTable()
    {//opens another window with the dictionary table display
        //if not working well try listView
        //https://stackoverflow.com/questions/27414689/a-java-advanced-text-logging-pane-for-large-output
        VBox vBox = new VBox();
        vBox.getChildren().addAll(dictionary);
        Scene dictionaryScene=new Scene(vBox);
        Stage dicwindow = new Stage();

        //Block events to other windows
        dicwindow.initModality(Modality.APPLICATION_MODAL);
        dicwindow.setTitle("THE DICTIONARY");
        dicwindow.setMinWidth(250);
        dicwindow.setScene(dictionaryScene);
        dicwindow.show();
    }
    public void displayCacheTable()
    {//opens another window with the dictionary table display
        VBox vBox = new VBox();
        vBox.getChildren().addAll(cache);
        Scene cacheScene=new Scene(vBox);
        Stage cachewindow = new Stage();

        //Block events to other windows
        cachewindow.initModality(Modality.APPLICATION_MODAL);
        cachewindow.setTitle("THE CACHE");
        cachewindow.setMinWidth(250);
        cachewindow.setScene(cacheScene);
        cachewindow.show();
    }
    public void loadFiles() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(loadInput+"myDictionary.ser"));
        FileInputStream fi2 = new FileInputStream(new File(loadInput+"myCache.ser"));
        ObjectInputStream oi = new ObjectInputStream(fi);
        ObjectInputStream zi = new ObjectInputStream(fi2);
        // Read objects
        indexer.m_Dictionary = (Map<String,TermDic>) oi.readObject();
        indexer.m_Cache=(Map<String,TermCache>) zi.readObject();
        /**List<File> selectedFiles=fc.showOpenMultipleDialog(null);
         if(selectedFiles!=null)
         {//https://www.youtube.com/watch?v=hNz8Xf4tMI4
         for(int i=0; i<selectedFiles.size();i++)
         {
         listview.getItems().add(selectedFiles.getAbsolutePath());
         }
         */
    }

    public void saveFiles() throws IOException
    {

        FileOutputStream fos = new FileOutputStream(saveInput+"\\myDictionary.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(indexer.m_Dictionary);
        oos.close();

        FileOutputStream fos1 = new FileOutputStream(saveInput+"\\myCache.ser");
        ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
        oos1.writeObject(indexer.m_Cache);
        oos1.close();

        //FileChooser fc=new FileChooser();
        //fc.setInitialDirectory((new File("C:\\")));
        //fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.t));
        //File selectedFile=fc.showSaveDialog(null);
        /**List<File> selectedFiles=fc.showOpenMultipleDialog(null);
         if(selectedFiles!=null)
         {//https://www.youtube.com/watch?v=hNz8Xf4tMI4
         for(int i=0; i<selectedFiles.size();i++)
         {
         listview.getItems().add(selectedFiles.getAbsolutePath());
         }
         */
    }

    public void browser(){
        DirectoryChooser dc=new DirectoryChooser();
        dc.setInitialDirectory((new File("C:\\")));
        File selectedFile=dc.showDialog(null);
        s=selectedFile.getAbsolutePath();
        postingInput.setText(s);

    }
    public void browserPosting()
    {
        DirectoryChooser dc=new DirectoryChooser();
        dc.setInitialDirectory((new File("C:\\")));
        File selectedFile=dc.showDialog(null);
         s=selectedFile.getAbsolutePath();
        corpusInput.setText(s);
    }

    public void browserSave()
    {
        DirectoryChooser dc=new DirectoryChooser();
        dc.setInitialDirectory((new File("C:\\")));
        File selectedFile=dc.showDialog(null);
        s=selectedFile.getAbsolutePath();
        saveInput.setText(s);
    }

    public void browserLoad()
    {
        DirectoryChooser dc=new DirectoryChooser();
        dc.setInitialDirectory((new File("C:\\")));
        File selectedFile=dc.showDialog(null);
        s=selectedFile.getAbsolutePath();
        loadInput.setText(s);
    }




    public void deleteReset(String path)
    {
        //https://docs.oracle.com/javase/tutorial/essential/io/delete.html
        String directoryPath = path;
        File file = new File(directoryPath);

        try {
            //Deleting the directory recursively.
            delete(file);
            System.out.println("Directory has been deleted recursively !");
        } catch (IOException e) {
            System.out.println("Problem occurs when deleting the directory : " + directoryPath);
            e.printStackTrace();
        }

    }

    private static void delete(File file) throws IOException {

        for (File childFile : file.listFiles()) {

            if (childFile.isDirectory()) {
                delete(childFile);
            } else {
                if (!childFile.delete()) {
                    throw new IOException();
                }
            }
        }

        if (!file.delete()) {
            throw new IOException();
        }
    }

    public void finishData()
    {//present all of the Data that is needed aout the program
        AlertBox.display("Program Information",
                "time of running:"+totalTime+
                        "number of files indexed:"+
                        "size of index in Bytes:"+
                        "size of cache in Bytes:");

    }

}