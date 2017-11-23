package com.example.phamh.devfest.Activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phamh.devfest.Object.RecruitMember;
import com.example.phamh.devfest.Object.SearchMatch;
import com.example.phamh.devfest.Object.Yard;
import com.example.phamh.devfest.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    private final String SEARCH_MATCH = "Tìm đội đấu";
    private final String RECRUIT_MEMBER = "Tuyển thành viên";

    //Post bài đăng tìm kiếm trận đấu
    private Spinner spnCategoryPost;
    private LinearLayout layoutPostSearchMatch, layoutPostRecruitMember, lnPermission;
    private EditText edtShowDayOfMatch, edtShowTimeStartMatch, edtShowTimeEndMatch;
    private TextView txtChooseDayOfMatch, txtChooseTimeStartMatch, txtChooseTimeEndMatch, txtFocus;
    private AutoCompleteTextView txtYard;
    private RadioButton rdb5vs5, rdb7vs7;
    private Button btnPostMatch, btnPostRecruit;

    //Post bài đăng tuyển thành viên
    private EditText edtNumberMember, edtDescription;

    private ArrayList<String> listCategoryPost;
    private ArrayAdapter<String> adapterCategoryPost;
    private ArrayList<String> listYard = new ArrayList<>();
    private ArrayAdapter<String> adapterYard;

    private Calendar today;

    private DatabaseReference databaseReference;
    //private SharedPreferences spfUserIsLeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        addControls();

        checkUserIsLeader(true);

        addEvents();
    }



    private void checkUserIsLeader(boolean test) {
//        spfUserIsLeader = this.getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
//        Boolean isLeader = spfUserIsLeader.getBoolean("leader", false);
//
//        if(isLeader){
//            lnPermission.setVisibility(View.GONE);
//        }

        if (test) {
            lnPermission.setVisibility(View.GONE);
        }
    }

    private void addEvents() {
        spnCategoryPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (listCategoryPost.get(i).equals(SEARCH_MATCH)) {
                    layoutPostSearchMatch.setVisibility(View.VISIBLE);
                    layoutPostRecruitMember.setVisibility(View.GONE);

                } else if (listCategoryPost.get(i).equals(RECRUIT_MEMBER)) {
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

        btnPostMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postSearchMatch();
            }
        });

        btnPostRecruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRecruitMember();
            }
        });

    }

    private void addControls() {

        //set giá trị mặc định
        today = Calendar.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        txtFocus = findViewById(R.id.txtFocus);
        txtFocus.requestFocus();

        //Spinner và select layout
        spnCategoryPost = findViewById(R.id.spnCategoryPost);
        layoutPostSearchMatch = findViewById(R.id.layoutPostSearchMatch);
        layoutPostRecruitMember = findViewById(R.id.layoutPostRecruitMember);
        lnPermission = findViewById(R.id.lnPermission);// nếu là leader sẽ ẩn layout này đi

        //set adapter spinner
        listCategoryPost = new ArrayList<>();
        listCategoryPost.add(SEARCH_MATCH);
        listCategoryPost.add(RECRUIT_MEMBER);
        adapterCategoryPost = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listCategoryPost);
        spnCategoryPost.setAdapter(adapterCategoryPost);

        /*          POST BÀI THI ĐẤU            */
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

        //chọn loại sân
        txtYard = findViewById(R.id.txtYard);
        listYard = new ArrayList<>();
        adapterYard = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listYard);

        //duyệt toàn bộ sân bóng để nhắc autocomplete
        databaseReference.child("FootballYard").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Yard yard = dataSnapshot.getValue(Yard.class);
                listYard.add(yard.getTenSan());
                adapterYard.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        txtYard.setAdapter(adapterYard);

        /*          POST BÀI TÌM THÀNH VIÊN           */
        //số lượng thành viên và ghi chú
        edtNumberMember = findViewById(R.id.edtNumberMember);
        edtDescription = findViewById(R.id.edtDescription);

        //hoàn thành bài đăng
        btnPostMatch = findViewById(R.id.btnPostMatch);
        btnPostRecruit = findViewById(R.id.btnPostRecruit);

    }

    //hàm đât giá trị giờ trận đấu
    private void setTimeOfMatch(final boolean isEnd) {
        int hour = today.get(Calendar.HOUR_OF_DAY);
        int minute = today.get(Calendar.MINUTE);

        TimePickerDialog timeDialog = new TimePickerDialog(PostActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                if (i1 == 0)
                    time += 0;
                if (isEnd) {
                    edtShowTimeEndMatch.setText(time);
                } else {
                    edtShowTimeStartMatch.setText(time);
                }
            }
        }, hour, minute, true);
        timeDialog.show();
    }

    //hàm đât giá trị ngày cho trận đấu
    private void setDateOfMatch() {
        int day = today.get(Calendar.DATE);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        DatePickerDialog dateDialog = new DatePickerDialog(PostActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtShowDayOfMatch.setText(i2 + "/" + (i1 + 1) + "/" + i);
            }
        }, year, month, day);
        dateDialog.show();
    }

    //hàm lấy ngày giờ hẽ thống để làm id bài đăng
    private String getDateTimeSystem() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate + "-" + System.currentTimeMillis();
    }

    //post bài đăng tìm đội thi đấu
    private void postSearchMatch() {
        if (edtShowDayOfMatch.getText().toString().equals("")
                || edtShowTimeStartMatch.getText().toString().equals("")
                || edtShowTimeEndMatch.getText().toString().equals("")
                || txtYard.getText().toString().equals("")) {
            Toast.makeText(PostActivity.this, "Bạn chưa điền đấy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            String ID = getDateTimeSystem();
            DatabaseReference referenceAddSearchMatch = FirebaseDatabase.getInstance().getReference();
            SearchMatch searchMatch = new SearchMatch();
            searchMatch.setID(ID);

            //3 trường này nữa mình lấy từ ShareReference
            searchMatch.setIDTeam("");
            searchMatch.setNameOfTeam("");
            searchMatch.setUrlAvatar("");

            searchMatch.setDate(edtShowDayOfMatch.getText().toString());
            searchMatch.setTime(edtShowTimeStartMatch.getText().toString() + "-" + edtShowTimeEndMatch.getText().toString());
            searchMatch.setNameOfYard(txtYard.getText().toString());

            if (rdb5vs5.isChecked())
                searchMatch.setType(rdb5vs5.getText().toString());
            else if (rdb7vs7.isChecked())
                searchMatch.setType(rdb7vs7.getText().toString());

            searchMatch.setX(0);
            searchMatch.setY(0);

            referenceAddSearchMatch.child("SEARCH_MATCH").child(ID).setValue(searchMatch).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    edtShowDayOfMatch.setText("");
                    edtShowTimeStartMatch.setText("");
                    edtShowTimeEndMatch.setText("");
                    txtYard.setText("");
                    rdb5vs5.setChecked(true);
                    Toast.makeText(PostActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //post bài đăng tuyển thành viên
    private void postRecruitMember() {

        if (edtNumberMember.getText().toString().equals("")
                || edtDescription.getText().toString().equals("")) {
            Toast.makeText(PostActivity.this, "Bạn chưa điền đấy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            String ID = getDateTimeSystem();
            DatabaseReference referenceAddRecruitMember = FirebaseDatabase.getInstance().getReference();
            RecruitMember recruitMember = new RecruitMember();
            recruitMember.setID(ID);

            //3 trường này nữa mình lấy từ ShareReference
            recruitMember.setIDTeam("");
            recruitMember.setNameOfTeam("");
            recruitMember.setUrlAvatar("");

            recruitMember.setNumber(edtNumberMember.getText().toString());
            recruitMember.setDescription(edtDescription.getText().toString());

            recruitMember.setX(0);
            recruitMember.setY(0);
            referenceAddRecruitMember.child("RECRUIT_MEMBER").child(ID).setValue(recruitMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    edtNumberMember.setText("");
                    edtDescription.setText("");
                    Toast.makeText(PostActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
