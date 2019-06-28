package ru.netologia.sharinm_task_511;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *  Адаптер - мост между данными и создаваемыми и управляемыми им View
 */
public class ItemDataAdapter extends BaseAdapter {

    private List<ItemData> items;

    private ImageView imageDelete;

    private LayoutInflater inflater;

    ItemDataAdapter(Context context, List<ItemData> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(ItemData item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        ItemData itemData = items.get(position);

        ImageView image = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        imageDelete = view.findViewById(R.id.icon_delete);

        image.setImageDrawable(itemData.getImage());
        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());
        imageDelete.setImageDrawable(view.getResources().getDrawable(android.R.drawable.ic_menu_delete));

        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        return view;
    }
}
