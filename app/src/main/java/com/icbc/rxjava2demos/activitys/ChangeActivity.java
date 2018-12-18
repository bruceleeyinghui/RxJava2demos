package com.icbc.rxjava2demos.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icbc.rxjava2demos.R;
import com.icbc.rxjava2demos.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.internal.operators.observable.ObservableGroupJoin;
import io.reactivex.schedulers.Schedulers;


public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_code;
    TextView tv_result;

    Button switchmap;
    Button buffer;
    Button window;
    Button scan;
    Button map_cast;
    Button flatmap;
    Button groupBy;

    Observable observable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        tv_code = findViewById(R.id.tv_code);
        tv_result = findViewById(R.id.tv_result);
        switchmap = findViewById(R.id.switchmap);
        buffer = findViewById(R.id.buffer);
        window = findViewById(R.id.window);
        scan = findViewById(R.id.scan);
        map_cast = findViewById(R.id.map_cast);
        flatmap = findViewById(R.id.flatmap);
        groupBy = findViewById(R.id.groupBy);

        switchmap.setOnClickListener(this);
        buffer.setOnClickListener(this);
        window.setOnClickListener(this);
        scan.setOnClickListener(this);
        map_cast.setOnClickListener(this);
        flatmap.setOnClickListener(this);
        groupBy.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchmap:
                sb.setLength(0);
                tv_code.setText("Observable.just(\"A\",\"B\",\"C\",\"D\",\"E\").switchMap(new Function<String, Observable<String>>() {\n" +
                        "                    @Override\n" +
                        "                    public Observable<String> apply(String s) throws Exception {\n" +
                        "                        return Observable.just(s).subscribeOn(Schedulers.newThread());\n" +
                        "                    }\n" +
                        "                }).subscribeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Consumer<String>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(String s) throws Exception {\n" +
                        "                        sb.append(s);\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");

                Observable.just("A", "B", "C", "D", "E").switchMap(new Function<String, Observable<String>>() {
                    @Override
                    public Observable<String> apply(String s) throws Exception {
                        return Observable.just(s).subscribeOn(Schedulers.newThread());
                        //注意区别  上面是上一个发射的数据没有 被消费  则取消发射
//                        return Observable.just(s);

                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        sb.append(s);
                        sb.append("\n");
                        tv_result.setText(sb.toString());
                    }
                });


                break;
            case R.id.buffer:
                tv_code.setText("Observable<List<Integer>> observable1 = Observable.just(1,2,3,4).buffer(3);\n" +
                        "                observable1.subscribe(new Consumer<List<Integer>>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(List<Integer> integers) throws Exception {\n" +
                        "                        for(Integer integer : integers){\n" +
                        "                            sb.append(integer);\n" +
                        "                            sb.append(\"\\n\");\n" +
                        "                        }\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable<List<Integer>> observable1 = Observable.just(1, 2, 3, 4).buffer(3);
                observable1.subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        for (Integer integer : integers) {
                            sb.append(integer);
                            sb.append("\n");
                        }
                        sb.append("\n");
                        tv_result.setText(sb.toString());

                    }
                });
                break;
            case R.id.window:
                tv_code.setText("window 的作用根buffer是一样的 只不过buffer拆分的是list  window拆分的是observable" +
                        "Observable<Observable<Integer>> observablewindow = Observable.just(1, 2, 3, 4).window(3);\n" +
                        "                observablewindow.subscribe(new Consumer<Observable<Integer>>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Observable<Integer> integerObservable) throws Exception {\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable<Observable<Integer>> observablewindow = Observable.just(1, 2, 3, 4,5,6,7,8,9,10).window(3);
                observablewindow.subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(Observable<Integer> integerObservable) throws Exception {
                        sb.append("\n");

                        integerObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                sb.append(integer);
                                sb.append("\n");
                                tv_result.setText(sb);
                            }
                        });


                    }
                });


                break;
            case R.id.scan:
                tv_code.setText("scan的重点是成对扫描，第一个输入值没有配对不做成对扫描直接返送给谁消费者" +
                        "bservable observablescna = Observable.just(testBean1,testBean2).scan(new BiFunction<TestBean,TestBean,TestBean>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public TestBean apply(TestBean testBean, TestBean testBean2) throws Exception {\n" +
                        "                        testBean.setAge(testBean2.getAge());\n" +
                        "                        return testBean;\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                observablescna.subscribe(new Consumer<TestBean>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(TestBean o) throws Exception {\n" +
                        "                        tv_result.setText(o.getAge());\n" +
                        "\n" +
                        "                    }\n" +
                        "                });\n");
                TestBean testBean1 = new TestBean();
                testBean1.setAge(11);
                testBean1.setName("testbean1");
                testBean1.setNo("0605101213");
                TestBean testBean2 = new TestBean();
                testBean2.setAge(11);
                testBean2.setName("testbean1");
                testBean2.setNo("0605101213");
                Observable observablescna = Observable.just(testBean1,testBean2).scan(new BiFunction<TestBean,TestBean,TestBean>() {

                    @Override
                    public TestBean apply(TestBean testBean, TestBean testBean2) throws Exception {
                        testBean.setAge(testBean2.getAge());
                        return testBean;
                    }
                });
                observablescna.subscribe(new Consumer<TestBean>() {
                    @Override
                    public void accept(TestBean o) throws Exception {
                        tv_result.setText(""+o.getAge());

                    }
                });


                break;
            case R.id.map_cast:
                tv_code.setText("map将一种类型转换成另外一种类型" +
                        "observable = Observable.just(1,2,3).map(new Function<Integer,String>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public String apply(Integer integer) throws Exception {\n" +
                        "                        return \"a\" +integer;\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                observable.subscribe(new Consumer() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");

                observable = Observable.just(1, 2, 3).map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "a" + integer;
                    }
                });
                observable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        sb.append(o);
                        sb.append("\n");
                        tv_result.setText(sb.toString());


                    }

                });

                break;
            case R.id.flatmap:
                tv_code.setText("Observable observablelatmap = Observable.just(1,2,3,4,5,6).flatMap(new Function<Integer, ObservableSource<TestBean>>() {\n" +
                        "                    @Override\n" +
                        "                    public ObservableSource<TestBean> apply(Integer integer) throws Exception {\n" +
                        "                        TestBean testBean = new TestBean();\n" +
                        "                        testBean.setAge(integer);\n" +
                        "                        return Observable.just(testBean);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                observablelatmap.subscribe(new Consumer<TestBean>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(TestBean testBean) throws Exception {\n" +
                        "\n" +
                        "                        tv_result.setText(testBean.getNo());\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");

                Observable observablelatmap = Observable.just(1,2,3,4,5,6).flatMap(new Function<Integer, ObservableSource<TestBean>>() {
                    @Override
                    public ObservableSource<TestBean> apply(Integer integer) throws Exception {
                        TestBean testBean = new TestBean();
                        testBean.setAge(integer);
                        testBean.setNo(""+integer);
                        testBean.setName(""+integer);
                        return Observable.just(testBean);
                    }
                });
                observablelatmap.subscribe(new Consumer<TestBean>() {
                    @Override
                    public void accept(TestBean testBean) throws Exception {
                        sb.append(testBean.getNo());
                        tv_result.setText(sb.toString());

                    }
                });

                break;
            case R.id.groupBy:
                tv_code.setText("groupBy 不能用from Array" +
                        "Observable observableGroupBy = Observable.just(1,2,3,4,5,6,7,8,9,10)\n" +
                        "                        .groupBy(new Function<Integer,Boolean>(){\n" +
                        "                            @Override\n" +
                        "                            public Boolean apply(Integer integer) throws Exception {\n" +
                        "                                return integer>4;\n" +
                        "                            }\n" +
                        "                        });\n" +
                        "                observableGroupBy.subscribe(new Consumer() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "                        tv_result.setText(o.toString());\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");

                Observable observableGroupBy = Observable.just(1,2,3,4,5,6,7,8,9,10)
                        .groupBy(new Function<Integer,Boolean>(){
                            @Override
                            public Boolean apply(Integer integer) throws Exception {
                                return integer>4;
                            }
                        });
                observableGroupBy.subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        tv_result.setText(o.toString());

                    }
                });
               
                break;
        }

    }
}
