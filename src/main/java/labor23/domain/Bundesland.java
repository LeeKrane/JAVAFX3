package labor23.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class Bundesland {
	private String wappen;
	private String name;
	private String hauptstadt;
	private String landeshauptmann;
	private Map<String, Integer> sitze;
	private int einwohner;
	private double flaeche;
	
	public Bundesland (String wappen, String name, String hauptstadt, String landeshauptmann, Map<String, Integer> sitze, int einwohner, double flaeche) {
		this.wappen = wappen;
		this.name = name;
		this.hauptstadt = hauptstadt;
		this.landeshauptmann = landeshauptmann;
		this.sitze = sitze;
		this.einwohner = einwohner;
		this.flaeche = flaeche;
	}
	
	public static List<Bundesland> readFile (InputStream is) {
		List<Bundesland> bundeslandList = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			List<String> parteien = new ArrayList<>();
			String line;
			String[] split;
			Map<String, Integer> sitzverteilung;
			List<String> aktuelleSitze;
			Pattern pattern = Pattern.compile("(\\w+\\.\\w+),([\\w\\süöä\\-.]+,){3}(\\d*,){5}([\\wÖ]+\\s\\d+)?,\\d*,\\d+,\\d+(\\.\\d+)?");
			
			if (reader.ready())
				parteien.addAll(Arrays.asList(reader.readLine().split(",")).subList(4, 11));
			
			while (reader.ready()) {
				line = reader.readLine();
				if (!pattern.matcher(line).matches())
					continue;
					
				split = line.split(",");
				sitzverteilung = new TreeMap<>();
				aktuelleSitze = new ArrayList<>(Arrays.asList(split).subList(4, 11));
				
				for (int i = 0; i < parteien.size(); i++) {
					if (i == parteien.indexOf("Andere") && !aktuelleSitze.get(i).isEmpty()) {
						String[] andere = aktuelleSitze.get(i).split(" ");
						if (andere.length == 2) {
							int anzahl;
							try {
								anzahl = Integer.parseInt(andere[1]);
							} catch (NumberFormatException e) {
								continue;
							}
							sitzverteilung.put(andere[0], anzahl);
						}
					} else {
						int anzahl;
						try {
							anzahl = Integer.parseInt(aktuelleSitze.get(i));
						} catch (NumberFormatException e) {
							continue;
						}
						sitzverteilung.put(parteien.get(i), anzahl);
					}
				}
				
				bundeslandList.add(new Bundesland(split[0], split[1], split[2], split[3], sitzverteilung, Integer.parseInt(split[11]) , Double.parseDouble(split[12])));
			}
		} catch (IOException e) {
			System.err.println(e + ": " + e.getMessage());
		}
		
		return bundeslandList;
	}
	
	public String getWappen () {
		return wappen;
	}
	
	public void setWappen (String wappen) {
		this.wappen = wappen;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getHauptstadt () {
		return hauptstadt;
	}
	
	public void setHauptstadt (String hauptstadt) {
		this.hauptstadt = hauptstadt;
	}
	
	public String getLandeshauptmann () {
		return landeshauptmann;
	}
	
	public void setLandeshauptmann (String landeshauptmann) {
		this.landeshauptmann = landeshauptmann;
	}
	
	public Map<String, Integer> getSitze () {
		return sitze;
	}
	
	public void setSitze (Map<String, Integer> sitze) {
		this.sitze = sitze;
	}
	
	public int getEinwohner () {
		return einwohner;
	}
	
	public void setEinwohner (int einwohner) {
		this.einwohner = einwohner;
	}
	
	public double getFlaeche () {
		return flaeche;
	}
	
	public void setFlaeche (double flaeche) {
		this.flaeche = flaeche;
	}
}
