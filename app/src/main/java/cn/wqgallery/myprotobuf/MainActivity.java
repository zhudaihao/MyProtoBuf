package cn.wqgallery.myprotobuf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tutorial.AddressBookProtos;
import com.example.tutorial.MsgProtos;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

import cn.wqgallery.myprotobuf.json.JsonTest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zdh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProtoBuf();


    }

    private void setProtoBuf() {
        //使用build模式 构建者模式
        MsgProtos.Msg.newBuilder()
                .setText("测试")
                .build();


        //初始化对象
        AddressBookProtos.Person.PhoneNumber.Builder builder = AddressBookProtos.Person.PhoneNumber
                .newBuilder().setNumber("119");
        AddressBookProtos.Person.Builder person = AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("张三")
                .addPhones(builder);


        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(person)
                .addPeople(person)
                .build();


        long time = System.currentTimeMillis();

        /**
         * 序列化对象
         */
        byte[] bytes = addressBook.toByteArray();
        Log.e(TAG, "---------protobuy序列化耗时" + (System.currentTimeMillis() - time));
        Log.e(TAG, "---------protobuy序列化后数据大小" + bytes.length);


        /**
         * 反序列化对象
         * bytes为序列化的对象
         * AddressBookProtos.AddressBook.parseFrom(bytes)
         */
        time = System.currentTimeMillis();
        try {
            AddressBookProtos.AddressBook addressBook1 = AddressBookProtos.AddressBook.parseFrom(bytes);

            //获取反序列化的数据
            List<AddressBookProtos.Person> peopleList = addressBook1.getPeopleList();
            AddressBookProtos.Person person1 = peopleList.get(0);
            person1.getPhones(1);
            Log.e("zdh", "---------protobuy反序列化耗时" + (System.currentTimeMillis() - time));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


        //对比json 序列化和 反序列化
        JsonTest.fastJson();
        JsonTest.gson();


    }


}
