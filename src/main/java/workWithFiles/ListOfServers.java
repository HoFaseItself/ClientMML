package workWithFiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListOfServers {
    private String[] serversList;


    public String[] getServersList() {
        return serversList;
    }

    public ListOfServers() throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader("C:/filesForJava/servers.txt"));
        try {
            while (reader.ready()){
                list.add(reader.readLine());
            }
            serversList = new String [list.size()];
            for (int i = 0; i < list.size(); i++) {
                serversList[i] = list.get(i);
            }
        } catch (IOException e) {
//            System.out.println("вылет здесь");
        }
    }
}
