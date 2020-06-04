package labor24;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class FileWatcher implements Runnable {
	private File file;
	private int loggingInterval;
	private FileTime oldLastModificationTime;
	
	public FileWatcher (Path filePath, int loggingInterval) throws IOException {
		file = filePath.toFile();
		this.loggingInterval = loggingInterval;
		oldLastModificationTime = Files.getLastModifiedTime(filePath);
	}
	
	@Override
	public void run () {
		try {
			System.out.println("File " + file.getName() + " now watching.");
			while (file.exists()) {
				FileTime newLastModificationTime = Files.getLastModifiedTime(file.toPath());
				
				if (!oldLastModificationTime.equals(newLastModificationTime)) {
					System.out.println("File " + file.getName() + " watched.");
					System.out.println("File " + file.getName() + " changed.");
				}
				
				oldLastModificationTime = newLastModificationTime;
				Thread.sleep(loggingInterval);
			}
		} catch (InterruptedException | IOException e) {
			System.err.println(e + ": " + e.getMessage());
		} finally {
			System.out.println("Can no longer find " + file.getName());
		}
	}
	
	public static void main (String[] args) {
		Thread[] watchers = new Thread[] {};
		
		if (args.length != 2)
			throw new IllegalArgumentException("There are not exactly 2 arguments!");
		
		if (!Files.isDirectory(Paths.get(args[0])))
			throw new IllegalArgumentException(args[0] + " is no directory!");
		
		File directory = new File(args[0]);
		File[] files = directory.listFiles();
		int loggingInterval = Integer.parseInt(args[1]);
		
		if (files != null) {
			watchers = new Thread[files.length];
			
			for (int i = 0; i < files.length; i++) {
				try {
					watchers[i] = new Thread(new FileWatcher(files[i].toPath(), loggingInterval));
				} catch (IOException e) {
					System.err.println(e + ": " + e.getMessage());
				}
			}
		}
		
		for (Thread watcher : watchers)
			watcher.start();
		
		/*
		The following sleep is to modify how long the files should be watched.
		Other solutions would be to watch until all files are deleted or maybe until the Process is shut down.
		I don't know which would be a good idea, so I just took a fixed time.
		 */
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.err.println(e + ": " + e.getMessage());
		}
	}
}
