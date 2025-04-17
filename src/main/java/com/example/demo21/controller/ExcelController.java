package com.example.demo21.controller;

import com.example.demo21.service.Implementation.ExcelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
@Tag(name = "Excel", description = "Excel file processing API endpoints")
public class ExcelController {

    @Autowired
    private ExcelServiceImpl excelService;

    @Operation(summary = "Upload and process Excel file", description = "Upload an Excel file, process it, and download the updated version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel file processed successfully",
                    content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/uploadAndDownload")
    public ResponseEntity<byte[]> uploadAndDownloadExcel(
            @Parameter(description = "Excel file to be processed") @RequestParam("file") MultipartFile file) {
        try {
            byte[] updatedFile = excelService.processAndDownloadExcel(file); // Process the Excel and return as byte array

            // Set headers to download the file as Excel
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=updated_file.xlsx"); // Specify the file name
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Set Excel MIME type

            // Return the updated Excel file
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(updatedFile);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get images from folder", description = "Retrieve a list of image details from the specified folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved image list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/folder")
    public List<Map> getImagesInFolder(
            @Parameter(description = "Name of the folder containing images") @RequestParam String folderName) throws Exception {
        return excelService.getImagesInFolder(folderName);
    }
}
