package org.durcit.be.post.service;

import org.durcit.be.post.domain.Post;
import org.durcit.be.post.dto.PostRegisterRequest;
import org.durcit.be.post.dto.PostResponse;
import org.durcit.be.post.dto.PostUpdateRequest;

import java.util.List;

public interface PostService {

    public List<PostResponse> getAllPosts();
    public PostResponse getPostById(Long postId);
    public Post getById(Long postId);
    public PostResponse createPost(PostRegisterRequest postRegisterRequest);
    public void updatePost(Long postId, PostUpdateRequest postUpdateRequest);
    public void deletePost(Long postId);

}
