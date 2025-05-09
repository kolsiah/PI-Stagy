package com.esprit.microservice.offrestage.Services;

import com.esprit.microservice.offrestage.Entities.Candidature;
import com.esprit.microservice.offrestage.Entities.Interview;
import com.esprit.microservice.offrestage.Repositories.CandidatureRepository;
import com.esprit.microservice.offrestage.Repositories.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private CandidatureService candidatureService;
    @Override
    public Interview createInterview(Interview interview) {

            interview.setNote(0.0);
            candidatureService.mettreAJourEtat(interview.getCandidature().getId(),"Accepter");
            return interviewRepository.save(interview);

    }

    @Override
    public Interview updateInterview(Long id, Interview interviewDetails) {
        Optional<Interview> optionalInterview = interviewRepository.findById(id);

        if (optionalInterview.isPresent()) {
            Interview interview = optionalInterview.get();
            interview.setDateEntretien(interviewDetails.getDateEntretien());
            interview.setMode(interviewDetails.getMode());
            interview.setLienVisio(interviewDetails.getLienVisio());
            interview.setNote(interviewDetails.getNote());
            interview.setCommentaire(interviewDetails.getCommentaire());
            interview.setStatus(interviewDetails.getStatus());
            return interviewRepository.save(interview);
        } else {
            throw new RuntimeException("Interview not found with id: " + id);
        }
    }

    @Override
    public void deleteInterview(Long id) {
        Optional<Interview> interview = interviewRepository.findById(id);
        if (interview.isPresent()) {
            interviewRepository.delete(interview.get());
        } else {
            throw new RuntimeException("Interview not found with id: " + id);
        }
    }

    @Override
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    @Override
    public Interview getInterviewById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
    }

    @Override
    public List<Interview> getInterviewsByCandidature(Long candidatureId) {
        return interviewRepository.findByCandidatureId(candidatureId);
    }

    public List<Interview> getInterviewsByStatus(String status) {
        return interviewRepository.findByStatus(status);
    }
}
