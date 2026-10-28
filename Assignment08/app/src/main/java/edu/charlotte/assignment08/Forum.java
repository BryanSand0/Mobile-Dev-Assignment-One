
package edu.charlotte.assignment08;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Forum implements Serializable {

    @DocumentId
    private String forumId;
    private String title;
    private String description;
    private String creatorId;
    private String creatorName;
    private Timestamp createdAt;
    private List<String> likedBy;

    public Forum() {
        this.likedBy = new ArrayList<>();
    }

    public Forum(String forumId, String title, String description, String creatorId, String creatorName, Timestamp createdAt, List<String> likedBy) {
        this.forumId = forumId;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createdAt = createdAt;
        this.likedBy = likedBy != null ? likedBy : new ArrayList<>();
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getLikedBy() {
        if (likedBy == null) {
            likedBy = new ArrayList<>();
        }
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public int getLikeCount() {
        return getLikedBy().size();
    }

    public boolean isLikedBy(String userId) {
        return userId != null && getLikedBy().contains(userId);
    }
}
