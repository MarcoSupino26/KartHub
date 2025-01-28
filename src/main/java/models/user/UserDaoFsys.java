package models.user;

import exceptions.DataLoadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoFsys extends UserDao {
    private static final String FILE = "src/main/resources/fsys/users.csv";
    private static final String DELIMITER = ",";
    private static final String OWNER = "Proprietario";
    private static final String CUSTOMER = "Cliente";

    @Override
    public void addUser(String username, User user) {
        boolean fileExists = new File(FILE).exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            if (!fileExists) {
                bw.write("username,password,type");
                bw.newLine();
            }
            bw.write(formatUserAsCSV(username, user));
            bw.newLine();
        } catch (IOException e) {
            throw new DataLoadException(e.getMessage());
        }
    }

    @Override
    public User getUser(String username, String password) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return populateUser(user);
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return populateUser(user);
            }
        }
        return null;
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                 if(line.equalsIgnoreCase("username,password,type")) {
                     continue;
                }

                String[] data = line.split(DELIMITER);

                String username = data[0];
                String password = data[1];
                String type = data[2];

                User user;
                if (OWNER.equals(type)) {
                    user = new Owner(username, password, type);
                } else {
                    user = new Customer(username, password, type);
                }
                users.add(user);
            }
        } catch (IOException e) {
            throw new DataLoadException(e.getMessage());
        }
        return users;
    }

    private String formatUserAsCSV(String username, User user) {
        return String.join(DELIMITER,
                username,
                user.getPassword(),
                user.getType()
        );
    }

    private User populateUser(User user) {//Nell'implementazione CSV tracciato e prenotazioni sono state lasciate vuote
        if (OWNER.equals(user.getType())) {
            ((Owner) user).setTrack(null);
        } else if (CUSTOMER.equals(user.getType())) {
            ((Customer) user).setBookings(new ArrayList<>());
        }
        return user;
    }
}
