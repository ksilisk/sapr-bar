package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.config.SaprBarConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MainController.class);

    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();
    private final Map<String, File> viewsMap = new HashMap<>();

    @FXML
    private Button preProcessor, processor, postProcessor;

    public void process(MouseEvent event) {
        try {
            Button button = (Button) event.getSource();
            Parent parent = FXMLLoader.load(viewsMap.get(button.getId()).toURI().toURL());
            Stage currentStage = (Stage) button.getScene().getWindow();
            Stage newStage = new StageBuilder()
                    .title(button.getText())
                    .modality(Modality.WINDOW_MODAL)
                    .scene(new Scene(parent))
                    .build();
            currentStage.hide();
            newStage.show();
            newStage.setOnCloseRequest(e -> currentStage.show());
        } catch (Exception e) {
            log.error("Error while process button action in Main View. Action: {}", event.getSource(), e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewsMap.put(preProcessor.getId(), saprBarConfig.getPreProcessorViewFile());
        viewsMap.put(processor.getId(), saprBarConfig.getProcessorViewFile());
        viewsMap.put(postProcessor.getId(), saprBarConfig.getPostProcessorViewFile());
    }
}
