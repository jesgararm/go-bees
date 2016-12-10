package com.davidmiguel.gobees.apiaries;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidmiguel.gobees.R;
import com.davidmiguel.gobees.data.model.Apiary;
import com.davidmiguel.gobees.utils.BaseViewHolder;
import com.davidmiguel.gobees.utils.ItemTouchHelperViewHolder;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Apiaries list adapter.
 */
class ApiariesAdapter extends RecyclerView.Adapter<ApiariesAdapter.ViewHolder> {

    private List<Apiary> apiaries;
    private ApiaryItemListener listener;

    ApiariesAdapter(List<Apiary> apiaries, ApiaryItemListener listener) {
        this.apiaries = checkNotNull(apiaries);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apiaries_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(apiaries.get(position));
    }

    @Override
    public int getItemCount() {
        return apiaries == null ? 0 : apiaries.size();
    }

    void replaceData(List<Apiary> apiaries) {
        this.apiaries = checkNotNull(apiaries);
        notifyDataSetChanged();
    }

    interface ApiaryItemListener {
        void onApiaryClick(Apiary clickedApiary);

        void onApiaryDelete(Apiary clickedApiary);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements BaseViewHolder<Apiary>, View.OnClickListener, ItemTouchHelperViewHolder {

        private CardView card;
        private TextView apiaryName;
        private TextView numHives;
        private Drawable background;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            card = (CardView) itemView.findViewById(R.id.card);
            apiaryName = (TextView) itemView.findViewById(R.id.apiary_name);
            numHives = (TextView) itemView.findViewById(R.id.num_hives);
            background = card.getBackground();
        }

        public void bind(@NonNull Apiary apiary) {
            apiaryName.setText(apiary.getName());
            if(apiary.getHives() != null) {
                numHives.setText(Integer.toString(apiary.getHives().size()));
            }
        }

        @Override
        public void onClick(View view) {
            listener.onApiaryClick(apiaries.get(getAdapterPosition()));
        }

        @Override
        public void onItemSelected() {
            card.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            card.setBackground(background);
        }
    }
}