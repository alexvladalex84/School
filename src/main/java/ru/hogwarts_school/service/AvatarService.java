package ru.hogwarts_school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts_school.model.Avatar;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }


    public void upLoadAvatar(Long studentId, MultipartFile file) throws IOException {
       Student student = studentService.findById(studentId);

        Path filePath = Path.of("/avatars", studentId + "." + getExtensions(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);                            //удаляет файл если тот существует по определенному адресу


        try (InputStream is = file.getInputStream();     //открыть входной паток и читать данные загруженного файла
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);  //место куда данные будем добавлять
            BufferedInputStream bis = new BufferedInputStream(is, 1024); //входной поток (сколько за раз будем забирать)
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);)//выходной поток (сколько за раз будем высыпть)
        {
            bis.transferTo(bos);//передача данных из входного в выходной поток
        }

        Avatar avatar = findAvatar(studentId);      //ищем студента по Id
        avatar.setStudent(student);                 //Студент к которому загружаем аватар
        avatar.setFilePath(filePath.toString());    //путь к фалу на диске
        avatar.setFileSize(file.getSize());          //размер файла
        avatar.setMediaType(file.getContentType());  //тип контент
        avatar.setData(generateImagePreview(filePath));    //для хранения в базе данных ,уменьшаем файл

         avatarRepository.save(avatar);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
        BufferedInputStream bis = new BufferedInputStream(is,1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();


            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }

    }
    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf("." ));
    }
}
