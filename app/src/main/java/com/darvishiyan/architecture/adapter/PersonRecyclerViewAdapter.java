package com.darvishiyan.architecture.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.darvishiyan.architecture.BR;
import com.darvishiyan.architecture.R;
import com.darvishiyan.architecture.binder.PersonBinder;
import com.darvishiyan.architecture.data.model.PersonModel;

import java.util.List;

/**
 * Created by Hesam on 2/15/2018.
 */

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.PersonViewHolder> {

    class PersonViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;
        private Context context;

        PersonViewHolder(Context context, ViewDataBinding binding) {
            super(binding.getRoot());
            this.context = context;
            this.binding = binding;
        }

        void bind(PersonModel model) {
            binding.setVariable(BR.person, new PersonBinder(context, model));
            binding.executePendingBindings();
        }
    }

    private List<PersonModel> personModels;

    public void setData(List<PersonModel> personModels) {
        this.personModels = personModels;
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.person_row, parent, false);
        return new PersonViewHolder(parent.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        if (personModels != null && personModels.size() > 0) {
            holder.bind(personModels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (personModels != null && personModels.size() > 0) {
            return personModels.size();
        }
        return 0;
    }
}
