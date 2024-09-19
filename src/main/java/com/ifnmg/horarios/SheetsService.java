package com.ifnmg.horarios;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class SheetsService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String APPLICATION_NAME = "Horários do IFNMG Salinas";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String API_KEY = dotenv.get("API_KEY");
    private final String spreadsheetId = dotenv.get("SPREADSHEET_ID");
    private final Sheets sheetsService;
    public SheetsService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        this.sheetsService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<List<Object>> getSheetValues(String range) throws IOException {
        // Inclua a API key na chamada
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .setKey(API_KEY)  // Adiciona a chave de API
                .execute();
        return response.getValues();
    }
}
