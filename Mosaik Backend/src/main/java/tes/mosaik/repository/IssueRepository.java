package tes.mosaik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tes.mosaik.modal.Issue;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    public List<Issue> findByProjectID(Long projectID);
}
