package com.football.service;

import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CreateTableLeague {

    public void createTableMatch(final List<MatchEntity> matchEntityList) {
        try (final XWPFDocument doc = new XWPFDocument();
             final FileOutputStream fileOutputStream =
                     new FileOutputStream(
                             "src/main/resources/result/" + (int) (Math.random() * 10) + " table.docx"
                     )) {

            XWPFTable table = doc.createTable();
            XWPFTableRow tableRow1 = table.getRow(0);


            tableRow1.getCell(0).setText("Дата матча ");
            tableRow1.addNewTableCell().setText("КОМАНДА1 ");
            tableRow1.addNewTableCell().setText("ГОЛЫ ");
            tableRow1.addNewTableCell().setText("ГОЛЫ ");
            tableRow1.addNewTableCell().setText("КОМАНДА2 ");
            tableRow1.addNewTableCell().setText("СЧЁТ ");

            matchEntityList.forEach(t -> {
                XWPFTableRow tableRow2 = table.createRow();
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("ru"));
                tableRow2.getCell(0).setText(t.getDateMatch().format(formatter));
                tableRow2.getCell(1).setText(t.getHomeTeamEntity().getName());
                tableRow2.getCell(2).setText(String.valueOf(t.getHomeGoals()));
                tableRow2.getCell(3).setText(String.valueOf(t.getAwayGoals()));
                tableRow2.getCell(4).setText(t.getAwayTeamEntity().getName());
                tableRow2.getCell(5).setText(t.getHomeGoals() + ":" + t.getAwayGoals());
            });
            doc.write(fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTableLeague(final List<TeamEntity> teamEntities) {
        try (final XWPFDocument doc = new XWPFDocument();
             final FileOutputStream fileOutputStream =
                     new FileOutputStream(
                             "src/main/resources/result/standings" + (int) (Math.random() * 10) + " table.docx"
                     )) {

            XWPFTable table = doc.createTable();
            XWPFTableRow tableRow1 = table.getRow(0);
            tableRow1.getCell(0).setText("КОМАНДА");
            tableRow1.addNewTableCell().setText("ПРОПУЩЕННЫЕ ГОЛЛЫ");
            tableRow1.addNewTableCell().setText("ЗАБИТЫЕ ГОЛЛЫ");
            tableRow1.addNewTableCell().setText("КОЛИЧЕСТВО ИГР");
            tableRow1.addNewTableCell().setText("КОЛИЧЕСТВО ОЧКОВ");
            tableRow1.addNewTableCell().setText("МЕСТО");

            teamEntities.forEach((v) -> {
                XWPFTableRow tableRow2 = table.createRow();
                tableRow2.getCell(0).setText(v.getName());
                tableRow2.getCell(1).setText(String.valueOf(v.getScipGoals()));
                tableRow2.getCell(2).setText(String.valueOf(v.getTotalGoals()));
                tableRow2.getCell(3).setText(String.valueOf(v.getNumberOfGames()));
                tableRow2.getCell(4).setText(String.valueOf(v.getPoints()));
                tableRow2.getCell(5).setText(String.valueOf(teamEntities.indexOf(v) + 1));
            });

            doc.write(fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

