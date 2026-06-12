package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.TeamMember;

public class TeamMemberView {
    
    public Long id;
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
    public String createdAt;
    public String updatedAt;
    
    public static TeamMemberView from(TeamMember member) {
        TeamMemberView view = new TeamMemberView();
        view.id = member.getId();
        view.name = member.getName();
        view.nickname = member.getNickname();
        view.title = member.getTitle();
        view.position = member.getPosition();
        view.photoUrl = member.getPhotoUrl();
        view.email = member.getEmail();
        view.phone = member.getPhone();
        view.department = member.getDepartment();
        view.description = member.getDescription();
        view.enabled = member.getEnabled();
        view.sortOrder = member.getSortOrder();
        view.createdAt = member.getCreatedAt() != null ? member.getCreatedAt().toString() : null;
        view.updatedAt = member.getUpdatedAt() != null ? member.getUpdatedAt().toString() : null;
        return view;
    }
}
