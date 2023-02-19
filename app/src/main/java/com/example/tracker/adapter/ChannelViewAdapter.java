package com.example.tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tracker.R;
import com.example.tracker.dto.ChannelDto;

import java.util.List;

public class ChannelViewAdapter extends ArrayAdapter<ChannelDto> {
    public ChannelViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ChannelViewAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ChannelViewAdapter(@NonNull Context context, int resource, @NonNull ChannelDto[] objects) {
        super(context, resource, objects);
    }

    public ChannelViewAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ChannelDto[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ChannelViewAdapter(@NonNull Context context, int resource, @NonNull List<ChannelDto> objects) {
        super(context, resource, objects);
    }

    public ChannelViewAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<ChannelDto> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.credit_channels_list, parent, false);
        }
        ChannelDto dto = getItem(position);
        TextView tv = currentItemView.findViewById(R.id.text1);
        ImageView img = currentItemView.findViewById(R.id.icon_img);
        assert dto != null;
        img.setImageResource(dto.getIconId());
        tv.setText(dto.getChName());
        return currentItemView;
    }
}
