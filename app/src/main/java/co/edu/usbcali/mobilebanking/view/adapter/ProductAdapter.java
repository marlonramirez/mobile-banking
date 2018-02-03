package co.edu.usbcali.mobilebanking.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.usbcali.mobilebanking.R;
import co.edu.usbcali.mobilebanking.model.Product;

/**
 * Created by Marlon.Ramirez on 3/02/2018.
 */

public class ProductAdapter extends BaseAdapter {
    protected Activity activity;
    protected List<Product> items;

    public ProductAdapter(Activity activity, List<Product> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(List<Product> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.adapter_simple_navigation, null);
        }
        Product product = items.get(position);
        TextView type = convertView.findViewById(R.id.title);
        type.setText("$" + product.getBalance());
        TextView code = convertView.findViewById(R.id.description);
        code.setText(product.getType());
        return convertView;
    }

}
