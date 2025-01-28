package models.event;

import exceptions.DataLoadException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class KartEventDaoFsys extends KartEventDao{
    private static final String FILE = "src/main/resources/fsys/kartevents.csv";
    private static final String DELIMITER = ",";

    @Override
    public List<KartEvent> getEventsByTrack(String trackName){
        List<KartEvent> events = new ArrayList<>();
        return events.stream()
                .filter(event -> event.getTrackName() != null && event.getTrackName().equalsIgnoreCase(trackName))
                .toList();
    }

    @Override
    public List<KartEvent> getAllEvents() {
        List<KartEvent> events = new ArrayList<>();
        File file = new File(FILE);

        if(!file.exists()){
            return events;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(DELIMITER);

                KartEvent event = new KartEvent(data[0]);
                event.setEventType(data[1]);
                event.setCost(Double.parseDouble(data[2]));
                event.setEventDate(LocalDate.parse(data[3]));
                event.setEventTime(LocalTime.parse(data[4]));
                event.setTickets(Integer.parseInt(data[5]));
                event.setTrackName(data[6]);

                events.add(event);
            }
        }catch (IOException e){
            throw new DataLoadException(e.getMessage());
        }
        return events;
    }

    @Override
    public void addKartEvent(KartEvent event) {
        boolean fileExists = new File(FILE).exists();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))){
            if(!fileExists){
                bw.write("eventName,eventType,cost,eventDate,eventTime,tickets,trackName");
                bw.newLine();
            }

            bw.write(formatKartEventForCSV(event));
            bw.newLine();
        }catch (IOException e){
            throw new DataLoadException(e.getMessage());
        }
    }

    private String formatKartEventForCSV(KartEvent event){
        return String.join(DELIMITER,
                event.getEventName(),
                event.getEventType(),
                String.valueOf(event.getCost()),
                event.getEventDate().toString(),
                event.getEventTime().toString(),
                String.valueOf(event.getTickets()),
                event.getTrackName()
        );
    }
}
