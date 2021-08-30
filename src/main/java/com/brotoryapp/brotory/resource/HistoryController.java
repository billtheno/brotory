package com.brotoryapp.brotory.resource;

import com.brotoryapp.brotory.model.History;
import com.brotoryapp.brotory.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@RestController
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @PostMapping("/history")
    public String saveHistory(@RequestBody History history){
        historyRepository.save(history);
        return "Added history with id: "+history.getId();
    }

    @GetMapping("/history")
    public List<History> getHistory(){
        return historyRepository.findAll();
    }

    @GetMapping("/history/{id}")
    public Optional<History> getHistory(@PathVariable String id){
        return historyRepository.findById(id);
    }

    @GetMapping("/username")
    public List<History> getUsername(@RequestParam(name =  "username") String username){
        return historyRepository.findUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHistory(@PathVariable String id){
        historyRepository.deleteById(id);
        return "History deleted with id: "+id;
    }

    public String readSqlite() throws IOException {
        String jdbcUrl = "jdbc:sqlite:/C:\\My Projects\\brotory\\src\\main\\resources\\History";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "select datetime(last_visit_time/1000000-11644473600,'unixepoch'),url from  urls order by last_visit_time desc";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            String data = "";

            for (int i=0; i<20; i++){
                History history = new History();
                String uniqueID = UUID.randomUUID().toString();
                String username = "billy.theno";
                String date = resultSet.getString("datetime(last_visit_time/1000000-11644473600,'unixepoch')");
                String urlTemp = resultSet.getString("url");
                String url = urlTemp.substring(0, urlTemp.indexOf("/", urlTemp.indexOf("|")+9));
                history.setId(uniqueID);
                history.setUsername(username);
                history.setUrl(url);
                history.setDate(date);
                historyRepository.save(history);
                data += url;
            }

            return data;
        } catch (SQLException e){
            e.printStackTrace();
            return "error";
        }
    }

    public String readWithScanner() throws IOException {
        String file = "src/main/resources/history_export.txt";
        Scanner scanner = new Scanner(new File(file));
        String data = "";
        for (int i=0; i<50; i++){
            History history = new History();
            String tempData = scanner.nextLine();
            String uniqueID = UUID.randomUUID().toString();
            String username = "billy.theno";
            String date = tempData.substring(0, tempData.indexOf("|"));
            String url = tempData.substring(tempData.indexOf("|")+1, tempData.indexOf("/", tempData.indexOf("|")+9));
            history.setId(uniqueID);
            history.setUsername(username);
            history.setUrl(url);
            history.setDate(date);
            historyRepository.save(history);
            data += url;
        }
        return data;
    }

    @GetMapping("/history/read")
    public String readHistory() throws IOException {
        return readSqlite();
    }

}
