package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.TeamMemberDisplayVO;
import com.xingdongadmin.backend.dto.TeamMemberView;
import com.xingdongadmin.backend.entity.TeamMember;
import com.xingdongadmin.backend.repository.TeamMemberRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
public class TeamMemberController {
    
    private final TeamMemberRepository teamMemberRepository;
    
    public TeamMemberController(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }
    
    public static class TeamMemberRequest {
        @NotBlank(message = "姓名不能为空")
        public String name;
        
        public String nickname;
        public String title;
        public String position;
        public String photoUrl;
        public String email;
        public String phone;
        public String department;
        public String description;
        public Boolean enabled;
        public Integer sortOrder;
    }
    
    @GetMapping
    public List<TeamMemberView> getAllTeamMembers() {
        return teamMemberRepository.findAllByOrderBySortOrderAsc().stream()
                .map(TeamMemberView::from)
                .toList();
    }
    
    @GetMapping("/{id}")
    public TeamMemberView getTeamMember(@PathVariable Long id) {
        return teamMemberRepository.findById(id)
                .map(TeamMemberView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team member not found"));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TeamMemberView createTeamMember(@RequestBody TeamMemberRequest req) {
        TeamMember member = new TeamMember();
        member.setName(req.name);
        member.setNickname(req.nickname);
        member.setTitle(req.title);
        member.setPosition(req.position);
        member.setPhotoUrl(req.photoUrl);
        member.setEmail(req.email);
        member.setPhone(req.phone);
        member.setDepartment(req.department);
        member.setDescription(req.description);
        if (req.enabled != null) member.setEnabled(req.enabled);
        if (req.sortOrder != null) member.setSortOrder(req.sortOrder);
        
        return TeamMemberView.from(teamMemberRepository.save(member));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TeamMemberView updateTeamMember(@PathVariable Long id, @RequestBody TeamMemberRequest req) {
        TeamMember existing = teamMemberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team member not found"));
        
        if (req.name != null) existing.setName(req.name);
        if (req.nickname != null) existing.setNickname(req.nickname);
        if (req.title != null) existing.setTitle(req.title);
        if (req.position != null) existing.setPosition(req.position);
        if (req.photoUrl != null) existing.setPhotoUrl(req.photoUrl);
        if (req.email != null) existing.setEmail(req.email);
        if (req.phone != null) existing.setPhone(req.phone);
        if (req.department != null) existing.setDepartment(req.department);
        if (req.description != null) existing.setDescription(req.description);
        if (req.enabled != null) existing.setEnabled(req.enabled);
        if (req.sortOrder != null) existing.setSortOrder(req.sortOrder);
        
        return TeamMemberView.from(teamMemberRepository.save(existing));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTeamMember(@PathVariable Long id) {
        teamMemberRepository.deleteById(id);
    }
    
    // ========== 前端公开接口 ==========

    /**
     * 获取启用的团队成员列表（前端页面使用）
     * 返回字段：id, name, title, position, photoUrl, description, sortOrder
     */
    @GetMapping("/frontend/list")
    public List<TeamMemberDisplayVO> getEnabledTeamMembers() {
        return teamMemberRepository.findByEnabledTrueOrderBySortOrderAsc().stream()
                .map(TeamMemberDisplayVO::from)
                .toList();
    }
}
