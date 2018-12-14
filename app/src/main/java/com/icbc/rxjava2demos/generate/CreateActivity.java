package com.icbc.rxjava2demos.generate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icbc.rxjava2demos.R;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    Button create;
    Button create_disposable;
    Button just;
    Button difer;
    Button interval;
    Button timer;
    Button range;
    Button repeat;
    TextView tv_code;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        create = findViewById(R.id.create);
        create_disposable = findViewById(R.id.create_disposable);
        just = findViewById(R.id.just);
        difer = findViewById(R.id.difer);
        interval = findViewById(R.id.interval);
        timer = findViewById(R.id.timer);
        range = findViewById(R.id.range);
        repeat = findViewById(R.id.repeat);

        tv_code = findViewById(R.id.tv_code);
        tv_result = findViewById(R.id.tv_result);

        create.setOnClickListener(this);
        create_disposable.setOnClickListener(this);
        just.setOnClickListener(this);
        difer.setOnClickListener(this);
        interval.setOnClickListener(this);
        timer.setOnClickListener(this);
        range.setOnClickListener(this);
        repeat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                tv_code.setText("Observable.create(new ObservableOnSubscribe<String>() {\n" +
                        "                    @Override\n" +
                        "                    public void subscribe(ObservableEmitter e) throws Exception {\n" +
                        "                        e.onNext(\"test1\");\n" +
                        "\n" +
                        "\n" +
                        "                    }\n" +
                        "                }).subscribe(new Observer<String>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(String s) {\n" +
                        "                        Log.e(\"onnext\",s);\n" +
                        "\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter e) throws Exception {
                        e.onNext("test1");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String s) {
                        tv_result.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;

            case R.id.create_disposable:

                tv_code.setText("Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {\n" +
                        "                    @Override\n" +
                        "                    public void subscribe(ObservableEmitter<String> e) throws Exception {\n" +
                        "\n" +
                        "                    }\n" +
                        "                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(String s) throws Exception {\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("test2");

                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tv_result.setText(s);


                    }
                });
                break;
            case R.id.just:
                tv_code.setText("Observable.just(\"test1\",\"test2\",\"test3\").subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(String string) throws Exception {\n" +
                        "\n" +
                        "\n" +
                        "                    }\n" +
                        "                });");
                Observable.just("test1", "test2", "test3").subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        tv_result.setText(string);
                    }
                });
                break;
            case R.id.difer:
                tv_code.setText("只有订阅时候才会产生 observable" +
                        "Observable observable =  Observable.defer(new Callable<ObservableSource<String>>() {\n" +
                        "                    @Override\n" +
                        "                    public ObservableSource<String> call() throws Exception {\n" +
                        "                        return  Observable.just(\"sadffdsdfs\");\n" +
                        "                    }\n" +
                        "                });\n" +
                        "               observable.subscribe(new Consumer<String>() {\n" +
                        "                   @Override\n" +
                        "                   public void accept(String s) throws Exception {\n" +
                        "                       tv_result.setText(s);\n" +
                        "                   }\n" +
                        "               });");
                Observable observable = Observable.defer(new Callable<ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> call() throws Exception {
                        return Observable.just("sadffdsdfs");
                    }
                });
                observable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tv_result.setText(s);
                    }
                });


                break;
            case R.id.interval:
                Disposable disposable1 = Observable.interval(3,TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tv_result.setText("second  "+aLong);
                        if(aLong>10){
                        }

                    }
                });

                break;
            case R.id.timer:
//                Observable.timer();
                break;
            case R.id.range:
                break;
            case R.id.repeat:
                break;
        }
    }
}
