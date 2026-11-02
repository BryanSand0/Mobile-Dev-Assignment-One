

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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;
    private final String currentUserId;
    private final CommentAdapterListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault());

    public interface CommentAdapterListener {
        void onDeleteClick(Comment comment);
    }

    public CommentAdapter(List<Comment> comments, String currentUserId, CommentAdapterListener listener) {
        this.comments = comments;
        this.currentUserId = currentUserId;
        this.listener = listener;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewAuthor;
        private final TextView textViewContent;
        private final TextView textViewDateTime;
        private final ImageView imageViewDelete;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        public void bind(final Comment comment) {
            textViewAuthor.setText(comment.getAuthorName() != null ? comment.getAuthorName() : "Unknown");
            textViewContent.setText(comment.getContent());

            // Format date and time
            Timestamp createdAt = comment.getCreatedAt();
            if (createdAt != null) {
                textViewDateTime.setText(dateFormat.format(createdAt.toDate()));
            } else {
                textViewDateTime.setText("N/A");
            }

            // Show delete icon only if current user is creator
            if (currentUserId != null && currentUserId.equals(comment.getCreatorId())) {
                imageViewDelete.setVisibility(View.VISIBLE);
            } else {
                imageViewDelete.setVisibility(View.GONE);
            }

            // Delete icon click listener
            imageViewDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(comment);
                }
            });
        }
    }
}
