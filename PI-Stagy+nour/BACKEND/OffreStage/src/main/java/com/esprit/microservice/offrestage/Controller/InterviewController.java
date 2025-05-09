package com.esprit.microservice.offrestage.Controller;


import com.esprit.microservice.offrestage.Entities.Candidature;
import com.esprit.microservice.offrestage.Entities.Interview;
import com.esprit.microservice.offrestage.Services.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    InterviewService interviewService;

    @PostMapping
    public Interview addInterview(@RequestBody Interview interview) {

        return interviewService.createInterview(interview);
    }

    @GetMapping
    public List<Interview> afficher() {

        return interviewService.getAllInterviews();
    }
}
