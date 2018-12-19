package com.icbc.rxjava2demos.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.icbc.rxjava2demos.R;
import com.icbc.rxjava2demos.bean.TestBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_result;
    TextView tv_code;
    Button debounce;
    Button distinct;
    Button ElementAt;
    Button filter_OfType;
    Button first_single;
    Button last;
    Button take;
    Button takelast_buffer;
    Button skip;
    Button skip_last;
    Button ignoreElements;
    Button ThrottleFirst;

    StringBuilder sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        sb = new StringBuilder();
        tv_code = findViewById(R.id.tv_code);
        tv_result = findViewById(R.id.tv_result);
        debounce = findViewById(R.id.debounce);
        distinct = findViewById(R.id.distinct);
        ElementAt = findViewById(R.id.ElementAt);
        filter_OfType = findViewById(R.id.filter_OfType);
        first_single = findViewById(R.id.first_single);
        last = findViewById(R.id.last);
        take = findViewById(R.id.take);
        takelast_buffer = findViewById(R.id.takelast_buffer);
        skip = findViewById(R.id.skip);
        skip_last = findViewById(R.id.skip_last);
        ignoreElements = findViewById(R.id.ignoreElements);
        ThrottleFirst = findViewById(R.id.ThrottleFirst);
        debounce.setOnClickListener(this);
        distinct.setOnClickListener(this);
        ElementAt.setOnClickListener(this);
        filter_OfType.setOnClickListener(this);
        first_single.setOnClickListener(this);
        last.setOnClickListener(this);
        take.setOnClickListener(this);
        takelast_buffer.setOnClickListener(this);
        skip.setOnClickListener(this);
        skip_last.setOnClickListener(this);
        ignoreElements.setOnClickListener(this);
        ThrottleFirst.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.debounce:
                tv_code.setText("debounce  过滤发送数据过快的数据项" +
                        " Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void subscribe(ObservableEmitter e) throws Exception {\n" +
                        "                        int[] nums = {0, 1, 2, 3, 4, 5};\n" +
                        "                        long[] sleeps = {100, 400, 100, 100, 200, 0};\n" +
                        "                        for (int i = 0 ; i<nums.length ; i++ ){\n" +
                        "                            e.onNext(nums[i]);\n" +
                        "                            Thread.sleep(sleeps[i]);\n" +
                        "                        }\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                observable.debounce(300,TimeUnit.MILLISECONDS).subscribe(new Consumer() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "                        sb.append(o);\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");
                Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter e) throws Exception {
                        int[] nums = {0, 1, 2, 3, 4, 5};
                        long[] sleeps = {100, 400, 100, 100, 200, 0};
                        for (int i = 0; i < nums.length; i++) {
                            e.onNext(nums[i]);
                            Thread.sleep(sleeps[i]);
                        }


                    }
                });
                observable.debounce(300, TimeUnit.MILLISECONDS).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        sb.append(o);
                        tv_result.setText(sb.toString());
                    }
                });
                break;
            case R.id.distinct:
                tv_code.setText("两种的区别在于  distinct  去掉所有重复  distinctUntilChanged  发射的数据跟上一个一样就去重" +
                        "Observable observabledistinct = Observable.just(1,1,2,2,3,1,2).distinct();\n" +
                        "                observabledistinct.subscribe(new Consumer() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "                        sb.append(\"distinct\");\n" +
                        "                        sb.append(o);\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                Observable observableDistinctUntilChange = Observable.just(1,1,2,2,3,1,2).distinctUntilChanged();\n" +
                        "                observableDistinctUntilChange.subscribe(new Consumer() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "                        sb.append(\"distinctUntilChange\");\n" +
                        "                        sb.append(o);\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");
                Observable observabledistinct = Observable.just(1, 1, 2, 2, 3, 1, 2).distinct();
                observabledistinct.subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        sb.append("distinct");
                        sb.append(o);
                        sb.append("\n");
                        tv_result.setText(sb.toString());
                    }
                });
                Observable observableDistinctUntilChange = Observable.just(1, 1, 2, 2, 3, 1, 2).distinctUntilChanged();
                observableDistinctUntilChange.subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        sb.append("distinctUntilChange");
                        sb.append(o);
                        sb.append("\n");
                        tv_result.setText(sb.toString());
                    }
                });

                break;
            case R.id.ElementAt:

                Observable.fromArray(1, 2, 3, 4, 5).elementAt(2).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        sb.append(integer);
                        sb.append("\n");
                        tv_result.setText(sb.toString());
                    }
                });
                break;
            case R.id.filter_OfType:
                tv_code.setText("过于简单 不再重读  predicate  里面是 条件" +
                        " Observable.just(1, 2, 3, 4, 5, 6, 7).filter(new Predicate<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public boolean test(Integer integer) throws Exception {\n" +
                        "                        return integer % 2 == 0;\n" +
                        "                    }\n" +
                        "                }).subscribe(new Consumer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Integer integer) throws Exception {\n" +
                        "                        sb.append(integer);\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable.just(1, 2, 3, 4, 5, 6, 7).filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        sb.append(integer);
                        sb.append("\n");
                        tv_result.setText(sb.toString());

                    }
                });

                break;
            case R.id.first_single:
                tv_code.setText("Observable.range(1,6).first(3).subscribe(new Consumer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Integer integer) throws Exception {\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        sb.append(integer);\n" +
                        "                        tv_result.setText(sb);\n" +
                        "                    }\n" +
                        "                });");
                Observable.range(1,6).takeLast(4).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        sb.append("\n");
                        sb.append(integer);
                        tv_result.setText(sb);
                    }
                });
                break;
            case R.id.last:
                break;
            case R.id.take:
                break;
            case R.id.takelast_buffer:
                break;
            case R.id.skip:
                sb.setLength(0);
                tv_code.setText("过于简单 不做解释" +
                        " Observable.range(1,10).skip(4).subscribe(new Consumer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Integer integer) throws Exception {\n" +
                        "                        sb.append(integer);\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb);\n" +
                        "                    }\n" +
                        "                });");
                Observable.range(1,10).skip(4).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        sb.append(integer);
                        sb.append("\n");
                        tv_result.setText(sb);
                    }
                });
                break;
            case R.id.skip_last:
                break;
            case R.id.ignoreElements:

                Observable.range(1,10).ignoreElements().subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(FilterActivity.this,"不关心发射的数据，只关心结束没结束",Toast.LENGTH_SHORT).show();
                        tv_result.setText("不关心发射的数据，只关心结束没结束");
                    }
                });
                break;
            case R.id.ThrottleFirst:
                tv_code.setText("过于简单不做解释" +
                        "Observable observableThrottleFirst= Observable.just(1,2,3,4,5,6).throttleFirst(300,TimeUnit.MILLISECONDS);\n" +
                        "                observableThrottleFirst.subscribe(new Consumer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Integer o) throws Exception {\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        sb.append(o);\n" +
                        "                        tv_result.setText(sb);\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable observableThrottleFirst= Observable.just(1,2,3,4,5,6).throttleFirst(300, TimeUnit.MILLISECONDS, Schedulers.io());
                observableThrottleFirst.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer o) throws Exception {
                        sb.append("\n");
                        sb.append(o);
                        tv_result.setText(sb);

                    }
                });

                break;

        }

    }
}
