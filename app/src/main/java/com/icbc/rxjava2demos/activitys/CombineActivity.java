package com.icbc.rxjava2demos.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icbc.rxjava2demos.R;
import com.icbc.rxjava2demos.bean.TestBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class CombineActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_code;
    TextView tv_result;

    Button zip;
    Button merge;
    Button combinelatest;
    Button join;
    Button startwith;
    Button rxswitch;

    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);
        tv_code = findViewById(R.id.tv_code);
        tv_result = findViewById(R.id.tv_result);
        zip = findViewById(R.id.zip);
        merge = findViewById(R.id.merge);
        combinelatest = findViewById(R.id.combinelatest);
        join = findViewById(R.id.join);
        startwith = findViewById(R.id.startwith);
        rxswitch = findViewById(R.id.rxswitch);

        sb = new StringBuilder();
        zip.setOnClickListener(this);
        merge.setOnClickListener(this);
        combinelatest.setOnClickListener(this);
        join.setOnClickListener(this);
        startwith.setOnClickListener(this);
        rxswitch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zip:
                tv_code.setText("合并两个不同的observable 当发射的数量不同的时候  取最短的数量（zip最多可以合并9个参数最  可可以合并 array）" +
                        "Observable observable1 = Observable.create(new ObservableOnSubscribe() {\n" +
                        "                    @Override\n" +
                        "                    public void subscribe(ObservableEmitter e) throws Exception {\n" +
                        "                        int [] numbers = {1,2,3,4};\n" +
                        "                        for (int i = 0 ; i<numbers.length ; i++){\n" +
                        "                            e.onNext(numbers[i]);\n" +
                        "                        }\n" +
                        "\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                Observable observable2 = Observable.just(\"A\",\"B\",\"C\",\"D\",\"E\");\n" +
                        "                Observable observableZip = Observable.zip(observable1, observable2, new BiFunction<Integer,String ,TestBean>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public TestBean apply(Integer integer, String s) throws Exception {\n" +
                        "                        TestBean testBean = new TestBean();\n" +
                        "                        testBean.setAge(integer);\n" +
                        "                        testBean.setName(s);\n" +
                        "                        return testBean;\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                observableZip.subscribe(new Consumer<TestBean>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void accept(TestBean testBean) throws Exception {\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        sb.append(\"age \"+testBean.getAge());\n" +
                        "                        sb.append(\"name \"+testBean.getName());\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");
                Observable observable1 = Observable.create(new ObservableOnSubscribe() {
                    @Override
                    public void subscribe(ObservableEmitter e) throws Exception {
                        int[] numbers = {1, 2, 3, 4};
                        for (int i = 0; i < numbers.length; i++) {
                            e.onNext(numbers[i]);
                        }

                    }
                });
                Observable observable2 = Observable.just("A", "B", "C", "D", "E");
                Observable observableZip = Observable.zip(observable1, observable2, new BiFunction<Integer, String, TestBean>() {

                    @Override
                    public TestBean apply(Integer integer, String s) throws Exception {
                        TestBean testBean = new TestBean();
                        testBean.setAge(integer);
                        testBean.setName(s);
                        return testBean;
                    }
                });
                observableZip.subscribe(new Consumer<TestBean>() {

                    @Override
                    public void accept(TestBean testBean) throws Exception {
                        sb.append("\n");
                        sb.append("age " + testBean.getAge());
                        sb.append("name " + testBean.getName());
                        tv_result.setText(sb.toString());
                    }
                });
                break;
            case R.id.merge:
                sb.setLength(0);
                tv_code.setText("类似zip" +
                        " Observable observablemerge1 = Observable.just(1,2,3);\n" +
                        "                Observable observablemerge2 = Observable.just(\"A\",\"B\",\"C\");\n" +
                        "                Observable observablemerge = Observable.merge(observablemerge1,observablemerge2);\n" +
                        "                observablemerge.subscribe(new Consumer() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "                        sb.append(o);\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable observablemerge1 = Observable.just(1, 2, 3);
                Observable observablemerge2 = Observable.just("A", "B", "C");
                Observable observablemerge = Observable.merge(observablemerge1, observablemerge2);
                observablemerge.subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        sb.append(o);
                        sb.append("\n");
                        tv_result.setText(sb.toString());

                    }
                });


                break;
            case R.id.combinelatest:
                sb.setLength(0);
                tv_code.setText("有必要解释一下 去的时候前一个 Observable的最后一个发射的元素 根后面一个observable 的每一个元素结合" +
                        " Observable observablecombine1 = Observable.create(new ObservableOnSubscribe() {\n" +
                        "                    @Override\n" +
                        "                    public void subscribe(ObservableEmitter e) throws Exception {\n" +
                        "                        int [] numbers = {1,2,3,4};\n" +
                        "                        for (int i = 0 ; i<numbers.length ; i++){\n" +
                        "                            e.onNext(numbers[i]);\n" +
                        "                        }\n" +
                        "\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                Observable observablecombine2 = Observable.just(\"A\",\"B\",\"C\",\"D\",\"E\");\n" +
                        "                Observable observableconmbine = Observable.combineLatest(observablecombine1, observablecombine2, new BiFunction<Integer,String ,TestBean>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public TestBean apply(Integer integer, String s) throws Exception {\n" +
                        "                        TestBean testBean = new TestBean();\n" +
                        "                        testBean.setAge(integer);\n" +
                        "                        testBean.setName(s);\n" +
                        "                        return testBean;\n" +
                        "                    }\n" +
                        "                });\n" +
                        "                observableconmbine.subscribe(new Consumer<TestBean>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void accept(TestBean testBean) throws Exception {\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        sb.append(\"age \"+testBean.getAge());\n" +
                        "                        sb.append(\"name \"+testBean.getName());\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");
                Observable observablecombine1 = Observable.create(new ObservableOnSubscribe() {
                    @Override
                    public void subscribe(ObservableEmitter e) throws Exception {
                        int[] numbers = {1, 2, 3, 4};
                        for (int i = 0; i < numbers.length; i++) {
                            e.onNext(numbers[i]);
                        }

                    }
                });
                Observable observablecombine2 = Observable.just("A", "B", "C", "D", "E");
                Observable observableconmbine = Observable.combineLatest(observablecombine2, observablecombine1, new BiFunction<String, Integer, TestBean>() {

                    @Override
                    public TestBean apply(String s, Integer integer) throws Exception {
                        TestBean testBean = new TestBean();
                        testBean.setAge(integer);
                        testBean.setName(s);
                        return testBean;
                    }
                });
                observableconmbine.subscribe(new Consumer<TestBean>() {

                    @Override
                    public void accept(TestBean testBean) throws Exception {
                        sb.append("\n");
                        sb.append("age " + testBean.getAge());
                        sb.append("name " + testBean.getName());
                        tv_result.setText(sb.toString());
                    }
                });
                break;
            case R.id.join:
                sb.setLength(0);
                tv_code.setText("有些小瑕疵  后期   不明白  join的两个参数为什么要 delay才能收到" +
                        "Observable observablejoin1 = Observable.just(1, 2, 3);\n" +
                        "                Observable observablejoin2 = Observable.just(\"A\", \"B\", \"C\", \"D\", \"E\");\n" +
                        "                Observable observableJoin = observablejoin1.join(observablejoin2,\n" +
                        "                        new Function<Integer, Observable<Integer>>() {\n" +
                        "                            @Override\n" +
                        "                            public Observable<Integer> apply(Integer integer) throws Exception {\n" +
                        "                                return Observable.just(integer).delay(3,TimeUnit.SECONDS);\n" +
                        "                            }\n" +
                        "                        },\n" +
                        "                        new Function<String, Observable<String>>() {\n" +
                        "                            @Override\n" +
                        "                            public Observable<String> apply(String s) throws Exception {\n" +
                        "                                return Observable.just(s).delay(3,TimeUnit.SECONDS);\n" +
                        "                            }\n" +
                        "                        },\n" +
                        "                        new BiFunction<Integer ,String ,TestBean>() {\n" +
                        "                            @Override\n" +
                        "                            public TestBean apply(Integer integer, String s) throws Exception {\n" +
                        "                                TestBean testBean = new TestBean();\n" +
                        "                                testBean.setName(s);\n" +
                        "                                testBean.setAge(integer);\n" +
                        "                                return testBean;\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                );\n" +
                        "                observableJoin.subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TestBean>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(TestBean o) throws Exception {\n" +
                        "                        sb.append(o.getAge() +\"|\"+ o.getName());\n" +
                        "                        sb.append(\"\\n\");\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");
                Observable observablejoin1 = Observable.just(1, 2, 3);
                Observable observablejoin2 = Observable.just("A", "B", "C", "D", "E");
                Observable observableJoin = observablejoin1.join(observablejoin2,
                        new Function<Integer, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> apply(Integer integer) throws Exception {
                                return Observable.just(integer).delay(3, TimeUnit.SECONDS);
                            }
                        },
                        new Function<String, Observable<String>>() {
                            @Override
                            public Observable<String> apply(String s) throws Exception {
                                return Observable.just(s).delay(3, TimeUnit.SECONDS);
                            }
                        },
                        new BiFunction<Integer, String, TestBean>() {
                            @Override
                            public TestBean apply(Integer integer, String s) throws Exception {
                                TestBean testBean = new TestBean();
                                testBean.setName(s);
                                testBean.setAge(integer);
                                return testBean;
                            }
                        }
                );
                observableJoin.subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TestBean>() {
                    @Override
                    public void accept(TestBean o) throws Exception {
                        sb.append(o.getAge() + "|" + o.getName());
                        sb.append("\n");
                        tv_result.setText(sb.toString());
                    }
                });
                break;
            case R.id.startwith:
                sb.setLength(0);
                tv_code.setText("在指定的数据前 添加指定的 一个或者多个数据" +
                        "Observable.just(1, 2, 3).startWith(4).subscribe(new Consumer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Integer integer) throws Exception {\n" +
                        "                        sb.append(integer);\n" +
                        "                        tv_result.setText(sb.toString());\n" +
                        "                    }\n" +
                        "                });");
                Observable.just(1, 2, 3).startWith(4).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        sb.append(integer);
                        tv_result.setText(sb.toString());
                    }
                });


                break;
            case R.id.rxswitch:
                break;
        }

    }
}
