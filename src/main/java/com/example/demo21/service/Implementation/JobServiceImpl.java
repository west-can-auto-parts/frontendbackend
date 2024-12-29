package com.example.demo21.service.Implementation;

import com.example.demo21.dto.JobApplicationRequest;
import com.example.demo21.dto.JobResponse;
import com.example.demo21.entity.JobApplicationDocument;
import com.example.demo21.entity.JobDocument;
import com.example.demo21.repository.JobApplicationRepository;
import com.example.demo21.repository.JobRepository;
import com.example.demo21.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;


    @Override
    public List<JobResponse> fetchAll () {
        List<JobDocument> jobDocumentList=jobRepository.findAll();
        List<JobResponse> jobResponseList=new ArrayList<>();
        for(JobDocument jbd: jobDocumentList){
            JobResponse jbr=new JobResponse();
            jbr.setId(jbd.getId());
            jbr.setJobTitle(jbd.getJobTitle());
            jbr.setLocation(jbd.getLocation());
            jbr.setCompany(jbd.getCompany());
            jbr.setJobDescription(jbd.getJobDescription());
            jbr.setResponsibilities(jbd.getResponsibilities());
            jbr.setQualifications(jbd.getQualifications());
            jbr.setEmploymentType(jbd.getEmploymentType());
            jbr.setSalaryRange(jbd.getSalaryRange());
            jbr.setApplicationDeadline(jbd.getApplicationDeadline());
            jbr.setCreatedAt(jbd.getCreatedAt());
            jbr.setUpdatedAt(jbd.getUpdatedAt());
            jbr.setActive(jbd.isActive());
            jobResponseList.add(jbr);
        }
        return jobResponseList;
    }

    @Override
    public JobResponse getById (String id) {
        Optional<JobDocument> jobDocument=jobRepository.findById(id);
        if(jobDocument.isEmpty())   return null;
        JobDocument jbd=jobDocument.get();
        JobResponse jbr=new JobResponse();
        jbr.setId(jbd.getId());
        jbr.setJobTitle(jbd.getJobTitle());
        jbr.setLocation(jbd.getLocation());
        jbr.setCompany(jbd.getCompany());
        jbr.setJobDescription(jbd.getJobDescription());
        jbr.setResponsibilities(jbd.getResponsibilities());
        jbr.setQualifications(jbd.getQualifications());
        jbr.setEmploymentType(jbd.getEmploymentType());
        jbr.setSalaryRange(jbd.getSalaryRange());
        jbr.setApplicationDeadline(jbd.getApplicationDeadline());
        jbr.setCreatedAt(jbd.getCreatedAt());
        jbr.setUpdatedAt(jbd.getUpdatedAt());
        jbr.setActive(jbd.isActive());
        return jbr;
    }

    @Override
    public String userApplication (JobApplicationRequest jobApplicationRequest) {
        JobApplicationDocument jobApplicationDocument=new JobApplicationDocument();
        jobApplicationDocument.setApplicantEmail(jobApplicationRequest.getApplicantEmail());
        jobApplicationDocument.setApplicantName(jobApplicationRequest.getApplicantName());
        jobApplicationDocument.setPhone(jobApplicationRequest.getPhone());
        jobApplicationDocument.setResume(jobApplicationRequest.getResume());
        jobApplicationDocument.setCoverLetter(jobApplicationRequest.getCoverLetter());
        jobApplicationDocument.setPositionApplied(jobApplicationRequest.getPositionApplied());
        jobApplicationDocument.setLocation(jobApplicationRequest.getLocation());
        jobApplicationDocument.setExperienceYears(jobApplicationRequest.getExperienceYears());
        jobApplicationDocument.setStatus(jobApplicationRequest.getStatus());
        jobApplicationDocument.setApplicationDate(jobApplicationRequest.getApplicationDate());

        jobApplicationRepository.save(jobApplicationDocument);
        return "Applied Successfully";
    }
}
