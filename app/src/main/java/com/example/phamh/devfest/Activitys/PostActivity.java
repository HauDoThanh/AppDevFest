package com.example.phamh.devfest.Activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.phamh.devfest.R;

import java.util.ArrayList;
import java.util.Calendar;

public class PostActivity extends AppCompatActivity {

    //Post bài đăng tìm kiếm trận đấu
    private Spinner spnCategoryPost;
    private LinearLayout layoutPostSearchMatch, layoutPostRecruitMember;
    private EditText edtShowDayOfMatch, edtShowTimeStartMatch, edtShowTimeEndMatch;
    private TextView txtChooseDayOfMatch, txtChooseTimeStartMatch, txtChooseTimeEndMatch;
    private RadioButton rdb5vs5, rdb7vs7;

    private final String SEARCH_MATCH = "Tìm đội đấu";
    private final String RECRUIT_MEMBER = "Tuyển thành viên";

    private ArrayList<String> listCategoryPost;
    private ArrayAdapter<String> adapterCategoryPost;

    private Calendar today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        addControls();

        addEvents();
    }

    private void addEvents() {
        spnCategoryPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(listCategoryPost.get(i).equals(SEARCH_MATCH)){
                    layoutPostSearchMatch.setVisibility(View.VISIBLE);
                    layoutPostRecruitMember.setVisibility(View.GONE);

                }else if(listCategoryPost.get(i).equals(RECRUIT_MEMBER)){
                    layoutPostSearchMatch.setVisibility(View.GONE);
                    layoutPostRecruitMember.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtChooseDayOfMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateOfMatch();
            }
        });

        txtChooseTimeStartMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimeOfMatch(false);
            }
        });

        txtChooseTimeEndMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimeOfMatch(true);
            }
        });
    }

    private void addControls() {
        //Spinner và select layout
        spnCategoryPost = findViewById(R.id.spnCategoryPost);
        layoutPostSearchMatch = findViewById(R.id.layoutPostSearchMatch);
        layoutPostRecruitMember = findViewById(R.id.layoutPostRecruitMember);

        listCategoryPost = new ArrayList<>();
        listCategoryPost.add(SEARCH_MATCH);
        listCategoryPost.add(RECRUIT_MEMBER);

        adapterCategoryPost = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listCategoryPost);
        spnCategoryPost.setAdapter(adapterCategoryPost);

        //chọn ngày
        edtShowDayOfMatch = findViewById(R.id.edtShowDayOfMatch);
        txtChooseDayOfMatch = findViewById(R.id.txtChooseDayOfMatch);

        //chọn giờ bắt đầu và kết thúc
        edtShowTimeStartMatch = findViewById(R.id.edtShowTimeStartMatch);
        txtChooseTimeStartMatch = findViewById(R.id.txtChooseTimeStartMatch);
        edtShowTimeEndMatch = findViewById(R.id.edtShowTimeEndMatch);
        txtChooseTimeEndMatch = findViewById(R.id.txtChooseTimeEndMatch);

        //chọn loại hình thi đấu
        rdb5vs5 = findViewById(R.id.rdb5vs5);
        rdb7vs7 = findViewById(R.id.rdb7vs7);
        rdb5vs5.setChecked(true);

        //set giá trị
        today = Calendar.getInstance();
    }

    private void setTimeOfMatch(final boolean isEnd){
        int hour = today.get(Calendar.HOUR_OF_DAY);
        int minute = today.get(Calendar.MINUTE);



        TimePickerDialog timeDialog = new TimePickerDialog(PostActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if(isEnd){
                    edtShowTimeEndMatch.setText(i + ":" + i1);
                }else{
                    edtShowTimeStartMatch.setText(i + ":" + i1);
                }
            }
        }, hour, minute, true);
        timeDialog.show();
    }

    public void setDateOfMatch(){
        int day = today.get(Calendar.DATE);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        DatePickerDialog dateDialog = new DatePickerDialog(PostActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtShowDayOfMatch.setText(i2 + "/" + (i1+1) + "/" + i);
            }
        }, year,month,day);
        dateDialog.show();
    }
}
