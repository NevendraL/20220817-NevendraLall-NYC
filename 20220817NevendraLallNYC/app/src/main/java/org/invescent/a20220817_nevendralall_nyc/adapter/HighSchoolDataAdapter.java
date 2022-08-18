package org.invescent.a20220817_nevendralall_nyc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.invescent.a20220817_nevendralall_nyc.R;
import org.invescent.a20220817_nevendralall_nyc.model.HighSchoolModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HighSchoolDataAdapter extends RecyclerView.Adapter<HighSchoolDataAdapter.HighSchoolDataViewHolder> {
    private Context context;
    private List<HighSchoolModel> highSchoolDataArrayList;
    private CircleImageView defaultImageView;


    public HighSchoolDataAdapter(Context context, List<HighSchoolModel> highSchoolDataArrayList) {
        this.context = context;
        this.highSchoolDataArrayList = highSchoolDataArrayList;


    }


    @NonNull
    @Override
    public HighSchoolDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.highschool_row, viewGroup, false);
        return new HighSchoolDataViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HighSchoolDataViewHolder viewHolder, final int i) {
        viewHolder.schoolnameTv.setText(highSchoolDataArrayList.get(i).getSchool_name());
        viewHolder.schoolNumberTv.setText(highSchoolDataArrayList.get(i).getSchool_number());
        viewHolder.schoolBoroughtTv.setText(highSchoolDataArrayList.get(i).getBorough());
        viewHolder.firstLetterOfHighSchoolTv.setText(highSchoolDataArrayList.get(i).getSchool_name().charAt(0) + "");


    }


    @Override
    public int getItemCount() {
        return highSchoolDataArrayList.size();

    }

    class HighSchoolDataViewHolder extends RecyclerView.ViewHolder {
        private TextView schoolnameTv, schoolNumberTv, schoolBoroughtTv, firstLetterOfHighSchoolTv;

        public HighSchoolDataViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolnameTv = itemView.findViewById(R.id.schoolNameTv);
            schoolNumberTv = itemView.findViewById(R.id.schoolNumberTv);
            schoolBoroughtTv = itemView.findViewById(R.id.schoolBoroughTv);
            firstLetterOfHighSchoolTv = itemView.findViewById(R.id.firstLetterHighSchoolTv);


        }

    }

}


