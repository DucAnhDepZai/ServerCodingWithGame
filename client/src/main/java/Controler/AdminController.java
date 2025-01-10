package Controler;

import com.example.client.Entity.DTO.*;
import com.example.client.Entity.Model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AdminController {
    private static final String BASE_API_URL = "http://localhost:8080/api";

    @Autowired
    private HttpSession session;
    @GetMapping("/manage")
    public String toManage(){
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "signIn";
        }
        return "manage";
    }
    @GetMapping("/manageCourse")
    public String manageCourse(Model model){
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = BASE_API_URL + "/course";
        ResponseEntity<Course[]> responseEntity2 = restTemplate2.exchange(
                url2,
                HttpMethod.GET,
                null,
                Course[].class
        );
        List<Course> courses = Arrays.asList(responseEntity2.getBody());
        model.addAttribute("courses", courses);
        return "manageCourse";
    }
    @GetMapping("/manageLesson")
    public String manageLesson(Model model){
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = BASE_API_URL + "/lesson";
        ResponseEntity<Lesson[]> responseEntity2 = restTemplate2.exchange(
                url2,
                HttpMethod.GET,
                null,
                Lesson[].class
        );
        List<Lesson> lessons = Arrays.asList(responseEntity2.getBody());
        model.addAttribute("lessons", lessons);
        return "manageLesson";
    }
    @GetMapping("/createCourse")
    public String createCourseForm(Model model) {
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "redirect:/signIn";
        }
        model.addAttribute("course", new Course());
        return "createCourse";
    }

    @PostMapping("/createCourse")
    public String createCourse(@ModelAttribute Course course) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_API_URL + "/course";
        restTemplate.postForEntity(url, course, Course.class);
        return "redirect:/manageCourse";
    }

    @GetMapping("/editCourse/{id}")
    public String editCourseForm(@PathVariable int id, Model model) {
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_API_URL + "/course/" + id;
        ResponseEntity<Course> response = restTemplate.getForEntity(url, Course.class);
        model.addAttribute("course", response.getBody());
        return "editCourse";
    }

    @PostMapping("/editCourse/{id}")
    public String editCourse(@PathVariable int id, @ModelAttribute Course course) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_API_URL + "/course/" + id;
        HttpEntity<Course> requestUpdate = new HttpEntity<>(course);
        restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);
        return "redirect:/manageCourse";
    }

    @GetMapping("/createLesson")
    public String createLessonForm(Model model) {
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "redirect:/signIn";
        }
        model.addAttribute("lesson", new Lesson());
        return "createLesson";
    }


    @GetMapping("/editLesson/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Integer userId = (Integer) session.getAttribute("id_admin");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_API_URL + "/lesson/" + id;
        ResponseEntity<Lesson> response = restTemplate.getForEntity(url, Lesson.class);
        model.addAttribute("lesson", response.getBody());
        return "editLesson";
    }

    @PostMapping("/editLesson/{id}")
    public String editLesson(@PathVariable int id, @ModelAttribute Lesson lesson) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_API_URL + "/lesson/" + id;
        HttpEntity<Lesson> requestUpdate = new HttpEntity<>(lesson);
        restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);
        return "redirect:/manageLesson";
    }
}