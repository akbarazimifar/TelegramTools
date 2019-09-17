package me.riguron.telegram.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class DownloadController {

    private FileService fileService;

    @Autowired
    public DownloadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/download/{target}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(@PathVariable("target") String fileName) throws IOException {

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment", fileService.getOriginalName(fileName));

        Path path = fileService.getFileLocation(fileName);

        if (!Files.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new InputStreamResource(Files.newInputStream(path)), respHeaders, HttpStatus.OK);


    }

}
