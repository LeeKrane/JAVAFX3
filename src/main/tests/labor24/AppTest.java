package labor24;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Path directory;
    private PrintStream originalSout;
    private final PrintStream redirectedSout = new PrintStream(output);
    
    @BeforeEach
    void setup() throws IOException {
        createDirectoryAndFiles();
        redirectSout();
    }
    
    private void createDirectoryAndFiles() throws IOException {
        directory = Files.createTempDirectory("filewatcherApp");
        IntStream.range(0, 10)
                .mapToObj(i -> "File_" + i)
                .map(directory::resolve)
                .forEach(this::tryCreatePath);
    }
    
    private void tryCreatePath(Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            fail();
        }
    }
    
    private void redirectSout() {
        originalSout = System.out;
        System.setOut(redirectedSout);
    }
    
    @AfterEach
    void cleanUp() throws IOException {
        deleteTestFiles();
        restoreSout();
    }
    
    private void deleteTestFiles() throws IOException {
        Files.list(directory)
                .forEach(this::tryDelete);
        Files.delete(directory);
    }
    
    private void tryDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void restoreSout() {
        System.setOut(originalSout);
    }
    
    @Test
    void main_directoryContainingFiles_allFilesWatched() throws InterruptedException {
        String[] args = {directory.toString(), "10"};
        
        FileWatcher.main(args);
        
        Thread.sleep(90);
        int lines = output.toString().split("\\n").length;
        assertEquals(10, lines);
        IntStream.range(0, 10).mapToObj(i -> "File_" + i)
                .forEach(s -> assertTrue(output.toString().contains(s)));
    }
}