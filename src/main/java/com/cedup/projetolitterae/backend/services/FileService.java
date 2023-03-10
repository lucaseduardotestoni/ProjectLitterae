package com.cedup.projetolitterae.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private String dirBaseArquivo;

    public String salvaArquivo(String relativePath, MultipartFile file) throws IOException {
        File diretorio = new File(dirBaseArquivo + relativePath);

        if(diretorio.exists()){
            File[] arquivos = diretorio.listFiles();
            for(File arquivo : arquivos){
                arquivo.delete();
            }
            diretorio.delete();
        }

        diretorio.mkdirs();

        Path pathFile = Path.of(diretorio.getAbsolutePath(), file.getOriginalFilename().replace(" ", ""));
        Files.copy(file.getInputStream(), pathFile);

        return relativePath+file.getOriginalFilename().replace(" ", "");
    }

}
