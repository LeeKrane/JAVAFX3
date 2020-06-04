package labor24;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileWatcherTest {

    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalSout;
    private final PrintStream redirectedSout = new PrintStream(output);
    private final Path path = Path.of("test.file");

    @BeforeEach
    void setup() throws IOException {
        createFile();
        redirectSout();
    }

    private void createFile() throws IOException {
        Files.createFile(path);
    }

    private void redirectSout() {
        originalSout = System.out;
        System.setOut(redirectedSout);
    }

    @AfterEach
    void cleanUp() throws IOException {
        restoreSout();
        deleteFile();
    }

    private void restoreSout() {
        System.setOut(originalSout);
    }

    private void deleteFile() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void constructor_fileDoesNotExist_exception() {
        Path path = Path.of("Z:", "shouldNotExist.file");
        assertThrows(IOException.class, () -> new FileWatcher(path, 1));
    }

    @Test
    void run_fileDeleted_logged() throws IOException, InterruptedException {
        FileWatcher watcher = new FileWatcher(path, 10);

        Thread thread = new Thread(watcher);
        thread.start();
        Files.deleteIfExists(path);

        Thread.sleep(15);
        assertTrue(output.toString().contains("Can no longer find " + path));
    }

    @Test
    void run_fileDeleted_threadTerminated() throws IOException, InterruptedException {
        FileWatcher watcher = new FileWatcher(path, 10);

        Thread thread = new Thread(watcher);
        thread.start();
        Files.deleteIfExists(path);

        Thread.sleep(15);
        assertFalse(thread.isAlive());
    }

    @Test
    void run_fileChanged_changeLogged() throws IOException, InterruptedException {
        FileWatcher watcher = new FileWatcher(path, 10);

        new Thread(watcher).start();
        // Without closing/flushing the BufferedWriter the changes won't be accepted, which is why I added a flush().
        Files.newBufferedWriter(path).append("someContent").flush();

        Thread.sleep(15);
        assertTrue(output.toString().contains(path + " watched"));
        assertTrue(output.toString().contains(path + " changed"));
    }
}