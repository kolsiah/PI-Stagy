package com.esprit.microservice.offrestage.Services;

import com.esprit.microservice.offrestage.Entities.Interview;

import java.util.List;

public interface InterviewService {
    Interview createInterview(Interview interview);
    List<Interview> getAllInterviews();
    Interview getInterviewById(Long id);
    Interview updateInterview(Long id, Interview updatedInterview);
    void deleteInterview(Long id);
    List<Interview> getInterviewsByCandidature(Long candidatureId);
}
