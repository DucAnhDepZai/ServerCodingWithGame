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
public class ClientController {
    @Autowired
    private HttpSession session;
    @GetMapping
    public String getSignIn(){
        session.invalidate();
        return "signIn";
    }
    @GetMapping("/signUp")
    public String getSignUp(){
        session.invalidate();
        return "signUp";
    }
    @GetMapping("/signIn")
    public String getSignIn2(){
        session.invalidate();
        return "signIn";
    }
    @PostMapping("/signIn")
    public String handleSignIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/user/authenticate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                User.class
        );
        User curUser = responseEntity.getBody();

        if (curUser.getId() != 0  ) {
            if(curUser.getRole().equals("user")) {
                int id = curUser.getId();
                session.setAttribute("id_user", id);
                return "redirect:/courses";
            }
            else{
                int id = curUser.getId();
                session.setAttribute("id_admin", id);
                return "redirect:/manage";
            }
        }
        else {
            model.addAttribute("error", "Email hoặc mật khẩu không chính xác!");
            return "signIn";
        }

    }

    @PostMapping("/signUp")
    public String handleSignUp(@RequestParam("name") String name,@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setRole("user");
        user.setPassword(password);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/user";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                User.class
        );
        User curUser = responseEntity.getBody();
        if(curUser.getId() != 0){
            model.addAttribute("message", "Đăng ký thành công!");
            return "signIn";
        }
        else {
            model.addAttribute("error", "Đăng ký thất bại!");
            return "signUp";
        }

    }

    @GetMapping("/courses")
    public String getCourse(Model model){
        Integer userId = (Integer) session.getAttribute("id_user");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = "http://localhost:8080/api/course";
        ResponseEntity<Course[]> responseEntity2 = restTemplate2.exchange(
                url2,
                HttpMethod.GET,
                null,
                Course[].class
        );
        List<Course> courses = Arrays.asList(responseEntity2.getBody());
        model.addAttribute("courses", courses);
        return "selectCourse";
    }

    @GetMapping("/courses/{id}")
    public String getCourseDetail(@PathVariable int id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("id_user");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/course/" + id;
        ResponseEntity<Course> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Course.class
        );
        Course course = responseEntity.getBody();
        RestTemplate restTemplate2 = new RestTemplate();
        String userCourseUrl = "http://localhost:8080/api/user-course/find?userId=" + userId + "&courseId=" + id;
        ResponseEntity<UserCourse> responseEntity2 = restTemplate2.exchange(
                userCourseUrl,
                HttpMethod.GET,
                null,
                UserCourse.class
        );
        UserCourse userCourse = responseEntity2.getBody();
        if(userCourse.getId() == 0){
            model.addAttribute("userCourse", null);
        }
        else {
            model.addAttribute("userCourse", userCourse);
        }
        model.addAttribute("course", course);


        return "courseDetail";
    }

    @GetMapping("/course/search")
    public List<Course> searchCourses(@RequestParam String name) {
        RestTemplate restTemplate3 = new RestTemplate();
        String userCourseUrl = "http://localhost:8080/api/course/search?name=" + name;
        ResponseEntity<Course[]> responseEntity3 = restTemplate3.exchange(
                userCourseUrl,
                HttpMethod.GET,
                null,
                Course[].class
        );
        return Arrays.asList(responseEntity3.getBody());
    }

    @GetMapping("/register-course/{id}")
    public String registerCourse(@PathVariable int id, Model model) {
        UserCourse userCourse = new UserCourse();
        userCourse.setUser_id((Integer) session.getAttribute("id_user"));
        userCourse.setCourse_id(id);
        userCourse.setStatus("Ok");
        RestTemplate restTemplate = new RestTemplate();
        String externalServiceUrl = "http://localhost:8080/api/user-course";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserCourse> request = new HttpEntity<>(userCourse, headers);
        ResponseEntity<UserCourse> responseEntity = restTemplate.exchange(
                externalServiceUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<UserCourse>() {}
        );
        UserCourse responseUserCourse = responseEntity.getBody();

        RestTemplate restTemplate2 = new RestTemplate();
        String url = "http://localhost:8080/api/userlesson/register/" + responseUserCourse.getId();
        ResponseEntity<UserLesson[]> responseEntity2 = restTemplate2.exchange(
                url,
                HttpMethod.GET,
                null,
                UserLesson[].class
        );
        List<UserLesson> lessons = Arrays.asList(responseEntity2.getBody());

        return "redirect:/courses/" + id;
    }
    @GetMapping("/continue-course/{id}")
    public String continueCourse(@PathVariable int id, Model model){
        Integer userId = (Integer) session.getAttribute("id_user");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/lesson/course/" + id;
        ResponseEntity<Lesson[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Lesson[].class
        );
        List<Lesson> lessons = Arrays.asList(responseEntity.getBody());



        RestTemplate restTemplate3 = new RestTemplate();
        String userCourseUrl = "http://localhost:8080/api/user-course/find?userId=" + userId + "&courseId=" + id;
        ResponseEntity<UserCourse> responseEntity3 = restTemplate3.exchange(
                userCourseUrl,
                HttpMethod.GET,
                null,
                UserCourse.class
        );
        UserCourse userCourse = responseEntity3.getBody();
        session.setAttribute("curUserCourse",userCourse);

        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = "http://localhost:8080/api/userlesson/user-course/" + userCourse.getId();
        ResponseEntity<UserLesson[]> responseEntity2 = restTemplate2.exchange(
                url2,
                HttpMethod.GET,
                null,
                UserLesson[].class
        );
        List<UserLesson> listUserLesson = Arrays.asList(responseEntity2.getBody());

        List<UserLessonDTO> listLesson = new ArrayList<>();
        for(Lesson lesson : lessons){
            boolean isComplete = false;
            for (UserLesson userLesson : listUserLesson){
                if (lesson.getId() == userLesson.getId_lesson() && userLesson.isComplete()){
                    isComplete = true;
                }
            }
            UserLessonDTO userLessonDTO = new UserLessonDTO();
            userLessonDTO.setLesson(lesson);
            userLessonDTO.setComplete(isComplete);
            System.out.println(userLessonDTO.getLesson().getName() + " " + userLessonDTO.isComplete());
            listLesson.add(userLessonDTO);
        }

        model.addAttribute("userlessons", listLesson);
        return "selectLesson";
    }
    @GetMapping("/lesson")
    public String getLessonPage(@RequestParam("id") String id, Model model) {
        Integer userId = (Integer) session.getAttribute("id_user");
        UserCourse userCourse = (UserCourse)session.getAttribute("curUserCourse");
        if (userId == null || userCourse == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/lesson/" + id;
        ResponseEntity<Lesson> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Lesson.class
        );
        Lesson lessonDB = responseEntity.getBody();

        int idGameLesson = lessonDB.getId_game_lesson();
        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = "http://localhost:8080/api/gamelesson/" + idGameLesson;
        ResponseEntity<GameLesson> responseEntity2 = restTemplate2.exchange(
                url2,
                HttpMethod.GET,
                null,
                GameLesson.class
        );
        GameLesson gameLesson = responseEntity2.getBody();
        System.out.println(gameLesson.getLesson_url());
        LessonDTO lesson = new LessonDTO();
        lesson.setId(lessonDB.getId());
        lesson.setMission(lessonDB.getMission());
        lesson.setGuild(lessonDB.getGuild());
        lesson.setName(lessonDB.getName());
        lesson.setOrder(lessonDB.getOrder());
        lesson.setLanguage(lessonDB.getLanguage());
        lesson.setId_course(lessonDB.getId_course());
        lesson.setGameLesson(gameLesson);
        model.addAttribute("lesson", lesson);
        session.setAttribute("curLesson", lessonDB);

        return "lesson";
    }

    @GetMapping("/lesson/next")
    public String getNextLessonById(Model model){
        Integer userId = (Integer) session.getAttribute("id_user");
        if (userId == null) {
            return "redirect:/signIn";
        }
        RestTemplate restTemplate = new RestTemplate();
        Lesson curLesson = (Lesson)session.getAttribute("curLesson");
        String url = "http://localhost:8080/api/lesson/next/" + curLesson.getId();
        ResponseEntity<Lesson> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Lesson.class
        );
        Lesson lessonDB = responseEntity.getBody();

        int idGameLesson = lessonDB.getId_game_lesson();
        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = "http://localhost:8080/api/gamelesson/" + idGameLesson;
        ResponseEntity<GameLesson> responseEntity2 = restTemplate.exchange(
                url2,
                HttpMethod.GET,
                null,
                GameLesson.class
        );
        GameLesson gameLesson = responseEntity2.getBody();
        System.out.println(gameLesson.getLesson_url());
        LessonDTO lesson = new LessonDTO();
        lesson.setId(lessonDB.getId());
        lesson.setMission(lessonDB.getMission());
        lesson.setGuild(lessonDB.getGuild());
        lesson.setName(lessonDB.getName());
        lesson.setOrder(lessonDB.getOrder());
        lesson.setId_course(lessonDB.getId_course());
        lesson.setLanguage(lessonDB.getLanguage());
        lesson.setGameLesson(gameLesson);
        model.addAttribute("lesson", lesson);
        session.setAttribute("curLesson", lessonDB);
        return "lesson";
    }

    @PutMapping("/update/{id_lesson}")
    @ResponseBody
    public ResponseEntity<String> updateLessonComplete(@PathVariable int id_lesson){
        UserCourse userCourse = (UserCourse)session.getAttribute("curUserCourse");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/userlesson/" + "/user-course/" + userCourse.getId() + "/lesson/" + id_lesson;
        ResponseEntity<UserLesson> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                UserLesson.class
        );
        UserLesson userLesson = responseEntity.getBody();
        userLesson.setComplete(true);

        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserLesson> request = new HttpEntity<>(userLesson, headers);
        String url2 = "http://localhost:8080/api/userlesson/" + userLesson.getId();
        ResponseEntity<UserLesson> responseEntity2 = restTemplate2.exchange(
                url2,
                HttpMethod.PUT,
                request,
                UserLesson.class
        );
        userLesson = responseEntity2.getBody();
        if(userLesson.isComplete())
            return ResponseEntity.ok("Update success!");
        else return ResponseEntity.ofNullable("Error!");
    }
    @GetMapping("/processGetCode")
    @ResponseBody
    public ProcessRequest getCode() {
        Lesson curLesson = (Lesson)session.getAttribute("curLesson");
        ProcessRequest response = new ProcessRequest();
        response.setLanguage(curLesson.getLanguage());
        FileDTO file = new FileDTO();

        file.setName("main");
        file.setContent(curLesson.getInit_code());
        List<FileDTO> files = Arrays.asList(file);
        response.setFiles(files);
        Result result = new Result();
        result.setSuccess(true);
        result.setOutput("");
        response.setResult(result);

        return response;
    }
    @PostMapping("/processRunCode/{id_lesson}")
    @ResponseBody
    public ProcessRequest runCode(@RequestBody ProcessRequest processRequest, @PathVariable int id_lesson) {

        UserCourse userCourse = (UserCourse)session.getAttribute("curUserCourse");
        System.out.println("Id course: " + userCourse.getId() + " lesson id: " + id_lesson);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/userlesson/" + "/user-course/" + userCourse.getId() + "/lesson/" + id_lesson;
        ResponseEntity<UserLesson> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                UserLesson.class
        );
        UserLesson userLesson = responseEntity.getBody();
        List<FileDTO> files = processRequest.getFiles();
        Submission submission = new Submission();
        submission.setLanguage("cpp");
        submission.setSource_code(files.get(0).getContent());
        submission.setId_userLesson(userLesson.getId());
        RestTemplate restTemplate2 = new RestTemplate();
        String url2 = "http://localhost:8080/process/run_code";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Submission> request2 = new HttpEntity<>(submission, headers);
        ResponseEntity<Submission> responseEntity2 = restTemplate.exchange(
                url2,
                HttpMethod.POST,
                request2,
                new ParameterizedTypeReference<Submission>() {}
        );
        Submission responseSubmit = responseEntity2.getBody();
        System.out.println("======= Here is ouput: " + responseSubmit.getResult());
        System.out.println("======= Here is success: " + responseSubmit.isSuccess());
        Result result = new Result();
        result.setOutput(responseSubmit.getResult());
        result.setSuccess(responseSubmit.isSuccess());
        processRequest.setResult(result);
        return processRequest;
    }
}