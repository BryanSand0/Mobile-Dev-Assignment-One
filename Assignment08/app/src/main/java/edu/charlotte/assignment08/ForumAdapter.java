

package edu.charlotte.assigment08;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<Forum> forums;
    private final String currentUserId;
    private final ForumAdapterListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault());

    public interface ForumAdapterListener {
        void onForumClick(Forum forum);
        void onLikeClick(Forum forum);
        void onDeleteClick(Forum forum);
    }

    public ForumAdapter(List<Forum> forums, String currentUserId, ForumAdapterListener listener) {
        this.forums = forums;
        this.currentUserId = currentUserId;
        this.listener = listener;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        Forum forum = forums.get(position);
        holder.bind(forum);
    }

    @Override
    public int getItemCount() {
        return forums != null ? forums.size() : 0;
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final TextView textViewCreator;
        private final TextView textViewDescription;
        private final TextView textViewLikes;
        private final TextView textViewDateTime;
        private final ImageView imageViewHeart;
        private final ImageView imageViewDelete;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCreator = itemView.findViewById(R.id.textViewCreator);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewLikes = itemView.findViewById(R.id.textViewLikes);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            imageViewHeart = itemView.findViewById(R.id.imageViewHeart);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        public void bind(final Forum forum) {
            textViewTitle.setText(forum.getTitle());
            textViewCreator.setText(forum.getCreatorName() != null ? forum.getCreatorName() : "Unknown");
            textViewDescription.setText(forum.getDescription());

            // Format date and time
            Timestamp createdAt = forum.getCreatedAt();
            if (createdAt != null) {
                textViewDateTime.setText(dateFormat.format(createdAt.toDate()));
            } else {
                textViewDateTime.setText("N/A");
            }

            // Display likes count
            int likesCount = forum.getLikeCount();
            textViewLikes.setText(likesCount + (likesCount == 1 ? " Like" : " Likes"));

            // Set heart icon based on whether current user liked this forum
            if (forum.isLikedBy(currentUserId)) {
                imageViewHeart.setImageResource(R.drawable.ic_favorite);
            } else {
                imageViewHeart.setImageResource(R.drawable.ic_favorite_border);
            }

            // Show delete icon only if current user is creator
            if (currentUserId != null && currentUserId.equals(forum.getCreatorId())) {
                imageViewDelete.setVisibility(View.VISIBLE);
            } else {
                imageViewDelete.setVisibility(View.GONE);
            }

            // Row click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onForumClick(forum);
                }
            });

            // Heart icon click listener
            imageViewHeart.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLikeClick(forum);
                }
            });

            // Delete icon click listener
            imageViewDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(forum);
                }
            });
        }
    }
}
