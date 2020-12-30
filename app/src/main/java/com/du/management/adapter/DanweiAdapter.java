package com.du.management.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.newBean.JczcField;
import com.du.management.newBean.JczcFieldValue;

import java.util.List;

public class DanweiAdapter extends BaseAdapter {

    private Context context;

    private List<JczcField> jczcFieldList;

    public DanweiAdapter(Context context, List<JczcField> jczcFieldList) {
        this.context = context;
        this.jczcFieldList = jczcFieldList;
    }

    @Override
    public int getCount() {
        return jczcFieldList.size();
    }

    @Override
    public Object getItem(int i) {
        return jczcFieldList.get(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_jczcfield, null);
            viewHolder.key = convertView.findViewById(R.id.key);
            viewHolder.value = convertView.findViewById(R.id.value);
            viewHolder.spinner = convertView.findViewById(R.id.spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JczcField jczcField = jczcFieldList.get(i);
        viewHolder.key.setText(jczcField.getFieldName());
        if (jczcField.getFieldType() == 1) {
            viewHolder.value.setVisibility(View.GONE);
            viewHolder.spinner.setVisibility(View.VISIBLE);
            String[] mItems = new String[jczcField.getJczcFieldValue().size()];
            for (int k = 0; k < jczcField.getJczcFieldValue().size(); k++) {
                mItems[k] = jczcField.getJczcFieldValue().get(k).getValueName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, mItems);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.spinner.setAdapter(adapter);
            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    viewHolder.spinner.setSelection(i);
                    jczcField.setSaveValue(i + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else {
            viewHolder.value.setVisibility(View.VISIBLE);
            viewHolder.spinner.setVisibility(View.GONE);
            viewHolder.value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    jczcField.setSaveValue(viewHolder.value.getText().toString());
                }
            });
        }
        if (!TextUtils.isEmpty(jczcField.getValue())) {
            viewHolder.value.setText(jczcField.getValue());
        }
        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView key;
        public EditText value;
        public Spinner spinner;
    }

}
