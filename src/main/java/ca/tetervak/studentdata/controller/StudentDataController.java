package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.data.entities.Program;
import ca.tetervak.studentdata.data.entities.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.NoSuchElementException;

import ca.tetervak.studentdata.data.repositories.StudentDataRepository;
import ca.tetervak.studentdata.data.repositories.ProgramDataRepository;

@Controller
@RequestMapping("/students")
public class StudentDataController {

    private final Logger log = LoggerFactory.getLogger(StudentDataController.class);

    private final StudentDataRepository studentDataRepository;
    private final ProgramDataRepository programDataRepository;

    public StudentDataController(
            StudentDataRepository studentDataRepository,
            ProgramDataRepository programDataRepository
    ) {
        this.studentDataRepository = studentDataRepository;
        this.programDataRepository = programDataRepository;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "StudentsIndex";
    }

    @GetMapping("/list-students")
    public ModelAndView listStudents() {
        log.trace("listStudents() is called");
        List<Student> students = studentDataRepository.findAll();
        log.debug("list size = " + students.size());
        return new ModelAndView("ListStudents", "students", students);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/add-student")
    public ModelAndView addStudent(){
        log.trace("addStudent() is called");
        Student student = new Student();
        ModelAndView modelAndView =
                new ModelAndView("AddStudent",
                                    "student", student);
        List<Program> programs = programDataRepository.findAll();
        modelAndView.addObject("programs", programs);
        return modelAndView;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/insert-student")
    public String insertStudent(
            @Validated @ModelAttribute Student student,
            BindingResult bindingResult,
            Model model
    ){
        log.trace("insertStudent() is called");
        log.debug("student = " + student);
        // checking for the input validation errors
        if(!programDataRepository.existsById(student.getProgram().getId())){
           bindingResult.rejectValue("program.id", "Invalid.student.program.id");
           log.trace("invalid program id");
           log.debug("program.id = " + student.getProgram().getId());
        }
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("student", student);
            List<Program> programs = programDataRepository.findAll();
            model.addAttribute("programs", programs);
            return "AddStudent";
        } else {
            log.trace("the user inputs are correct");
            Student savedStudent = studentDataRepository.save(student);
            log.debug("id = " + savedStudent.getId());
            return"redirect:/students/confirm-insert/" + savedStudent.getId();
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/confirm-insert/{id}")
    public String confirmInsert(@PathVariable String id, Model model){
        log.trace("confirmInsert() is called");
        log.debug("id = " + id);
        try {
            log.trace("looking for the data in the database");
            Student student =
                    studentDataRepository.findById(Integer.parseInt(id)).orElseThrow();
            log.debug("student = " + student);
            log.trace("showing the data in the confirmation page");
            model.addAttribute("student", student);
            return "ConfirmInsert";
        } catch (NumberFormatException e) {
            log.trace("the id is not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id = " + id);
            return "DataNotFound";
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-all")
    public String deleteAll(){
        log.trace("deleteAll() is called");
        studentDataRepository.deleteAll();
        return "redirect:/students/list-students";
    }

    @GetMapping("/student-details/{id}")
    public String studentDetails(@PathVariable String id, Model model){
        log.trace("studentDetails() is called");
        log.debug("id = " + id);
        try {
            Student student = studentDataRepository.findById(Integer.parseInt(id)).orElseThrow();
            model.addAttribute("student", student);
            return "StudentDetails"; // show the student data in the form to edit
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id=" + id);
            return "DataNotFound";
        }
    }

    // a user clicks "Delete" link (in the table) to "DeleteStudent"
    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-student")
    public String deleteStudent(@RequestParam String id, Model model) {
        log.trace("deleteStudent() is called");
        log.debug("id = " + id);
        try {
            Student student = studentDataRepository.findById(Integer.parseInt(id)).orElseThrow();
            model.addAttribute("student", student);
            return "DeleteStudent"; // ask "Do you really want to remove?"
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id = " + id);
            return "DataNotFound";
        }
    }

    // a user clicks "Remove Record" button in "DeleteStudent" page,
    // the form submits the data to "RemoveStudent"
    @Secured("ROLE_ADMIN")
    @PostMapping("/remove-student")
    public String removeStudent(@RequestParam String id) {
        log.trace("removeStudent() is called");
        log.debug("id = " + id);
        try {
            studentDataRepository.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        }
        return "redirect:/students/list-students";
    }

    // a user clicks "Edit" link (in the table) to "EditStudent"
    @Secured("ROLE_ADMIN")
    @GetMapping("/edit-student")
    public String editStudent(@RequestParam String id, Model model) {
        log.trace("editStudent() is called");
        log.debug("id = " + id);
        try {
            Student student = studentDataRepository.findById(Integer.parseInt(id)).orElseThrow();
            model.addAttribute("student", student);
            List<Program> programs = programDataRepository.findAll();
            model.addAttribute("programs", programs);
            return "EditStudent";
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        } catch (NoSuchElementException e){
            log.trace("no data for this id = " + id);
            return "DataNotFound";
        }
    }

    // the form submits the data to "UpdateStudent"
    @Secured("ROLE_ADMIN")
    @PostMapping("/update-student")
    public String updateStudent(
            @Validated @ModelAttribute Student student,
            BindingResult bindingResult,
            Model model) {
        log.trace("updateStudent() is called");
        log.debug("student = " + student);
        // checking for the input validation errors
        if(!programDataRepository.existsById(student.getProgram().getId())){
            bindingResult.rejectValue("program.id", "Invalid.student.program.id");
            log.trace("invalid program id");
            log.debug("program.id = " + student.getProgram().getId());
        }
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("student", student);
            List<Program> programs = programDataRepository.findAll();
            model.addAttribute("programs", programs);
            return "EditStudent";
        } else {
            log.trace("the user inputs are correct");
            studentDataRepository.save(student);
            log.debug("id = " + student.getId());
            return "redirect:/students/student-details/" + student.getId();
        }
    }
}
