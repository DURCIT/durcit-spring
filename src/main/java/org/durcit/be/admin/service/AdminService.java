package org.durcit.be.admin.service;

import org.durcit.be.admin.dto.AdminLogResponse;
import org.durcit.be.post.dto.PostCardResponse;

import java.util.List;

public interface AdminService {

    public List<AdminLogResponse> getAllLogs();
    public void recoverPostAndPostsTag(Long postId);
    public void roleUpdateToAdmin(Long memberId);
    public void roleUpdateToManager(Long memberId);
    public void roleUpdateToMember(Long memberId);
    public void hardDeletePostAndPostsTag(Long postId);

}
