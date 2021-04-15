package com.swathingblock.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyWatchService {
	
	public static void start() throws IOException, InterruptedException {
		final Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir"));
		System.out.println(path);
		try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
			
			final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				final WatchKey wk = watchService.take();
				for (WatchEvent<?> event : wk.pollEvents()) {
					//we only register "ENTRY_MODIFY" so the context is always a Path.
					final Path changed = (Path) event.context();
					System.out.println(changed);
					if (changed.endsWith("input.txt")) {
						System.out.println("My file has changed -> " + changed.toAbsolutePath().toString());
						java.io.File f = new java.io.File(changed.toAbsolutePath().toString());
						
						
						StringBuilder resultStringBuilder = new StringBuilder();
						try (BufferedReader br  = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
							String line;
							while ((line = br.readLine()) != null) {
								resultStringBuilder.append(line).append("\n");
							}
						}
						System.out.println(resultStringBuilder.toString());
						JsonParser parser = new JsonParser();
						try {
						JsonObject obj = parser.parse(resultStringBuilder.toString()).getAsJsonObject();
						Controller.process(obj);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
				// reset the key
				boolean valid = wk.reset();
				if (!valid) {
					System.out.println("Key has been unregisterede");
				}
			}
		}
		
	}
	
}
