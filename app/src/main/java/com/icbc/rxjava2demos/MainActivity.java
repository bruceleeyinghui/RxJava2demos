package com.icbc.rxjava2demos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icbc.rxjava2demos.generate.CreateActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        String[] datas = {"创建操作", "变换操作"};
        MyAdapter myAdapter = new MyAdapter(this, datas) {

            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, CreateActivity.class));
                        break;
                }

            }
        };
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(myAdapter);
    }


    public abstract class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
        private Context context;
        String datas[];

        public MyAdapter(Context context, String[] datas) {
            this.context = context;
            this.datas = datas;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main, viewGroup, false);
            view.setOnClickListener(this);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.tv.setText(datas[i]);
            myViewHolder.itemView.setTag(i);

        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        @Override
        public void onClick(View v) {
            onItemClick((int) v.getTag());

        }

        public abstract void onItemClick(int position);


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }
}
