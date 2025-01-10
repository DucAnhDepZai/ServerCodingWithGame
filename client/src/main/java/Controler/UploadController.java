package Controler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    @RequestMapping("/toUpload")
    public String home() {
        return "upload"; // Tên file HTML trong thư mục templates
    }

    @PostMapping("/upload")
    public String uploadFolder(@RequestParam("folder") MultipartFile[] files, Model model) {
        String uploadDir = "E:\\New folder\\App\\Unity\\CodingWithGame\\Assets\\Resources\\Levels\\1"; // Thư mục lưu trên server
        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdir();
        }

        try {
            for (MultipartFile file : files) {
                File destFile = new File(uploadPath, file.getOriginalFilename());

                // Tạo thư mục cha nếu cần thiết
                File parentDir = destFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs(); // Tạo thư mục cha
                }

                // Lưu file
                file.transferTo(destFile);
            }
            model.addAttribute("message", "Tải folder lên thành công!");
        } catch (IOException e) {
            model.addAttribute("message", "Lỗi khi tải lên: " + e.getMessage());
        }

        return "upload";
    }
}
