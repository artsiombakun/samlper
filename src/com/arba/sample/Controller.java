package com.arba.sample;

import com.arba.sample.model.MemoryItem;
import com.arba.sample.model.ThreadInfo;
import com.arba.sample.rendering.AllocatedObjectsDrawer;
import com.arba.sample.rendering.MemoryUsageDrawer;
import com.arba.sample.rendering.ThreadsDrawer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private Integer pid;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private TableView<MemoryItem> histoTable;
    
    @FXML
    private TableColumn<MemoryItem, String> nameCol;

    @FXML
    private TableColumn<MemoryItem, Double> percentCol;

    @FXML
    private TableColumn<MemoryItem, Integer> bytesCol;

    @FXML
    private TableColumn<MemoryItem, Integer> itemsCol;
    
    @FXML
    public TableView<ThreadInfo> threadsTable;

    @FXML
    public TableColumn<ThreadInfo, String> threadNameCol;

    @FXML
    public TableColumn<ThreadInfo, String> threadStateCol;

    public Controller() {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nameCol.setCellValueFactory(cell -> cell.getValue().fullNameProperty());
        percentCol.setCellValueFactory(cell -> cell.getValue().weightProperty().asObject());
        bytesCol.setCellValueFactory(cell -> cell.getValue().bytesProperty().asObject());
        itemsCol.setCellValueFactory(cell -> cell.getValue().instancesProperty().asObject());

        threadNameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        threadStateCol.setCellValueFactory(cell -> {
            List<String> l = cell.getValue().getState();
            return new SimpleStringProperty(l.get(l.size() - 1));
        });
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
        new MemoryUsageDrawer(canvas, pid).start();
        Platform.runLater(new AllocatedObjectsDrawer(histoTable, pid));
        Platform.runLater(new ThreadsDrawer(threadsTable, pid));
    }

}
