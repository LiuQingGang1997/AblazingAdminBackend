package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.TeamMember;

/**
 * 团队成员展示DTO（前端页面使用）
 * 只包含核心展示字段：头像、姓名、职称、职位、简介
 */
public class TeamMemberDisplayVO {
    
    public Long id;
    public String name;
    public String title;
    public String position;
    public String photoUrl;
    public String description;
    public Integer sortOrder;
    
    public static TeamMemberDisplayVO from(TeamMember member) {
        TeamMemberDisplayVO vo = new TeamMemberDisplayVO();
        vo.id = member.getId();
        vo.name = member.getName();
        vo.title = member.getTitle();
        vo.position = member.getPosition();
        vo.photoUrl = member.getPhotoUrl();
        vo.description = member.getDescription();
        vo.sortOrder = member.getSortOrder();
        return vo;
    }
}
