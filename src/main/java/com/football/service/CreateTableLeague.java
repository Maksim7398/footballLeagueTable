package com.football.service;

import com.football.persist.entity.Team;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CreateTableLeague {

    public void createTable(Map<String, Map<LocalDateTime, List<Team>>> result) {
        File file = new File("src/main/resources/table.docx");
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
            XWPFDocument doc = new XWPFDocument();
            XWPFTable table = doc.createTable();
            XWPFTableRow tableRow1 = table.getRow(0);
            tableRow1.getCell(0).setText("ID");
            tableRow1.addNewTableCell().setText("КОМАНДА");
            tableRow1.addNewTableCell().setText("ПРОПУЩЕННЫЕ ГОЛЛЫ");
            tableRow1.addNewTableCell().setText("ЗАБИТЫЕ ГОЛЛЫ");
            tableRow1.addNewTableCell().setText("КОЛИЧЕСТВО ОЧКОВ");
            tableRow1.addNewTableCell().setText("СЧЁТ");

            result.forEach((key, value) -> value.values().forEach(t -> {
                new HashSet<>(t).forEach(v -> {
                    XWPFTableRow tableRow2 = table.createRow();
                    tableRow2.getCell(0).setText(String.valueOf(v.getId()));
                    tableRow2.getCell(1).setText(v.getName());
                    tableRow2.getCell(2).setText(String.valueOf(v.getScipGoals()));
                    tableRow2.getCell(3).setText(String.valueOf(v.getTotalGoals()));
                    tableRow2.getCell(4).setText(String.valueOf(v.getPoints()));
                    tableRow2.getCell(5).setText(key);
                });
            }));

            doc.write(new FileOutputStream("src/main/resources/" + (int) (Math.random() * 10) + " table.docx"));

            doc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

