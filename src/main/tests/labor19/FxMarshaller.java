package labor19;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

class FxMarshaller {
    private Parent root;

    public FxMarshaller(Stage primaryStage, String pathToFxml) throws IOException {
        start(primaryStage, pathToFxml);
    }

    private static List<Node> getNestedChildren(Parent parent) {
        List<Node> children = new ArrayList<>();
        parent.getChildrenUnmodifiable().forEach(node -> {
            children.add(node);
            if (node instanceof Parent)
                children.addAll(getNestedChildren((Parent) node));
        });
        if (parent instanceof SplitPane)
            children.addAll(getNestedItems((SplitPane) parent));
        return children;
    }

    private static Collection<? extends Node> getNestedItems(SplitPane pane) {
        List<Node> children = new ArrayList<>();
        pane.getItems().forEach(node -> {
            children.add(node);
            if (node instanceof Parent)
                children.addAll(getNestedChildren((Parent) node));
        });
        return children;
    }

    private void start(Stage primaryStage, String pathToFxml) throws IOException {
        URL fxml = getClass().getResource(pathToFxml);
        assertFxmlExists(fxml, pathToFxml);
        root = FXMLLoader.load(fxml);
        primaryStage.setScene(new Scene((root)));
        primaryStage.show();
    }

    private void assertFxmlExists(URL fxml, String pathToFxml) {
        if (fxml == null) {
            System.err.println("Fxml not found: " + pathToFxml);
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getById(Class<T> subclass, String id) {
        Optional<Node> requested = getNestedChildren(root).parallelStream()
                .filter(subclass::isInstance)
                .filter(node -> id.equals(node.getId()))
                .findAny();
        if (requested.isEmpty()) {
            System.err.println("FxId not found: " + id);
            System.exit(2);
        }
        return (T) requested.get();
    }
}
