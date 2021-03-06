package com.jeffding05.mypmtool.services;


import com.jeffding05.mypmtool.domain.Project;
import com.jeffding05.mypmtool.exceptions.ProjectIdException;
import com.jeffding05.mypmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = findProjectByIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot Delete Project with ID '" + projectId + "'. This project does not exist");
        }

        projectRepository.delete(project);
    }
}
