import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Task1 {

	public static void main(String[] args) throws IOException {
		String inputFile = "";
		String outputFile = "";
		for (int i = 0; i < args.length; ++i) {
			if (args[i].equals("--input")) {
				inputFile = args[i + 1];
			}
			if (args[i].equals("--output")) {
				outputFile = args[i + 1];
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<Station> stations = new ArrayList();
		stations = mapper.readValue(new File(inputFile), new TypeReference<ArrayList<Station>>() {});
		
		Map<String, Integer> stationTimesMet = new HashMap();
		Map<String, Float> sumPowersPerStation = new HashMap();
		
		for (Station station : stations) {
			Integer timesMet = stationTimesMet.get(station.stationName);
			if (timesMet == null) {
				stationTimesMet.put(station.stationName, 1);
			} else {
				stationTimesMet.put(station.stationName, timesMet + 1);
			}
			
			Float power = sumPowersPerStation.get(station.stationName);
			if (power == null) {
				sumPowersPerStation.put(station.stationName, station.power);
			} else {
				sumPowersPerStation.put(station.stationName, power + station.power);
			}
		}
		
		List<Station> results = new ArrayList();
		for (Map.Entry<String, Float> entry : sumPowersPerStation.entrySet()) {
			Station result = new Station();
			result.stationName = entry.getKey();
			result.power = entry.getValue() / stationTimesMet.get(entry.getKey());
			results.add(result);
		}
		
		mapper.writeValue(new File(outputFile), results);
	}
}

